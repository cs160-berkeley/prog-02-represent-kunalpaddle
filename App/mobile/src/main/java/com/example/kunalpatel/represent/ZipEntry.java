package com.example.kunalpatel.represent;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import org.apache.commons.lang3.SerializationUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class ZipEntry extends AppCompatActivity {

    ImageButton nextButton;
    EditText zipField;
    ArrayList<Representative> reps;
    private ZipEntry identity = this;
    private Context context;
    private FallbackLocationTracker locationTracker;
    private Location currentLocation;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zip_entry);


        nextButton = (ImageButton) findViewById(R.id.forwardButton);
        zipField = (EditText) findViewById(R.id.zipEditText);

        this.context = getApplicationContext();

        setListeners();

    }

    public void sendMessage() {
        PhoneToWatchService ph = new PhoneToWatchService("/START_ACTIVITY", "hello");

        System.out.println(ph.getMessage());
        System.out.println(ph.getPath());
        ph.sendMessage(getApplicationContext());
    }

    private void setListeners() {
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (zipField.length() == 5) {

                    new SunlightApi(context, new SunlightApi.SunlightRepresentativeListResponder() {
                        @Override
                        public void processFinish(ArrayList<Representative> output) {
                            System.out.println("processFinish called.");
                            for (Representative r : output) {
                                System.out.println(r);
                            }
                            reps = output;
                            openMyRepresentatives();

                        }
                    }).getRepresentativeUsingZip(Integer.parseInt(zipField.getText().toString()));
                } else {
                    zipField.setText("0");
                }
                ;
            }
        });
    }

    public void openMyRepresentatives() {
        new ReverseGeocoding(context, new ReverseGeocoding.CountyReceiver() {
            @Override
            public void receiveCounty(String count) {
                System.out.println("RECEIVED COUNTY:" +count);
                openMyRepresentatives(count);
            }
        }).reverseGeocode(zipField.getText().toString());
    }

    public void openMyRepresentatives(String county) {
        Intent openMyRepresentatives= new Intent(this, MyRepresentatives.class);

        DataWrapper payload = new DataWrapper(reps);
        String stateAbbreviation = reps.get(0).getStateAbbreviation();
        payload.setCounty(county + ", " + stateAbbreviation);
        payload.updateVoteUsingJSON(getElectionResultsFromCounty(county,stateAbbreviation));
        startWatch(payload);

        openMyRepresentatives.putExtra("REPRESENTATIVES", payload);
        startActivity(openMyRepresentatives);
    }

    public void openZipEntry() {
        Intent openZipEntry= new Intent(this, ZipEntry.class);
        startActivity(openZipEntry);
    }

    public void startWatch(DataWrapper payload) {

        byte[] serializedPayload = SerializationUtils.serialize(payload);
        PhoneToWatchService ph = new PhoneToWatchService("/START_ACTIVITY", serializedPayload);
        System.out.println("sending payload and starting watch mainactivity");
        ph.sendMessage(context);
    }


    public JSONObject getElectionResultsFromCounty(String county, String stateAbbreviation ) {
        final String key = county+", "+stateAbbreviation;
        System.out.println("Searching for: " + key);

        try {
            JSONObject allCounties = new JSONObject(loadJSONFromAsset());
            JSONObject results = allCounties.getJSONObject(key);
            return results;

        } catch (JSONException e1) {
            e1.printStackTrace();
        }

        return null;
    }

    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getAssets().open("electioncounty2012.json");

            int size = is.available();

            byte[] buffer = new byte[size];

            is.read(buffer);

            is.close();

            json = new String(buffer, "UTF-8");

        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

}