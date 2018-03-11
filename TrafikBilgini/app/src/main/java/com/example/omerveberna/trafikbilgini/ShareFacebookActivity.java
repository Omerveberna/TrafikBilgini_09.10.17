package com.example.omerveberna.trafikbilgini;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.facebook.FacebookSdk;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class ShareFacebookActivity extends AppCompatActivity {
    private Button btnFacebook,btnDuzeyAtla;
    private ShareDialog shareDialog;
    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private TextView share_txt;
    private Users currentUser;
    private DatabaseReference databaseReferenceScores;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_facebook);

        auth = FirebaseAuth.getInstance();
        share_txt = (TextView) findViewById(R.id.share_txt);


        FacebookSdk.sdkInitialize(getApplicationContext());
        databaseReferenceScores = FirebaseDatabase.getInstance().getReference("users/"+ FirebaseAuth.getInstance().getCurrentUser().getUid());

        databaseReferenceScores.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                currentUser = dataSnapshot.getValue(Users.class);
                share_txt.setText("Sayın , " + currentUser.getAd() +" "+currentUser.getSoyad() + " " + "Puanınız:" + currentUser.getScore());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        btnFacebook =(Button)findViewById(R.id.btnFacebook);
        btnDuzeyAtla = (Button) findViewById(R.id.btnDuzeyAtla);

        btnDuzeyAtla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShareFacebookActivity.this,CompetitionActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
        shareToWall();
            }
        });

    }
    private void shareToWall()
    {
        shareDialog = new ShareDialog(ShareFacebookActivity.this);
        String send = share_txt.getText().toString();


        if(shareDialog.canShow(ShareLinkContent.class))
        {
            ShareLinkContent shareLinkContent = new ShareLinkContent.Builder()
                    .setContentUrl(Uri.parse("https://gelecegiyazanlar.turkcell.com.tr/"))
                    .setContentDescription(""+" "+send)
                    .setContentTitle(currentUser.getAd() +" "+currentUser.getSoyad() + " ")
                    .build();

            shareDialog.show(shareLinkContent);
        }






    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.actions,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_cikis:
                auth.signOut();
//                Intent intent = new Intent(ShareFacebookActivity.this, LoginActivity.class);
//                startActivity(intent);
                moveTaskToBack(true);
                android.os.Process.killProcess(android.os.Process.myPid());
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
