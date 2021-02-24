package com.friendlyitsolution.meet.irv_admin;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Login_Activity extends AppCompatActivity {

    EditText etadminid,etadminpass;
    Button lgnbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_);

        etadminid = (EditText)findViewById(R.id.etadminid);
        etadminpass = (EditText)findViewById(R.id.etadminpass);
        lgnbtn = (Button)findViewById(R.id.lgnbtn);


        final SharedPreferences pref = getSharedPreferences("myshared",MODE_PRIVATE);
        String userid = pref.getString("uid","");
        if (!userid.equals(""))
        {
            Intent sh = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(sh);
            finish();
        }


        lgnbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (etadminid.getText().toString().equals("admin") && etadminpass.getText().toString().equals("admin"))
                {
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString("uid",etadminid.getText().toString());
                    editor.putString("upass",etadminpass.getText().toString());
                    editor.commit();

                    Intent login = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(login);
                    finish();
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Invalid username or password", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

}
