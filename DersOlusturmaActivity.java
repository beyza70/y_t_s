package com.example.javalogin;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class DersOlusturmaActivity extends AppCompatActivity {

    private EditText editTextOkulAdı2, editTextFakulteadı2, editTextBolumAdı2, editTextDersAdı, editTextDersKodu;
    private String txtOkulAdı2, txtFakulteAdı2, txtBolumAdı2, txtDersAdı, txtDersKodu;
    private Button buttonDersOlustur;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;
    private DatabaseReference mReferance;
    private HashMap<String, Object> mdata;
    private DatabaseReference rootDatabaseref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ders_olusturma);

        editTextOkulAdı2 = (EditText) findViewById(R.id.editTextOkulAdı2);
        editTextBolumAdı2 = (EditText) findViewById(R.id.editTextBolumAdı2);
        editTextFakulteadı2 = (EditText) findViewById(R.id.editTextFakulteAdı2);
        editTextDersAdı = (EditText) findViewById(R.id.editTextFakulteAdı2);
        editTextDersKodu = (EditText) findViewById(R.id.editTextDersKodu);
        buttonDersOlustur = (Button) findViewById(R.id.buttonDersOlustur);
        mAuth = FirebaseAuth.getInstance();
        mReferance = FirebaseDatabase.getInstance().getReference();


        sp = getSharedPreferences("GirisBilgi", MODE_PRIVATE);
        editor = sp.edit();


        txtOkulAdı2 = editTextOkulAdı2.getText().toString();
        txtFakulteAdı2 = editTextFakulteadı2.getText().toString();
        txtBolumAdı2 = editTextBolumAdı2.getText().toString();
        txtDersAdı = editTextDersAdı.getText().toString();
        txtDersKodu = editTextDersKodu.getText().toString();



        buttonDersOlustur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mUser = mAuth.getCurrentUser();
                mdata = new HashMap<>();

                    mReferance.child("Dersler").child(mUser.getUid())
                            .setValue(mdata)
                            .addOnCompleteListener(DersOlusturmaActivity.this, new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        mUser = mAuth.getCurrentUser();
                                        mdata = new HashMap<>();
                                        mdata.put("OkulAdı", txtOkulAdı2);
                                        mdata.put("FakulteAdı", txtFakulteAdı2);
                                        mdata.put("BolumAdı", txtBolumAdı2);
                                        mdata.put("DersAdı", txtDersAdı);
                                        mdata.put("DersKodu", txtDersKodu);
                                        mdata.put("DersId", mUser.getUid());
                                        mReferance.child("Dersler").child(mUser.getUid())
                                                .setValue(mdata)
                                                .addOnCompleteListener(DersOlusturmaActivity.this, new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful()) {
                                                            Toast.makeText(DersOlusturmaActivity.this, "Kayıt İşlemi Başarılı", Toast.LENGTH_SHORT).show();
                                                            startActivity(new Intent(DersOlusturmaActivity.this, AnaEkranActivity.class));
                                                        } else {
                                                            Toast.makeText(DersOlusturmaActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                });


                                }
                            };
                });

        };
    });
    }}


// if(!TextUtils.isEmpty(txtOkulAdı2)&&!TextUtils.isEmpty(txtBolumAdı2)&&!TextUtils.isEmpty(txtFakulteAdı2)&&!TextUtils.isEmpty(txtDersAdı)&& !TextUtils.isEmpty(txtDersKodu))
// {

//   }else{
//    Toast.makeText(DersOlusturmaActivity.this,"Boş Alanları Doldurunuz",Toast.LENGTH_SHORT).show();
// }