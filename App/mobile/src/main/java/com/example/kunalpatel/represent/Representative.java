package com.example.kunalpatel.represent;

import android.graphics.Bitmap;

import java.io.Serializable;

/**
 * Created by KunalPatel on 2/27/16.

 */
public class Representative implements Serializable {

    private String name;
    private String state, stateAbbreviation;
    private String tweet;
    private String party;
    private int district;
    private String imageUrl;
    private String twitter_id, website, email;


    private String termEndDate;
    private Bitmap picture;
    private String bioguide;

    public String getTermEndDate() {
        return termEndDate;
    }

    private String chamber;

    public String getTwitterId() {
        return twitterId;
    }

    public void setTwitterId(String twitterId) {
        this.twitterId = twitterId;
    }

    private String twitterId;


    public static final String PARTY_REPUBLICAN = "Republican";
    public static final String PARTY_DEMOCRAT = "Democrat";
    public static final String PARTY_INDEPENDENT = "Independent";



    public Representative() {

    }


    public Representative(String name, String state, String termEndDate, Bitmap picture) {
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

    public Bitmap getPicture() {
        return picture;
    }

    public void setPicture(Bitmap image) {
        this.picture = image;
    }
    public void setName(String name) {
        this.name = name;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setParty(String party) {
        if (party.equals("D")) {

            this.party = PARTY_DEMOCRAT;
        }
        else if (party.equals("R")){
            this.party = PARTY_REPUBLICAN;
        }
        else {
            this.party = PARTY_INDEPENDENT;
        }
    }

    public String getChamber() {
        return chamber;
    }

    public void setChamber(String chamber) {
        this.chamber = chamber;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }
    public int getDistrict() {
        return district;
    }

    public void setDistrict(int district) {
        this.district = district;
    }

    public String getTwitter_id() {
        return twitter_id;
    }

    public void setTwitter_id(String twitter_id) {
        this.twitter_id = twitter_id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBioguide() {
        return bioguide;
    }

    public String getStateAbbreviation() {
        return stateAbbreviation;
    }

    public void setStateAbbreviation(String stateAbbreviation) {
        this.stateAbbreviation = stateAbbreviation;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setBioguide(String bioguide) {
        this.bioguide = bioguide;
    }

    public void setTermEndDate(String termEndDate) {
        this.termEndDate = termEndDate;
    }

    @Override
    public String toString() {
        return "Representative{" +
                "name='" + name + '\'' +
                ", state='" + state + '\'' +
                ", stateAbbreviation='" + stateAbbreviation + '\'' +
                ", tweet='" + tweet + '\'' +
                ", party='" + party + '\'' +
                ", district=" + district +
                ", imageUrl='" + imageUrl + '\'' +
                ", twitter_id='" + twitter_id + '\'' +
                ", website='" + website + '\'' +
                ", email='" + email + '\'' +
                ", termEndDate='" + termEndDate + '\'' +
                ", picture=" + picture +
                ", bioguide='" + bioguide + '\'' +
                ", chamber='" + chamber + '\'' +
                ", twitterId='" + twitterId + '\'' +
                '}';
    }
}