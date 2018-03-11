package com.example.omerveberna.trafikbilgini;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

import org.json.JSONObject;

import java.util.Arrays;

public class LoginActivity extends AppCompatActivity {

    private EditText emailEditText,passwordEditText;
    private Button loginButton,registerButton,yeniSifreButton;
    private FirebaseAuth auth;
    private LoginButton facebookLoginButton;
    private CallbackManager callbackManager;
    private Button btnBizeUlasin;


    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();

        setContentView(R.layout.activity_login);


        facebookLoginButton = (LoginButton)findViewById(R.id.facebookLogin_button);
        facebookLoginButton.setReadPermissions(Arrays.asList("public_profile ","user_friends","email"));
        facebookLoginButton.registerCallback(callbackManager,new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
//                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                startActivity(intent);





                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(
                                    JSONObject user,
                                    GraphResponse response) {





                                // user_name.setText(response.toString());

                                try{



                                    String id = user.getString("id");
                                    String name = user.getString("name");
                                    //user_name.setText("Hoşgeldin " + " " + name);




                                }catch (Exception e)
                                {
                                    e.printStackTrace();
                                }



                            }
                        });

                request.executeAsync();

            }

            @Override
            public void onCancel() {




            }

            @Override
            public void onError(FacebookException e) {

            }
        });



        auth= FirebaseAuth.getInstance();


        emailEditText = (EditText) findViewById(R.id.emailEditText);
        passwordEditText = (EditText) findViewById(R.id.passwordEditText);
        loginButton = (Button) findViewById(R.id.loginButton);
        registerButton = (Button) findViewById(R.id.registerButton);
        yeniSifreButton = (Button)findViewById(R.id.yeniSifreButton);
        btnBizeUlasin = (Button) findViewById(R.id.btnBizeUlasin);

        btnBizeUlasin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/html");
                intent.putExtra(Intent.EXTRA_EMAIL, new String[] { "omerveberna@gmail.com" });
                startActivity(Intent.createChooser(intent, "Send Email"));
            }
        });

        //Geçerli bir yetkilendirme olup olmadığını kontrol ediyoruz.
        if(auth.getCurrentUser() != null){
            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
            startActivity(intent);
            finish();
        }
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });
        yeniSifreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,YeniParolaActivity.class);
                startActivity(intent);
            }
        });
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString();
                final String password = passwordEditText.getText().toString();

                // email error
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Lütfen emailinizi giriniz", Toast.LENGTH_SHORT).show();
                    return;
                }
                //password error
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Lütfen parolanızı giriniz", Toast.LENGTH_SHORT).show();
                    return;
                }
                auth.signInWithEmailAndPassword(email,password)
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    startActivity(new Intent(LoginActivity.this,MainActivity.class));
                                    finish();
                                }else{
                                    Toast.makeText(getApplicationContext(),""+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                                    Log.e("Giriş Hatası",task.getException().getMessage());
                                }
                            }
                        });
            }
        });

    }

    }

