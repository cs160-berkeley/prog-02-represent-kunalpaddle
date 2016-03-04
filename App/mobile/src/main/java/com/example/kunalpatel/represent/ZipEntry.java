package com.example.kunalpatel.represent;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

public class ZipEntry extends AppCompatActivity {

    ImageButton nextButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zip_entry);

        nextButton = (ImageButton) findViewById(R.id.forwardButton);

        setListeners();

    }

    public void sendMessage() {
        PhoneToWatchService ph = new PhoneToWatchService("/START_ACTIVITY", "hello");
//        ph.setMessage("hello watch".getBytes());
//        ph.setPath("/TEST");
        System.out.println(ph.getMessage());
        System.out.println(ph.getPath());
        ph.sendMessage(getApplicationContext());
//        ph.sendMessage("/TEST" , "hello watch", this);

    }

    private void setListeners() {
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
                openMyRepresentatives()
                ;
            }
        });
    }


    public void openMyRepresentatives() {
        Intent openMyRepresentatives= new Intent(this, MyRepresentatives.class);
        startActivity(openMyRepresentatives);

    }

}
