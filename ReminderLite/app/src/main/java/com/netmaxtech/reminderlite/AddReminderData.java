package com.netmaxtech.reminderlite;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
//import android.support.design.widget.FloatingActionButton;
//import android.support.design.widget.Snackbar;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import com.netmaxtech.reminderlite.R;

import com.netmaxtech.reminderlite.Database.DataHelper;

import com.netmaxtech.reminderlite.ui.ShowLocationsReminder;

import org.w3c.dom.Text;

import java.util.Calendar;

public class AddReminderData extends AppCompatActivity implements View.OnClickListener, SeekBar.OnSeekBarChangeListener, NumberPicker.OnValueChangeListener {
    Button b8,b9,b10,b11,b12;
    TextView txtloc,txtdis;
    int hour,min,yr,month,day;
    DataHelper dh;
    EditText et1,et2,et3;
    CheckBox cb1,cb2;
    int km=100;
    int m,k;



    private NumberPicker n1,n2;

    String lat, lng, addr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reminder_data);

       initUi();

        getGivenData();


        dh=new DataHelper(AddReminderData.this);





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



    private void initUi()
    {
        b8=(Button)findViewById(R.id.button8);
        b9=(Button)findViewById(R.id.button9);
        b11=(Button)findViewById(R.id.button11);
        b12=(Button)findViewById(R.id.button12);
        b8.setOnClickListener(this);
        b9.setOnClickListener(this);

        b11.setOnClickListener(this);
        b12.setOnClickListener(this);
        txtloc = (TextView)findViewById(R.id.textView8);
        txtdis = (TextView)findViewById(R.id.textView12);

        et1 = (EditText)findViewById(R.id.editText);
        et2 = (EditText)findViewById(R.id.editText2);
        et3 = (EditText)findViewById(R.id.editText3);

        cb1 = (CheckBox)findViewById(R.id.checkBox);
        cb2 = (CheckBox)findViewById(R.id.checkBox2);



        n1 = (NumberPicker)findViewById(R.id.numberPicker);
        n1.setMinValue(0);
        n1.setMaxValue(50);
        n1.setOnValueChangedListener(this);



        n2 = (NumberPicker)findViewById(R.id.numberPicker2);
        n2.setMinValue(1);
        n2.setMaxValue(9);
        n2.setOnValueChangedListener(this);



        txtdis.setText(String.valueOf(km));


    }


    private void getGivenData()
    {
        Bundle b = getIntent().getExtras();
        lat = b.getString("lat");
        lng = b.getString("lng");
        addr = b.getString("addr");

        txtloc.setText(lat +"\n"+ addr+ "\n" + lng);

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

            case R.id.button11:
                if(TextUtils.isEmpty(et1.getText()))
                {
                    Toast.makeText(this, "Enter Reminder Text", Toast.LENGTH_LONG).show();
                    return;
                }
                if(!(cb1.isChecked() || cb2.isChecked()))
                {
                    Toast.makeText(this, "Atleast Select Enter or Exit Option", Toast.LENGTH_LONG).show();
                    return;
                }
                if(km == 0)
                {
                    Toast.makeText(this, "Select Distance", Toast.LENGTH_LONG).show();
                    return;
                }



                String distance = String.valueOf(km);
                b11.setEnabled(false);
                dh.addData(lat, lng, et2.getText().toString(),
                        et3.getText().toString(), addr, et1.getText().toString(),
                        distance,cb1.isChecked(),cb2.isChecked());
                Toast.makeText(AddReminderData.this,"Reminder Saved",Toast.LENGTH_LONG).show();
                th.start();
                break;
            case R.id.button12:
                finish();



                break;





        }

    }
    public void setTime() {
        Calendar cal = Calendar.getInstance();
        new TimePickerDialog(AddReminderData.this, tl1, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show();
    }

    TimePickerDialog.OnTimeSetListener tl1=new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            hour=hourOfDay;
            min=minute;
            et3.setText(hour +":"+ min);




        }
    };
    public void setDate(){
        Calendar cal1=Calendar.getInstance();
        new DatePickerDialog(AddReminderData.this,dl1,cal1.get(Calendar.YEAR),cal1.get(Calendar.MONTH),cal1.get(Calendar.DAY_OF_MONTH)).show();
    }
    DatePickerDialog.OnDateSetListener dl1=new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            yr=year;
            month=monthOfYear;
            day=dayOfMonth;
            et2.setText(day +":"+ month +":"+ yr);

        }
    };



    Thread th = new Thread(new Runnable() {
        @Override
        public void run() {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            h1.sendMessage(h1.obtainMessage());
        }
    });

    Handler h1 = new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Intent i = new Intent(AddReminderData.this,GeoActivity.class);
            startActivity(i);
            finish();

        }
    };

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        txtdis.setText(String.valueOf(progress));
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {

        switch (picker.getId())
        {
            case R.id.numberPicker:


                k = newVal * 1000;
                km = k + m;
                txtdis.setText(String.valueOf(km) + " Meters");

                break;

            case R.id.numberPicker2:


                m = newVal * 100;

                km = k + m;
                txtdis.setText(String.valueOf(km) +  " Meters");

                break;

        }


    }
}


