package com.friendlyitsolution.meet.irvapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

public class HomeActivity extends AppCompatActivity implements ewallet.OnFragmentInteractionListener,echallan.OnFragmentInteractionListener,towing.OnFragmentInteractionListener,alerts.OnFragmentInteractionListener,knowledge.OnFragmentInteractionListener {


    static Context con;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_ewallet:
                    getSupportFragmentManager().beginTransaction().replace(R.id.content,new ewallet()).commit();
                    return true;
                case R.id.navigation_echallan:
                    //getSupportFragmentManager().beginTransaction().replace(R.id.content,new echallan()).commit();

                    Intent i=new Intent(HomeActivity.this,finespage.class);
                    startActivity(i);

                    return true;
                case R.id.navigation_towing:
                    getSupportFragmentManager().beginTransaction().replace(R.id.content,new towing()).commit();
                    return true;
                case R.id.navigation_alert:
                    getSupportFragmentManager().beginTransaction().replace(R.id.content,new alerts()).commit();
                    return true;
                case R.id.navigation_knowledge:
                    getSupportFragmentManager().beginTransaction().replace(R.id.content,new knowledge()).commit();
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        con = this;

        getSupportFragmentManager().beginTransaction().replace(R.id.content,new ewallet()).commit();

        Intent i=getIntent();

        try{

            String data=i.getStringExtra("fine");
            if(data.equals("yes"))
            {

            }

        }
        catch(Exception e)
        {

        }


        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
