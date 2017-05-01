package com.netmaxtech.reminderlite;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.netmaxtech.reminderlite.Database.DataHelper;
import com.netmaxtech.reminderlite.core.DecodeAddress;
import com.netmaxtech.reminderlite.core.DecodeByName;
import com.netmaxtech.reminderlite.core.MyService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutionException;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapLongClickListener, GoogleMap.OnMarkerClickListener, GoogleMap.OnMapClickListener {

    private GoogleMap mMap;
    DataHelper dh;
    Marker m;
    Double lati;
    Double longi;

    String a = null, d, t, sln, slt;
    TextView t1;
    String pAdd = null;
    Location loc = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mapdesign);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        dh = new DataHelper(this);
        t1 = (TextView) findViewById(R.id.textView);


        if (getIntent().hasExtra("address")) {
            pAdd = getIntent().getExtras().getString("address");
        }


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


        mMap.setOnMapClickListener(this);
        mMap.setOnMapLongClickListener(this);
        mMap.setOnMarkerClickListener(this);



        if(pAdd != null) {
            getLocationData();

            loc.getLongitude();
            loc.getLatitude();
            MarkerOptions mo=new MarkerOptions();
            mo.position(new LatLng(loc.getLatitude(), loc.getLongitude()));
            mMap.addMarker(mo);
            mo.title(pAdd);
            mMap.animateCamera(CameraUpdateFactory.newLatLng(new LatLng(loc.getLatitude(),loc.getLongitude())));
            mMap.getMaxZoomLevel();



        }


    }

    @Override
    public void onMapLongClick(LatLng latLng) {

        if(!true)
        {
            Toast.makeText(this, "No Internet connection", Toast.LENGTH_SHORT).show();
            return;
        }


        mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.getMaxZoomLevel();
        MarkerOptions mop = new MarkerOptions();
        mop.position(latLng);
        String lt=String.valueOf(latLng.latitude);
        String ln=String.valueOf(latLng.longitude);
        try {
            a =new DecodeAddress(this).execute(latLng.latitude,latLng.longitude).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        if(a != null) {
            mop.title(a);
            t1.setText(a.toString());
            mMap.addMarker(mop);
        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {

        if(!true)
        {
            Toast.makeText(this, "No Internet connection", Toast.LENGTH_SHORT).show();
            return false;
        }


        m=marker;
        SimpleDateFormat df=new SimpleDateFormat("dd/MM/yyyy");
        d=df.format(new Date());

        SimpleDateFormat df2= new SimpleDateFormat("HH:mm");
        t=df2.format(new Date());

        lati= marker.getPosition().latitude;
        longi=marker.getPosition().longitude;
        sln=lati.toString();
        slt=longi.toString();

        openDialog();





        return false;
    }
    public void openDialog(){

        String[] mylist = {"Add Reminder for this Location", "Remove Marker", "Cancel"};
        AlertDialog.Builder ad=new AlertDialog.Builder(this);
        ad.setTitle("Address:\n" + a);
        ad.setItems(mylist,dialogClick);
        ad.show();

    }
    DialogInterface.OnClickListener dialogClick=new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            if (which==0)
            {
                add();

            }
            if(which==1)
            {
                remove();

            }
            if (which==2)
            {


            }
        }
    };
    public void remove() {
        m.remove();
    }


    public void add(){


        Intent inte1 = new Intent(this, AddReminderData.class);
        inte1.putExtra("lat",slt);
        inte1.putExtra("lng",sln);
        inte1.putExtra("addr",a);
        startActivity(inte1);



/*
        final AlertDialog.Builder ad2=new AlertDialog.Builder(this);
        ad2.setTitle("Enter Reminder Discription");
        final EditText et=new EditText(this);
        et.setInputType(InputType.TYPE_CLASS_TEXT);

        ad2.setView(et);
        ad2.setPositiveButton("ADD", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dh.addData(slt,sln,d,t,a,et.toString());
                Toast.makeText(MapsActivity.this,"added to database",Toast.LENGTH_LONG).show();

            }


        });
        ad2.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        ad2.show();  */





    }

    @Override
    public void onMapClick(LatLng latLng) {

        if(!true)
        {
            Toast.makeText(this, "No Internet connection", Toast.LENGTH_SHORT).show();
            return;
        }


        try {
            a =new DecodeAddress(this).execute(latLng.latitude,latLng.longitude).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


        mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
        t1.setText(a.toString());


    }


    private void getLocationData()
    {

        try {
            loc =new DecodeByName(this).execute(pAdd).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        Toast.makeText(MapsActivity.this, loc.toString(),Toast.LENGTH_LONG).show();

    }


}
