package com.netmaxtech.reminderlite;

/**
 * Created by jagdeep on 03/05/15.
 */
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class SplashScreenActivity extends Activity {


/*
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        Window window = getWindow();
        window.setFormat(PixelFormat.RGBA_8888);
    }*/


    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.splash);
       StartAnimations();

        t1.start();

     //   setContentView(new MyView(this));


    }
    private void StartAnimations() {
        Animation anim1 = AnimationUtils.loadAnimation(this, R.anim.alpha);
        anim1.reset();
        LinearLayout l=(LinearLayout) findViewById(R.id.lin_lay);
        l.clearAnimation();
        l.startAnimation(anim1);



        Animation anim2 = AnimationUtils.loadAnimation(this, R.anim.scale);
        anim2.reset();
        ImageView iv = (ImageView) findViewById(R.id.logo);
        iv.clearAnimation();
        iv.startAnimation(anim2);

    }


    Thread t1  =  new Thread(new Runnable() {
        @Override
        public void run() {

            try {
                Thread.sleep(4000);
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

            Intent in = new Intent(SplashScreenActivity.this, MainActivity.class);
            startActivity(in);

           finish();
            try {
                t1.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    };



}