package com.friendlyitsolution.meet.irvapp;

import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static java.util.Collections.singleton;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    double lati, longi;
    private FusedLocationProviderClient mFusedLocationClient;
    String fulladd = "";
    Marker m=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    void getCurrLocation() {
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mFusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            try {
                                lati = location.getLatitude();
                                longi = location.getLongitude();
                                Map<String, String> lastloc = new HashMap<>();
                                lastloc.put("lati", lati + "");
                                lastloc.put("longi", longi + "");
                                Calendar c = Calendar.getInstance();
                                SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
                                String formattedDate = df.format(c.getTime());
                                lastloc.put("time", formattedDate);
                                Geocoder code = new Geocoder(getApplicationContext(), Locale.ENGLISH);
                                List<Address> list = code.getFromLocation(lati, longi, 10);
                                Address add;
                                String myadd[];
                                add = list.get(0);
                                myadd = new String[add.getMaxAddressLineIndex()];
                                //fulladd=fulladd+"\nlati"+lati+" longi:"+longi;


                                String postalcode = add.getPostalCode();
                                String city = add.getLocality();
                                String area = add.getSubLocality();
                                String adds = add.getThoroughfare() + " , " + area + " , " + postalcode;
                                fulladd = fulladd + adds + "\n";
                                fulladd = fulladd + city + " , " + add.getAdminArea();

                                Glide.with(MyApp.con)
                                        .load(MyApp.userdata.get("dpurl")+"")
                                        .asBitmap()
                                        .into(new SimpleTarget<Bitmap>(100, 100) {
                                            @Override
                                            public void onResourceReady(Bitmap bitmap, GlideAnimation anim) {
                                                Bitmap.Config conf = Bitmap.Config.ARGB_8888;
                                                Bitmap bmp = Bitmap.createBitmap(140, 140, conf);
                                                Canvas canvas1 = new Canvas(bmp);

                                                Paint color = new Paint();
                                                color.setTextSize(30);
                                                color.setColor(Color.WHITE);

                                                BitmapFactory.Options opt = new BitmapFactory.Options();
                                                opt.inMutable = true;


                                                Bitmap resized = Bitmap.createScaledBitmap(bitmap, 100, 100, true);
                                                canvas1.drawBitmap(resized, 40, 40, color);

                                                canvas1.drawText(MyApp.userdata.get("name")+"", 30, 40, color);
                                                //canvas1.drawText(MyApp.myname, 30, 40, color);

// add marker to Map



                                                LatLng cu=new LatLng(lati,longi);
                                                if(m!=null) {
                                                    m.remove();
                                                    m =   mMap.addMarker(new MarkerOptions()
                                                            .position(new LatLng(lati, longi))
                                                            .icon(BitmapDescriptorFactory.fromBitmap(bmp))
                                                            // Specifies the anchor to be at a particular point in the marker image.
                                                            .anchor(0.5f, 1));
                                                    mMap.moveCamera(CameraUpdateFactory.newLatLng(cu));
                                                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lati, longi), 15));

                                                    mMap.setTrafficEnabled(true);
                                                }
                                                else
                                                {
                                                    m =   mMap.addMarker(new MarkerOptions()
                                                            .position(new LatLng(lati, longi))
                                                            .icon(BitmapDescriptorFactory.fromBitmap(bmp))
                                                            // Specifies the anchor to be at a particular point in the marker image.
                                                            .anchor(0.5f, 1));
                                                    mMap.moveCamera(CameraUpdateFactory.newLatLng(cu));
                                                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lati, longi), 15));

                                                    mMap.setTrafficEnabled(true);
                                                }


                                            }
                                        });


                                // Toast.makeText(getApplicationContext(), "geting :"+fulladd, Toast.LENGTH_LONG).show();
                            } catch (Exception e) {
                                Toast.makeText(getApplicationContext(), "Error 1 :" + e.getMessage(), Toast.LENGTH_LONG).show();

                            }
                        }
                    }
                });
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        // googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        // googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        // googleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
        // googleMap.setMapType(GoogleMap.MAP_TYPE_NONE);

        // Showing / hiding your current location
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        googleMap.setMyLocationEnabled(true);
        googleMap.setTrafficEnabled(true);
        // Enable / Disable zooming controls
        googleMap.getUiSettings().setZoomControlsEnabled(true);

        // Enable / Disable my location button
        googleMap.getUiSettings().setMyLocationButtonEnabled(true);

        // Enable / Disable Compass icon
        googleMap.getUiSettings().setCompassEnabled(true);

        // Enable / Disable Rotate gesture
        googleMap.getUiSettings().setRotateGesturesEnabled(true);

        // Enable / Disable zooming functionality
        googleMap.getUiSettings().setZoomGesturesEnabled(true);
        try {
            getCurrLocation();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Please give the location permission", Toast.LENGTH_LONG).show();
        }
        // Add a marker in Sydney and move the camera
    }
}
