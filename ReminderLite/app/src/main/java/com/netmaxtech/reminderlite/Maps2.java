package com.netmaxtech.reminderlite;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.netmaxtech.reminderlite.Database.DataHelper;

public class Maps2 extends FragmentActivity implements OnMapReadyCallback {
    DataHelper dh;
    int i = 0;

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps2);


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        dh = new DataHelper(this);


    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.getCameraPosition();
        MarkerOptions mop=new MarkerOptions();
        Bundle bun=getIntent().getExtras();


        Cursor cur=dh.viewData();
        if(cur.moveToFirst())
        {
            do {

                    Double s1 = Double.parseDouble(cur.getString(cur.getColumnIndex("latitudes")));
                    Double s2 = Double.parseDouble(cur.getString(cur.getColumnIndex("longitudes")));
                    if (bun != null) {
                        if (i == bun.getInt("location")) {
                            String s3 = cur.getString(5);
                            mop.title(s3);
                        } else {
                            i++;
                        }
                    }
                    mop.position(new LatLng(s1, s2));
                    mMap.addMarker(mop);

                }
                while (cur.moveToNext()) ;
            }





    }
}