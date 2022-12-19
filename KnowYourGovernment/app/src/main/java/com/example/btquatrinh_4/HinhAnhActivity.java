package com.example.btquatrinh_4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class HinhAnhActivity extends AppCompatActivity {
    private TextView tvLocation, tvTitle, tvName;
    private ImageView imgPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hinhanh);
        addControls();
        addEvents();
    }

    private void addControls() {
        tvLocation = findViewById(R.id.tvLocation_Photo);
        tvTitle = findViewById(R.id.tvTitle_Photo);
        tvName = findViewById(R.id.tvName_Photo);
        imgPhoto = findViewById(R.id.imgPhoto);
    }

    private void addEvents() {
        getInfor();
    }

    public void getInfor() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            String loc = bundle.getString("location");
            String title = bundle.getString("title");
            String name = bundle.getString("name");
            String photoUrl = bundle.getString("url");
            tvLocation.setText(loc);
            tvTitle.setText(title);
            tvName.setText(name);
            loadProfilePicture(photoUrl);
        }
    }

    public void loadProfilePicture(String URL){
        if(URL.equals(""))
        {
            Picasso.get()
                    .load(R.drawable.no_image)
                    .resize(400,570)
                    .into(imgPhoto);
        }
        else
        {
            Picasso.get()
                    .load(URL)
                    .placeholder(R.drawable.loading)
                    .error(R.drawable.error)
                    .resize(400,600)
                    .centerCrop()
                    .into(imgPhoto);
        }
    }
}