package com.example.kunalpatel.represent;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.common.api.GoogleApiClient;

import org.apache.commons.lang3.SerializationUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class WelcomeScreen extends AppCompatActivity {


    private ArrayList<Representative> reps;

    Button useLocationButton, zipButton;

    private GoogleApiClient mApiClient;

    private Context context;

    private FallbackLocationTracker locationTracker;

    private WelcomeScreen identity = this;

    private Location currentLocation;

    private static final String START_ACTIVITY = "/start_activity";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);

        useLocationButton = (Button) findViewById(R.id.locationButton);
        zipButton = (Button) findViewById(R.id.zipButton);

        context = getApplicationContext();

        setListeners();

    }

    private void setListeners() {
        useLocationButton.setOnClickListener(new View.OnClickListener() {
                                                 @Override
                                                 public void onClick(View view) {
                                                     locationTracker = new FallbackLocationTracker(getApplicationContext());
                                                     locationTracker.start();

                                                     Location currentLocation;


                                                     if(locationTracker.hasLocation()){
                                                         currentLocation = locationTracker.getLocation();
                                                     }
                                                     else {
                                                         currentLocation = locationTracker.getPossiblyStaleLocation();
                                                     }

                                                     new SunlightApi(context, new SunlightApi.SunlightRepresentativeListResponder() {
                                                         @Override
                                                         public void processFinish(ArrayList<Representative> output) {

//                                                             System.out.println("Process finished");
                                                             reps = output;

                                                             openMyRepresentatives();
                                                         }
                                                     }).getRepresentativeUsingLocation(currentLocation);

                                                     locationTracker.stop();
//                reps = new SunlightApi(getApplicationContext()).getRepresentativeUsingLocation(currentLocation);
                                                 }
                                             }
        );

        zipButton.setOnClickListener(new View.OnClickListener() {
                                         @Override
                                         public void onClick(View view) {
//                 sendMessage();
                                             openZipEntry();
                                         }
                                     }
        );
    }

    @Override
    protected void onResume() {

        super.onResume();
        locationTracker = new FallbackLocationTracker(getApplicationContext());
        if (currentLocation == null ) {
            currentLocation = locationTracker.getLocation();
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mApiClient.disconnect();
    }
    public void openMyRepresentatives() {
        Location currentLocation = locationTracker.getLocation();

        new ReverseGeocoding(context, new ReverseGeocoding.CountyReceiver() {
            @Override
            public void receiveCounty(String count) {
                System.out.println("RECEIVED COUNTY:" +count);
                openMyRepresentatives(count);
            }
        }).reverseGeocode(currentLocation.getLatitude(), currentLocation.getLongitude());

        locationTracker.stop();

    }

    public void openMyRepresentatives(String county) {
        Intent openMyRepresentatives= new Intent(this, MyRepresentatives.class);

        DataWrapper payload = new DataWrapper(reps);
        String stateAbbreviation = reps.get(0).getStateAbbreviation();
        payload.setCounty(county+", " +stateAbbreviation);
        payload.updateVoteUsingJSON(getElectionResultsFromCounty(county,stateAbbreviation));
        startWatch(payload);

        openMyRepresentatives.putExtra("REPRESENTATIVES", payload);
//        openMyRepresentatives.("ELECTION_RESULTS", getElectionResultsFromCounty(county, stateAbbreviation));
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
        System.out.println("Searching for: "+key);

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
