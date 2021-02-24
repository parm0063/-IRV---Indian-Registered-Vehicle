package com.friendlyitsolution.meet.irvapp;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.zxing.WriterException;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class QR_Code extends AppCompatActivity {

    ImageView ivmyqr,qrbackarrow;
    String TAG = "GenerateQRCode";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr__code);

        ivmyqr = (ImageView)findViewById(R.id.ivmyqr);
        qrbackarrow = (ImageView)findViewById(R.id.back);


        //________________________QR Code Start_____________________________________________________
        String inputValue = ewallet.tvuserid.getText().toString().trim();
        if (inputValue.length() > 0)
        {
            WindowManager manager = (WindowManager) getSystemService(WINDOW_SERVICE);
            Display display = manager.getDefaultDisplay();
            Point point = new Point();
            display.getSize(point);
            int width = point.x;
            int height = point.y;
            int smallerDimension = width < height ? width : height;
            smallerDimension = smallerDimension * 3 / 4;
            QRGEncoder qrgEncoder = new QRGEncoder(inputValue,null, QRGContents.Type.TEXT,smallerDimension);

            try {
                Bitmap bitmap = qrgEncoder.encodeAsBitmap();
                ivmyqr.setImageBitmap(bitmap);
            }
            catch (WriterException e)
            {
                Log.v(TAG,e.toString());
            }
        }
        else
        {
            Toast.makeText(getApplicationContext(), "Something is wrong", Toast.LENGTH_SHORT).show();
        }

        qrbackarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

}
