package com.netmaxtech.reminderlite;

import android.app.KeyguardManager;
import android.media.MediaPlayer;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.netmaxtech.reminderlite.core.AlarmWakeLock;


public class AlarmTrigger extends ActionBarActivity implements View.OnClickListener {


    Button b1;
    static MediaPlayer mp1 ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_trigger);

        b1 = (Button)findViewById(R.id.button);
        b1.setOnClickListener(this);

        mp1 = MediaPlayer.create(this, R.raw.belltower);


        final Window window1 = getWindow();
        window1.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
        | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);


        KeyguardManager manager = (KeyguardManager) this.getSystemService(KEYGUARD_SERVICE);
        KeyguardManager.KeyguardLock lock = manager.newKeyguardLock("abc");
        lock.disableKeyguard();





    }

    @Override
    protected void onResume() {
        super.onResume();
        mp1.start();
        mp1.setLooping(true);

        AlarmWakeLock.acquireWakeLock(this);


    }

    @Override
    protected void onPause() {
        super.onPause();
        mp1.stop();

        AlarmWakeLock.releaseWakeLock();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_alarm_trigger, menu);
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
    public void onClick(View v) {

        mp1.stop();
    }
}
