package com.example.kunalpatel.represent;

import android.graphics.Bitmap;

/**
 * Created by KunalPatel on 2/27/16.
 */
public class HouseRep extends Representative {

    private  int district;
    private String name, state, party;

    public HouseRep(String name, String party, String state, String termEndDate, int district, Bitmap picture) {
        super(name, state, termEndDate, picture);
    }

    public HouseRep (String name, String state, String party, int district) {
        super(name,state,party);
        this.district = district;

    }

    public int getDistrict() {
        return district;
    }


}
