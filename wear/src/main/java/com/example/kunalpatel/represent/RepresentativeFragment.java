package com.example.kunalpatel.represent;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 CREDIT

 A majority of  following code was obtained with consent from Dusting Winings through his tutorial at:
 https://www.youtube.com/watch?v=afi3BRrwCeY


 */
public class RepresentativeFragment extends Fragment {

    private TextView repName;
    private ImageView badge;
    private ImageView picture;
    private View repView;
    private Resources res;
    private Context context;
    private Bitmap republicanBadge, democratBadge;
    private Representative rep;


    public void setRepresentative(Representative rep ) {
        this.rep = rep;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if( rep == null) {
            throw new IllegalArgumentException();
        }
        repView = inflater.inflate(R.layout.representative_slide, container, false);
        picture = (ImageView) repView.findViewById(R.id.representative_icon);
        repName = (TextView) repView.findViewById(R.id.representativeName);
        badge = (ImageView) repView.findViewById(R.id.badge);

        if (rep.getParty().equals(Representative.PARTY_REPUBLICAN)) {
            badge.setImageBitmap(republicanBadge);
        }
        else {

            badge.setImageBitmap(democratBadge);
        }

        repName.setText(rep.getName());
        picture.setImageBitmap(rep.getPicture());

        return repView;
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
        res = getResources();


        int republicanBadgeResource = res.getIdentifier("@drawable/badge_republican", null, getActivity().getApplicationContext().getPackageName());
        republicanBadge = downscaleBitmapUsingDensities(2, republicanBadgeResource);

        int democratBadgeResource = res.getIdentifier("@drawable/badge_democrat", null, getActivity().getApplicationContext().getPackageName());
        democratBadge = downscaleBitmapUsingDensities(2, democratBadgeResource);
    }

//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        //  instance = this;
//    }
}
