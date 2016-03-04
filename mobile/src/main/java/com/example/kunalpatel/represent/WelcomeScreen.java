package com.example.kunalpatel.represent;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.common.api.GoogleApiClient;

public class WelcomeScreen extends AppCompatActivity {

    Button useLocationButton, zipButton;

    private GoogleApiClient mApiClient;

    private Context context;

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

    public void sendMessage() {
        PhoneToWatchService ph = new PhoneToWatchService("/START_ACTIVITY", "hello");
//        ph.setMessage("hello watch".getBytes());
//        ph.setPath("/TEST");
        System.out.println(ph.getMessage());
        System.out.println(ph.getPath());
        ph.sendMessage(context);
//        ph.sendMessage("/TEST" , "hello watch", this);

    }

    private void setListeners() {
        useLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                sendMessage();
                openMyRepresentatives();
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
    protected void onDestroy() {
        super.onDestroy();
        mApiClient.disconnect();
    }

    public void openMyRepresentatives() {
        Intent openMyRepresentatives= new Intent(this, MyRepresentatives.class);
        startActivity(openMyRepresentatives);
    }

    public void openZipEntry() {
        Intent openZipEntry= new Intent(this, ZipEntry.class);
        startActivity(openZipEntry);
    }

}
