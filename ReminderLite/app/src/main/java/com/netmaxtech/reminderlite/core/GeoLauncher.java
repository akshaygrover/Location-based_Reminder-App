package com.netmaxtech.reminderlite.core;

import android.app.PendingIntent;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.LocationServices;
import com.netmaxtech.reminderlite.Constants;
import com.netmaxtech.reminderlite.Database.DataHelper;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Jagdeep on 19/05/16.
 */
public class GeoLauncher {

    Context con;

    public GeoLauncher(Context c)
    {

    }


    /**
     * Provides the entry point to Google Play services.
     */
    protected GoogleApiClient mGoogleApiClient;

    /**
     * The list of geofences used in this sample.
     */
    protected ArrayList<Geofence> mGeofenceList;

    /**
     * Used to keep track of whether geofences were added.
     */
    private boolean mGeofencesAdded;

    /**
     * Used when requesting to add or remove geofences.
     */
    private PendingIntent mGeofencePendingIntent;

    /**
     * Used to persist application state about whether geofences were added.
     */
    private SharedPreferences mSharedPreferences;




    public void init()
    {
        // Empty list for storing geofences.
        mGeofenceList = new ArrayList<Geofence>();

        // Initially set the PendingIntent used in addGeofences() and removeGeofences() to null.
        mGeofencePendingIntent = null;

        // Retrieve an instance of the SharedPreferences object.
        mSharedPreferences = con.getSharedPreferences(Constants.SHARED_PREFERENCES_NAME,
                con.MODE_PRIVATE);

        // Get the value of mGeofencesAdded from SharedPreferences. Set to false as a default.
        mGeofencesAdded = mSharedPreferences.getBoolean(Constants.GEOFENCES_ADDED_KEY, false);


        // Get the geofences used. Geofence data is hard coded in this sample.
        //  populateGeofenceList();
        populateGeoFData();

        // Kick off the request to build GoogleApiClient.
      //  buildGoogleApiClient();
    }

/*
    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(con)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }
*/

    private void populateGeoFData()
    {

        DataHelper dh1 = new DataHelper(con);

        Cursor c = dh1.viewData();

        while(c.moveToNext())
        {
            Double lat = Double.parseDouble(c.getString(c.getColumnIndex("latitudes")));
            Double lon = Double.parseDouble(c.getString(c.getColumnIndex("longitudes")));
            Float dis_meter = Float.parseFloat(c.getString(c.getColumnIndex("distance")));
            String description = c.getString(c.getColumnIndex("disc"));
            String address = c.getString(c.getColumnIndex("address"));

            String ti = c.getString(c.getColumnIndex("time"));
            String[] hhmm = ti.split(":");

            String da = c.getString(c.getColumnIndex("date"));
            String[] dmy = da.split(":");

            Calendar cal1 = Calendar.getInstance();
            cal1.set(Calendar.HOUR_OF_DAY, Integer.parseInt(hhmm[0]));
            cal1.set(Calendar.MINUTE, Integer.parseInt(hhmm[1]));
            cal1.set(Calendar.MONTH, Integer.parseInt(dmy[1]));
            cal1.set(Calendar.DAY_OF_MONTH, Integer.parseInt(dmy[0]));
            cal1.set(Calendar.YEAR, Integer.parseInt(dmy[2]));

            Long expiry = cal1.getTimeInMillis() - System.currentTimeMillis();


            mGeofenceList.add(new Geofence.Builder()
                    // Set the request ID of the geofence. This is a string to identify this
                    // geofence.
                    .setRequestId(description)

                            // Set the circular region of this geofence.
                    .setCircularRegion(
                            lat,
                            lon,
                            dis_meter
                    )

                            // Set the expiration duration of the geofence. This geofence gets automatically
                            // removed after this period of time.
                    .setExpirationDuration(expiry)

                            // Set the transition types of interest. Alerts are only generated for these
                            // transition. We track entry and exit transitions in this sample.
                    .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER |
                            Geofence.GEOFENCE_TRANSITION_EXIT)

                            // Create the geofence.
                    .build());





        }




    }



}
