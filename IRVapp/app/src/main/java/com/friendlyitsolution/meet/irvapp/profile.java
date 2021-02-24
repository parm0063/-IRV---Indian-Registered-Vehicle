package com.friendlyitsolution.meet.irvapp;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.text.SimpleDateFormat;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;
import gun0912.tedbottompicker.TedBottomPicker;

public class profile extends AppCompatActivity {

    CircleImageView iv;
    TextView edit;
    Uri imguri = null;
    ProgressDialog pd;
    MaterialEditText et;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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
               profile.super.onBackPressed();
            }
        });



        pd = new ProgressDialog(profile.this);
        pd.setCancelable(false);
        pd.setMessage("please wait");
        btn = (Button) findViewById(R.id.btn);
        iv = (CircleImageView) findViewById(R.id.profile_image);
        edit = (TextView) findViewById(R.id.edit);
        edit.setVisibility(View.GONE);
        et = (MaterialEditText) findViewById(R.id.etname);
        setprofile();
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TedBottomPicker tedBottomPicker = new TedBottomPicker.Builder(profile.this)
                        .setOnImageSelectedListener(new TedBottomPicker.OnImageSelectedListener() {
                            @Override
                            public void onImageSelected(Uri uri) {

                                // Toast.makeText(getApplicationContext(),"get : "+uri,Toast.LENGTH_LONG).show();
                                if (uri != null) {
                                    imguri = uri;
                                    iv.setImageURI(uri);
                                    edit.setVisibility(View.VISIBLE);
                                }
                            }
                        })
                        .create();

                tedBottomPicker.show(getSupportFragmentManager());

            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadImage(imguri);

            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (et.getText().toString().equals("")) {
                    et.setError("please enter this");
                } else {
                    MyApp.ref.child("users").child(MyApp.mynumber).child("name").setValue(et.getText().toString());
                }
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
            StorageReference riversRef = mStorageRef.child("img/" + MyApp.mynumber + "/" + MyApp.mynumber + ".jpg");

            riversRef.putFile(uri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            pd.dismiss();
                            Uri downloadUri = taskSnapshot.getMetadata().getDownloadUrl();
                            edit.setVisibility(View.GONE);
                            MyApp.ref.child("users").child(MyApp.mynumber).child("dpurl").setValue("" + downloadUri);
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

                Intent i = new Intent(profile.this,Mylogin.class);
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




       /* new AwesomeInfoDialog(this)
                .setTitle("Logout")
                .setMessage("Want To Logout?")
                .setColoredCircle(R.color.colorAccent)
                .setDialogIconAndColor(R.drawable.logoutbtn, R.color.white)
                .setCancelable(true)
                .setPositiveButtonText(getString(R.string.dialog_yes_button))
                .setPositiveButtonbackgroundColor(R.color.colorAccent)
                .setPositiveButtonTextColor(R.color.white)
                .setNegativeButtonText(getString(R.string.dialog_no_button))
                .setNegativeButtonbackgroundColor(R.color.colorAccent)
                .setNegativeButtonTextColor(R.color.white)
                .setPositiveButtonClick(new Closure() {
                    @Override
                    public void exec() {
                        //click

                        SharedPreferences sharedPreferences = getSharedPreferences("myinfo", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.clear();
                        editor.commit();
                        finish();


                        Intent i = new Intent(new_home.this,Login.class);
                        startActivity(i);

                        finish();

                    }
                })
                .setNegativeButtonClick(new Closure() {
                    @Override
                    public void exec() {
                        //click


                    }
                })
                .show();
        */

    }

    void setprofile() {
        try {

            if (!MyApp.userdata.get("dpurl").toString().equals(" ")) {
                Glide.with(iv.getContext()).load(MyApp.userdata.get("dpurl").toString())
                        .override(100, 100)
                        .fitCenter()
                        .into(iv);
            }
            et.setText(MyApp.userdata.get("name")+"");

        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Something went wrong please reopen app", Toast.LENGTH_LONG).show();
        }

    }
}
