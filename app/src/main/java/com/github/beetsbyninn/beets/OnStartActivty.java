package com.github.beetsbyninn.beets;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class OnStartActivty extends AppCompatActivity {
    private TextView tvLogo;
    private SharedPreferences mSharedPreferences = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_start_activty);
        tvLogo = (TextView)findViewById(R.id.tvLogo);
        Typeface typeFace=Typeface.createFromAsset(getAssets(),"street cred.ttf");
        tvLogo.setTypeface(typeFace);

        mSharedPreferences = getSharedPreferences("MainActivity", Context.MODE_PRIVATE);
        startAnotherActivity();
    }

    public void startAnotherActivity(){
        Thread splashScreenThread = new Thread(){
            @Override
            public void run() {
                try {
                    super.run();
                    sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    if(mSharedPreferences.getBoolean("isFirstStart",true)){
                        mSharedPreferences.edit().putBoolean("isFirstStart",false).commit();
                        Intent slideActivity = new Intent(OnStartActivty.this, TutorialActivity.class);
                        startActivity(slideActivity);
                        finish();
                    }else{
                        Intent i = new Intent(OnStartActivty.this,MainActivity.class);
                        startActivity(i);
                        finish();
                    }
                }

            }
        };
        splashScreenThread.start();
    }
}
