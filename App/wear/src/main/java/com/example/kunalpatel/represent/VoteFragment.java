package com.example.kunalpatel.represent;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by KunalPatel on 3/3/16.
 */
public class VoteFragment extends Fragment {

    private TextView obamaVote, romneyVote, countyLabel;

    private String obamaPercentage, romneyPercentage, county;

    public void setObamaPercentage(int percentage) {
        this.obamaPercentage = ""+percentage+"%";
    }
    public void setRomneyPercentage(int percentage) {
        this.romneyPercentage = ""+percentage+"%";
    }

    public void setCounty(String newCounty) {
        this.county = newCounty;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View voteView = inflater.inflate(R.layout.vote_slide, container, false);
        obamaVote = (TextView) voteView.findViewById(R.id.obamaVote);
        romneyVote = (TextView) voteView.findViewById(R.id.romneyVote);
        countyLabel = (TextView) voteView.findViewById(R.id.countyLabel);

        if (obamaPercentage == null || romneyPercentage == null ){
            throw new IllegalArgumentException();
        }

        romneyVote.setText(romneyPercentage);
        obamaVote.setText(obamaPercentage);

        countyLabel.setText(county);


        return voteView;
    }



    private Bitmap downscaleBitmapUsingDensities(final int sampleSize,final int imageResId)
    {
        final BitmapFactory.Options bitmapOptions=new BitmapFactory.Options();
        bitmapOptions.inDensity=sampleSize;
        bitmapOptions.inTargetDensity=1;
        final Bitmap scaledBitmap=BitmapFactory.decodeResource(this.getResources(),imageResId,bitmapOptions);
        scaledBitmap.setDensity(Bitmap.DENSITY_NONE);
        return scaledBitmap;
    }

    public void startCycling() {
        // TODO Auto-generated method stub

    }
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

    }


}

