package com.example.bai4;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import androidx.appcompat.app.AppCompatActivity;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class ViewRSS extends AppCompatActivity implements Serializable {

    int PositionOfTheList;
    String RSSURL;
    ListView lvRSS;
    ArrayList<String> titles; //Lưu các title trong XSS
    ArrayList<String> links; //Lưu các link của title trong XSS
    ArrayList<String> description; //Lưu các link của title trong XSS
    ArrayList<String> images;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_rss);
        lvRSS = findViewById(R.id.lvRSS);

        if(getIntent() != null && getIntent().getExtras() != null && getIntent().hasExtra(MainActivity.TEXT_KEY)) //Xét coi các biến bị null không
        {
            RSSURL = (String)getIntent().getSerializableExtra(MainActivity.TEXT_KEY); //Dùng phương thức Serializable để truyền dữ liệu từ MainAtivity vô
        }
        lvRSS = (ListView) findViewById(R.id.lvRSS);
        titles = new ArrayList<>();
        links = new ArrayList<>();
        description = new ArrayList<>();
        images = new ArrayList<>();
        lvRSS.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Dialog dialog = new Dialog(ViewRSS.this);
                dialog.setContentView(R.layout.dialog_custom);

                TextView tvTen = dialog.findViewById(R.id.textTitle);
                TextView tvNoiDung = dialog.findViewById(R.id.textDescription);
                ImageView imgView = dialog.findViewById(R.id.imageLink);
                Button btnCancel = dialog.findViewById(R.id.buttonCancel);
                Button btnMore = dialog.findViewById(R.id.buttonMore);

                tvTen.setText(titles.get(position));
                tvNoiDung.setText(description.get(position));
                Picasso.get().load(images.get(position)).into(imgView);

                dialog.show();

                btnMore.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(links.get(position)));
                        startActivity(intent);
                    }
                });
                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
            }
        });
        new ProcessInBackgournd().execute();
    }

    public InputStream getInputStream(URL url) //Trả về một input stream ?
    {
        try {
            return url.openConnection().getInputStream();
        } catch (IOException e) {
            return null;
        }
    }

    public class ProcessInBackgournd extends AsyncTask<Integer, Void, Exception> {
        ProgressDialog progressDialog = new ProgressDialog(ViewRSS.this);
        Exception exception = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog.setMessage("Loading RSS feed...");
            progressDialog.show();
        }

        @Override //Trả về lỗi trong quá trình đọc và ghi dữ liệu từ XSS
        protected Exception doInBackground(Integer... integers) {

            try
            {
                URL url = new URL(RSSURL); //Khởi tạo biến url và tạo đường dẫn URL
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance(); //Khởi tạo biến PullParser để tạo xml pull parser để láy dữ liệu từ xml
                factory.setNamespaceAware(false); //Cho biết biến parser này có hỗ trợ XML namespace hay không - False là ko
                XmlPullParser xpp = factory.newPullParser(); //Tạo biến xpp được tùy chỉnh bởi factory
                xpp.setInput(getInputStream(url), "UTF_8"); //Encoding của cái document mình muốn đọc là UTF_8
                boolean insideItem = false; //Để biết khi nào mình đang ở trong tag Item để bắt đầu đọc dữ liệu
                int eventType = xpp.getEventType(); //Khi đang lấy dữ liệu, cái này sẽ lấy event hiện tại: start tag < , end tag />
                while (eventType != XmlPullParser.END_DOCUMENT) //Vòng while để bắt đầu lấy dữ liệu
                {
                    if (eventType == XmlPullParser.START_TAG) //Nếu là tag bắt đầu e.g: <body>, <title> ,...
                    {
                        if (xpp.getName().equalsIgnoreCase("item")) //Nếu là tag item
                        {
                            insideItem = true;
                        }
                        else if (xpp.getName().equalsIgnoreCase("title"))//Nếu là lag title
                        {
                            if (insideItem) //Để chắc chắn rằng ta đang trong tag item của 1 item nào đó đang cần lấy dữ liệu
                            {
                                titles.add(xpp.nextText()); //Trả về cái test trong cái tag đang xét vd: <title>Hello World!</title>
                            }
                        }
                        else if (xpp.getName().equalsIgnoreCase("description")) //Nếu là tag description
                        {
                            if (insideItem)
                            {
                                String s = xpp.nextText();
                                s = s.replace("<p>","");
                                s = s.replace("</p>","");
                                description.add(s);

                            }
                        }
                        else if (xpp.getName().equalsIgnoreCase("link")) //Nếu là tag link
                        {
                            if (insideItem)
                            {
                                links.add(xpp.nextText());
                            }
                        }
                        else if (xpp.getName().equalsIgnoreCase("media:content")) //Nếu là tag media:content
                        {
                            if (insideItem)
                            {
                                images.add(xpp.getAttributeValue(null, "url"));
                            }
                        }

                    }
                    else if (eventType == XmlPullParser.END_TAG && xpp.getName().equalsIgnoreCase("item")) //Nếu là tag đóng và nó là tag item (</item>)
                    {
                        insideItem = false;
                    }
                    eventType = xpp.next(); //Nếu không phải tag cần tìm (item) thì tiếp tục xét kế tiếp
                }
            }
            catch (MalformedURLException e) //Kiểm tra url truyền vào có bị gì hay không
            {
                exception = e;
            }
            catch (XmlPullParserException e) //Kiểm tra trong quá trình extract dữ liệu có bị gì hay ko
            {
                exception = e;
            }
            catch (IOException e) //Input output exception
            {
                exception = e;
            }

            return exception;
        }

        @Override
        protected void onPostExecute(Exception s) {
            super.onPostExecute(s);
            ArrayAdapter<String> adapter = new ArrayAdapter<>(ViewRSS.this, android.R.layout.simple_list_item_1, titles);
            lvRSS.setAdapter(adapter);
            progressDialog.dismiss();
        }
    }
}