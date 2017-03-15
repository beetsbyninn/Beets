package com.github.beetsbyninn.beets;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;

/**
 * A Service Connection between a service and a activity.
 */
public class BeetsServiceConnection implements ServiceConnection {
    private MainActivity mMainAcitivty;
    private BeetsService service;

    /**
     * Gets a reference to the Main Activity.
     * @param a
     *      A Reference to main activity
     */
    public BeetsServiceConnection(MainActivity a) {
        this.mMainAcitivty = a;
    }


    @Override
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        BeetsService.LocalBinder binder = (BeetsService.LocalBinder) iBinder;
       service = binder.getService();
        mMainAcitivty.setService(binder.getService());
        mMainAcitivty.setBound(true);
        binder.getService().setListenerActivity(mMainAcitivty);
    }

    @Override
    public void onServiceDisconnected(ComponentName componentName) {
        mMainAcitivty.setBound(false);
    }
    public void stop(){
        service.stopSelf();
    }
}
