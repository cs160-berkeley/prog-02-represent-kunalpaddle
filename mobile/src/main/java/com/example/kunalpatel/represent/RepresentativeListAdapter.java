package com.example.kunalpatel.represent;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


/**
 CREDIT

 A majority of  following code was obtained with consent from Dusting Winings through his tutorial at:
 https://www.youtube.com/watch?v=afi3BRrwCeY


 */
public class RepresentativeListAdapter  extends ArrayAdapter<Representative> {

    private final Context context;
    private final ArrayList<Representative> representativeList;

    public RepresentativeListAdapter(Context context, ArrayList<Representative> representativeList) {

        super(context, R.layout.representative_item, representativeList);
        this.context = context;
        this.representativeList = representativeList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // 1. Make new inflater
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // 2. inflate row view

        View rowView = null;
        rowView = inflater.inflate(R.layout.representative_item, parent, false);

        // 3. Get icon,title & counter views from the rowView
        ImageView repIcon = (ImageView) rowView.findViewById(R.id.item_icon);
        TextView latestTweet = (TextView) rowView.findViewById(R.id.tweetTextView);
        TextView repName = (TextView) rowView.findViewById(R.id.representativeName);
        ImageView badge = (ImageView) rowView.findViewById(R.id.badge);

        // 4. Set icon a
        Representative rep = representativeList.get(position);

        if (rep.getParty().equals(Representative.PARTY_DEMOCRAT)) {
            int imageResource = context.getResources().getIdentifier("@drawable/badge_democrat", null, context.getPackageName());
            Bitmap bm = downscaleBitmapUsingDensities(2, imageResource);
            badge.setImageBitmap(bm);
        }
        else {
            int imageResource = context.getResources().getIdentifier("@drawable/badge_republican", null, context.getPackageName());
            Bitmap bm = downscaleBitmapUsingDensities(2, imageResource);
            badge.setImageBitmap(bm);
        }

        repName.setText(rep.getName());
        repIcon.setImageBitmap(rep.getPicture());
        latestTweet.setText(rep.getTweet());


        ImageButton infoButton = (ImageButton) rowView.findViewById(R.id.infoButton);
        infoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openRepresentativeDetails = new Intent(context, RepresentativeDetails.class);
                context.startActivity(openRepresentativeDetails);
            }
        });


        return rowView;
    }

    /*

        Credit for this downscale function goes to Farhan on stackoverflow!
     */
    private Bitmap downscaleBitmapUsingDensities(final int sampleSize,final int imageResId)
    {
        final BitmapFactory.Options bitmapOptions=new BitmapFactory.Options();
        bitmapOptions.inDensity=sampleSize;
        bitmapOptions.inTargetDensity=1;
        final Bitmap scaledBitmap=BitmapFactory.decodeResource(context.getResources(),imageResId,bitmapOptions);
        scaledBitmap.setDensity(Bitmap.DENSITY_NONE);
        return scaledBitmap;
    }


}