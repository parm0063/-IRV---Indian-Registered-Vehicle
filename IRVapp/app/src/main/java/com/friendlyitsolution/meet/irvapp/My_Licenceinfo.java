package com.friendlyitsolution.meet.irvapp;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Map;

public class My_Licenceinfo extends AppCompatActivity {

    TextView tfname,tswd,tdob,tbldgrp,tcontact,taddress,tlicence,tauthtodrive,tvfrom,tvtill;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my__licenceinfo);

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
                My_Licenceinfo.super.onBackPressed();
            }
        });



        tfname = (TextView)findViewById(R.id.tfname);
        tswd = (TextView)findViewById(R.id.tswd);
        tdob = (TextView)findViewById(R.id.tdob);
        tbldgrp = (TextView)findViewById(R.id.tbldgrp);
        tcontact = (TextView)findViewById(R.id.tcontact);
        taddress = (TextView)findViewById(R.id.taddress);
        tlicence = (TextView)findViewById(R.id.tlicence);
        tauthtodrive = (TextView)findViewById(R.id.tauthtodrive);
        tvfrom = (TextView)findViewById(R.id.tvfrom);
        tvtill = (TextView)findViewById(R.id.tvtill);


        Map<String,Object> licinfo=(Map<String, Object>)MyApp.userdata.get("licence");
        tfname.setText(licinfo.get("ufullname").toString());
        tswd.setText(licinfo.get("uswd").toString());
        tdob.setText(licinfo.get("udob").toString());
        tbldgrp.setText(licinfo.get("ubldgrp").toString());
        tcontact.setText(licinfo.get("uphone").toString());
        taddress.setText(licinfo.get("uaddress").toString());
        tlicence.setText(licinfo.get("udlno").toString());
        tauthtodrive.setText(licinfo.get("uauthtodrive").toString());
        tvfrom.setText(licinfo.get("uvfrom").toString());
        tvtill.setText(licinfo.get("uvtill").toString());

        /*MyApp.ref.child("users").child(MyApp.myid).child("licence").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Map<String,Object> licinfo = (Map<String, Object>) dataSnapshot.getValue();
                tfname.setText(licinfo.get("ufullname").toString());
                tswd.setText(licinfo.get("uswd").toString());
                tdob.setText(licinfo.get("udob").toString());
                tbldgrp.setText(licinfo.get("ubldgrp").toString());
                tcontact.setText(licinfo.get("uphone").toString());
                taddress.setText(licinfo.get("uaddress").toString());
                tlicence.setText(licinfo.get("udlno").toString());
                tauthtodrive.setText(licinfo.get("uauthtodrive").toString());
                tvfrom.setText(licinfo.get("uvfrom").toString());
                tvtill.setText(licinfo.get("uvtill").toString());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(My_Licenceinfo.this, "Try Again", Toast.LENGTH_SHORT).show();
            }
        });*/


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

                Intent i = new Intent(My_Licenceinfo.this,Mylogin.class);
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

}
