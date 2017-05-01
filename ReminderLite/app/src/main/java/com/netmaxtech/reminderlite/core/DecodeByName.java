package com.netmaxtech.reminderlite.core;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * Created by Akki on 09-05-2016.
 */
public class DecodeByName extends AsyncTask<String,Void,Location> {

    Context con;
    public DecodeByName(Context c)
    {
        con = c;
    }


    @Override
    protected Location doInBackground(String... params) {


        Location myLocation = new Location("");
        String r = null;
        Geocoder gc = new Geocoder(con, Locale.getDefault() );
        try {
          //  List<Address> add=gc.getFromLocation(params[0],params[1],1);


            List<Address> addList=gc.getFromLocationName(params[0], 1);


            if (addList.size()>0) {
                Address addr = addList.get(0);
                myLocation.setLatitude(addr.getLatitude());
                myLocation.setLongitude(addr.getLongitude());
            }



        } catch (IOException e) {
            e.printStackTrace();
        }


        return myLocation;
    }
}
