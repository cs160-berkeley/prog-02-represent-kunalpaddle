package com.example.kunalpatel.represent;

import android.content.Context;
import android.location.Location;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

/**
 * Created by KunalPatel on 3/9/16.
 */
public class SunlightApi {

    public interface SunlightRepresentativeListResponder {
        void processFinish(ArrayList<Representative> output);
    }

    /*
    
        REPRESENTATIVE LIST CODE 
     */
    private Context context;
    public ArrayList<Representative> representatives = new ArrayList<Representative>();
    public ArrayList<String> bills = new ArrayList<String>();
    public ArrayList<String> committees = new ArrayList<String>();
    private SunlightRepresentativeListResponder listResponderDelegate;
    private SunlightBillsResponder billDelegate;
    private SunlightCommitteesResponder committeeDelegate;


    public SunlightApi(Context context, SunlightRepresentativeListResponder listResponderDelegate) {
        this.context = context;
        this.listResponderDelegate = listResponderDelegate;
    }

    public SunlightApi(Context context, SunlightBillsResponder billDelegate) {
        this.context = context;
        this.billDelegate = billDelegate;
    }

    public SunlightApi(Context context, SunlightCommitteesResponder delegate) {
        this.context = context;
        this.committeeDelegate = delegate;
    }


    public void parseJSONObjectToRepresentative(JSONObject obj) {

        String name = obj.optString("first_name" ) +" " + obj.optString("last_name");
        String bioguide = obj.optString("bioguide_id");
        String chamber = obj.optString("chamber");
        String party = obj.optString("party");
        String email = obj.optString("oc_email");
        String twitterId = obj.optString("twitter_id");
        String website = obj.optString("website");
        String stateName = obj.optString("state_name");
        String stateAbbreviation = obj.optString("state");
        String termEndDate = obj.optString("term_end");
        String districtString = obj.optString("district");
        String imageUrl = "https://theunitedstates.io/images/congress/225x275/"+bioguide+".jpg";

        //System.out.println("image url: "  + imageUrl);

        int district = -9999;
        if (districtString != null && !districtString.equals("") && !districtString.equals("null")) {
            district = Integer.parseInt(districtString) ;

        }


        Representative newRep = new Representative();

        newRep.setName(name);
        newRep.setBioguide(bioguide);
        newRep.setChamber(chamber);
        newRep.setParty(party);
        newRep.setEmail(email);
        newRep.setTwitter_id(twitterId);
        newRep.setWebsite(website);
        newRep.setState(stateName);
        newRep.setStateAbbreviation(stateAbbreviation);
        newRep.setTermEndDate(termEndDate);
        newRep.setDistrict(district);
        newRep.setImageUrl(imageUrl);
        this.representatives.add(newRep);
        if(this.representatives.size() == 3) {
            listResponderDelegate.processFinish(representatives);
        }
        //System.out.println(".........Created " + newRep.getName());
//        //System.out.println(newRep);

    }

    public void getRepresentativeUsingZip(int zip) {

        RequestParams params = new RequestParams();
        params.put("zip", zip);
        params.put("apikey", context.getResources().getString(R.string.sunlight_api_key));

        AsyncHttpClient client = new AsyncHttpClient();


        client.get("http://congress.api.sunlightfoundation.com/legislators/locate?", params, new JsonHttpResponseHandler() {

            @Override
            public void onStart() {
                // called before request is started
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // If the response is JSONObject instead of expected JSONArray
                //System.out.println("--------onSuccess called ---- ");

                //System.out.println("-----------GOT JSON RESPONSE--------");
                //System.out.println(response.toString());
                try {
                    onSuccess(statusCode, headers, response.getJSONArray("results"));
                } catch (JSONException ex) {
                    System.err.println("EXCEPTION: JSON Exception when trying to fetch results ");
                }

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray reps) {
                //System.out.println("--------onSuccess called ---- ");
                //TODO: Hardcode 3?

                try {
                    for (int i = 0; i < reps.length(); i++) {
                        parseJSONObjectToRepresentative(reps.getJSONObject(i));
                        //System.out.println(representatives.size());

                    }
                    //System.out.println("exited loop");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFinish() {
//                listResponderDelegate.processFinish(representatives);
                //System.out.println(representatives);
                //System.out.println("Returning new reps.");
                super.onFinish();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                System.err.println("ERROR: HTTP REQUEST FAILED.");
            }

            @Override
            public void onRetry(int retryNo) {
                // called when request is retried
            }
        });

        //System.out.println("Returning new reps from sunlight api!");
        for (Representative rep: this.representatives) {
            //System.out.println(rep.getName());
        }

    }

    public void  getRepresentativeUsingLocation(Location location) {
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();

        RequestParams params = new RequestParams();
        params.put("latitude", latitude);
        params.put("longitude", longitude);
        params.put("apikey", context.getResources().getString(R.string.sunlight_api_key));

        AsyncHttpClient client = new AsyncHttpClient();

        client.get("http://congress.api.sunlightfoundation.com/legislators/locate?", params, new JsonHttpResponseHandler() {

            @Override
            public void onStart() {
                // called before request is started
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                //System.out.println("_________-----------GOT JSON RESPONSE--------___________");
                //System.out.println(response.toString());
                // If the response is JSONObject instead of expected JSONArray
                try {
                    onSuccess(statusCode, headers, response.getJSONArray("results"));
                } catch (JSONException ex) {
                    System.err.println("EXCEPTION: JSON Exception when trying to fetch results ");
                }

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray reps) {
                //TODO: Hardcode 3?

                try {
                    for (int i = 0; i < reps.length(); i++) {
                        parseJSONObjectToRepresentative(reps.getJSONObject(i));
                        //System.out.println(representatives.size());

                    }
                    return;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                System.err.println("ERROR: HTTP REQUEST FAILED.");
            }


            @Override
            public void onRetry(int retryNo) {
                // called when request is retried
            }


            @Override
            public void onFinish() {
//                listResponderDelegate.processFinish(representatives);
                //System.out.println(representatives);
                //System.out.println("Returning new reps.");
                super.onFinish();
            }
        });
    }
    
    
    /*
    
        BILLS AND COMMITTEES CODE
     */

    public interface SunlightBillsResponder {
        void processFinish(ArrayList<String> bills);
    }


    public void getBills(String bioguide) {

        RequestParams params = new RequestParams();
        params.put("sponsor_id", bioguide);
        params.put("apikey", context.getResources().getString(R.string.sunlight_api_key));

        AsyncHttpClient client = new AsyncHttpClient();

        client.get("http://congress.api.sunlightfoundation.com/bills?", params, new JsonHttpResponseHandler() {

            @Override
            public void onStart() {
                // called before request is started
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                //System.out.println("_________-----------GOT JSON RESPONSE--------___________");
                //System.out.println(response.toString());
                // If the response is JSONObject instead of expected JSONArray
                try {
                    onSuccess(statusCode, headers, response.getJSONArray("results"));
                } catch (JSONException ex) {
                    System.err.println("EXCEPTION: JSON Exception when trying to fetch results ");
                }

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray bills) {
                //TODO: Hardcode 3?

                parseBills(bills);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                System.err.println("ERROR: HTTP REQUEST FAILED.");
            }


            @Override
            public void onRetry(int retryNo) {
                // called when request is retried
            }
        });
    }

    private void parseBills(JSONArray bills) {
        //System.out.println("PARSING BILLS");

        //TODO : limit 5 or put all?
        try {
            for (int i = 0; i < Math.min(bills.length(), 5); i++) {
                JSONObject bill = bills.getJSONObject(i);
                String date = bill.optString("introduced_on");
                String title = bill.optString("short_title");
                String appended = date + ": " + title;

                this.bills.add(appended);
            }
            billDelegate.processFinish(this.bills);
        }
            catch( JSONException e) {
                e.printStackTrace();
            }
    }

    public interface SunlightCommitteesResponder {

        void processFinish(ArrayList<String> committees);
     }

    public void getCommittees(String bioguide) {


        RequestParams params = new RequestParams();
        params.put("member_ids", bioguide);
        params.put("apikey", context.getResources().getString(R.string.sunlight_api_key));

        AsyncHttpClient client = new AsyncHttpClient();

        client.get("http://congress.api.sunlightfoundation.com/committees?", params, new JsonHttpResponseHandler() {

            @Override
            public void onStart() {
                // called before request is started
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                //System.out.println("_________-----------GOT JSON RESPONSE--------___________");
                //System.out.println(response.toString());
                // If the response is JSONObject instead of expected JSONArray
                try {
                    onSuccess(statusCode, headers, response.getJSONArray("results"));
                } catch (JSONException ex) {
                    System.err.println("EXCEPTION: JSON Exception when trying to fetch results ");
                }

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray committees) {
                //TODO: Hardcode 3?
                parseCommittees(committees);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                System.err.println("ERROR: HTTP REQUEST FAILED.");
            }


            @Override
            public void onRetry(int retryNo) {
                // called when request is retried
            }
        });
    }

    private void parseCommittees(JSONArray committees) {
        try {
            //System.out.println("PARSING COMMITTEES");
            for (int i = 0; i < Math.min(committees.length(), 5); i++){
                JSONObject committee = committees.getJSONObject(i);
                String name = committee.optString("name");
                this.committees.add(name);
            }
            this.committeeDelegate.processFinish(this.committees);
        }
        catch (JSONException e) {
            e.printStackTrace();
        }

    }

}

