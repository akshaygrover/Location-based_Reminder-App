package com.netmaxtech.reminderlite.ui;



import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
//import android.support.design.widget.FloatingActionButton;
//import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.netmaxtech.reminderlite.Maps2;
import com.netmaxtech.reminderlite.R;

import com.netmaxtech.reminderlite.Database.DataHelper;

import java.util.ArrayList;

public class ShowLocationsReminder extends AppCompatActivity implements AdapterView.OnItemLongClickListener {
    ListView lv;
    DataHelper dh;
    public ArrayAdapter<String> listAdapter;
    String[] mylist = {"show on map", "delete this"};
    Integer[] ids;
    ArrayList<String> dataList;
    int i;
    String pos;
    int p;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_show_locations);
        dataList = new ArrayList<String>();

        lv = (ListView) findViewById(R.id.listView2);
        dh = new DataHelper(this);

        Cursor cur = dh.viewData();
        ids = new Integer[cur.getCount()];

        if (cur.moveToFirst()) {

            do {
                StringBuilder sb = new StringBuilder();

                ids[i] = cur.getInt(cur.getColumnIndex("id"));

                String la = cur.getString(cur.getColumnIndex("latitudes"));
                String lo = cur.getString(cur.getColumnIndex("longitudes"));

                String dat = cur.getString(cur.getColumnIndex("date"));
                String time = cur.getString(cur.getColumnIndex("time"));
                 pos = cur.getString(cur.getColumnIndex("address"));
                String des = cur.getString(cur.getColumnIndex("disc"));
                String dis = cur.getString(cur.getColumnIndex("distance"));
                int renter = cur.getInt(cur.getColumnIndex("renter"));
                int rexit = cur.getInt(cur.getColumnIndex("rexit"));



                sb.append(la).append(":").append(lo).append("\n");


                sb.append("Date : ").append(dat).append("\n").append("Time : ").append(time)
                        .append("\n").append("Address : ").append(pos).append("\n").append("Description : ").append(des);

                sb.append("\n");
                sb.append("Reminder Distance : ");
                sb.append(dis);

                if(renter == 1)
                {
                    sb.append("\n");
                    sb.append("Remind on Entering Location");
                }
                if(rexit == 1)
                {
                    sb.append("\n");
                    sb.append("Remind on Leaving Location");
                }



                dataList.add(sb.toString());


                i++;

            }
            while (cur.moveToNext());


            listAdapter = new ArrayAdapter<String>(this, R.layout.simplelist, dataList);
            lv.setAdapter(listAdapter);
            lv.setOnItemLongClickListener(this);


        } else {
            Toast.makeText(this, "no location reminder found", Toast.LENGTH_LONG).show();

        }
/*
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        p=position;



        AlertDialog.Builder ad = new AlertDialog.Builder(this);
        ad.setTitle("reminder options");
        ad.setItems(mylist, dialogClick);
        ad.show();
        return false;
    }

    DialogInterface.OnClickListener dialogClick = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            if (which == 0) {
                showMap();
            }
            if (which == 1) {
                dh.deleteLocation(ids[which+1 ]);
                dataList.remove(which);
                listAdapter=null;
                listAdapter = new ArrayAdapter<String>(ShowLocationsReminder.this, R.layout.simplelist, dataList);
                lv.setAdapter(listAdapter);
            }
        }
    };
    public void showMap(){
        Intent in=new Intent(ShowLocationsReminder.this,Maps2.class);
        in.putExtra("location",p);
        startActivity(in);

    }

}