package com.netmaxtech.reminderlite;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class LocationList extends ActionBarActivity implements TextWatcher,  AdapterView.OnItemClickListener {

    EditText searchIn;
    Button searchButton;
    ListView searchOut;

    private ArrayAdapter<String> adapter;

    Geocoder geocoder;
    final static int maxResults = 5;
    List<Address> locationList;
    List<String> locationNameList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location_list);
        searchIn = (EditText)findViewById(R.id.searchin);

        searchIn.addTextChangedListener(this);



        searchButton = (Button)findViewById(R.id.search);
        searchOut = (ListView)findViewById(R.id.searchout);

        searchButton.setOnClickListener(searchButtonOnClickListener);

        geocoder = new Geocoder(this, Locale.ENGLISH);

        locationNameList = new ArrayList<String>(); //empty in start

     //   adapter = new ArrayAdapter<String>(this,
     //           android.R.layout.simple_spinner_item, locationNameList);

        adapter = new ArrayAdapter<String>(this,
                R.layout.simplerow, locationNameList);


        searchOut.setAdapter(adapter);

        searchOut.setOnItemClickListener(this);

    }

    OnClickListener searchButtonOnClickListener =
            new OnClickListener(){

                @Override
                public void onClick(View arg0) {

                 showLocations();
                }};


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_locationlist, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

        if(count > 3)
        {
            synchronized (this)
            {
                showLocations();
            }
        }

    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

       String addres = adapter.getItem(position);
        Toast.makeText(this, addres, Toast.LENGTH_LONG).show();
        Intent in = new Intent(LocationList.this, MapsActivity.class);
        in.putExtra("address", addres);
        startActivity(in);






    }

    private class getlistTask extends AsyncTask<String, Void, List<Address>>
    {
        List<Address>  locationList = null;

        @Override
        protected List<Address> doInBackground(String... params) {

            try {
                Geocoder geocoder1 = new Geocoder(getApplicationContext(), Locale.ENGLISH);

                locationList = geocoder.getFromLocationName(params[0], 5);



            } catch (IOException e) {
                e.printStackTrace();
            }
            return locationList;
        }
    }

    private void showLocations()
    {
        String locationName = searchIn.getText().toString();

      /*  Toast.makeText(getApplicationContext(),
                "Search for: " + locationName,
                Toast.LENGTH_SHORT).show();*/

        if(locationName == null){
            Toast.makeText(getApplicationContext(),
                    "locationName == null",
                    Toast.LENGTH_LONG).show();
        }else{
            try {

                locationList = new getlistTask().execute(locationName).get();

                if(locationList == null){
                    Toast.makeText(getApplicationContext(),
                            "locationList == null",
                            Toast.LENGTH_LONG).show();
                }else{
                    if(locationList.isEmpty()){
                        Toast.makeText(getApplicationContext(),
                                "locationList is empty",
                                Toast.LENGTH_LONG).show();
                    }else{
                      /*  Toast.makeText(getApplicationContext(),
                                "number of result: " + locationList.size(),
                                Toast.LENGTH_LONG).show();*/

                        locationNameList.clear();

                        for(Address add1 : locationList){
                            if(add1.getFeatureName() == null){
                                locationNameList.add("unknown");
                            }else{


                                ArrayList<String> addressFragments = new ArrayList<String>();

                                // Fetch the address lines using getAddressLine,
                                // join them, and send them to the thread.
                                for (int i = 0; i < add1.getMaxAddressLineIndex(); i++) {
                                    addressFragments.add(add1.getAddressLine(i));
                                }
                                locationNameList.add(addressFragments.toString());

                                //     locationNameList.add(add1.getFeatureName());
                            }
                        }

                        adapter.notifyDataSetChanged();
                    }
                }


            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

        }
    }


}




