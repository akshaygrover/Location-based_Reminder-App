package com.netmaxtech.reminderlite.core;

import android.content.Context;
import android.os.PowerManager;

/**
 * Created by Jagdeep on 17/05/16.
 */
public class AlarmWakeLock {

    private static PowerManager.WakeLock myWakeLock;

    public static void acquireWakeLock(Context con)
    {
        PowerManager pm = (PowerManager)con.getSystemService(Context.POWER_SERVICE);
        myWakeLock = pm.newWakeLock(
                PowerManager.PARTIAL_WAKE_LOCK |
                        PowerManager.ACQUIRE_CAUSES_WAKEUP |
                        PowerManager.ON_AFTER_RELEASE, "wakelock"
        );
        myWakeLock.acquire();



    }


    public static void releaseWakeLock()
    {
        if(myWakeLock != null)
        {
            myWakeLock.release();
            myWakeLock = null;
        }


    }




}
