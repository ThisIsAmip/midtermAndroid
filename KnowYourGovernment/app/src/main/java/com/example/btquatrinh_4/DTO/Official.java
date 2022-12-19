package com.example.btquatrinh_4.DTO;

import java.io.Serializable;
import java.util.ArrayList;

public class Official implements Serializable {

    private String title;
    private String name;
    private String address;
    private String party;
    private String phones ;
    private String urls;
    private String emails;
    private String photoURL;
    private ArrayList<Social> socials;

    public Official() {
    }

    public Official(String title, String name, String address, String party, String phones, String urls, String emails, String photoURL, ArrayList<Social> socials) {
        this.title = title;
        this.name = name;
        this.address = address;
        this.party = party;
        this.phones = phones;
        this.urls = urls;
        this.emails = emails;
        this.photoURL = photoURL;
        this.socials = socials;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getParty() {
        return party;
    }

    public void setParty(String party) {
        this.party = party;
    }

    public String getPhones() {
        return phones;
    }

    public void setPhones(String phones) {
        this.phones = phones;
    }

    public String getUrls() {
        return urls;
    }

    public void setUrls(String urls) {
        this.urls = urls;
    }

    public String getEmails() {
        return emails;
    }

    public void setEmails(String emails) {
        this.emails = emails;
    }

    public String getPhotoURL() {
        return photoURL;
    }

    public void setPhotoURL(String photoURL) {
        this.photoURL = photoURL;
    }

    public ArrayList<Social> getChannels() {
        return socials;
    }

    public void setChannels(ArrayList<Social> socials) {
        this.socials = socials;
    }
}
