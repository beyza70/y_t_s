package com.example.javalogin;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ilkActivity extends AppCompatActivity {
    private EditText editTextUserName, editTextPassword;
    private String txtUserName , txtSifre;
    private Button buttonGiris;
    private Button buttonkyt;
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ilk);

        editTextUserName = (EditText) findViewById(R.id.editTextTextPersonName2);
        editTextPassword = (EditText) findViewById(R.id.editTextTextPersonPassword2);
        buttonGiris = (Button) findViewById(R.id.buttonKaydet);
        buttonkyt = (Button) findViewById(R.id.buttonkyt);

        sp = getSharedPreferences("GirisBilgi", MODE_PRIVATE);
        editor = sp.edit();

        mAuth = FirebaseAuth.getInstance();

        buttonkyt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(ilkActivity.this, KytActivity.class));


            }
        });

    }

    public void buttonGiriş(View view){
        txtUserName =editTextUserName.getText().toString();
        txtSifre =editTextPassword.getText().toString();

        if(!TextUtils.isEmpty(txtUserName)&& !TextUtils.isEmpty(txtSifre)){
            mAuth.signInWithEmailAndPassword(txtUserName,txtSifre)
                    .addOnSuccessListener(this, new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            mUser = mAuth.getCurrentUser();

                            System.out.println("Kullanıcı Email:"+ mUser.getEmail());
                            System.out.println("Kullanıcı Adı:"+ mUser.getDisplayName());
                            System.out.println("Kullanıcı Uid:"+ mUser.getUid());

                            startActivity(new Intent(ilkActivity.this, AnaEkranActivity.class));



                        }
                    }).addOnFailureListener(this, new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(ilkActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();

                }
            });

        }else{
            Toast.makeText(getApplicationContext(), "Giriş Hatalı", Toast.LENGTH_SHORT).show();

        }


    }



}
