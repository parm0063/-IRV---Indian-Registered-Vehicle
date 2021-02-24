package com.friendlyitsolution.meet.irvapp;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class FormActivity extends AppCompatActivity {


    ImageView ivuphoto;
    EditText etufullname,etuswd,etucontact,etuaddress,etulicence;
    LinearLayout layoutvalidfrom,layoutvalidtill,layoutudob,layoutbldgrp,layoutauthtodrive;
    TextView tvuvfrom,tvuvtill,tvudob,tvubloodgrp,tvauthtodrive;
    Calendar mCurrentDate;
    int day,month,year;
    Button formsubmitbtn;
    String ufullname,uswd,udob,ubldgrp,uphone,uaddress,udlno,uauthtodrive,uvfrom,uvtill;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);


        //____________________Date Picker Start_____________________________________________________
        formsubmitbtn = (Button)findViewById(R.id.formsubmitbtn);
        etulicence = (EditText)findViewById(R.id.etulicence);
        etuaddress = (EditText)findViewById(R.id.etuaddress);
        etucontact = (EditText)findViewById(R.id.etucontact);
        etuswd = (EditText)findViewById(R.id.etuswd);
        etufullname = (EditText)findViewById(R.id.etufullname);
        ivuphoto = (ImageView)findViewById(R.id.ivuphoto);
        tvauthtodrive = (TextView) findViewById(R.id.tvuauthtodrive);
        layoutauthtodrive = (LinearLayout)findViewById(R.id.layoutauthtodrive);
        layoutudob = (LinearLayout)findViewById(R.id.layoutudob);
        tvudob = (TextView)findViewById(R.id.tvudob);
        tvubloodgrp = (TextView)findViewById(R.id.tvubloodgrp);
        layoutbldgrp = (LinearLayout)findViewById(R.id.layoutbldgrp);
        layoutvalidfrom = (LinearLayout)findViewById(R.id.layoutvalidfrom);
        layoutvalidtill = (LinearLayout)findViewById(R.id.layoutvalidtill);
        tvuvfrom = (TextView)findViewById(R.id.tvuvfrom);
        tvuvtill = (TextView)findViewById(R.id.tvuvtill);
        mCurrentDate = Calendar.getInstance();
        day = mCurrentDate.get(Calendar.DAY_OF_MONTH);
        month = mCurrentDate.get(Calendar.MONTH);
        year = mCurrentDate.get(Calendar.YEAR);

        layoutvalidfrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatePickerDialog datePickerDialog = new DatePickerDialog(FormActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {

                        i1 = i1 + 1;
                        tvuvfrom.setText(i2+"/"+i1+"/"+i);
                        tvuvfrom.setTextColor(Color.BLACK);
                    }
                },year,month,day);
                datePickerDialog.show();


            }
        });


        layoutvalidtill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatePickerDialog datePickerDialog = new DatePickerDialog(FormActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {

                        i1 = i1 + 1;
                        tvuvtill.setText(i2+"/"+i1+"/"+i);
                        tvuvtill.setTextColor(Color.BLACK);
                    }
                },year,month,day);
                datePickerDialog.show();

            }
        });


        layoutudob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatePickerDialog datePickerDialog = new DatePickerDialog(FormActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {

                        i1 = i1 + 1;
                        tvudob.setText(i2+"/"+i1+"/"+i);
                        tvudob.setTextColor(Color.BLACK);
                    }
                },year,month,day);
                datePickerDialog.show();

            }
        });
        //____________________Date Picker end_______________________________________________________





        //____________________Single Choice Dialog Start____________________________________________

        layoutbldgrp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                AlertDialog.Builder blooddlg = new AlertDialog.Builder(FormActivity.this);
                blooddlg.setTitle("Select your blood group");

                final String[] bloodgrps = {"A+","A-","B+","B-","O+","O-","AB+","AB-"};

                blooddlg.setSingleChoiceItems(bloodgrps, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        // User clicked an item

                        tvubloodgrp.setText(bloodgrps[i]);
                        tvubloodgrp.setTextColor(Color.BLACK);
                    }
                });


                //Add OK Button

                blooddlg.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        //User clicked OK

                        dialogInterface.dismiss();

                    }
                });


                AlertDialog dialog = blooddlg.create();
                dialog.show();

            }
        });
        //____________________Single Choice Dialog end______________________________________________





        //____________________Multi Choice Dialog start_____________________________________________

        final String[] listVehicle = {"MC 50CC","MCW/MCWOG","MCWG","LMV","LMV-NT","LMV-TR","HMV"};
        final ArrayList<Integer> selectedVehicles = new ArrayList<>();


        layoutauthtodrive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                final AlertDialog.Builder vehicledlg = new AlertDialog.Builder(FormActivity.this);
                vehicledlg.setTitle("Select vehicle type");

                boolean[] checkedItems = new boolean[listVehicle.length];

                AlertDialog.Builder mBuilder = new AlertDialog.Builder(FormActivity.this);
                mBuilder.setTitle("Select vehicle type");
                mBuilder.setMultiChoiceItems(listVehicle, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int position, boolean isChecked) {

                        if(isChecked){
                            selectedVehicles.add(position);
                        }else{
                            selectedVehicles.remove((Integer.valueOf(position)));
                        }

                    }
                });

                mBuilder.setCancelable(false);
                mBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        String item = "";
                        for (int i = 0; i < selectedVehicles.size(); i++) {
                            item = item + listVehicle[selectedVehicles.get(i)];
                            if (i != selectedVehicles.size() - 1) {
                                item = item + ", ";
                            }
                        }
                        tvauthtodrive.setText(item);
                        tvauthtodrive.setTextColor(Color.BLACK);
                    }
                });

                AlertDialog mDialog = mBuilder.create();
                mDialog.show();
            }
        });
        //____________________Multi Choice Dialog end_______________________________________________





        //____________________user profile photo selecting__________________________________________

        ivuphoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });




        //____________________Submit button OnClick event start_____________________________________

        formsubmitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



        final ProgressDialog pdlg = new ProgressDialog(FormActivity.this);
        pdlg.setMessage("Please Wait");
        pdlg.setTitle("Submit");
        pdlg.show();

        ufullname = etufullname.getText().toString();
        uswd = etuswd.getText().toString();
        udob = tvudob.getText().toString();
        ubldgrp = tvubloodgrp.getText().toString();
        uphone = etucontact.getText().toString();
        uaddress = etuaddress.getText().toString();
        udlno = etulicence.getText().toString();
        uauthtodrive = tvauthtodrive.getText().toString();
        uvfrom = tvuvfrom.getText().toString();
        uvtill = tvuvtill.getText().toString();

        if (ufullname=="" || uswd=="" || udob=="" || ubldgrp=="" || uphone=="" || uaddress=="" || udlno==""  || uauthtodrive=="" || uvfrom=="" || uvtill=="")
        {
            pdlg.dismiss();
            Toast.makeText(getApplicationContext(), "Some details are missing", Toast.LENGTH_LONG).show();
        }
        else
        {
            Map<String,String> formmap = new HashMap<String, String>();
            formmap.put("ufullname",ufullname);
            formmap.put("uswd",uswd);
            formmap.put("udob",udob);
            formmap.put("ubldgrp",ubldgrp);
            formmap.put("uphone",uphone);
            formmap.put("uaddress",uaddress);
            formmap.put("udlno",udlno);
            formmap.put("uauthtodrive",uauthtodrive);
            formmap.put("uvfrom",uvfrom);
            formmap.put("uvtill",uvtill);

            MyApp.ref.child("users").child(MyApp.myid).child("licence").setValue(formmap).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    pdlg.dismiss();
                    Toast.makeText(getApplicationContext(), "Submitted successfully", Toast.LENGTH_SHORT).show();
                    Intent ihome = new Intent(getApplicationContext(),Myhome.class);
                    startActivity(ihome);
                    finish();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getApplicationContext(), "Try Again", Toast.LENGTH_SHORT).show();
                }
            });
        }


            }
        });


    }

    //____________________method for select image start_____________________________________________

    void selectImage()
    {
        Intent imgintent = new Intent();
        imgintent.setType("image/*");
        imgintent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(imgintent,"Select image"),1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==1 && resultCode==RESULT_OK && data!=null && data.getData()!=null)
        {
            try {
                Bitmap bits= MediaStore.Images.Media.getBitmap(getContentResolver(),data.getData());
                ivuphoto.setImageBitmap(bits);
                uploadFile(data.getData());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else
        {
            Toast.makeText(getApplicationContext(), "Please select one image", Toast.LENGTH_LONG).show();
        }
    }


    private void uploadFile(Uri filePath) {
        //if there is a file to upload

            //displaying a progress dialog while upload is going on
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Uploading");
            progressDialog.setCancelable(false);
            progressDialog.show();
            StorageReference mStorageRef = FirebaseStorage.getInstance().getReference();

            StorageReference riversRef = mStorageRef.child("dp/"+MyApp.myid+".jpg");

            riversRef.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {



                            Uri downloadUri = taskSnapshot.getMetadata().getDownloadUrl();
                            DatabaseReference ref= FirebaseDatabase.getInstance().getReference("users");
                            ref.child(MyApp.myid).child("dpurl").setValue(""+downloadUri).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getApplicationContext(),"try again "+e.getMessage(),Toast.LENGTH_LONG).show();

                                }
                            }).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Toast.makeText(getApplicationContext(),"Successfully Uploaded",Toast.LENGTH_LONG).show();
                                    progressDialog.dismiss();
                                }
                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                        progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(),"Try again  "+exception.getMessage(),Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            //calculating progress percentage
                            double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                            progressDialog.setProgress(((int) progress));

                            //displaying percentage in progress dialog
                            // progressDialog.setMessage("Uploaded " + ((int) progress) + "%...");
                        }
                    });

    }
    //____________________method for select image end_______________________________________________
}
