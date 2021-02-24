package com.friendlyitsolution.meet.irvapp;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.friendlyitsolution.meet.irvapp.recy.rc_adp;
import com.friendlyitsolution.meet.irvapp.recy.rc_model;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class myrcbook extends AppCompatActivity {

    ProgressDialog pg;
    RecyclerView recyclerView;
    rc_adp mAdapter;
    List<rc_model> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myrcbook);
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
                myrcbook.super.onBackPressed();
            }
        });



        pg=new ProgressDialog(myrcbook.this);
        pg.setMessage("please wait");
        pg.setCancelable(false);


        recyclerView=(RecyclerView)findViewById(R.id.recy);
        list = new ArrayList<>();
        mAdapter = new rc_adp(list);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(MyApp.con);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        getData();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addRcbook();
             }
        });
    }
    void addRcbook()
    {


        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.rc_add);
        dialog.show();
        final EditText etvclass,etvehicleno,etchassisno;
        Button donebtn;
        donebtn=(Button)dialog.findViewById(R.id.donebtn);
        etchassisno=(EditText)dialog.findViewById(R.id.etchassisno);
        etvclass=(EditText)dialog.findViewById(R.id.etvclass);
        etvehicleno=(EditText)dialog.findViewById(R.id.etvehicleno);


        donebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pg.show();
                Map<String,Object> data=new HashMap<>();
                data.put("chassis",etchassisno.getText().toString());
                data.put("class",etvclass.getText().toString());
                data.put("vehicalno",etvehicleno.getText().toString());

                MyApp.ref.child("users").child(MyApp.myid).child("rcbook").push().setValue(data).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        MyApp.showToastMsg("Successfully submitted");
                        dialog.dismiss();
                        pg.dismiss();
                        getData();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        MyApp.showToastMsg("Please try again later");
                        pg.dismiss();
                    }
                });

            }
        });


    }

    void getData()
    {
        try{
            list.clear();
            Map<String,Object> data=(Map<String, Object>) MyApp.userdata.get("rcbook");
            List<String> allkeys=new ArrayList<>(data.keySet());
            for(int i=0;i<allkeys.size();i++)
            {

                Map<String,Object> temp=(Map<String, Object>)data.get(allkeys.get(i));
                list.add(new rc_model(temp));


            }
            mAdapter.notifyDataSetChanged();



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

                Intent i = new Intent(myrcbook.this,Mylogin.class);
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


}
