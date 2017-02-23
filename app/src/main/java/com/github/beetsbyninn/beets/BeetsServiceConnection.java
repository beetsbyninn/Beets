package com.github.beetsbyninn.beets;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;

/**
 * Created by patriklarsson on 2017-02-23.
 */

public class BeetsServiceConnection implements ServiceConnection {
    private MainActivity mMainAcitivty;

    public BeetsServiceConnection(MainActivity a) {
        this.mMainAcitivty = a;
    }

    @Override
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        BeetsService.LocalBinder binder = (BeetsService.LocalBinder) iBinder;

        mMainAcitivty.setService(binder.getService());
        mMainAcitivty.setBound(true);

    }

    @Override
    public void onServiceDisconnected(ComponentName componentName) {
        mMainAcitivty.setBound(false);
    }
}
