package com.netmaxtech.reminderlite.core;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by Jagdeep on 17/05/16.
 */
public class PhoneBootComp extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        Toast.makeText(context, "Phone Boot Completed", Toast.LENGTH_LONG).show();

    }
}
