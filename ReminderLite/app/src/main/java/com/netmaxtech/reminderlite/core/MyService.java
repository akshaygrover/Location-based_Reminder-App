package com.netmaxtech.reminderlite.core;

import android.app.Service;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.netmaxtech.reminderlite.Database.DataHelper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutionException;


public class MyService extends Service implements LocationListener {

    LocationManager lm;
    String source;
    DataHelper dh;
    Location l;
    String ad;


    @Override
    public void onCreate() {
        super.onCreate();
        lm = (LocationManager) getSystemService(LOCATION_SERVICE);
        dh = new DataHelper(this);
        Criteria cr = new Criteria();
        cr.setPowerRequirement(6);
        cr.setAccuracy(6);
        source = lm.getBestProvider(cr, true);
        lm.requestLocationUpdates(source, 60000, 2000, this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        lm.removeUpdates(this);
    }

    @Override
    public void onLocationChanged(Location location) {

        l = location;
        String lt = String.valueOf(location.getLatitude());
        String ln = String.valueOf(location.getLongitude());
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String da = df.format(new Date());
        SimpleDateFormat df1 = new SimpleDateFormat("HH:mm:ss");
        String ti = df1.format(new Date());
        try {
             ad=new DecodeAddress(this).execute(location.getLatitude(),location.getLongitude()).get();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
      //  dh.addData2(lt,ln,da,ti,ad, "");
        Toast.makeText(this,"location changed",Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


}