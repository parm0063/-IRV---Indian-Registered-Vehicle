package com.friendlyitsolution.meet.irv_admin;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class userinfo extends AppCompatActivity {

    Button btn;
    CheckBox r100,r200,r300;
    TextView name,status,licinfo;
    ProgressDialog pd;
    int amt=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userinfo);
        pd=new ProgressDialog(userinfo.this);
        pd.setMessage("please wait");
        pd.setCancelable(false);
     name=(TextView)findViewById(R.id.name);
     status=(TextView)findViewById(R.id.status);
     r100=(CheckBox)findViewById(R.id.rs100);
        r200=(CheckBox)findViewById(R.id.rs200);
        r300=(CheckBox)findViewById(R.id.rs300);
        btn=(Button)findViewById(R.id.btn);
        licinfo=(TextView)findViewById(R.id.lic_info);

        name.setText(Myapp.uid);
        status.setText(Myapp.userdata.get("status")+"");

        Map<String,String> lic=(Map<String, String>)Myapp.userdata.get("licence");
        licinfo.setText(""+lic);

        r100.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b)
                {
                    amt=amt+100;
                }
                else
                {
                    amt=amt-100;
                }

                btn.setText("Fines( "+amt+" Rs /- )");
            }
        });

        r300.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b)
                {
                    amt=amt+300;
                }
                else
                {
                    amt=amt-300;
                }
                btn.setText("Fines( "+amt+" Rs /- )");

            }
        });


        r200.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b)
                {
                    amt=amt+200;
                }
                else
                {
                    amt=amt-200;
                }
                btn.setText("Fines( "+amt+" Rs /- )");

            }
        });




        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pd.show();
                Calendar c = Calendar.getInstance();
                SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
                String formattedDate = df.format(c.getTime());

                final Map<String,String> data=new HashMap<>();
                data.put("uid",Myapp.uid);
                data.put("date",formattedDate);
                data.put("rules","This are rules");
                data.put("amt",""+amt);
                data.put("status","pending");

               final String key= Myapp.ref.child("fines").child("pending").push().getKey();
                Myapp.ref.child("fines").child("pending").child(key).setValue(data).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        Myapp.ref.child("users").child(Myapp.uid).child("fines").child("pending").child(key).setValue(data).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                pd.dismiss();
                                sendNotificationTo(Myapp.uid,"Fines","Rs . "+amt+"/ - ");
                                btn.setText("Submited");
                                btn.setEnabled(false);

                            }
                        });

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
pd.dismiss();
                        Toast.makeText(getApplicationContext(),"Try again",Toast.LENGTH_LONG).show();
                    }
                });


            }
        });

    }

    void sendNotificationTo(String to,String nn,String mm)
    {
        String sst="/topics/"+to;
        JSONObject json = new JSONObject();
        try {
            JSONObject userData=new JSONObject();
            userData.put("title",nn);
            userData.put("body",mm);
            //  userData.put("sound","default");

            json.put("data",userData);
            json.put("to", sst);
        }
        catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest("https://fcm.googleapis.com/fcm/send", json, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //   Toasty.info(getApplicationContext(),"response : "+response.toString(),Toast.LENGTH_LONG).show();

                Log.i("onResponse", "" + response.toString());
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("Error", "" + error.toString());
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization","key=AAAAfAFP2tY:APA91bG_LOT5m6TcbmppErUldCgO67MeCuNDfjfe9aj7APW5Dzk7uaGw-YbamCGuoG8twRzUOYjzGeue2oan77au8KPn7hsy2YlSjAZ209_mPeeicUlPW6d_CxEGEkJp-M9qQgyZF31d");
                params.put("Content-Type","application/json");
                return params;
            }
        };
        Myapp.getInstance().addToRequestQueue(jsonObjectRequest);

    }

}
