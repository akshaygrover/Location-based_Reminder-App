package com.netmaxtech.reminderlite;

import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AlertDialog;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.netmaxtech.reminderlite.Database.DataHelper;
import com.netmaxtech.reminderlite.core.MyService;
import com.netmaxtech.reminderlite.ui.SimpleReminder;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public class MainActivity extends ActionBarActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener, View.OnClickListener {


    ImageButton b1,b2,b3,b4;
    DataHelper dh;
    MyService ms;
    Intent in;
    TextView txtInt, txtGps;
    Thread th1;



    //  1.  Define api client
    GoogleApiClient mGoogleApiClient;

    // 2 define location
    private Location mLastLocation;
    private LocationRequest mLocationRequest;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupUi();



        // 4. call build clinet
        buildGoogleApiClient();


        // request updates
        createLocationRequest();






    }



    private void setupUi()
    {
        b1=(ImageButton)findViewById(R.id.imageButton);
        b2=(ImageButton)findViewById(R.id.imageButton2);
        b3=(ImageButton)findViewById(R.id.imageButton3);
        b4=(ImageButton)findViewById(R.id.imageButton4);

        txtInt = (TextView)findViewById(R.id.textView5);
        txtGps = (TextView)findViewById(R.id.textView7);
        txtGps.setOnClickListener(this);


        b1.setOnClickListener(this);
        b2.setOnClickListener(this);
        b3.setOnClickListener(this);
        b4.setOnClickListener(this);


    }




    //3. Build API client call this in oncreate
    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }



    //4 Connect Google Api Client
    @Override
    protected void onStart() {
        super.onStart();
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }


    /*    th1 = new Thread(r1);
        th1.start();*/

    }


    // Disconnect Google Api Client
    @Override
    protected void onStop() {
        super.onStop();
        if (mGoogleApiClient != null) {
            mGoogleApiClient.disconnect();
        }

     /*   try {
            th1.join();
            th1 = null;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
    }


    // stop updates
    @Override
    protected void onPause() {
        super.onPause();
        stopLocationUpdates();



    }




    // Override On service connected method
    @Override
    public void onConnected(Bundle bundle) {


        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        if (mLastLocation != null) {

            Toast.makeText(this, "OnConnected : Latitude:" + mLastLocation.getLatitude() + ", Longitude:" + mLastLocation.getLongitude(), Toast.LENGTH_SHORT).show();

        }

     //   startLocationUpdates();


    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }



    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(600000);
        mLocationRequest.setFastestInterval(60000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }


    protected void startLocationUpdates() {
        LocationServices.FusedLocationApi.requestLocationUpdates(
                mGoogleApiClient, mLocationRequest, this);
    }


    @Override
    public void onLocationChanged(Location location) {


        String lat = String.valueOf(location.getLatitude());
        String lon = String.valueOf(location.getLongitude());
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String da = df.format(new Date());
        DateFormat df1 = new SimpleDateFormat("HH:mm:ss");
        String ti = df1.format(new Date());

        Toast.makeText(this,"OnChange" + location.toString() , Toast.LENGTH_SHORT).show();

        //dh1.addData(lat, lon, da, ti);







    }

    protected void stopLocationUpdates() {
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(
                    mGoogleApiClient, this);
        }
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imageButton:
                in=new Intent(MainActivity.this,MapsActivity.class);
                startActivity(in);
                break;

            case R.id.imageButton2:
                in=new Intent(MainActivity.this,SimpleReminder.class);
                startActivity(in);
                break;

            case R.id.imageButton3:
                in=new Intent(MainActivity.this,LocationList.class);
                startActivity(in);
                break;

            case R.id.imageButton4:

                in=new Intent(MainActivity.this,GeoActivity.class);
                startActivity(in);

                break;


            case R.id.textView7:
                Location loc = LocationServices.FusedLocationApi.getLastLocation(
                        mGoogleApiClient);
                Toast.makeText(this, "Test GPS " +  loc.toString(), Toast.LENGTH_SHORT).show();

                break;


        }

    }







    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.start_service)
        {
            in=new Intent(MainActivity.this,MyService.class);
            startActivity(in);
            Toast.makeText(this,"Start All Reminders",Toast.LENGTH_LONG).show();
        }
        if(item.getItemId() == R.id.stop_service)
        {
            stopService(in);
            Toast.makeText(this,"Stop All Reminders",Toast.LENGTH_LONG).show();
        }

        return super.onOptionsItemSelected(item);
    }




    public Boolean checknet() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo nf = cm.getActiveNetworkInfo();
        if (nf == null) {
            return false;
        } else {
            return true;
        }
    }


   Runnable r1 = new Runnable() {
        @Override
        public void run() {

            while (true) {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                h1.sendMessage(h1.obtainMessage());
            }
        }
    };

    private Handler h1 = new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
           if(checknet())
           {
               txtInt.setText("Internet Connection Available");
           }
            else
           {
               txtInt.setText("No Internet Connection");
           }

        }
    };



}
