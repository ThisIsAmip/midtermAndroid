package com.example.multinotes;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private static final String FILE_NAME = "MainActivityData4.txt";
    private static final int requestCodeAdd = 0;
    private static final int requestCodeAction = 1;
    private ListView mListView;
    private int SelectedItem = -1;
    private NoteListAdapter mAdapter;
    private Calendar[] calendars = new Calendar[255];
    private int numberofnotesremind = 0;
    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity_layout);
        createNotifycationChannel();
        mListView = findViewById(R.id.lvNote);
        mAdapter = new NoteListAdapter(getApplicationContext());
        loadItems();
        if(mAdapter.getCount() != 0) {
            mListView.setAdapter(mAdapter);
        }
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                SelectedItem = i;
                view.setBackgroundColor(0);
            }
        });
    }

    private void createNotifycationChannel() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "RemindChannel";
            String description = "Channel for Alarm Manager";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel("androidchannel", name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater mi = getMenuInflater();
        mi.inflate(R.menu.main_activity_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            case R.id.mybuttonadd:
                //startActivity(new Intent(MainActivity.this, AddActivity.class));
                startActivityForResult(new Intent(MainActivity.this, AddActivity.class), requestCodeAdd);
                return true;
            case R.id.mybuttoninfo:
                if(SelectedItem == -1) {
                    AlertDialog dlgAlert  = new AlertDialog.Builder(this).create();
                    dlgAlert.setTitle("ERROR!");
                    dlgAlert.setMessage("You haven't select any note");
                    dlgAlert.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                    dlgAlert.show();
                }
                else {
                    Intent mIntent = new Intent(MainActivity.this, ActionNoteActivity.class);
                    mIntent.putExtra("SELECTED", SelectedItem);
                    //startActivity(new Intent(MainActivity.this, ActionNoteActivity.class));
                    MainActivity.this.startActivityForResult(mIntent, requestCodeAction);
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == requestCodeAdd) {
            if (resultCode == RESULT_OK) {
                Note mNote = new Note(data);
                mAdapter.add(mNote);
            }
        }
        if (requestCode == requestCodeAction) {
            if (resultCode == RESULT_OK) {
                Note mNote = new Note(data);
                int pos = (int) data.getIntExtra("POS", -1);
                int action = (int) data.getIntExtra("ACTION", -1);
                if(action == 0) {
                    mAdapter.change(pos, mNote);
                }
                else {
                    mAdapter.del(pos);
                }
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        if(mAdapter.getCount() != 0) {
            mListView.setAdapter(mAdapter);
        }
        SelectedItem = -1;
        saveItems();
        loadItems();
        setAlarm();
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveItems();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    private void setAlarm() {
        for(int i=0; i<numberofnotesremind; i++) {
            alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

            Intent intent = new Intent(MainActivity.this, Receiver.class);

            pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, intent, 0);

            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendars[i].getTimeInMillis(), pendingIntent);
            Log.i("MAIN", "SETTED");
        }
    }

    private String TransMonth(String month) {
        String remonth = "";
        if(month.equalsIgnoreCase("Jan")) {
            remonth = "01";
        }
        if(month.equalsIgnoreCase("Feb")) {
            remonth = "02";
        }
        if(month.equalsIgnoreCase("Mar")) {
            remonth = "03";
        }
        if(month.equalsIgnoreCase("Apr")) {
            remonth = "04";
        }
        if(month.equalsIgnoreCase("May")) {
            remonth = "05";
        }
        if(month.equalsIgnoreCase("Jun")) {
            remonth = "06";
        }
        if(month.equalsIgnoreCase("Jul")) {
            remonth = "07";
        }
        if(month.equalsIgnoreCase("Aug")) {
            remonth = "08";
        }
        if(month.equalsIgnoreCase("Sep")) {
            remonth = "09";
        }
        if(month.equalsIgnoreCase("Oct")) {
            remonth = "10";
        }
        if(month.equalsIgnoreCase("Nov")) {
            remonth = "11";
        }
        if(month.equalsIgnoreCase("Dec")) {
            remonth = "12";
        }
        return remonth;
    }

    private void loadItems() {
        numberofnotesremind = 0;
        mAdapter.clear();
        Calendar now = Calendar.getInstance();
        String stimenow = now.getTime().toString();
        String[] ssplittimenow = stimenow.split(" ");
        ssplittimenow[1] = TransMonth(ssplittimenow[1]);
        BufferedReader reader = null;
        try {
            FileInputStream fis = openFileInput(FILE_NAME);
            reader = new BufferedReader(new InputStreamReader(fis));
            String caption;
            String content;
            Date dateCreate;
            Date dateRemind;
            String image;
            while (null != (caption = reader.readLine())) {
                content = reader.readLine();
                dateCreate = Note.FORMAT.parse(reader.readLine());
                String storeDateRemind = reader.readLine();
                dateRemind = Note.FORMAT.parse(storeDateRemind);
                image = reader.readLine();
                mAdapter.add(new Note(caption, content, dateCreate, dateRemind, image));
                String[] ssplitstore = storeDateRemind.split(" ");
                String[] ssplitdate = ssplitstore[0].split("-");
                String[] ssplittime = ssplitstore[1].split(":");
                if(ssplitdate[0].equalsIgnoreCase(ssplittimenow[5]) && ssplitdate[1].equalsIgnoreCase(ssplittimenow[1]) && ssplitdate[2].equalsIgnoreCase(ssplittimenow[2])) {
                    calendars[numberofnotesremind] = Calendar.getInstance();
                    calendars[numberofnotesremind].set(Calendar.HOUR_OF_DAY, Integer.parseInt(ssplittime[0]));
                    calendars[numberofnotesremind].set(Calendar.MINUTE, Integer.parseInt(ssplittime[1]));
                    calendars[numberofnotesremind].set(Calendar.SECOND, 0);
                    calendars[numberofnotesremind].set(Calendar.MILLISECOND, 0);
                    numberofnotesremind++;
                }
            }
        } catch (ParseException | IOException e) {
            e.printStackTrace();
        } finally {
            if (null != reader) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void saveItems() {
        PrintWriter writer = null;
        try {
            FileOutputStream fos = openFileOutput(FILE_NAME, MODE_PRIVATE);
            writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(fos)));
            for (int idx = 0; idx < mAdapter.getCount(); idx++) {
                writer.println(mAdapter.getItem(idx));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != writer) {
                writer.close();
            }
        }
    }
}