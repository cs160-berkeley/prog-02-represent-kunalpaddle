package com.example.kunalpatel.represent;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by KunalPatel on 2/29/16.
 */
public class MyRepresentatives extends AppCompatActivity {


    private ListView representativeListView;

    private ArrayList<Representative> representatives;
    private static final String START_ACTIVITY = "/start_activity";
    private SunlightApi sunlightApi;
    private ArrayList<String> tweets = tweetList();



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        System.out.println("------ started MyRepresentatives activity");

        super.onCreate(savedInstanceState);

        loadRepresentatives();

        System.out.println("------ All reps have been set in the representatives variable. ");


        // Mock representatives. Will use API in part 3 .

//        mockRepresentatives();

        setContentView(R.layout.activity_my_representatives);

        representativeListView = (ListView) findViewById(R.id.representativeListView);

        RepresentativeListAdapter representativeListAdapter = new RepresentativeListAdapter(this, representatives);

        representativeListView.setAdapter(representativeListAdapter);


    }




    public void mockRepresentatives() {

        Representative marcoRubio = new Senator("Marco Rubio", "Florida", "Republican");
        int marcoPictureId = getResourceIdFromUri("@drawable/rep_marco_rubio");
        final BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
        Bitmap marcoPic = BitmapFactory.decodeResource(this.getApplicationContext().getResources(),marcoPictureId,bitmapOptions);
        marcoRubio.setPicture(marcoPic);
        marcoRubio.setTermEndDate("2016-07-08");
        marcoRubio.setTweet("Sign up to be part of our team. Because the future of America — and the conservative movement — is at stake.");

        Senator marcoRoboto = new Senator("Marco Roboto", "Florida", "Republican");
        marcoRoboto.setTermEndDate("2016-07-08");
        marcoRoboto.setPicture(marcoPic);
        marcoRoboto.setTweet("Lets dispel this fiction that Barack Obama does not know what he’s doing. He knows exactly what he’s doing…");

        HouseRep marcoPolo = new HouseRep("Marco Polo", "Florida", "Democrat", 9);
        marcoPolo.setPicture(marcoPic);
        marcoPolo.setTermEndDate("2016-07-08");
        marcoPolo.setTweet("You cannot give up on the American dream.");

        representatives.add(marcoRubio);
        representatives.add(marcoRoboto);
        representatives.add(marcoPolo);


    }

    public int getResourceIdFromUri(String uri) {
        return getApplicationContext().getResources().getIdentifier(uri, null, getApplicationContext().getPackageName());

    }

    public void loadRepresentatives() {
        System.out.println("loadRepresentatives called.");

        Intent receivedIntent = getIntent();

        DataWrapper repWrapper = (DataWrapper) (receivedIntent.getSerializableExtra("REPRESENTATIVES"));
        representatives = repWrapper.getRepresentatives();

        //Fire off a watch update?

//        System.out.println(representatives);
        for (Representative rep: representatives) {
            rep.setTweet(getTweet());
        }
    }


    private ArrayList<String> tweetList(){
        ArrayList<String> tweets = new ArrayList<String>();
        tweets.add("NJ papers demand @ChrisChristie's resignation after he endorses Trump: http://on.msnbc.com/1png5kd  ");
        tweets.add("Flags flying half-staff to honor the beautiful life of Mrs. Nancy Reagan today at the Capitol ");
        tweets.add("Interested to hear from other #SXSW attendees what they think about Obama asking for backdoor into encryption #SXSW2016");
        tweets.add("On #InternationalWomensDay, I'm proud to represent Wyoming - the first state to grant women the right to vote. #EqualityState");
        tweets.add("At today's Foreign Relations hearing, I challenged admin's authority to divert taxpayer $ to UN Green Climate Fund http://1.usa.gov/24OGS9x ");
        tweets.add("This morning, I spoke on the Senate floor about how important it is to #LetThePeopleDecide on a #SCOTUS nomination. https://youtu.be/-6ODqTsSB4k ");
        tweets.add("Pleased the Senate just overwhelmingly passed a bipartisan bill to combat heroin & prescription drug abuse epidemic. ");
        tweets.add("Prayers to the family of Taylor Force & all victims of Palestinian terrorist attacks in Israel http://1.usa.gov/1LS1gQV ");
        tweets.add("Rs are using one excuse after another to explain why they shouldn't have to do their jobs. Wouldn't it be easier just to do the right thing?");
        tweets.add("Happy to meet with my constituents, Dona and Alexis who are advocating on behalf of #ProstateCancerAwareness today! ");
        tweets.add("Gov't backdoor access to iPhone would undo years of progress in online security. Read my op-ed w/@DarrellIssa here: http://lat.ms/1Sf8pvZ ");
        tweets.add("After presenting the West Point appointment to Garrett I had a Q & A with students at West… https://www.instagram.com/p/BC0evXrl-HC/ ");
        tweets.add("They've been listening to Donald Trump too much. Republicans need to stop and consider this disgusting rhetoric they're spewing.");
        tweets.add("Pressing the Obama administration to provide relief for victims of predatory student loan practices. More info here: http://1.usa.gov/24QZKV");
        tweets.add("More good news for U.S. economy: 242,000 private-sector jobs added in February, extending longest streak on record. ");
        tweets.add("The federal government needs authority to aggressively pursue transnational criminal organizations to reduce flow of drugs into our country.");
        tweets.add("Good read in @StarAdvertiser on Ginsburg/Scalia friendship. Despite deep differences, a relationship of true respect http://bit.ly/1pdtziU ");
        tweets.add("Astronaut Scott Kelly to Retire from NASA in April via NASA");
        tweets.add("Senate passes ethics reform bill targeting money of lawmakers who become lobbyists...");
        tweets.add("@ConcernedHsd the focus of the parents and taxpayers should not turn away from our children and the budget.");
        tweets.add("It's a great relief that #SupremeCourt decided we can protect our families from the dangers of #mercury & air toxics ");
        tweets.add("Women veterans are committing suicide at nearly 6 times the rate of civilian women. We must act to protect our vets. http://on.fb.me/1nNiHaB ");
        return tweets;
    }

    private String getTweet() {
        return tweets.get((int) (Math.random()*tweets.size()));

    }

}
