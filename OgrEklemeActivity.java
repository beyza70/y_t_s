package com.example.javalogin;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ProgressBar;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class OgrEklemeActivity extends AppCompatActivity {
    private Button buttonOgrnciKaydet;
    private Button buttonFotoCek;
    private EditText editTextOgnciAdıSoyadı,editTextOgrnciNumara, editTextOgrnciSinif,editTextOgnciOkulAdı,editTextOgrnciFakulte,editTextOgrnciBolum,editTextOgrnciDersAdı;
    private String  txtOgrnciAdıSoyadı,txtOgrnciNumara,txtOgrenciSınıf,txtOkulAdı3,txtFakulteAdı3 , txtBolumAdı3 , txtDersAdı3 ,txtogrid;


    private ProgressBar mProgressBar;
    private FirebaseStorage FirebaseStorage;
    private StorageReference mStorageRef;
    private Uri imageUri = null;
    private ImageView imageViewFoto;
    private FirebaseAuth mAuth;

    private FirebaseUser mUser;
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;
    private DatabaseReference mReferance;
    private HashMap<String,Object> mdata;
    private DatabaseReference rootDatabaseref;

    private static final int PICK_IMAGE_REQUEST = 1;


    private Uri mImageUri;
    private Uri m;
    private DatabaseReference mDatabaseRef;
    private StorageTask mUploadTask;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ogr_ekleme);



        mProgressBar = findViewById(R.id.progress_bar);
        editTextOgnciAdıSoyadı = (EditText) findViewById(R.id.editTextOgnciAdıSoyadı);
        editTextOgrnciNumara= (EditText) findViewById(R.id.editTextOgrnciNumara);
        editTextOgrnciSinif = (EditText) findViewById(R.id.editTextOgrnciSinif);
        editTextOgnciOkulAdı = (EditText) findViewById(R.id.editTextOgnciOkulAdı);
        editTextOgrnciFakulte = (EditText) findViewById(R.id.editTextOgrnciFakulte);
        editTextOgrnciBolum = (EditText) findViewById(R.id.editTextOgrnciBolum);
        editTextOgrnciDersAdı = (EditText) findViewById(R.id.editTextOgrnciDersAdı);

        mStorageRef = FirebaseStorage.getInstance().getReference("Öğrenci Fotoğrafları");
        mDatabaseRef = FirebaseDatabase.getInstance().getReference();

        mAuth =FirebaseAuth.getInstance();
        mReferance = FirebaseDatabase.getInstance().getReference();


        sp = getSharedPreferences("GirisBilgi", MODE_PRIVATE);
        editor = sp.edit();


        txtOgrnciAdıSoyadı=editTextOgnciAdıSoyadı.getText().toString();
        txtOgrnciNumara =editTextOgrnciNumara.getText().toString();
        txtOgrenciSınıf =editTextOgrnciSinif .getText().toString();
        txtOkulAdı3=editTextOgnciOkulAdı.getText().toString();
        txtFakulteAdı3  =editTextOgrnciFakulte.getText().toString();
        txtBolumAdı3  =editTextOgrnciBolum .getText().toString();
        txtDersAdı3=editTextOgrnciDersAdı.getText().toString();
        //txtogrid  =editTextDersKodu.getText().toString();

        buttonOgrnciKaydet=(Button)findViewById(R.id.buttonOgrnciKaydet);
        buttonFotoCek=(Button)findViewById(R.id.buttonFotoCek);
        imageViewFoto=(ImageView)findViewById(R.id.imageViewFoto);



        buttonFotoCek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFileChooser();
            }
        });






        buttonOgrnciKaydet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mUploadTask != null && mUploadTask.isInProgress()) {
                    Toast.makeText(OgrEklemeActivity.this, "Upload in progress", Toast.LENGTH_SHORT).show();
                } else {
                    uploadFile();
                }

                // if(!TextUtils.isEmpty(txtOkulAdı2)&&!TextUtils.isEmpty(txtBolumAdı2)&&!TextUtils.isEmpty(txtFakulteAdı2)&&!TextUtils.isEmpty(txtDersAdı)&& !TextUtils.isEmpty(txtDersKodu))
                // {

                mUser =mAuth.getCurrentUser();

               // Uri downloadUrl = imageViewFoto.getDownloadUrl();

                mdata = new HashMap<>();


                    mdata.put("Ögrenci Adı Soyadı:", txtOgrnciAdıSoyadı);
                    mdata.put("Ögrenci Numarası", txtOgrnciNumara);
                    mdata.put("Ögrenci Sınıfı:", txtOgrenciSınıf);
                    mdata.put("OkulAdı", txtOkulAdı3);
                    mdata.put("FakulteAdı", txtFakulteAdı3);
                    mdata.put("BolumAdı", txtBolumAdı3);
                    mdata.put("DersAdı", txtDersAdı3);
                    mdata.put("Öğrenci Fotoğrafı:", mDatabaseRef.toString());


                mdata.put("OgrId",mUser.getUid());

                mReferance.child("Öğrenciler").child(mUser.getUid())
                        .setValue(mdata)
                        .addOnCompleteListener(OgrEklemeActivity.this, new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(OgrEklemeActivity.this,"Kayıt İşlemi Başarılı",Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(OgrEklemeActivity.this, AnaEkranActivity.class));

                                }else{
                                    Toast.makeText(OgrEklemeActivity.this,task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                                }


                            }
                        });

                //   }else{
                //    Toast.makeText(DersOlusturmaActivity.this,"Boş Alanları Doldurunuz",Toast.LENGTH_SHORT).show();
                // }



            }


        });


}
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            mImageUri = data.getData();

            Picasso.with(this).load(mImageUri).into(imageViewFoto);
        }


        }


    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    private void uploadFile() {
        if (mImageUri != null) {
            StorageReference fileReference = mStorageRef.child(System.currentTimeMillis()
                    + "." + getFileExtension(mImageUri));

            mUploadTask = fileReference.putFile(mImageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    mProgressBar.setProgress(0);
                                }
                            }, 50);

                            Toast.makeText(OgrEklemeActivity.this, "Fotoğraf Kayıt Edildi!", Toast.LENGTH_LONG).show();
                            Upload upload = new Upload(txtOgrnciAdıSoyadı.toString().trim(),
                                    taskSnapshot.toString());
                            String uploadId = mDatabaseRef.push().getKey();
                            mDatabaseRef.child(uploadId).setValue(upload);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(OgrEklemeActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                            mProgressBar.setProgress((int) progress);

                        }
                    });
        } else {
            Toast.makeText(this, "No file selected", Toast.LENGTH_SHORT).show();
        }
    }






    }





