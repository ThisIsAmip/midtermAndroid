package com.example.multinotes;

import java.util.Calendar;
import java.util.Date;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class AddActivity extends AppCompatActivity {

    private static String timeRemindString;
    private static String dateRemindString;

    private static String timeCreateString;
    private static String dateCreateString;

    private static TextView dateRemindView;
    private static TextView timeRemindView;

    private Date mDate;

    private EditText mCaptionText;
    private EditText mContentText;

    private String Priority = new String();
    private ImageView imageView;

    public static int[] imgPriority = {R.drawable.verygood, R.drawable.good, R.drawable.normal, R.drawable.bad, R.drawable.worst};
    public static String[] tvPriority = {"verygood", "good", "normal", "bad", "worst"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_activity_layout);

        mCaptionText = findViewById(R.id.etxtCaption);
        mContentText = findViewById(R.id.etxtContent);

        dateRemindView = findViewById(R.id.txtDateRemind);
        timeRemindView = findViewById(R.id.txtTimeRemind);

        imageView = findViewById(R.id.myImgView);

        setDefaultDateTime();

        final Button imagePickerButton = findViewById(R.id.btnInsertImage);
        imagePickerButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(AddActivity.this);
                builder.setTitle("CHOOSE THE PRIORITY OF THE NOTE");
                builder.setItems(tvPriority, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        imageView.setImageResource(imgPriority[i]);
                        Priority = tvPriority[i];
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        final Button datePickerButton = findViewById(R.id.btnDatePicker);
        datePickerButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        final Button timePickerButton = (Button) findViewById(R.id.btnTimePicker);
        timePickerButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater mi = getMenuInflater();
        mi.inflate(R.menu.add_activity_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                setResult(RESULT_CANCELED);
                finish();
                return true;
            case R.id.mybuttonsave:
                String captionString = mCaptionText.getText().toString();
                String contentString = mContentText.getText().toString();
                String fullDateCreate = dateCreateString + " " + timeCreateString;
                String fullDateRemind = dateRemindString + " " + timeRemindString;
                String image = Priority;
                Intent data = new Intent();
                Note.packageIntent(data, captionString, contentString, fullDateCreate, fullDateRemind, image);
                setResult(RESULT_OK, data);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setDefaultDateTime() {
        mDate = new Date();
        mDate = new Date(mDate.getTime());

        Calendar c = Calendar.getInstance();
        c.setTime(mDate);

        setDateString(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));

        dateCreateString = dateRemindString;

        dateRemindView.setText(dateRemindString);

        setTimeString(c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), c.get(Calendar.MILLISECOND));

        timeCreateString = timeRemindString;

        timeRemindView.setText(timeRemindString);
    }

    private static void setDateString(int year, int monthOfYear, int dayOfMonth) {
        monthOfYear++;
        String mon = "" + monthOfYear;
        String day = "" + dayOfMonth;

        if (monthOfYear < 10)
            mon = "0" + monthOfYear;
        if (dayOfMonth < 10)
            day = "0" + dayOfMonth;

        dateRemindString = year + "-" + mon + "-" + day;
    }

    private static void setTimeString(int hourOfDay, int minute, int mili) {
        String hour = "" + hourOfDay;
        String min = "" + minute;

        if (hourOfDay < 10)
            hour = "0" + hourOfDay;
        if (minute < 10)
            min = "0" + minute;

        timeRemindString = hour + ":" + min + ":00";
    }

    private String getToDoCaption() {
        return mCaptionText.getText().toString();
    }

    public static class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            setDateString(year, monthOfYear, dayOfMonth);
            dateRemindView.setText(dateRemindString);
        }
    }

    public static class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);
            return new TimePickerDialog(getActivity(), this, hour, minute, true);
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            setTimeString(hourOfDay, minute, 0);
            timeRemindView.setText(timeRemindString);
        }
    }

    private void showDatePickerDialog() {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getFragmentManager(), "datePicker");
    }

    private void showTimePickerDialog() {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getFragmentManager(), "timePicker");
    }
}
