package com.example.omerveberna.trafikbilgini;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Handler;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class CompetitionActivity extends AppCompatActivity {

    private Button answerButton;
    private RadioGroup radioGroup;
    private RadioButton a_option, b_option, rb_selected;
    private TextView questionText;
    private int currentQuestionIndex;
    private ArrayList<Question> questionArrayList;
    private ArrayList<Question> correctAnswerTextArrayList;
    private DatabaseReference databaseReference;
    private DatabaseReference databaseReferenceScores;
    private Resources resources;
    private int duzey;//mevcut düzey
    //private static final int MAX_LEVEL = 5;
    private int scoreNum = 0;
    private TextView scoreText, gameState, duzeyTextView;
    private Users currentUser;



    private ProgressBar progressBar;

    // private ProgressDialog progress;
    private Button btn;
    private int progressStatus = 0;
    private Handler handler = new Handler();
    private int progressBarValue = 0;
    public int enYuksek = 50;
    private static int count;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_competition);

        AdRequest adRequest = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .addTestDevice("52D1C7D47931D5E779B16369E14030DD").build();
        AdView adView = (AdView) this.findViewById(R.id.adView);
        adView.loadAd(adRequest); //adView i yukluyoruz


        String android_id = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
        String deviceId = md5(android_id).toUpperCase();
        Log.i("device id=",deviceId);


        duzeyTextView = (TextView) findViewById(R.id.duzeyTextView);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
//        progressBar.setMax(10);
//        progressBar.setIndeterminate(false);


        Init();

        databaseReference = FirebaseDatabase.getInstance().getReference("questions");

        databaseReferenceScores = FirebaseDatabase.getInstance().getReference("users/" + FirebaseAuth.getInstance().getCurrentUser().getUid());


        questionArrayList = new ArrayList<>();
        correctAnswerTextArrayList = new ArrayList<>();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Question question = postSnapshot.getValue(Question.class);
                    questionArrayList.add(question);

                }

                //currentQuestionIndex = 0;
                displayQuestion(currentQuestionIndex);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        databaseReferenceScores.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                currentUser = dataSnapshot.getValue(Users.class);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        answerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (radioGroup.getCheckedRadioButtonId() != -1) {

                    controlAnswers();


                } else {
                    Toast.makeText(getApplicationContext(), "Lütfen bir seçim yapınız!", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    public String md5(String s) {
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            for (int i=0; i<messageDigest.length; i++)
                hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }



    private void controlAnswers() {

        progressBar.setProgress(0);

        if (answerCheck()) {

            scoreNum = scoreNum + 5;
//            progressBar.setProgress(scoreNum);


            progressBar.setMax(enYuksek);
            currentUser.setScore(scoreNum);

            String.valueOf(currentUser.getScore());
            DatabaseReference databaseReference_new = databaseReferenceScores.child("score");
            databaseReference_new.setValue(scoreNum);
            scoreText.setText("PUANINIZ:\n " + " " + String.valueOf(scoreNum));
            Query queryRef = databaseReferenceScores.orderByChild("score").limitToLast(1);

            progressBar.setProgress(scoreNum);

//            progressStatus =0;
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    while (progressStatus<questionArrayList.size())
//                    {progressStatus +=1;
//                        handler.post(new Runnable() {
//                            @Override
//                            public void run() {
//                                progressBar.setProgress(progressStatus);
//                            }
//                        });
//                        try {
//                            Thread.sleep(questionArrayList.size());
//                        }
//                        catch(InterruptedException e){
//                            e.printStackTrace();
//                        }
//                    }
//                }
//            }).start();

//            progress=new ProgressDialog(this);
//            progress.setMessage("Downloading Music");
//            progress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
//            progress.setIndeterminate(true);
//            progress.setProgress(0);
//            progress.show();
//
//            final int totalProgressTime = 100;
//            final Thread t = new Thread() {
//                @Override
//                public void run() {
//                    int jumpTime = 0;
//
//                    while(jumpTime < totalProgressTime) {
//                        try {
//                            sleep(200);
//                            jumpTime += 5;
//                            progress.setProgress(jumpTime);
//                        } catch (InterruptedException e) {
//                            // TODO Auto-generated catch block
//                            e.printStackTrace();
//                        }
//                    }
//                }
//            };
//            t.start();

            //Toast.makeText(getApplicationContext(),"TEBRİKLER DOĞRU CEVAP :)",Toast.LENGTH_SHORT).show();

        } else {


            progressBar.setProgress(currentUser.getScore());
            enYuksek = enYuksek - 5;
            progressBar.setMax(enYuksek);

            String str = questionArrayList.get(currentQuestionIndex).getCorrectAnswerText();
            //Toast.makeText(getApplicationContext(),"doğru cevap"+" "+str,Toast.LENGTH_SHORT).show();


            Intent intent = new Intent(CompetitionActivity.this, FalseAnswerActivity.class);
            intent.putExtra("right_answer", str);
            startActivity(intent);


            //Toast.makeText(getApplicationContext(),"ÜZGÜNÜM YANLIŞ CEVAP :(",Toast.LENGTH_SHORT).show();

        }

        goOn();

    }

    private void goOn() {
//        duzey=1;

        currentQuestionIndex = (currentQuestionIndex + 1) % questionArrayList.size();

//        if(currentQuestionIndex % 5 == 0){
//            ++duzey;
//            duzeyTextView.setText("Düzey: "+" "+ duzey);
//
//            displayQuestion(currentQuestionIndex);
//            ;
//
//
//        }


        if (currentQuestionIndex == 0) {

            gameState.setVisibility(View.VISIBLE);
            radioGroup.clearCheck();
            answerButton.setEnabled(false);
            Toast.makeText(getApplicationContext(), "TEBRİKLER DÜZEYİ TAMAMLADINIZ ! :)", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, ShareFacebookActivity.class);
            startActivity(intent);


            return;
        }


        displayQuestion(currentQuestionIndex);
    }


    private boolean answerCheck() {

        String answer = "";
        int id = radioGroup.getCheckedRadioButtonId();
        rb_selected = (RadioButton) findViewById(id);
        if (rb_selected == a_option) {
            answer = "a";
        }
        if (rb_selected == b_option) {
            answer = "b";
        }


        return questionArrayList.get(currentQuestionIndex).isCorrectAnswer(answer);
    }

    private void Init() {

        answerButton = (Button) findViewById(R.id.answerButton);
        questionText = (TextView) findViewById(R.id.questionText);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        a_option = (RadioButton) findViewById(R.id.a_option);
        b_option = (RadioButton) findViewById(R.id.b_option);
        scoreText = (TextView) findViewById(R.id.scoreText);
        gameState = (TextView) findViewById(R.id.gameState);
        radioGroup.clearCheck();

    }

    private void displayQuestion(int pos) {


        radioGroup.clearCheck();
        questionText.setText(questionArrayList.get(pos).getQuestionText());
        a_option.setText(questionArrayList.get(pos).getChoice_a());
        b_option.setText(questionArrayList.get(pos).getChoice_b());

    }


}
