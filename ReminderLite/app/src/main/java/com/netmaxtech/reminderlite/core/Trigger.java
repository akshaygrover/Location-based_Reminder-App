package com.netmaxtech.reminderlite.core;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.netmaxtech.reminderlite.AlarmTrigger;

/**
 * Created by Jagdeep on 17/05/16.
 */
public class Trigger extends BroadcastReceiver{


    @Override
    public void onReceive(Context context, Intent intent) {

        Intent in = new Intent(context, AlarmTrigger.class);
        in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(in);


    }
}
