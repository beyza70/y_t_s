
package com.example.javalogin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private Button buttonOgrnci;
    private Button buttonOgretmen;
    private ImageView img1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonOgrnci = (Button) findViewById(R.id.buttonOgrnci);
        buttonOgretmen = (Button) findViewById(R.id.buttonOgretmen);
        img1 = (ImageView) findViewById(R.id.img1);

        buttonOgrnci.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        buttonOgretmen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(MainActivity.this, ilkActivity.class));


            }
        });
    }
}