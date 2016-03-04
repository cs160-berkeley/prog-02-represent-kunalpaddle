package com.example.kunalpatel.represent;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.wearable.view.DismissOverlayView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.Toast;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends FragmentActivity {

    private ViewPager mViewPager;
    private DismissOverlayView mDismissOverlayView;
    private GestureDetector mGestureDetector;
    private ArrayList<Fragment> myReps;
    private Resources res;
    private Context context;
    private Bitmap marcoPic;
    private SensorManager mSensorManager;
    private float mAccel; // acceleration apart from gravity
    private float mAccelCurrent; // current acceleration including gravity
    private float mAccelLast; // last acceleration including gravity
    private boolean triggered = false;

    /*

        All accelerometer code was obtained from the following URL:
        http://stackoverflow.com/questions/2317428/android-i-want-to-shake-it

        All credit for accelerometer code goes to Thilo Köhler.
     */


    private ArrayList<String> sampleCounties;



    @Override
    protected void onCreate(Bundle dfuse) {
        super.onCreate(dfuse);
        setContentView(R.layout.activity_representatives);


        Representative marcoRubio = new Representative("Marco Rubio", "Florida", "Republican");
        final BitmapFactory.Options bitmapOptions =new BitmapFactory.Options();
        int marcoPictureId= getResources().getIdentifier("@drawable/rep_marco_rubio", null, getApplicationContext().getPackageName());
        marcoPic = BitmapFactory.decodeResource(getApplicationContext().getResources(),marcoPictureId,bitmapOptions);


        //Mock counties
        sampleCounties = new ArrayList<String>();
        mockCounties();

        mViewPager = (ViewPager) findViewById(R.id.pager);
        res = getResources();

        ArrayList<Fragment> repFragments = new ArrayList<Fragment>();
        mockFragments(repFragments);

        FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager());
        for (Fragment frag: repFragments) {
            adapter.addFragment(frag);
        }

        context = getApplicationContext();



        mViewPager.setAdapter(adapter);
        mViewPager.setOnPageChangeListener(new OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                // UPDATE PHONE UI WITH NEW REPS

            }

            @Override
            public void onPageScrolled(int position, float positionOffset,
                                       int positionOffsetPixels) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        mGestureDetector = new GestureDetector(this, new LongPressListener());

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensorManager.registerListener(mSensorListener, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
        mAccel = 0.00f;
        mAccelCurrent = SensorManager.GRAVITY_EARTH;
        mAccelLast = SensorManager.GRAVITY_EARTH;

    }

    private void mockFragments(ArrayList<Fragment> repFragments) {
        Representative marcoRubio = new Representative("Marco Rubio", "Florida", "Republican");
        marcoRubio.setPicture(marcoPic);
        marcoRubio.setTermEndDate(new Date());
        marcoRubio.setTweet("Sign up to be part of our team. Because the future of America — and the conservative movement — is at stake.");

        Representative marcoRoboto = new Representative("Marco Roboto", "Florida", "Republican");
        marcoRoboto.setTermEndDate(new Date());
        marcoRoboto.setPicture(marcoPic);
        marcoRoboto.setTweet("Lets dispel this fiction that Barack Obama does not know what he’s doing. He knows exactly what he’s doing…");

        Representative marcoPolo = new Representative("Mark O Polo", "Florida", "Democrat");
        marcoPolo.setPicture(marcoPic);
        marcoPolo.setTermEndDate(new Date());
        marcoPolo.setTweet("You cannot give up on the American dream.");


        RepresentativeFragment marcoRubioFragment = new RepresentativeFragment();
        RepresentativeFragment marcoRobotoFragment = new RepresentativeFragment();
        RepresentativeFragment marcoPoloFragment = new RepresentativeFragment();
        marcoRubioFragment.setRepresentative(marcoRubio);
        marcoRobotoFragment.setRepresentative(marcoRoboto);
        marcoPoloFragment.setRepresentative(marcoPolo);

        repFragments.add(marcoRubioFragment);
        repFragments.add(marcoRobotoFragment);
        repFragments.add(marcoPoloFragment);


        //Vote view

        VoteFragment vote = new VoteFragment();
        vote.setCounty(sampleCounties.get ((int)( Math.random()*sampleCounties.size())));
        int obama =(int)  (Math.random() *100);
        int romney  = (100- obama);

        vote.setObamaPercentage(obama);
        vote.setRomneyPercentage(romney);

        repFragments.add(vote);

    }

    public void addVoteFragment() {
        VoteFragment vote = new VoteFragment();
        vote.setCounty(sampleCounties.get ((int)( Math.random()*sampleCounties.size())));
        int obama =(int)  (Math.random() *100);
        int romney  = (100- obama);

        vote.setObamaPercentage(obama);
        vote.setRomneyPercentage(romney);

        myReps.add(vote);
    }

    public int getResourceIdFromUri(String uri) {

        return res.getIdentifier(uri, null, context.getPackageName());

    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        return mGestureDetector.onTouchEvent(event)
                || super.dispatchTouchEvent(event);
    }

    private class LongPressListener extends
            GestureDetector.SimpleOnGestureListener {
        @Override
        public void onLongPress(MotionEvent event) {
            mDismissOverlayView.show();

        }
        @Override
        public boolean onSingleTapUp(MotionEvent event) {
            new WatchToPhoneService("/OPEN_DETAIL_VIEW", "").sendMessage(context);
            return true;
        }
//        @Override
//        public boolean onSingleTapConfirmed(MotionEvent event) {
//            new WatchToPhoneService("/OPEN_DETAIL_VIEW", "").essage(context);
//            return true;
//        }

    }

    public void mockCounties() {
        sampleCounties.add("Broward County, FL");
        sampleCounties.add("Orange County, CA");
        sampleCounties.add("James County, AL");
        sampleCounties.add("Forest County, MI");
        sampleCounties.add("Cherokee County, OK");
        sampleCounties.add("Jackson County, AK");
        sampleCounties.add("Chickasaw County, WY");
        sampleCounties.add("Santa Clara County, CA");
        sampleCounties.add("Washington County, WA");

    }


    private final SensorEventListener mSensorListener = new SensorEventListener() {

        public void onSensorChanged(SensorEvent se) {
            System.out.println("SENSOR TRIGGERED");
            float x = se.values[0];
            float y = se.values[1];
            float z = se.values[2];
            mAccelLast = mAccelCurrent;
            mAccelCurrent = (float) Math.sqrt((double) (x*x + y*y + z*z));
            float delta = mAccelCurrent - mAccelLast;
            mAccel = mAccel * 0.9f + delta; // perform low-cut filter
            if (mAccel > 12) {
                int zip = (int) (Math.random()*100000);
                Toast toast = Toast.makeText(getApplicationContext(), "Zip Code changed to : " + zip, Toast.LENGTH_LONG);
//              new WatchToPhoneService("/CHANGE_ZIP", intToBytes(zip)).sendMessage(context);

                toast.show();

//
//                mAccel = 0;
//                mAccelCurrent = 0;
//                mAccelLast = 0;
            }
        }

        public byte[] intToBytes( final int i ) {
            ByteBuffer bb = ByteBuffer.allocate(4);
            bb.putInt(i);
            return bb.array();
        }

        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(mSensorListener, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        mSensorManager.unregisterListener(mSensorListener);
        super.onPause();
    }
}
