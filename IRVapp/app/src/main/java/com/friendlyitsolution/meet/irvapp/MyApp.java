package com.friendlyitsolution.meet.irvapp;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.Map;

/**
 * Created by hiren on 01/10/2017.
 */

public class MyApp extends Application {

    public static Context con;

    public static ValueEventListener velistener;
    static FirebaseDatabase fd;
    static DatabaseReference ref;
    static String myid,mypass,mycontact,myemail;

    public static Map<String,Object> userdata;
    public static String mynumber,myname;

    public static DatabaseReference userref;
static SharedPreferences sharedpreferences;
    @Override
    public void onCreate() {
        super.onCreate();

        con=getApplicationContext();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        fd=FirebaseDatabase.getInstance();
        ref=fd.getReference();
      sharedpreferences = getSharedPreferences("myinfo", Context.MODE_PRIVATE);
getSharedData();
        if(!myid.equals(""))
        {
            mynumber=myid;
            getUserData();

            FirebaseMessaging.getInstance().subscribeToTopic(myid);
        }
    }

    static void getSharedData()
    {
        mynumber=sharedpreferences.getString("mynumber","");


        myid=sharedpreferences.getString("mynumber","");
        mypass=sharedpreferences.getString("upass","");
        mycontact=sharedpreferences.getString("ucont","");
        myemail=sharedpreferences.getString("uemail","");

    }

    static void showToastMsg(String msg)
    {
        Toast.makeText(con,msg,Toast.LENGTH_LONG).show();
    }

   static void getUserData()
    {
        userref=fd.getReference("users").child(myid);
        velistener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                userdata=(Map<String, Object>)dataSnapshot.getValue();
                try{//ewallet.setStatus();
                     }
                catch(Exception e)
                {}
               //Toast.makeText(con,"Data : "+userdata,Toast.LENGTH_LONG).show();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        userref.keepSynced(true);
        userref.addValueEventListener(velistener);
    }

    public static void clearUserData()
    {
        userref.removeEventListener(velistener);
        userdata.clear();
    }
}
