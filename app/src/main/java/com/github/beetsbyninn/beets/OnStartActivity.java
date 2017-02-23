package com.github.beetsbyninn.beets;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class OnStartActivity extends AppCompatActivity {
    private TextView tvLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_start);
        tvLogo = (TextView)findViewById(R.id.tvLogo);
        Typeface typeFace=Typeface.createFromAsset(getAssets(),"street cred.ttf");
        tvLogo.setTypeface(typeFace);

        Thread splashScreenThread = new Thread(){
            @Override
            public void run() {
                try {
                    super.run();
                    sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    Intent i = new Intent(OnStartActivity.this,MainActivity.class);
                    startActivity(i);
                    finish();
                }

            }
        };

        splashScreenThread.start();
    }
}
