package com.example.kunalpatel.represent;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;


/**
 CREDIT

 A majority of  following code was obtained with consent from Dusting Winings through his tutorial at:
 https://www.youtube.com/watch?v=afi3BRrwCeY


 */
public class RepresentativeListAdapter  extends ArrayAdapter<Representative> {

    private final Context context;
    private final ArrayList<Representative> representativeList;
    private ImageView repIcon, badge;
    private TextView latestTweet, repName, repTitle;

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
        repIcon = (ImageView) rowView.findViewById(R.id.item_icon);
        latestTweet = (TextView) rowView.findViewById(R.id.tweetTextView);
        repName = (TextView) rowView.findViewById(R.id.representativeName);
        badge = (ImageView) rowView.findViewById(R.id.badge);
        repTitle = (TextView) rowView.findViewById(R.id.representativeTitle);

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

        if (rep.getChamber().equalsIgnoreCase("senate")){
            repTitle.setText("SEN.");
        }
        else{
            repTitle.setText("REP.");
        }

        repName.setText(rep.getName());

        Picasso.with(context).load(rep.getImageUrl()).centerCrop().fit().into(repIcon);

        latestTweet.setText(rep.getTweet());

        final Representative repToSend = rep;


        ImageButton infoButton = (ImageButton) rowView.findViewById(R.id.infoButton);
        infoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openRepresentativeDetails = new Intent(context, RepresentativeDetails.class);
                openRepresentativeDetails.putExtra("REPRESENTATIVES", new DataWrapper(repToSend));
                context.startActivity(openRepresentativeDetails);
            }
        });
        final Representative repFinal  = rep;

        ImageButton webButton = (ImageButton) rowView.findViewById(R.id.websiteButton);
        webButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = repFinal.getWebsite();
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                getContext().startActivity(i);
            }
        });

        ImageButton mailButton = (ImageButton) rowView.findViewById(R.id.emailButton);
        mailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(context)
                        .setTitle(repFinal.getName()+"'s E-mail Address" )
                        .setMessage(repFinal.getEmail())
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // continue with delete
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        });

        return rowView;
    }


    public void openWebsite(String url) {


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