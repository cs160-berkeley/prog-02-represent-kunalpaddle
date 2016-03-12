package com.example.kunalpatel.represent;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by KunalPatel on 3/1/16.
 */
public class RepresentativeDetails extends AppCompatActivity {

    LinearLayout commiteesLayout, billsLayout;

    private ArrayList<String> committeesList, billsList;
    private Representative representative;

    private TextView repTitle, repName;
    private ImageView badge, repIcon;
    private TextView termEndDateField;
    private Context context;

    private String bioguide = "L000551";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.context = getApplicationContext();
        committeesList = new ArrayList<String>();
        billsList = new ArrayList<String>();

//        mockCommittees();
//        mockBills();

        setContentView(R.layout.activity_representative_details);

        commiteesLayout = (LinearLayout) findViewById(R.id.committees_list);
        billsLayout = (LinearLayout) findViewById(R.id.bills_list);

        repIcon = (ImageView) findViewById(R.id.representative_icon);
        repName = (TextView) findViewById(R.id.representativeName);
        badge = (ImageView) findViewById(R.id.badge);
        repTitle = (TextView) findViewById(R.id.representativeTitle);
        termEndDateField = (TextView) findViewById(R.id.termEndDate);


        loadRepresentative();
        loadBillsAndCommittees();


    }

    private void loadBillsAndCommittees() {

        //Fetch bills
        new SunlightApi(context, new SunlightApi.SunlightBillsResponder() {
            @Override
            public void processFinish(ArrayList<String> bills) {
                billsList = bills;
                updateBills();
            }
        }).getBills(bioguide);

        //Fetch committees
        new SunlightApi(context, new SunlightApi.SunlightCommitteesResponder() {
            @Override
            public void processFinish(ArrayList<String> committees) {
                committeesList = committees;
                updateCommittees();
            }
        }).getCommittees(bioguide);

    }

    private void updateBills() {
        for (String bill: billsList) {
            TextView toAdd = new TextView(this);
            toAdd.setText(bill);
            toAdd.setPadding(0, 12, 0, 12);
            bill.replace(':', '\n');
            if(!bill.contains("null")) {
                billsLayout.addView(toAdd);
            }
        }
    }
    private void updateCommittees() {
        for (String commitee: committeesList) {
            TextView toAdd = new TextView(this);
            toAdd.setPadding(0, 12, 0, 12);
            toAdd.setText("-" + commitee);
            commiteesLayout.addView(toAdd);
        }
    }

    public void loadRepresentative() {
        System.out.println("loadRepresentatives called.");

        Intent receivedIntent = getIntent();

        DataWrapper repWrapper = (DataWrapper) (receivedIntent.getSerializableExtra("REPRESENTATIVES"));
        representative = repWrapper.getRepresentative();

        if (representative.getParty().equals(Representative.PARTY_DEMOCRAT)) {
            int imageResource = context.getResources().getIdentifier("@drawable/democrat_badge_large", null, context.getPackageName());
            Bitmap bm = downscaleBitmapUsingDensities(2, imageResource);
            badge.setImageBitmap(bm);
        }
        else {
            int imageResource = context.getResources().getIdentifier("@drawable/republican_badge_large", null, context.getPackageName());
            Bitmap bm = downscaleBitmapUsingDensities(2, imageResource);
            badge.setImageBitmap(bm);
        }

        if(repTitle == null) {
            System.out.println("REP TITLE IS NULL");
        }

        if (representative.getChamber().equalsIgnoreCase("senate")){
            this.repTitle.setText("SENATOR");
        }
        else{
            this.repTitle.setText("REPRESENTATIVE");
        }
        this.termEndDateField.setText("Term ends: " +representative.getTermEndDate());

        this.repName.setText(representative.getName());
        this.bioguide = representative.getBioguide();

        Picasso.with(context).load(representative.getImageUrl()).centerCrop().fit().into(repIcon);

    }

    private Bitmap downscaleBitmapUsingDensities(final int sampleSize,final int imageResId)
    {
        final BitmapFactory.Options bitmapOptions=new BitmapFactory.Options();
        bitmapOptions.inDensity=sampleSize;
        bitmapOptions.inTargetDensity=1;
        final Bitmap scaledBitmap=BitmapFactory.decodeResource(context.getResources(),imageResId,bitmapOptions);
        scaledBitmap.setDensity(Bitmap.DENSITY_NONE);
        return scaledBitmap;
    }


    private void mockCommittees() {
        committeesList.add("Committee on Commerce, Science and Transportation");
        committeesList.add("Select Committee on Intelligence");
        committeesList.add("Committee on Foreign Relations");
        committeesList.add("Committee on Small Business and Entrepreneurship");
    }

    private void mockBills() {
        billsList.add("Comprehensive Addiction and Recovery Act of 2016");
        billsList.add("Office of Strategic Services Congressional Gold Medal Act");
        billsList.add("End Modern Slavery Initiative Act of 2015");

    }
}
