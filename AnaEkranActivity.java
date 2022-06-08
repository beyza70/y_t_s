package com.example.javalogin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AnaEkranActivity extends AppCompatActivity {
    private Button buttonÖgrEkleme;
    private Button buttonDersOlusturma;
    private Button buttonYoklamaAlma;
    private Button buttonYoklamaListesi;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ana_ekran);

        buttonÖgrEkleme = (Button) findViewById(R.id.buttonÖgrEkleme);
        buttonDersOlusturma = (Button) findViewById(R.id.buttonDersOlusturma);
        buttonYoklamaAlma = (Button) findViewById(R.id.buttonYoklamaAlma);
        buttonYoklamaListesi = (Button) findViewById(R.id.buttonYoklamaListesi);

        buttonÖgrEkleme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AnaEkranActivity.this, OgrEklemeActivity.class));
            }
        });

        buttonDersOlusturma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AnaEkranActivity.this, DersOlusturmaActivity.class));
            }
        });

        buttonYoklamaAlma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AnaEkranActivity.this, YoklamaAlmaActivity.class));
            }
        });

        buttonYoklamaListesi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AnaEkranActivity.this, YoklamaListesiActivity.class));
            }
        });




        }






    }



