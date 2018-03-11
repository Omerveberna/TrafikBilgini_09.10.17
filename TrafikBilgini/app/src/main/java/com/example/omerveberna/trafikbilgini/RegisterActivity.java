package com.example.omerveberna.trafikbilgini;


import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.omerveberna.trafikbilgini.LoginActivity;
import com.example.omerveberna.trafikbilgini.MainActivity;
import com.example.omerveberna.trafikbilgini.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {
    private EditText userEmail,userPassword,userAd,userSoyad;
    private Button yeniUyeButton,userLoginButton;
    private  FirebaseAuth auth;
    private DatabaseReference databaseReference;
    private  String email;
    private String ad;
    private String soyad;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        auth = FirebaseAuth.getInstance();

        databaseReference = FirebaseDatabase.getInstance().getReference("users");

        userAd =(EditText)findViewById(R.id.uyeAd);
        userSoyad = (EditText)findViewById(R.id.uyeSoyad);
        userEmail =(EditText)findViewById(R.id.uyeEmail);
        userPassword =(EditText)findViewById(R.id.uyeParola);
        yeniUyeButton =(Button)findViewById(R.id.yeniUyeButton);
        userLoginButton =(Button)findViewById(R.id.uyeGirisButton);

        yeniUyeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerFunction();
            }
        });

        userLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(intent);
                registerFunction();


            }
        });
    }

    private void registerFunction(){
        ad = userAd.getText().toString();
        soyad = userSoyad.getText().toString();
        email = userEmail.getText().toString();

        String parola = userPassword.getText().toString();

        if(TextUtils.isEmpty(email)){
            Toast.makeText(getApplicationContext(),"Lütfen emailinizi giriniz",Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(parola)){
            Toast.makeText(getApplicationContext(),"Lütfen parolanızı giriniz",Toast.LENGTH_SHORT).show();
        }
        if (parola.length()<6){
            Toast.makeText(getApplicationContext(),"Parola en az 6 haneli olmalıdır", Toast.LENGTH_SHORT).show();
        }

        auth.createUserWithEmailAndPassword(email,parola).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(!task.isSuccessful()){
                    Toast.makeText(RegisterActivity.this, "Yetkilendirme Hatası",
                            Toast.LENGTH_SHORT).show();
                }
                //İşlem başarılı olduğu takdir de giriş yapılıp MainActivity e yönlendiriyoruz.
                else {
                    databaseReference = FirebaseDatabase.getInstance().getReference("users/"+FirebaseAuth.getInstance().getCurrentUser().getUid());
                    Users user = new Users(ad,soyad,0);
                    databaseReference.setValue(user);
                    startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                    finish();
                }

            }
        });


    }
}
