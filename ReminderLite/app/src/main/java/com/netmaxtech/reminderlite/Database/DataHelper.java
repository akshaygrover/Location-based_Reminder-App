package com.netmaxtech.reminderlite.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class DataHelper extends SQLiteOpenHelper {
    public DataHelper(Context context) {
        super(context, "data", null, 50);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table reminder (id integer primary key autoincrement,latitudes text,longitudes text," +
                "date text,time text,address text,disc text, distance text, renter integer, rexit integer);");
        db.execSQL("create table reminder2(id integer primary key autoincrement,date text,time text,disc text);");
        Log.i("MYAPP","Database Created");

    }
    public boolean addData(String lat, String lon, String date, String time,String add,String dis,
                           String distance,boolean renter, boolean rexit ) {

        int enter=0,exit=0;
        if(renter)
        {
            enter=1;
        }
        if(rexit)
        {
            exit=1;
        }


        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("latitudes", lat);
        cv.put("longitudes", lon);
        cv.put("date", date);
        cv.put("time", time);
        cv.put("address", add);
        cv.put("disc", dis);
        cv.put("distance", distance);
        cv.put("renter",enter);
        cv.put("rexit",exit);

        db.insert("reminder", null, cv);
        db.close();
        return true;
    }
    public boolean addData2(String date,String time,String dis ){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();


        cv.put("date", date);
        cv.put("time", time);

        cv.put("disc", dis);
        db.insert("reminder2", null, cv);
        db.close();
        return true;

    }
    public Cursor viewData() {


        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query("reminder", null, null, null, null, null, null);


        return c;
    }
    public Cursor viewData2(){
        SQLiteDatabase db1 = this.getReadableDatabase();
        Cursor c1 = db1.query("reminder2",null,null,null,null,null,null);
        return c1;
    }
    public void deleteFromList(int id){
        SQLiteDatabase db=this.getWritableDatabase();
        db.delete("reminder2", "id=" + id,null);
        db.close();

    }


    public void deleteLocation(int id){
        SQLiteDatabase db=this.getWritableDatabase();
        db.delete("reminder", "id=" + id,null);
        db.close();

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists reminder2");
        db.execSQL("drop table if exists reminder");
        onCreate(db);


    }


}
