package com.example.kunalpatel.represent;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.Wearable;
import com.google.android.gms.wearable.WearableListenerService;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * Created by joleary on 2/19/16.
 */
public class PhoneToWatchService extends WearableListenerService implements GoogleApiClient.ConnectionCallbacks {

    private static GoogleApiClient mApiClient;
    private  String path;
    private  byte[] message;
    private Context context;

    public PhoneToWatchService() {
        this.path = "sample";
        this.message = "sample".getBytes();

    }

    public PhoneToWatchService(String path, String message) {
        this.path = path;
        this.message = message.getBytes();
    }
    public PhoneToWatchService(String path, byte[] message) {
        this.path = path;
        this.message = message;
    }


    public static int byteArrayToLeInt(byte[] b) {
        final ByteBuffer bb = ByteBuffer.wrap(b);
        bb.order(ByteOrder.LITTLE_ENDIAN);
        return bb.getInt();
    }


    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        System.out.println("------------------message received by phone------------------------");
        path = messageEvent.getPath();
        message = messageEvent.getData();

        if (path.equals("/CHANGE_ZIP")) {
            int zip = byteArrayToLeInt(message);
            Toast.makeText(getApplicationContext(), "Zip changed! New ZIP :  45543 ", Toast.LENGTH_SHORT).show();
//            Intent openMyRepresentatives= new Intent(this, MyRepresentatives.class);
//            openMyRepresentatives.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            startActivity(openMyRepresentatives);


        }

        if (path.equals("/OPEN_DETAIL_VIEW")){
            Intent openRepresentativeDetails = new Intent(getApplicationContext(), RepresentativeDetails.class);

            openRepresentativeDetails.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            startActivity(openRepresentativeDetails);

        }

    }

    public void sendMessage(Context givenContext) {
        this.context = givenContext;
        mApiClient = new GoogleApiClient.Builder(givenContext).addApi(Wearable.API).addConnectionCallbacks(this).build();
        mApiClient.connect();

    }

    public void sendMessage(final String newPath, final String text, final Context context) {
        Log.d("SENDING", "MESSAGE");
        Log.d("SENDING", "MESSAGE");

        setPath(path);
        setMessage( text.getBytes());

        mApiClient = new GoogleApiClient.Builder(context).addApi(Wearable.API).addConnectionCallbacks(new PhoneToWatchService(path, message)).build();
        mApiClient.connect();
    }

    @Override
    public void onConnected(Bundle bundle) {
        System.out.println("Connected");


        Log.d("SENDING", "MESSAGE");


        System.out.println(" I'm about to send " + this.path);
        System.out.println(" I'm about to send "  + new String(message));


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

        System.out.println("Sent");
    }

    @Override
    public void onConnectionSuspended(int i) {

    }


    public String getPath() {
        return path;
    }

    public byte[] getMessage() {
        return message;
    }

    public void setPath(final String newPath) {
        this.path = newPath;
    }

    public void setMessage(final byte[] newMessage) {
        this.message = newMessage;
    }
}
