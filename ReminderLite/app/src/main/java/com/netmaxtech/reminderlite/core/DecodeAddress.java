package com.netmaxtech.reminderlite.core;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * Created by Akki on 09-05-2016.
 */
public class DecodeAddress extends AsyncTask<Double,Void,String> {

    Context con;
    public DecodeAddress(Context c)
    {
        con = c;
    }


    @Override
    protected String doInBackground(Double... params) {


        String r = null;
        Geocoder gc = new Geocoder(con, Locale.getDefault() );
        try {
            List<Address> add=gc.getFromLocation(params[0],params[1],1);




            StringBuilder sb=new StringBuilder("");

            if(add.isEmpty())
            {
                return r;
            }
            Address add2=add.get(0);
            for (int i=0;i<add2.getMaxAddressLineIndex();i++){
                sb.append(add2.getAddressLine(i));
                sb.append("\n");
            }
            r=sb.toString();



        } catch (IOException e) {
            e.printStackTrace();
        }


        return r;
    }
}
