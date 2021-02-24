package com.friendlyitsolution.meet.irvapp;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import de.hdodenhof.circleimageview.CircleImageView;

public class Myhome extends AppCompatActivity {

    RelativeLayout lic,echallan,traff,myrc,puc;
    ImageView ivstatus;
    TextView tvstatus;
    CircleImageView iv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myhome);
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
                Myhome.super.onBackPressed();
            }
        });

        tvstatus=(TextView)findViewById(R.id.tvname);
        ivstatus=(ImageView)findViewById(R.id.ivstatus);

        iv=(CircleImageView)findViewById(R.id.profile_image);
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(MyApp.con,profile.class);

                startActivity(i);
            }
        });
        if(MyApp.userdata.containsKey("dpurl"))
        {
            Glide.with(iv.getContext()).load(MyApp.userdata.get("dpurl")+"")
                    .override(100, 100)
                    .fitCenter()
                    .into(iv);

        }


        lic=(RelativeLayout)findViewById(R.id.lic);
        lic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(MyApp.con,Myallpage.class);
                i.putExtra("flag","1");
                startActivity(i);
            }
        });

        puc=(RelativeLayout)findViewById(R.id.puc);
        puc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(MyApp.con,puc.class);
                startActivity(i);
            }
        });
        myrc=(RelativeLayout)findViewById(R.id.myrc);
        myrc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(MyApp.con,myrcbook.class);

                startActivity(i);
            }
        });


        echallan=(RelativeLayout)findViewById(R.id.rel3);
        echallan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(MyApp.con,finespage.class);

                startActivity(i);
            }
        });

        traff=(RelativeLayout)findViewById(R.id.traf);
        traff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(MyApp.con,MapsActivity.class);

                startActivity(i);
            }
        });
        setStatus();
    }
     void setStatus()
    {

        if(MyApp.userdata.get("status").equals("pending"))
        {

            tvstatus.setText(MyApp.userdata.get("name")+"");
            ivstatus.setImageResource(R.drawable.exclamation_mark);
        }
        else
        {

            tvstatus.setText(MyApp.userdata.get("name")+"");
            ivstatus.setImageResource(R.drawable.verified);


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

                Intent i = new Intent(Myhome.this,Mylogin.class);
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

    @Override
    protected void onResume() {
        super.onResume();
        if(MyApp.userdata.containsKey("dpurl"))
        {
            Glide.with(iv.getContext()).load(MyApp.userdata.get("dpurl")+"")
                    .override(100, 100)
                    .fitCenter()
                    .into(iv);

        }
    }
}
