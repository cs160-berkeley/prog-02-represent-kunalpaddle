package com.example.kunalpatel.represent;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.Wearable;
import com.google.android.gms.wearable.WearableListenerService;

import org.apache.commons.lang3.SerializationUtils;

/**
Somewhat based of joleary's Catnip app.

 */
public class WatchToPhoneService extends WearableListenerService implements GoogleApiClient.ConnectionCallbacks {
    private static GoogleApiClient mApiClient;
    private String path;
    private byte[] message;

    private Context context;

    public WatchToPhoneService(String path, String message) {
        this.path = path;
        this.message = message.getBytes();
    }
    public WatchToPhoneService(String path, byte[] message) {
        this.path = path;
        this.message = message;
    }

    public WatchToPhoneService() {
        this.path = "sample";
        this.message = "sample".getBytes();

    }

    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        System.out.println("Watch received a message.");

        path = messageEvent.getPath();
        message = messageEvent.getData();

        System.out.println(path);
        System.out.println(new String(message));

        if (path.equals("/OPEN_APP")) {


        }

        if (path.equals("/START_ACTIVITY")) {
            Toast.makeText(getBaseContext(), "Communication Successful!!!",
                    Toast.LENGTH_SHORT).show();

            DataWrapper dataWrapper = (DataWrapper) SerializationUtils.deserialize(message);

            Intent start = new Intent(this, MainActivity.class);
            start.putExtra("REPRESENTATIVES", dataWrapper);

            start.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            startActivity(start);
        }

    }

    public void sendMessage(String path, String text, Context context) {
        mApiClient = new GoogleApiClient.Builder(context).addApi(Wearable.API).addConnectionCallbacks(new WatchToPhoneService(path,text)).build();
        mApiClient.connect();
    }

    public void sendMessage(Context givenContext) {
        this.context = givenContext;
        mApiClient = new GoogleApiClient.Builder(givenContext).addApi(Wearable.API).addConnectionCallbacks(this).build();
        mApiClient.connect();

    }

    @Override
    public void onConnected(Bundle bundle) {
        System.out.println("connected");

        System.out.println("WATCH CONNECTS TO PHONE");
        new Thread( new Runnable() {
            @Override
            public void run() {
                NodeApi.GetConnectedNodesResult nodes = Wearable.NodeApi.getConnectedNodes( mApiClient ).await();
                for(Node node : nodes.getNodes()) {
                    MessageApi.SendMessageResult result = Wearable.MessageApi.sendMessage(
                            mApiClient, node.getId(), path, message ).await();
                }
                mApiClient.disconnect();
            }
        }).start();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

}
