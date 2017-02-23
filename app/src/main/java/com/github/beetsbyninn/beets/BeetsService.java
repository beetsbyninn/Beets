package com.github.beetsbyninn.beets;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.LocaleList;

/**
 * The service listens for events from the sensor handler.
 */
public class BeetsService extends Service {
    private SensorHandler sensorHandler = new SensorHandler(this);
    private LocalBinder mBinder = new LocalBinder();

    public BeetsService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        sensorHandler.registerListener();
        return mBinder;

    }



    @Override
    public void onCreate() {
        super.onCreate();
        sensorHandler.onCreate();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        sensorHandler.onDestroy();
    }

    /**
     *  Returns a reference to the service.
     */
    public class LocalBinder extends Binder {
        BeetsService getService() {
            return BeetsService.this;
        }
    }
}
