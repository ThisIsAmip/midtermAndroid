package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    SeekBar bar;
    TextView block1, block2, block3, block4;

    SeekBar.OnSeekBarChangeListener changeColorListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        selectLayout(R.id.linear);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.layout_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        selectLayout(item.getItemId());
        return true;
    }

    private void openDialog() {
        AlertDialog dialog = new AlertDialog.Builder(this).create();
        String messenge = "HELLO Nhóm 16 ";
        dialog.setMessage(messenge);
        dialog.show();
    }

    private void initAllBlock() {
        block1 = findViewById(R.id.block1);
        block2 = findViewById(R.id.block2);
        block3 = findViewById(R.id.block3);
        block4 = findViewById(R.id.block4);
    }

    private void selectLayout(int id) {
        changeColorListener = new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                        block1.setBackgroundColor(Color.rgb(255-i,i*2,0));
                        block2.setBackgroundColor(Color.rgb(255-i,255-i,0));
                        block3.setBackgroundColor(Color.rgb(i*2,255-i,0));
                        block4.setBackgroundColor(Color.rgb(i*2,0,255-i));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        };

        switch (id) {
            case R.id.linear:
                setContentView(R.layout.linear_layout);
                initAllBlock();

                bar = findViewById(R.id.seekBar3);
                bar.setOnSeekBarChangeListener(changeColorListener);
                break;
            case R.id.relative:
                setContentView(R.layout.relative_layout);
                initAllBlock();

                bar = findViewById(R.id.seekBar2);
                bar.setOnSeekBarChangeListener(changeColorListener);
                break;
            case R.id.table:
                setContentView(R.layout.table_layout);
                initAllBlock();

                bar = findViewById(R.id.seekBar);
                bar.setOnSeekBarChangeListener(changeColorListener);
                break;
            case R.id.more:
                openDialog();
                break;
        }
    }
}