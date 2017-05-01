package com.netmaxtech.reminderlite.ui;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
//import android.support.design.widget.FloatingActionButton;
//import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import com.netmaxtech.reminderlite.R;

import com.netmaxtech.reminderlite.Database.DataHelper;
import com.netmaxtech.reminderlite.core.Trigger;

import java.util.Calendar;

public class SimpleReminder extends AppCompatActivity implements View.OnClickListener {
    Button b8,b9,b10,b11,b12;
    TextView t2,t3,t4;
    int hour,min,yr,month,day;
    DataHelper dh;

    Calendar cal1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_simple_reminder);

        b8=(Button)findViewById(R.id.button8);
        b9=(Button)findViewById(R.id.button9);
        b10=(Button)findViewById(R.id.button10);
        b11=(Button)findViewById(R.id.button11);
        b12=(Button)findViewById(R.id.button12);
        b8.setOnClickListener(this);
        b9.setOnClickListener(this);
        b10.setOnClickListener(this);
        b11.setOnClickListener(this);
        b12.setOnClickListener(this);
        t2=(TextView)findViewById(R.id.textView2);
        t3=(TextView)findViewById(R.id.textView3);
        t4=(TextView)findViewById(R.id.textView4);
         dh=new DataHelper(SimpleReminder.this);


        cal1 = Calendar.getInstance();


  /*       Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
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
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button8:
                setDate();




                break;
            case R.id.button9:
                setTime();




                break;
            case R.id.button10:
                setDisc();




                break;
            case R.id.button11:
              //  dh.addData2(t2.getText().toString(),t3.getText().toString(),t4.getText().toString());



                setAlarm();


                Toast.makeText(SimpleReminder.this,"reminder added",Toast.LENGTH_LONG).show();





                break;
            case R.id.button12:
                finish();



                break;





        }

    }

    private void setAlarm()
    {
        Intent in = new Intent(SimpleReminder.this, Trigger.class);
        PendingIntent pi = PendingIntent.getBroadcast(SimpleReminder.this, 0, in, 0);

        AlarmManager am = (AlarmManager)getSystemService(ALARM_SERVICE);
        am.set(AlarmManager.RTC_WAKEUP, cal1.getTimeInMillis(), pi);





    }




    public void setTime() {
        Calendar cal = Calendar.getInstance();
        new TimePickerDialog(SimpleReminder.this, tl1, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show();
    }

        TimePickerDialog.OnTimeSetListener tl1=new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                hour=hourOfDay;
                min=minute;
                t3.setText(hour +":"+ min);

                cal1.set(Calendar.HOUR_OF_DAY, hourOfDay);
                cal1.set(Calendar.MINUTE, minute);
                cal1.set(Calendar.SECOND, 0);

            }
        };


    public void setDate(){
        Calendar cal1=Calendar.getInstance();
        new DatePickerDialog(SimpleReminder.this,dl1,cal1.get(Calendar.YEAR),cal1.get(Calendar.MONTH),cal1.get(Calendar.DAY_OF_MONTH)).show();
    }
    DatePickerDialog.OnDateSetListener dl1=new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            yr=year;
            month=monthOfYear;
            day=dayOfMonth;
            t2.setText(day +"/"+ month +"/"+ yr);

            cal1.set(Calendar.YEAR, year);
            cal1.set(Calendar.MONTH, monthOfYear);
            cal1.set(Calendar.DAY_OF_MONTH, dayOfMonth);


        }
    };
    public void setDisc(){
        final AlertDialog.Builder ad2=new AlertDialog.Builder(this);
        ad2.setTitle("Enter Reminder Description");
        final EditText et=new EditText(this);
        et.setInputType(InputType.TYPE_CLASS_TEXT);

        ad2.setView(et);
        ad2.setPositiveButton("ADD", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String e =et.getText().toString();
               t4.setText(e);

            }


        });
        ad2.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        ad2.show();
    }


    }


