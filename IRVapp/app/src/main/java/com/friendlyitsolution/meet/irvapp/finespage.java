package com.friendlyitsolution.meet.irvapp;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.friendlyitsolution.meet.irvapp.recy.fines_adp;
import com.friendlyitsolution.meet.irvapp.recy.fines_model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class finespage extends AppCompatActivity {

    RecyclerView recyclerView;
    fines_adp mAdapter;
    List<fines_model> list;
    ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finespage);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        iv=(ImageView)findViewById(R.id.img);
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
                finespage.super.onBackPressed();
            }
        });
        recyclerView=(RecyclerView)findViewById(R.id.recy);
        list = new ArrayList<>();
        mAdapter = new fines_adp(list);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(MyApp.con);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
try {
    recyclerView.setVisibility(View.VISIBLE);
    iv.setVisibility(View.GONE);
    setData();
}
catch(Exception e)
{
    iv.setVisibility(View.VISIBLE);
    recyclerView.setVisibility(View.GONE);
    Toast.makeText(getApplicationContext(),"no fines for you",Toast.LENGTH_LONG).show();
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

                Intent i = new Intent(finespage.this,Mylogin.class);
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
    void setData()
    {
        Map<String,Object> fines=(Map<String, Object>)MyApp.userdata.get("fines");
        Map<String,Object> pending=(Map<String, Object>)fines.get("pending");

        List<String> allkeys=new ArrayList<>(pending.keySet());
        for(int i=0;i<allkeys.size();i++)
        {
            Map<String,String> data=(Map<String, String>)pending.get(allkeys.get(i));
            fines_model f=new fines_model(allkeys.get(i),data.get("rules"),data.get("amt"),data.get("date"),data.get("status"));
            list.add(f);

        }
        mAdapter.notifyDataSetChanged();


    }

}
