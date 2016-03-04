package com.example.kunalpatel.represent;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by KunalPatel on 3/1/16.
 */
public class RepresentativeDetails extends AppCompatActivity {

    LinearLayout commiteesLayout, billsLayout;

    private ArrayList<String> commiteesList, billsList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        commiteesList = new ArrayList<String>();
        billsList = new ArrayList<String>();
        mockCommittees();
        mockBills();

        setContentView(R.layout.activity_representative_details);

        commiteesLayout = (LinearLayout) findViewById(R.id.committees_list);
        billsLayout = (LinearLayout) findViewById(R.id.bills_list);

        for (String commitee: commiteesList) {
            TextView toAdd = new TextView(this);
            toAdd.setPadding(0,12,0,12);
            toAdd.setText("-"+commitee);
//            LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
//            llp.setMargins(0, 5, 0, 5);
//            commiteesLayout.setLayoutParams(llp);
         commiteesLayout.addView(toAdd);
        }
        for (String bill: billsList) {
            TextView toAdd = new TextView(this);
            toAdd.setText("-"+bill);
            toAdd.setPadding(0,12,0,12);
//            LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//            llp.setMargins(0, 5, 0, 5);
//            billsLayout.setLayoutParams(llp);
          billsLayout.addView(toAdd);
        }


    }

    private void mockCommittees() {
        commiteesList.add("Committee on Commerce, Science and Transportation");
        commiteesList.add("Select Committee on Intelligence");
        commiteesList.add("Committee on Foreign Relations");
        commiteesList.add("Committee on Small Business and Entrepreneurship");
    }


    private void mockBills() {
        billsList.add("Comprehensive Addiction and Recovery Act of 2016");
        billsList.add("Office of Strategic Services Congressional Gold Medal Act");
        billsList.add("End Modern Slavery Initiative Act of 2015");

    }

}
