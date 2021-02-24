package com.friendlyitsolution.meet.irvapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class MainActivity extends AppCompatActivity {
    TextView tv11,tv12;
    ImageView iv11,iv12,iv13;
    EditText et11,et12;
    Button btnlogin1,btnsignup1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // All components mapping
        tv11=(TextView)findViewById(R.id.tv11);
        tv12=(TextView)findViewById(R.id.tv12);
        iv11=(ImageView)findViewById(R.id.iv11);
        iv12=(ImageView)findViewById(R.id.iv12);
        iv13=(ImageView)findViewById(R.id.iv13);
        et11=(EditText)findViewById(R.id.et11);
        et12=(EditText)findViewById(R.id.et12);
        btnlogin1=(Button)findViewById(R.id.btnlogin1);
        btnsignup1=(Button)findViewById(R.id.btnsignup1);


        // Get Intent from SplashScreen
        //Intent i=getIntent();


        //btnsignup1 click event
        btnsignup1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in=new Intent(getApplicationContext(),Main2Activity.class);
                startActivity(in);
            }
        });



        if(!MyApp.myid.equals(""))
        {
            if(!MyApp.userdata.containsKey("licence"))
            {

                Intent i=new Intent(getApplicationContext(),FormActivity.class);
                startActivity(i);
                finish();
            }
            else
            {
                Intent im=new Intent(getApplicationContext(),HomeActivity.class);
                startActivity(im);
                finish();
            }
            /*
            MyApp.ref.child("users").child(MyApp.myid).child("licence").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    if (dataSnapshot.getValue()==null)
                    {
                        Intent i=new Intent(getApplicationContext(),FormActivity.class);
                        startActivity(i);
                        finish();
                    }
                    else
                    {
                        Intent im=new Intent(getApplicationContext(),HomeActivity.class);
                        startActivity(im);
                        finish();
                    }


                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });*/
        }


        btnlogin1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                final String uid=et11.getText().toString();
                final String upass=et12.getText().toString();

                if(uid.equals("") || upass.equals(""))
                {
                    if(uid.equals(""))
                    {
                        et11.setError("Enter id");
                    }
                    else
                    {
                        et12.setError("Enter Password");
                    }
                }
                else
                {
                    final ProgressDialog p=new ProgressDialog(MainActivity.this);
                    p.setMessage("please wait");
                    p.setTitle("Login");
                    p.show();

                    MyApp.ref.child("users").child(uid).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            if(dataSnapshot.getValue()!=null)
                            {
                                p.dismiss();
                                Map<String, Object> mm = (Map<String, Object>) dataSnapshot.getValue();


                                String password=""+mm.get("pass");

                                if(password.equals(upass))
                                {
                                    MyApp.userdata=mm;
                                    //MyApp.mypass=upass;
                                    MyApp.myid=uid;
                                    //MyApp.myemail=""+mm.get("email");
                                    //MyApp.mycontact=""+mm.get("contact");

                                    /*MyApp.ewname=""+mm.get("formname");
                                    MyApp.ewswd=""+mm.get("formswd");
                                    MyApp.ewdob=""+mm.get("formdob");
                                    MyApp.ewbloodgrp=""+mm.get("formbloodgrp");
                                    MyApp.ewcontact=""+mm.get("formcontact");
                                    MyApp.ewaddress=""+mm.get("formaddress");
                                    MyApp.ewfdate=""+mm.get("formfdate");
                                    MyApp.ewldate=""+mm.get("formldate");
                                    MyApp.ewlicnum=""+mm.get("formlicnum");
                                    MyApp.ewlicvehtype=""+mm.get("formlicvehtype");*/

                                    SharedPreferences sharedpreferences = getSharedPreferences("myinfo", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedpreferences.edit();
                                    editor.putString("uid", MyApp.myid);
                                    //editor.putString("upass", MyApp.mypass);
                                    //editor.putString("ucont",MyApp.mycontact);
                                    //editor.putString("uemail",MyApp.myemail);
                                    editor.commit();


                                    if(!MyApp.userdata.containsKey("licence"))
                                    {

                                        Intent i=new Intent(getApplicationContext(),FormActivity.class);
                                        startActivity(i);
                                        finish();
                                    }
                                    else
                                    {
                                        Intent im=new Intent(getApplicationContext(),HomeActivity.class);
                                        startActivity(im);
                                        finish();
                                    }



                                    /*MyApp.ref.child("users").child(MyApp.myid).child("licence").addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            if (dataSnapshot.getValue()==null)
                                            {
                                                p.dismiss();
                                                Intent ifill=new Intent(getApplicationContext(),FormActivity.class);
                                                startActivity(ifill);
                                                finish();
                                            }
                                            else
                                            {
                                                p.dismiss();
                                                Intent ifilled=new Intent(getApplicationContext(),HomeActivity.class);
                                                startActivity(ifilled);
                                                finish();
                                            }
                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {

                                        }
                                    });*/
                                    /*Intent i=new Intent(getApplicationContext(),FormActivity.class);
                                    startActivity(i);
                                    finish();*/
                                }
                                else
                                {
                                    et12.setError("wrong password");
                                    p.dismiss();
                                }

                            }
                            else
                            {
                                et11.setError("wrong id");
                                Toast.makeText(MainActivity.this, "Wrong username or password", Toast.LENGTH_SHORT).show();
                                p.dismiss();
                            }


                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            p.dismiss();
                            Toast.makeText(getApplicationContext(),"Please try again",Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
