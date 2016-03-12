package com.example.kunalpatel.represent;

import android.content.Context;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * Created by KunalPatel on 3/8/16.
 */
public class ReverseGeocoding {

    private Context context;
    private CountyReceiver countyReceiver;
    public ReverseGeocoding(Context context) {
        this.context = context;
    }

    public interface CountyReceiver {

        public void receiveCounty(String county);

    }
    public ReverseGeocoding(Context context, CountyReceiver delegate){
        this.countyReceiver = delegate;
        this.context = context;
    }

    public void reverseGeocode(String zip) {

        RequestParams params = new RequestParams();
        params.put("address", ""+zip+", USA");
        params.put("key", context.getResources().getString(R.string.representServerKey));

        AsyncHttpClient client = new AsyncHttpClient();

        client.get("https://maps.googleapis.com/maps/api/geocode/json?", params, new JsonHttpResponseHandler() {

            @Override
            public void onStart() {
                // called before request is started
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                System.out.println("_________-----------GOT JSON RESPONSE--------___________");
//                System.out.println(response.toString());
                // If the response is JSONObject instead of expected JSONArray
                try {
                    System.out.println(response);
                    onSuccess(statusCode, headers, response.getJSONArray("results").getJSONObject(0).getJSONArray("address_components"));
                } catch (JSONException ex) {
                    System.err.println("EXCEPTION: JSON Exception when trying to fetch results ");
                    ex.printStackTrace();
                }

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray address_components) {
                //TODO: Hardcode 3?

                try {
                    for (int i = 0; i < address_components.length(); i++) {
                        JSONObject component = address_components.getJSONObject(i);

                        String type = component.optString("types");

                        if (type.contains("administrative_area_level_2")){
                            updateCounty(component.optString("short_name"));
                        }

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
                System.out.println("Returning new reps.");
                super.onFinish();
            }
        });
    }

    private void updateCounty(String short_name) {
        this.countyReceiver.receiveCounty(short_name);
    }

    public void reverseGeocode(double latitude, double longitude) {

        RequestParams params = new RequestParams();
        params.put("latlng", latitude+","+longitude);
        params.put("key", context.getResources().getString(R.string.representServerKey));



        AsyncHttpClient client = new AsyncHttpClient();

        client.get("https://maps.googleapis.com/maps/api/geocode/json?", params, new JsonHttpResponseHandler() {

            @Override
            public void onStart() {
                // called before request is started
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                System.out.println("_________-----------GOT JSON RESPONSE--------___________");
                System.out.println(response.toString());
                // If the response is JSONObject instead of expected JSONArray
                try {
                    onSuccess(statusCode, headers, response.getJSONArray("results").getJSONObject(0).getJSONArray("address_components"));
                } catch (JSONException ex) {
                    System.err.println("EXCEPTION: JSON Exception when trying to fetch results ");
                    ex.printStackTrace();
                }

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray address_components) {
                //TODO: Hardcode 3?

                try {
                    for (int i = 0; i < address_components.length(); i++) {
                        JSONObject component = address_components.getJSONObject(i);

                        String type = component.optString("types");

                        if (type.contains("administrative_area_level_2")) {
                            updateCounty(component.optString("short_name"));
                        }

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
                System.out.println("Returning new reps.");
                super.onFinish();
            }
        });
    }


}