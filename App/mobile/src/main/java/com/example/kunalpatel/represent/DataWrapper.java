package com.example.kunalpatel.represent;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by KunalPatel on 3/9/16.
 */
public class DataWrapper implements Serializable {
    static final long serialVersionUID = 42L;

    private ArrayList<Representative> representatives;
    private Representative representative;

    public String getObamaVote() {
        return obamaVote;
    }

    public void setObamaVote(String obamaVote) {
        this.obamaVote = obamaVote;
    }

    private String obamaVote;
    private String romneyVote;
    private String county;

    public ArrayList<byte[]> pictures = new ArrayList<byte[]>();

    public DataWrapper(ArrayList<Representative> data) {
        this.representatives = data;
    }

    public ArrayList<Representative> getRepresentatives() {
        return this.representatives;
    }

    public DataWrapper(Representative rep) {
        this.representative = rep;
    }

    public Representative getRepresentative() {
        return representative;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String count) {
        this.county = count;
    }

    public void updateVoteUsingJSON(JSONObject jsonObject){
        romneyVote = jsonObject.optString("romney");
        obamaVote = jsonObject.optString("obama");
    }

    public String getRomneyVote() {
        return romneyVote;
    }

    public void setRomneyVote(String romneyVote) {
        this.romneyVote = romneyVote;
    }
}