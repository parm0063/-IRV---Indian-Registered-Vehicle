package com.friendlyitsolution.meet.irv_admin;

import android.*;
import android.content.Context;
import android.content.Intent;
import android.graphics.PointF;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.dlazaro66.qrcodereaderview.QRCodeReaderView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link echallan.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * create an instance of this fragment.
 */
public class echallan extends Fragment implements QRCodeReaderView.OnQRCodeReadListener {

   private OnFragmentInteractionListener mListener;
        EditText et;
        Button submit;
        boolean callit=true;
    private QRCodeReaderView qrCodeReaderView;
    public echallan() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_echallan, container, false);
        et=(EditText)v.findViewById(R.id.txtet);
        submit=(Button)v.findViewById(R.id.submit);
        qrCodeReaderView = (QRCodeReaderView) v.findViewById(R.id.qrdecoderview);
        qrCodeReaderView.setOnQRCodeReadListener(this);

        // Use this function to enable/disable decoding
        qrCodeReaderView.setQRDecodingEnabled(true);

        // Use this function to change the autofocus interval (default is 5 secs)
        qrCodeReaderView.setAutofocusInterval(2000L);

        // Use this function to enable/disable Torch
        qrCodeReaderView.setTorchEnabled(true);

        // Use this function to set front camera preview
        qrCodeReaderView.setFrontCamera();

        // Use this function to set back camera preview
        qrCodeReaderView.setBackCamera();




        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkUser(et.getText().toString());

            }
        });

        return v;
    }

    void checkUser(final String uid)
    {   MainActivity.pd.show();
        final DatabaseReference reff=Myapp.ref.child("users").child(uid);
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
               reff.removeEventListener(this);
               MainActivity.pd.dismiss();

                if(dataSnapshot.getValue()==null)
                {
                    et.setError("Wrong Userid");
                    callit=true;
                }
                else
                {
                    Myapp.uid=uid;
                    Myapp.userdata=(Map<String, Object>)dataSnapshot.getValue();
                    startActivity(new Intent(getActivity(),userinfo.class));
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                  MainActivity.pd.dismiss();
                  callit=true;
                reff.removeEventListener(this);
            }
        });

    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
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

    @Override
    public void onQRCodeRead(String text, PointF[] points) {
       if(callit) {
           callit=false;
           et.setText(text);
           checkUser(text);
           Toast.makeText(getActivity(), "get", Toast.LENGTH_LONG).show();
       }
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
