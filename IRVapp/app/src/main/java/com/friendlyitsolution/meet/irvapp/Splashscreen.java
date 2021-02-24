package com.friendlyitsolution.meet.irvapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class Splashscreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);
        Thread th=new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    sleep(2500);
                    Intent i=new Intent(getApplicationContext(),Mylogin.class);
                    startActivity(i);
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        th.start();


    }

}
