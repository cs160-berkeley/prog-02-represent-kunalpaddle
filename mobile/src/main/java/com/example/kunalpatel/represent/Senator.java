package com.example.kunalpatel.represent;

import android.graphics.Bitmap;
import android.media.Image;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by KunalPatel on 2/27/16.
 */
public class Senator extends Representative {

    private ArrayList<String> bills;
    private ArrayList<String> committees;
    private String name;
    private String state;



    private String party;
    private Date termEndDate;
    private Image picture;


    public Senator(String name, String party, String state, Date termEndDate, Bitmap picture) {
        super(name, state, termEndDate, picture);
    }

    public Senator(String name, String state, String party) {
        super(name,state);
        this.name = name;
        this.party = party;
    }

    public void addBill (String bill) {
        this.bills.add(bill);

    }

    public ArrayList<String> getBills() {
        return  bills;

    }

    public ArrayList<String> getCommittees() {
        return committees;
    }

    public void addCommittee(String committee) {
        this.committees.add(committee);
    }

    public String getParty() {
        return party;
    }

}
