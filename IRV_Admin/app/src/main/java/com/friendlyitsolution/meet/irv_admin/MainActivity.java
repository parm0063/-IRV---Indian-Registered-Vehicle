package com.friendlyitsolution.meet.irv_admin;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.friendlyitsolution.meet.irv_admin.dummy.DummyContent;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements echallan.OnFragmentInteractionListener,ItemFragment.OnListFragmentInteractionListener{

    public static Context con;
    ImageView logoutbtn;
    SharedPreferences pref;
    Spinner spinner;
    List<String> menus;

    public static ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        con=getApplicationContext();
        pd=new ProgressDialog(MainActivity.this);
        pd.setCancelable(false);
        pd.setMessage("please wait");
        logoutbtn = (ImageView)findViewById(R.id.logoutbtn);
        menus=new ArrayList<>();
        menus.add("Licence Approval");
        menus.add("E-Challan");
        spinner = (Spinner) findViewById(R.id.sp);
        ArrayAdapter<String> adapter =new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,menus);
    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if(i==0)
                {
                    getSupportFragmentManager().beginTransaction().replace(R.id.mycont,new ItemFragment()).commit();

                }
                else if(i==1)
                {
                    getSupportFragmentManager().beginTransaction().replace(R.id.mycont,new echallan()).commit();

                }
                else
                {

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        getSupportFragmentManager().beginTransaction().replace(R.id.mycont,new ItemFragment()).commit();

        pref = getSharedPreferences("myshared",MODE_PRIVATE);


        logoutbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("uid","");
                editor.putString("upass","");
                editor.commit();

                Intent logout = new Intent(getApplicationContext(),Login_Activity.class);
                startActivity(logout);
                finish();
            }
        });
    }

    @Override
    public void onListFragmentInteraction(DummyContent.DummyItem item) {

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
