package com.netmaxtech.reminderlite.ui;

import android.content.DialogInterface;
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
import com.netmaxtech.reminderlite.R;


import com.netmaxtech.reminderlite.Database.DataHelper;

import java.util.ArrayList;

public class ShowList extends AppCompatActivity implements  AdapterView.OnItemLongClickListener {
    ListView lv;
    DataHelper dh;
    public ArrayAdapter<String> listAdapter;
    String[] mylist = {"delete reminder"};
    Integer[] ids;
    ArrayList<String> dataList;


    int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.content_show_list);

        dataList = new ArrayList<String>();

        lv = (ListView) findViewById(R.id.listView);
        dh = new DataHelper(this);

        Cursor cur = dh.viewData2();
       ids = new Integer[cur.getCount()];

        if (cur.moveToFirst()) {

            do {
                StringBuilder sb = new StringBuilder();

                ids[i] = cur.getInt(cur.getColumnIndex("id"));

                String dat = cur.getString(cur.getColumnIndex("date"));
                String time = cur.getString(cur.getColumnIndex("time"));
                String des = cur.getString(cur.getColumnIndex("disc"));


                sb.append(ids[i]).append("\n");
                sb.append("date :").append(dat).append("\n").append("time :").append(time).append("\n").append("description :").append(des);

                dataList.add(sb.toString());
                i++;

            }
            while (cur.moveToNext());


            listAdapter = new ArrayAdapter<String>(this, R.layout.simplelist, dataList);
            lv.setAdapter(listAdapter);
                 lv.setOnItemLongClickListener(this);


        } else {
            Toast.makeText(this, "no reminder found", Toast.LENGTH_LONG).show();

        }


   /*        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

     FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });  */
    }






    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        deleteDialog();
        return false;
    }
    public void deleteDialog() {
        AlertDialog.Builder ad = new AlertDialog.Builder(this);
        ad.setTitle("do you want to this reminder");
        ad.setItems(mylist, dialogClick);
        ad.show();
    }

    DialogInterface.OnClickListener dialogClick = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {

            dh.deleteFromList(ids[which+1 ]);
            dataList.remove(which);
            listAdapter=null;
            listAdapter = new ArrayAdapter<String>(ShowList.this, R.layout.simplelist, dataList);
            lv.setAdapter(listAdapter);


        }
    };
}
