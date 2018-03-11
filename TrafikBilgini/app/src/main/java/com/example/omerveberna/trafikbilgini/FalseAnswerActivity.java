package com.example.omerveberna.trafikbilgini;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.firebase.database.DataSnapshot;

import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class FalseAnswerActivity extends AppCompatActivity {

    private static String URL = "http://mevzuat.meb.gov.tr/html/18195_2918.html";

    private Button buttonDogrusu;
    private ProgressDialog progressDialog;
    private LinearLayout dogrusu_layout;
    private TextView txt_dogrusu;

    InterstitialAd InterstitialAds;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_false_answer);

        InterstitialAds = new InterstitialAd(this);
        InterstitialAds.setAdUnitId("ca-app-pub-7528920711791081/3060869429");
        reklamiYukle();

        InterstitialAds.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                super.onAdClosed();
            }
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                InterstitialAds.show();
            }
        });



        dogrusu_layout = (LinearLayout) findViewById(R.id.dogrusu_layout);
        txt_dogrusu = (TextView) findViewById(R.id.txt_dogrusu);
        buttonDogrusu = (Button) findViewById(R.id.buttonDogrusu);

        buttonDogrusu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = getIntent().getExtras();

                String str = bundle.getString("right_answer");
                txt_dogrusu.setText(str);

            }
        });


    }
    private void reklamiYukle() {
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .addTestDevice("52D1C7D47931D5E779B16369E14030DD").build();

        InterstitialAds.loadAd(adRequest);
    }



}