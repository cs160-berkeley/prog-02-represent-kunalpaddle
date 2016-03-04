package com.example.kunalpatel.represent;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by KunalPatel on 2/29/16.
 */
public class MyRepresentatives extends AppCompatActivity {


    private ListView representativeListView;

    private ArrayList<Representative> representatives;
    private static final String START_ACTIVITY = "/start_activity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        // Mock representatives. Will use API in part 3 .
        representatives = new ArrayList<Representative>();
        mockRepresentatives();

        setContentView(R.layout.activity_my_representatives);

        representativeListView = (ListView) findViewById(R.id.representativeListView);

        RepresentativeListAdapter representativeListAdapter = new RepresentativeListAdapter(this, representatives);

        representativeListView.setAdapter(representativeListAdapter);


    }




    public void mockRepresentatives() {

        Representative marcoRubio = new Senator("Marco Rubio", "Florida", "Republican");
        int marcoPictureId = getResourceIdFromUri("@drawable/rep_marco_rubio");
        final BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
        Bitmap marcoPic = BitmapFactory.decodeResource(this.getApplicationContext().getResources(),marcoPictureId,bitmapOptions);
        marcoRubio.setPicture(marcoPic);
        marcoRubio.setTermEndDate(new Date());
        marcoRubio.setTweet("Sign up to be part of our team. Because the future of America — and the conservative movement — is at stake.");

        Senator marcoRoboto = new Senator("Marco Roboto", "Florida", "Republican");
        marcoRoboto.setTermEndDate(new Date());
        marcoRoboto.setPicture(marcoPic);
        marcoRoboto.setTweet("Lets dispel this fiction that Barack Obama does not know what he’s doing. He knows exactly what he’s doing…");

        HouseRep marcoPolo = new HouseRep("Marco Polo", "Florida", "Democrat", 9);
        marcoPolo.setPicture(marcoPic);
        marcoPolo.setTermEndDate(new Date());
        marcoPolo.setTweet("You cannot give up on the American dream.");

        representatives.add(marcoRubio);
        representatives.add(marcoRoboto);
        representatives.add(marcoPolo);


    }

    public int getResourceIdFromUri(String uri) {
        return getApplicationContext().getResources().getIdentifier(uri, null, getApplicationContext().getPackageName());

    }

    public void openEmail (String emailAddress){

    };

}
