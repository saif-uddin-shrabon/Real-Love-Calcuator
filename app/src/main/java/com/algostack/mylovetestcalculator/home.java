package com.algostack.mylovetestcalculator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

public class home extends AppCompatActivity {

    private String SHARED_PREF="tinydb";

    private InterstitialAd mInterstitialAd;
    TextView Results;
    Integer backpressed = 0;
    ProgressBar pb2;
    AdView  mAdView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mAdView = findViewById(R.id.adView);

        //init ad done
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        //banner add
        InterstitialAd.load(this,"ca-app-pub-6122232860446418/9757446612", adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        // The mInterstitialAd reference will be null until
                        // an ad is loaded.
                        mInterstitialAd = interstitialAd;
//                        Log.i(TAG, "onAdLoaded");
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error
//                        Log.i(TAG, loadAdError.getMessage());
                        mInterstitialAd = null;
                    }
                });


    }

    public void Submitbt(View view) {

        backpressed = 0;
        EditText boyname = findViewById(R.id.boyname);
        EditText girlname = findViewById(R.id.girlname);
        String boy = "", girl = "";
        boy = boyname.getText().toString();
        girl = girlname.getText().toString();
        boy = boy.trim();
        girl = girl.trim();
        boy = boy.toLowerCase();
        girl = girl.toLowerCase();

        if (!(boy.isEmpty()) && !(girl.isEmpty())) {

            boy = boy.toLowerCase();

            char boyc, girlc;
            int boyint = 0, girlint = 0;

            for (int i = 0; i < boy.length(); i++) {
                boyc = boy.charAt(i);
                boyint = boyint + boyc;
            }

            for (int i = 0; i < girl.length(); i++) {
                girlc = girl.charAt(i);
                girlint = girlint + girlc;
            }

            Results(boyint, girlint, boy, girl);

        } else {
            Toast empty = Toast.makeText(getApplicationContext(), "Enter Both Names", Toast.LENGTH_SHORT);
            empty.setGravity(Gravity.BOTTOM, 0, 200);
            empty.show();
        }
    }





    public void Results(int boytotal, int girltotal, String boyname, String girlname) {

        try {
            RelativeLayout datalayout = findViewById(R.id.datalayout);
            RelativeLayout resultlayout = findViewById(R.id.resultlayout);
            TextView boynametext = findViewById(R.id.boynametext);
            TextView girlnametext = findViewById(R.id.girlnametext);

            Results = findViewById(R.id.results);
            datalayout.setVisibility(View.INVISIBLE);
            resultlayout.setVisibility(View.VISIBLE);

            boyname = boyname.toUpperCase();
            girlname = girlname.toUpperCase();
            boynametext.setText(boyname);
            girlnametext.setText(girlname);

            Integer sumtotal = boytotal + girltotal, temptotal = 0;
            String result1 = "", result2 = "", results = "";
            for (Integer i = 0; i <= 1; i++) {
                temptotal = sumtotal % 10;
                sumtotal = sumtotal / 10;
                if (i == 0) {
                    result1 = temptotal.toString();
                } else {
                    result2 = temptotal.toString();
                }
            }
            results = result1 + result2;
            Integer afterresults, temp;
            afterresults = Integer.parseInt(results);
            if (afterresults < 40)
                afterresults = afterresults + 35;

            results = afterresults.toString();
            Results.setText(results + "%");

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    closeanim();
                }
            }, 2000);


        } catch (Exception ex) {
            Toast empty = Toast.makeText(getApplicationContext(), "Some Error Occured", Toast.LENGTH_SHORT);
            empty.setGravity(Gravity.BOTTOM, 0, 200);
            empty.show();
        }
    }





    public void Back(View view) {
        onBackPressed();
    }

    public void onBackPressed() {

        //add show
        if (mInterstitialAd != null) {
            mInterstitialAd.show(home.this);
        } else {
            Log.d("TAG", "The interstitial ad wasn't ready yet.");
        }

        RelativeLayout datalayout = findViewById(R.id.datalayout);
        RelativeLayout resultlayout = findViewById(R.id.resultlayout);
        EditText boyname = findViewById(R.id.boyname);
        EditText girlname = findViewById(R.id.girlname);
        TextView Results = findViewById(R.id.results);
        ProgressBar pb = findViewById(R.id.pb);


        if ((datalayout.getVisibility() == View.VISIBLE) && backpressed.equals(1)) {
            finish();
            System.exit(0);
        } else if (resultlayout.getVisibility() == View.VISIBLE) {
            resultlayout.setVisibility(View.INVISIBLE);
            datalayout.setVisibility(View.VISIBLE);
            pb.setVisibility(View.VISIBLE);
            Results.setVisibility(View.GONE);
            boyname.setText("");
            girlname.setText("");
        } else {
            backpressed = (datalayout.getVisibility() == View.VISIBLE) ? 1 : 0;
            Toast empty = Toast.makeText(getApplicationContext(), "Press again to close", Toast.LENGTH_SHORT);
            empty.setGravity(Gravity.BOTTOM, 0, 200);
            empty.show();
        }
    }

    public void closeanim() {
        TextView Results = findViewById(R.id.results);
        ProgressBar pb = findViewById(R.id.pb);

        pb.setVisibility(View.GONE);
        Results.setVisibility(View.VISIBLE);
    }



}