package com.example.kunalpatel.represent;

import android.graphics.Bitmap;
import android.media.Image;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by KunalPatel on 2/27/16.

 */
public class Representative {

    private String name;
    private String state;
    private String tweet;

    private String party;
    private Date termEndDate;
    private Bitmap picture;


    public static final String PARTY_REPUBLICAN = "Republican";
    public static final String PARTY_DEMOCRAT = "Democrat";



    public Representative(String name, String state, Date termEndDate, Bitmap picture) {
        this.name = name;
        this.state = state;
        this.termEndDate = termEndDate;
        this.picture = picture;
    }

    public Representative(String name, String state) {
        this.name = name;
        this.state = state;

    }

    public Representative (String name, String state, String party) {
        this.name = name;
        this.state = state;
        this.party = party;
    }


    public String getParty() {
        return party;
    }

    public String getName() {
        return name;
    }

    public String getState() {
        return state;

    }

    public String getTweet() {
        return tweet;
    }

    public void setTweet(String tweet) {
        this.tweet = tweet;
    }
    public Date getTermEndDate() {
        return termEndDate;
    }

    public Bitmap getPicture() {
        return picture;
    }

    public void setPicture(Bitmap image) {
        this.picture = image;
    }

    public void setTermEndDate(Date date) {
        this.termEndDate = date;
    }
}