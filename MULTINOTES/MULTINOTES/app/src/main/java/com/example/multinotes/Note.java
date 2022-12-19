package com.example.multinotes;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.content.Intent;

public class Note {

    public static final String ITEM_SEP = System.getProperty("line.separator");

    public final static String Caption = "caption";
    public final static String Content = "content";
    public final static String DATECREATE = "datecreate";
    public final static String DATEREMIND = "dateremind";
    public final static String Image = "image";

    public final static SimpleDateFormat FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);

    private String mCaption = new String();
    private String mContent = new String();
    private Date mDateCreate = new Date();
    private Date mDateRemind = new Date();
    private String mImage = new String();

    Note(String caption, String content, Date datecreate, Date dateremind, String image) {
        this.mCaption = caption;
        this.mContent = content;
        this.mDateCreate = datecreate;
        this.mDateRemind = dateremind;
        this.mImage = image;
    }

    Note(Intent intent) {
        mCaption = intent.getStringExtra(Note.Caption);
        mContent = intent.getStringExtra(Note.Content);
        try {
            mDateCreate = Note.FORMAT.parse(intent.getStringExtra(Note.DATECREATE));
        } catch (ParseException e) {
            mDateCreate = new Date();
        }
        try {
            mDateRemind = Note.FORMAT.parse(intent.getStringExtra(Note.DATEREMIND));
        } catch (ParseException e) {
            mDateRemind = new Date();
        }
        mImage = intent.getStringExtra(Note.Image);
    }

    public String getCaption() {
        return mCaption;
    }

    public void setCaption(String caption) {
        mCaption = caption;
    }

    public String getContent() {
        return mContent;
    }

    public void setContent(String content) {
        mContent = content;
    }

    public Date getDateCreate() {
        return mDateCreate;
    }

    public void setDateCreate(Date datecreate) {
        mDateCreate = datecreate;
    }

    public Date getDateRemind() {
        return mDateRemind;
    }

    public void setDateRemind(Date dateremind) {
        mDateRemind = dateremind;
    }

    public String getImage() {
        return mImage;
    }

    public void setImage(String image) {
        mImage = image;
    }

    public static void packageIntent(Intent intent, String caption, String content, String datecreate, String dateremind, String image) {
        intent.putExtra(Note.Caption, caption);
        intent.putExtra(Note.Content, content);
        intent.putExtra(Note.DATECREATE, datecreate);
        intent.putExtra(Note.DATEREMIND, dateremind);
        intent.putExtra(Note.Image, image);
    }

    public String toString() {
        return mCaption + ITEM_SEP +
                mContent + ITEM_SEP +
                FORMAT.format(mDateCreate) + ITEM_SEP +
                FORMAT.format(mDateRemind) + ITEM_SEP +
                mImage;
    }
}
