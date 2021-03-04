package com.willing.ipcdemo.proxy;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

public class ProxyService extends Service {

    private String TAG = ProxyService.class.getSimpleName();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "ProxyService onBind");
        return new MyStub();
    }
}
