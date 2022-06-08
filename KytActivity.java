package com.example.javalogin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class KytActivity extends AppCompatActivity {

    private EditText editTextUserName, editTextPassword,editTextAdıSoyadı, editTextOkulAdı,editTextFakulteadı, editTextBolumAdı;
    private String txtUserName , txtSifre,txtAdıSoyadı , txtOkulAdı,txtFakulteAdı , txtBolumAdı;
    private Button buttonKaydet;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;
    private DatabaseReference mReferance;
    private HashMap<String,Object> mdata;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kyt);
    }

    public void butonKayıtOl(View v){


        editTextUserName = (EditText) findViewById(R.id.editTextTextPersonName2);
        editTextPassword = (EditText) findViewById(R.id.editTextTextPersonPassword2);
        editTextAdıSoyadı = (EditText) findViewById(R.id.editTextAdıSoyadı);
        editTextOkulAdı = (EditText) findViewById(R.id.editTextOkulAdı);
        editTextBolumAdı= (EditText) findViewById(R.id.editTextBolumAdı);
        editTextFakulteadı = (EditText) findViewById(R.id.editTextFakulteAdı);
        buttonKaydet = (Button) findViewById(R.id.buttonKaydet);
        mAuth =FirebaseAuth.getInstance();
        mReferance = FirebaseDatabase.getInstance().getReference();


        sp = getSharedPreferences("GirisBilgi", MODE_PRIVATE);
        editor = sp.edit();

        txtUserName =editTextUserName.getText().toString();
        txtSifre =editTextPassword.getText().toString();
        txtAdıSoyadı =editTextAdıSoyadı.getText().toString();
        txtOkulAdı =editTextOkulAdı.getText().toString();
        txtFakulteAdı =editTextFakulteadı.getText().toString();
        txtBolumAdı =editTextBolumAdı.getText().toString();



        if(!TextUtils.isEmpty(txtAdıSoyadı)&&!TextUtils.isEmpty(txtOkulAdı)&&!TextUtils.isEmpty(txtBolumAdı)&&!TextUtils.isEmpty(txtFakulteAdı)&&!TextUtils.isEmpty(txtUserName)&& !TextUtils.isEmpty(txtSifre)){
            mAuth.createUserWithEmailAndPassword(txtUserName,txtSifre)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){

                                mUser =mAuth.getCurrentUser();

                                mdata = new HashMap<>();
                                mdata.put("ÖğretmenAdi",txtAdıSoyadı);
                                mdata.put("OkulAdı",txtOkulAdı);
                                mdata.put("FakulteAdı",txtFakulteAdı);
                                mdata.put("BolumAdı",txtBolumAdı);
                                mdata.put("E-mail",txtUserName);
                                mdata.put("Şifre",txtSifre);
                                mdata.put("KullaniciId",mUser.getUid());

                                mReferance.child("Kullanicilar").child(mUser.getUid())
                                        .setValue(mdata)
                                        .addOnCompleteListener(KytActivity.this, new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if(task.isSuccessful()){
                                                    Toast.makeText(KytActivity.this,"Kayıt İşlemi Başarılı",Toast.LENGTH_SHORT).show();
                                                    startActivity(new Intent(KytActivity.this, MainActivity.class));

                                                }else{
                                                    Toast.makeText(KytActivity.this,task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                                                }


                                            }
                                        });

                                }
                            else
                                Toast.makeText(KytActivity.this,task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    });

        }else{
            Toast.makeText(getApplicationContext(), "Kayıt İşlemi Başarısız", Toast.LENGTH_SHORT).show();

        }



    }




    }
