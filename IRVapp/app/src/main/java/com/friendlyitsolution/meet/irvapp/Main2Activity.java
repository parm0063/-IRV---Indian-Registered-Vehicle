package com.friendlyitsolution.meet.irvapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;

import java.util.HashMap;
import java.util.Map;

public class Main2Activity extends AppCompatActivity {
    TextView tv21;
    EditText et21,et22,et23,et24;
    Button btnsignup2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);


        // All components mapping
        tv21=(TextView)findViewById(R.id.tv21);
        et21=(EditText)findViewById(R.id.et21);
        et22=(EditText)findViewById(R.id.et22);
        et23=(EditText)findViewById(R.id.et23);
        et24=(EditText)findViewById(R.id.et24);
        btnsignup2=(Button)findViewById(R.id.btnsignup2);


        // Get Intent from MainActivity
        //Intent in=getIntent();

        btnsignup2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                final String uid = et21.getText().toString();
                final String upass = et23.getText().toString();
                final String uemail = et22.getText().toString();
                final String ucontact = et24.getText().toString();

                if (uid.equals("") || upass.equals("") || uemail.equals("") || ucontact.equals(""))
                {
                    if (uid.equals("")) {
                        et21.setError("Enter username");
                    }
                    if (uemail.equals("")) {
                        et22.setError("Enter email id");
                    }
                    if (upass.equals("")) {
                        et23.setError("Enter password");
                    }
                    if (ucontact.equals("")) {
                        et24.setError("Enter contact number");
                    }
                }
                else
                {


                    final ProgressDialog pg = new ProgressDialog(Main2Activity.this);
                    pg.setMessage("Registring..");
                    pg.setTitle("Register");
                    pg.show();

                    Map<String, String> allval = new HashMap<String, String>();
                    allval.put("pass", et23.getText().toString());
                    allval.put("contact", et24.getText().toString());
                    allval.put("email", et22.getText().toString());
                    allval.put("status","pending");

                    MyApp.ref.child("users").child(et21.getText().toString()).setValue(allval).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            pg.dismiss();

                            Toast.makeText(getApplicationContext(), "Registered Successfully", Toast.LENGTH_LONG).show();

                            MyApp.myid = et21.getText().toString();
                            MyApp.myemail = et22.getText().toString();
                            MyApp.mypass = et23.getText().toString();
                            MyApp.mycontact = et24.getText().toString();

                            SharedPreferences sharedpreferences = getSharedPreferences("myinfo", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedpreferences.edit();
                            editor.putString("uid", MyApp.myid);
                            editor.putString("upass", MyApp.mypass);
                            editor.putString("ucont",MyApp.mycontact);
                            editor.putString("uemail",MyApp.myemail);
                            editor.commit();

                            MyApp.getSharedData();
                            MyApp.getUserData();
                            Intent i = new Intent(Main2Activity.this,FormActivity.class);
                            startActivity(i);
                            finish();

                            /*Intent i = new Intent(Main2Activity.this, MainActivity.class);
                            startActivity(i);
                            finish();*/
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            pg.dismiss();
                            Toast.makeText(Main2Activity.this, "Try Again", Toast.LENGTH_LONG).show();
                        }
                    });

                }

            }

        });


    }

}
