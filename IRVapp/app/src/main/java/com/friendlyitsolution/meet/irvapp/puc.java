package com.friendlyitsolution.meet.irvapp;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.friendlyitsolution.meet.irvapp.recy.puc_adp;
import com.friendlyitsolution.meet.irvapp.recy.puc_model;
import com.friendlyitsolution.meet.irvapp.recy.rc_adp;
import com.friendlyitsolution.meet.irvapp.recy.rc_model;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import gun0912.tedbottompicker.TedBottomPicker;

public class puc extends AppCompatActivity {

    Uri imguri;
    RecyclerView recyclerView;
    puc_adp mAdapter;
    List<puc_model> list;
    ProgressDialog pd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puc);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        pd = new ProgressDialog(puc.this);
        pd.setCancelable(false);
        pd.setMessage("please wait");
        ImageView lgout=(ImageView)findViewById(R.id.lgbtn);
        ImageView backbtn=(ImageView)findViewById(R.id.backbtn);
        lgout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                logOut();
            }
        });
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                puc.super.onBackPressed();
            }
        });



        recyclerView=(RecyclerView)findViewById(R.id.recy);
        list = new ArrayList<>();
        mAdapter = new puc_adp(list);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(MyApp.con);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        getData();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TedBottomPicker tedBottomPicker = new TedBottomPicker.Builder(puc.this)
                        .setOnImageSelectedListener(new TedBottomPicker.OnImageSelectedListener() {
                            @Override
                            public void onImageSelected(Uri uri) {

                                // Toast.makeText(getApplicationContext(),"get : "+uri,Toast.LENGTH_LONG).show();
                                if (uri != null) {
                                    imguri = uri;
                                    uploadImage(imguri);
                                }
                            }
                        })
                        .create();

                tedBottomPicker.show(getSupportFragmentManager());
            }
        });
    }


    void getData()
    {
        try{
            list.clear();
            Map<String,String> data=(Map<String, String>) MyApp.userdata.get("puc");
            List<String> allkeys=new ArrayList<>(data.keySet());
            for(int i=0;i<allkeys.size();i++)
            {

                list.add(new puc_model(data.get(allkeys.get(i)).toString()));


            }
            mAdapter.notifyDataSetChanged();

            MyApp.showToastMsg("Please wait while loading");

        }
        catch(Exception e)
        {
            MyApp.showToastMsg("no data found");
        }

    }



    void logOut()
    {


        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialoug_logout);
        dialog.show();

        final Button btnInDialog = (Button) dialog.findViewById(R.id.btn);
        btnInDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = MyApp.sharedpreferences.edit();
                editor.clear();
                editor.commit();
                finish();
                MyApp.mynumber="";
                MyApp.myname="";

                Intent i = new Intent(puc.this,Mylogin.class);
                startActivity(i);

                finish();
            }
        });
        final ImageView btnClose = (ImageView) dialog.findViewById(R.id.canclebtn);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });





    }

    private void uploadImage(final Uri uri) {
        //if there is a file to upload

        if (uri != null) {
            //  bnp.setVisibility(View.VISIBLE);
            pd.show();
            final String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            StorageReference mStorageRef = mStorageRef = FirebaseStorage.getInstance().getReference();
            StorageReference riversRef = mStorageRef.child("img/" + MyApp.mynumber + "/" + "PUC_"+timeStamp + ".jpg");

            riversRef.putFile(uri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            pd.dismiss();
                            Uri downloadUri = taskSnapshot.getMetadata().getDownloadUrl();
                              MyApp.ref.child("users").child(MyApp.mynumber).child("puc").push().setValue("" + downloadUri).addOnCompleteListener(new OnCompleteListener<Void>() {
                                  @Override
                                  public void onComplete(@NonNull Task<Void> task) {

                                      getData();
                                  }
                              });
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {

                    pd.dismiss();
                    Toast.makeText(getApplicationContext(), "Try again", Toast.LENGTH_LONG).show();

                }
            })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                            // pd.setProgress(((int) progress));

                        }
                    });
        } else {
            Toast.makeText(getApplicationContext(), "Try again ..", Toast.LENGTH_LONG).show();

        }
    }

}
