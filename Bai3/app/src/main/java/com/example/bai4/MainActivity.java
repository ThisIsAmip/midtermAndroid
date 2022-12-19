package com.example.bai4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.Serializable;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements Serializable {
    ListView lvItems;
    ArrayList<String> listOfItems;
    String RSSURL;
    final static public String TEXT_KEY = "TEXT";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listOfItems = new ArrayList<>();
        listOfItems.add("Proteins");
        listOfItems.add("Amino Acids");
        listOfItems.add("Grains and Starches");
        listOfItems.add("Fibers and Legumes");
        listOfItems.add("Vitamins");
        listOfItems.add("Minerals");
        listOfItems.add("Nutraceuticals");
        listOfItems.add("Processing finctional ingredients");
        listOfItems.add("Fats and Oils");
        listOfItems.add("Preservatives");
        listOfItems.add("Ingredients and subtances to advoid");
        lvItems = (ListView) findViewById(R.id.lvItems);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, listOfItems);
        lvItems.setAdapter(adapter);

        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this, ViewRSS.class); //Tạo intent để bắt đầu một Activity khác

                switch (i){
                    case 0:
                    {
                        RSSURL = "https://www.petfoodindustry.com/rss/topic/292-proteins";
                        intent.putExtra(TEXT_KEY, RSSURL); //Dùng phương thức Serializable để truyền biến vô Activity khác
                        startActivity(intent);
                        break;
                    }
                    case 1:
                    {
                        RSSURL = "https://www.petfoodindustry.com/rss/topic/293-amino-acids";
                        intent.putExtra(TEXT_KEY, RSSURL); //Dùng phương thức Serializable để truyền biến vô Activity khác
                        startActivity(intent);
                        break;
                    }
                    case 2:
                    {
                        RSSURL = "https://www.petfoodindustry.com/rss/topic/294-grains-and-starches";
                        intent.putExtra(TEXT_KEY, RSSURL); //Dùng phương thức Serializable để truyền biến vô Activity khác
                        startActivity(intent);
                        break;
                    }
                    case 3:
                    {
                        RSSURL = "https://www.petfoodindustry.com/rss/topic/295-fibers-and-legumes";
                        intent.putExtra(TEXT_KEY, RSSURL); //Dùng phương thức Serializable để truyền biến vô Activity khác
                        startActivity(intent);
                        break;
                    }
                    case 4:
                    {
                        RSSURL = "https://www.petfoodindustry.com/rss/topic/296-vitamins";
                        intent.putExtra(TEXT_KEY, RSSURL); //Dùng phương thức Serializable để truyền biến vô Activity khác
                        startActivity(intent);
                        break;
                    }
                    case 5:
                    {
                        RSSURL = "https://www.petfoodindustry.com/rss/topic/297-minerals";
                        intent.putExtra(TEXT_KEY, RSSURL); //Dùng phương thức Serializable để truyền biến vô Activity khác
                        startActivity(intent);
                        break;
                    }
                    case 6:
                    {
                        RSSURL = "https://www.petfoodindustry.com/rss/topic/298-nutraceuticals";
                        intent.putExtra(TEXT_KEY, RSSURL); //Dùng phương thức Serializable để truyền biến vô Activity khác
                        startActivity(intent);
                        break;
                    }
                    case 7:
                    {
                        RSSURL = "https://www.petfoodindustry.com/rss/topic/299-processing-functional-ingredients";
                        intent.putExtra(TEXT_KEY, RSSURL); //Dùng phương thức Serializable để truyền biến vô Activity khác
                        startActivity(intent);
                        break;
                    }
                    case 8:
                    {
                        RSSURL = "https://www.petfoodindustry.com/rss/topic/300-fats-and-oils";
                        intent.putExtra(TEXT_KEY, RSSURL); //Dùng phương thức Serializable để truyền biến vô Activity khác
                        startActivity(intent);
                        break;
                    }
                    case 9:
                    {
                        RSSURL = "https://www.petfoodindustry.com/rss/topic/301-preservatives";
                        intent.putExtra(TEXT_KEY, RSSURL); //Dùng phương thức Serializable để truyền biến vô Activity khác
                        startActivity(intent);
                        break;
                    }
                    case 10:
                    {
                        RSSURL = "https://www.petfoodindustry.com/rss/topic/302-ingredients-and-substances-to-avoid";
                        intent.putExtra(TEXT_KEY, RSSURL); //Dùng phương thức Serializable để truyền biến vô Activity khác
                        startActivity(intent);
                        break;
                    }
                    default:
                    {
                        break;
                    }
                }


            }
        });
    }
}