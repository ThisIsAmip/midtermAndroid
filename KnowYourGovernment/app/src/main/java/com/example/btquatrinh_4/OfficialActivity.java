package com.example.btquatrinh_4;

import static android.graphics.Color.rgb;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.btquatrinh_4.DTO.Social;
import com.example.btquatrinh_4.DTO.Official;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class OfficialActivity extends AppCompatActivity {
    private ImageView imghinhAnh;
    private TextView tvLocation, tvTitle, tvName, tvParty, tvAddress, tvPhone, tvEmail, tvWebsite;
    private ImageButton ibtnTwitter, ibtnGoogle, ibtnFacebook, ibtnYoutube;
    private LinearLayout rootLayout;
    List<String> listItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_official_avticity);
        addControls();
        addEvents();
    }

    private void addEvents() {
        getInforOfficial();
    }

    private void addControls() {
        imghinhAnh = findViewById(R.id.imgHinhAnh);
        tvLocation = findViewById(R.id.tvLocation);
        tvTitle = findViewById(R.id.tvTitle);
        tvName = findViewById(R.id.tvName);
        tvParty = findViewById(R.id.tvParty);
        tvAddress = findViewById(R.id.tvAddress);
        tvPhone = findViewById(R.id.tvPhone);
        tvEmail = findViewById(R.id.tvEmail);
        tvWebsite = findViewById(R.id.tvWebsite);
        ibtnTwitter = findViewById(R.id.ibtnTwitter);
        ibtnGoogle = findViewById(R.id.ibtnGoogle);
        ibtnFacebook = findViewById(R.id.ibtnFacebook);
        ibtnYoutube = findViewById(R.id.ibtnYoutube);
        rootLayout = findViewById(R.id.rootLayout);
    }

    public void getInforOfficial() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            String loc = bundle.getString("location");
            Official official = (Official) bundle.getSerializable("official");
            String photoURL = official.getPhotoURL().trim();
            loadProfilePicture(photoURL.trim());
            String title = (!official.getTitle().equals("") ? official.getTitle() : "No Data Provided");
            String name = (!official.getName().equals("") ? official.getName() : "No Data Provided");
            String party = (!official.getParty().equals("") ? official.getParty() : "No Data Provided");
            String location = (!loc.equals("") ? loc : "No Data Provided");
            String address = (!official.getAddress().equals("") ? official.getAddress() : "No Data Provided");
            String phone = (!official.getPhones().equals("") ? official.getPhones() : "No Data Provided");
            String email = (!official.getEmails().equals("") ? official.getEmails() : "No Data Provided");
            String urls = (!official.getUrls().equals("") ? official.getUrls() : "No Data Provided");
            tvTitle.setText(title);
            tvName.setText(name);
            tvParty.setText(party);
            tvLocation.setText(location);
            tvAddress.setText(address);
            if(!address.equals("No Data Provided")) {
                tvAddress.setPaintFlags(tvAddress.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
            }
            tvAddress.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String s = tvAddress.getText().toString();
                    if(s.equals("No Data Provided")) {
                        NoDataProvided();
                    }
                    else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(OfficialActivity.this);
                        builder.setTitle("CHOOSE THE ADDRESS YOU WANT TO FIND");
                        String[] split = s.split("\n");
                        builder.setItems(split, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                linkMap(split[i]);
                            }
                        });
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }
                }
            });
            tvPhone.setText(phone);
            if(!phone.equals("No Data Provided")) {
                tvPhone.setPaintFlags(tvPhone.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
            }
            tvPhone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String s = tvPhone.getText().toString();
                    if(s.equals("No Data Provided")) {
                        NoDataProvided();
                    }
                    else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(OfficialActivity.this);
                        builder.setTitle("CHOOSE THE NUMBER YOU WANT TO CALL");
                        String[] split = s.split("\n");
                        builder.setItems(split, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent(Intent.ACTION_DIAL);
                                String temp = split[i].replace("-", "");
                                intent.setData(Uri.parse("tel:" + temp));
                                startActivity(intent);
                            }
                        });
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }
                }
            });
            tvEmail.setText(email);
            if(!email.equals("No Data Provided")) {
                tvEmail.setPaintFlags(tvEmail.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
            }
            tvEmail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String s = tvEmail.getText().toString();
                    if(s.equals("No Data Provided")) {
                        NoDataProvided();
                    }
                    else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(OfficialActivity.this);
                        builder.setTitle("CHOOSE THE EMAIL YOU WANT TO SEND A MESSESS");
                        String[] split = s.split("\n");
                        builder.setItems(split, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("mailto:" + split[i]));
                                intent.putExtra(Intent.EXTRA_SUBJECT, "");
                                intent.putExtra(Intent.EXTRA_TEXT, "");
                                startActivity(intent);
                            }
                        });
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }
                }
            });
            tvWebsite.setText(urls);
            if(!urls.equals("No Data Provided")) {
                tvWebsite.setPaintFlags(tvWebsite.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
            }
            tvWebsite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String s = tvWebsite.getText().toString();
                    if(s.equals("No Data Provided")) {
                        NoDataProvided();
                    }
                    else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(OfficialActivity.this);
                        builder.setTitle("CHOOSE THE LINK YOU WANT TO SEARCH");
                        String[] split = s.split("\n");
                        builder.setItems(split, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                linkWeb(split[i]);
                            }
                        });
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }
                }
            });
            ArrayList<Social> socials;
            socials = official.getChannels();
            ibtnFacebook.setVisibility(View.GONE);
            ibtnTwitter.setVisibility(View.GONE);
            ibtnGoogle.setVisibility(View.GONE);
            ibtnYoutube.setVisibility(View.GONE);

//            Social s = new Social();
//            s.setId("watch?v=H5_Hu1ju1QM");
//            s.setType("Youtube");
//            socials.add(s);

//            Social s = new Social();
//            s.setId("?");
//            s.setType("GooglePlus");
//            socials.add(s);

            for (Social c : socials) {
                if(c.getType().equalsIgnoreCase("FaceBook")){
                    ibtnFacebook.setVisibility(View.VISIBLE);
                    ibtnFacebook.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            linkFaceBook(c.getId());
                        }
                    });
                }

                if(c.getType().equalsIgnoreCase("Twitter")){
                    ibtnTwitter.setVisibility(View.VISIBLE);
                    ibtnTwitter.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            linkTwitter(c.getId());
                        }
                    });
                }

                if(c.getType().equalsIgnoreCase("GooglePlus")){
                    ibtnGoogle.setVisibility(View.VISIBLE);
                    ibtnGoogle.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            linkGooglePlus(c.getId());
                        }
                    });
                }

                if(c.getType().equalsIgnoreCase("Youtube")){
                    ibtnYoutube.setVisibility(View.VISIBLE);
                    ibtnYoutube.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            linkYoutube(c.getId());
                        }
                    });
                }
            }
            if (party.contains("Democratic Party"))
                rootLayout.setBackgroundColor(rgb(0,0,255));
            if (party.contains("Republican Party"))
                rootLayout.setBackgroundColor(rgb (255,0,0));
            if (party.contains("Nonpartisan") ||party.contains("Unknown") )
                rootLayout.setBackgroundColor(rgb (0,0,0));
            imghinhAnh.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    openPhotoActivity(loc,title,name,photoURL);
                }
            });
        }
    }

    private void NoDataProvided() {
        AlertDialog dlgAlert  = new AlertDialog.Builder(OfficialActivity.this).create();
        dlgAlert.setTitle("ERROR!");
        dlgAlert.setMessage("NO DATA TO MAKE AN ACTION");
        dlgAlert.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        dlgAlert.show();
    }

    public void linkFaceBook(String id){
        String FACEBOOK_URL = "https://www.facebook.com/" + id;
        String urlToUse;
        PackageManager packageManager = getPackageManager();
        try {
            int versionCode = packageManager.getPackageInfo("com.facebook.katana",0).versionCode;
            if (versionCode >= 3002850) {
                urlToUse = "fb://facewebmodal/f?href=" + FACEBOOK_URL;
            }
            else {
                urlToUse = "fb://page/" + id;
            }
        }
        catch ( PackageManager.NameNotFoundException e)
        {
            urlToUse = FACEBOOK_URL;
        }
        Intent facebookIntent = new Intent(Intent.ACTION_VIEW);
        facebookIntent.setData(Uri.parse(urlToUse));
        startActivity(facebookIntent);
    }

    public void linkTwitter(String id){
         Intent intent;
         try {
             getPackageManager().getPackageInfo("com.twitter.android", 0);
             intent = new Intent(Intent.ACTION_VIEW, Uri.parse("twitter://user?screen_name=" + id));
             intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
         } catch (Exception e) {
             intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/" + id));
         }
         startActivity(intent);
    }

    public void linkGooglePlus(String id){
        Intent intent;
        try {
            intent = new Intent(Intent.ACTION_VIEW);
            intent.setClassName("com.google.android.apps.plus", "com.google.android.apps.plus.phone.UrlGatewayActivity");
            intent.putExtra("customAppUri", id);
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://plus.google.com/" + id)));
        }
    }

    public void linkYoutube(String id){
        Intent intent;
        try {
            intent = new Intent(Intent.ACTION_VIEW);
            intent.setPackage("com.google.android.youtube");
            intent.setData(Uri.parse("https://www.youtube.com/" + id));
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/" + id)));
        }
    }

    public void linkWeb(String link) {
        Intent intent;
        try {
            intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(link));
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void linkMap(String link) {
        Intent intent;
        try {
            intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("https://www.google.com/maps/place/"+link));
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }

    void loadProfilePicture(String URL)
    {
        String[] temp = URL.split("https");
        if(temp.length == 1) {
            URL = URL.replace("http", "https");
        }
        if(URL.equals(""))
        {
            imghinhAnh.setImageResource(R.drawable.no_image);
        }
        else
        {
            Picasso.get()
                    .load(URL)
                    .placeholder(R.drawable.loading)
                    .error(R.drawable.error)
                    .resize(350,350)
                    .centerCrop()
                    .into(imghinhAnh);
        }
    }

    public void openPhotoActivity(String location, String title, String name, String url ){
        Intent intent = new Intent(OfficialActivity.this, HinhAnhActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("location", location);
        bundle.putString("title", title);
        bundle.putString("name", name);
        bundle.putString("url", url);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}