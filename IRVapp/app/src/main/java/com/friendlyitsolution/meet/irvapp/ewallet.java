package com.friendlyitsolution.meet.irvapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ewallet.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ewallet#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ewallet extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    LinearLayout myqrcodelayout,mylicence,myvehicle;
    static ImageView ivstatus;
    static CircleImageView ivuphoto;
   static TextView tvstatus,tvuname,tvumail,tvucontact,tvudob;
    static TextView tvuserid;

    private OnFragmentInteractionListener mListener;

    public ewallet() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ewallet.
     */
    // TODO: Rename and change types and number of parameters
    public static ewallet newInstance(String param1, String param2) {
        ewallet fragment = new ewallet();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_ewallet, container, false);


        myqrcodelayout = (LinearLayout)v.findViewById(R.id.myqrcodelayout);
        mylicence = (LinearLayout)v.findViewById(R.id.mylicence);
        myvehicle = (LinearLayout)v.findViewById(R.id.myvehicle);
        ivuphoto = (CircleImageView) v.findViewById(R.id.ivuphoto);
        tvuserid = (TextView)v.findViewById(R.id.tvuserid);
        tvstatus = (TextView)v.findViewById(R.id.tvstatus);
        ivstatus = (ImageView)v.findViewById(R.id.ivstatus);
        tvuname = (TextView)v.findViewById(R.id.tvuname);
        tvumail = (TextView)v.findViewById(R.id.tvumail);
        tvucontact = (TextView)v.findViewById(R.id.tvucontact);
        //tvudob = (TextView)v.findViewById(R.id.tvudob);

       try{
           tvuserid.setText(MyApp.myid);
           //tvumail.setText(MyApp.userdata.get("email").toString());
           tvucontact.setText(MyApp.userdata.get("contact").toString());
           tvuname.setText(MyApp.userdata.get("name").toString());

           setStatus();
           if(MyApp.userdata.containsKey("dpurl"))
        {
            Glide.with(ivuphoto.getContext()).load(MyApp.userdata.get("dpurl")+"")
                    .override(100, 100)
                    .fitCenter()
                    .into(ivuphoto);

        }

        else
        {
            ivuphoto.setImageResource(R.drawable.user_icon_grey);
        }
       }
       catch(Exception e)
       {
           ivuphoto.setImageResource(R.drawable.user_icon_grey);

       }


        /*
        MyApp.ref.child("users").child(MyApp.myid).child("licence").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Map<String,Object> ew = (Map<String,Object>) dataSnapshot.getValue();
                tvuname.setText(ew.get("ufullname").toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getActivity().getApplicationContext(), "Try Again", Toast.LENGTH_SHORT).show();
            }
        });*/


        myqrcodelayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getActivity().getApplicationContext(), "My QR Code", Toast.LENGTH_SHORT).show();
                Intent qrintent = new Intent(getActivity().getApplicationContext(),QR_Code.class);
                startActivity(qrintent);
            }
        });


        mylicence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getActivity().getApplicationContext(), "My Licence", Toast.LENGTH_SHORT).show();
                Intent lic = new Intent(getActivity().getApplicationContext(),My_Licenceinfo.class);
                startActivity(lic);
            }
        });


        myvehicle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity().getApplicationContext(), "My Vehicle", Toast.LENGTH_SHORT).show();
            }
        });


        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    static void setStatus()
    {

        if(MyApp.userdata.get("status").equals("pending"))
        {

            tvstatus.setText("pending");
           ivstatus.setImageResource(R.drawable.exclamation_mark);
        }
        else
        {

            tvstatus.setText("Verified");
            ivstatus.setImageResource(R.drawable.verified);


        }

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
       // Toast.makeText(getActivity(),"I m ewallet",Toast.LENGTH_LONG).show();
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
