package com.willing.ipcdemo.proxy;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;

import com.willing.ipcdemo.R;
import com.willing.ipcdemo.aidl.ICallback;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ProxyActivity extends AppCompatActivity {

    private String TAG = ProxyActivity.class.getSimpleName();

    private TextView int_result, str_result;
    private Switch swc_service;

    private String PACKAGE_NAME = "com.willing.ipcdemo";
    private String ACTION = "ipcdemo.action.start.ProxyService";

    private IMyInterface mService;
    private boolean isBinded = false;
    private int testInt = 234;
    private String testStr = "hello, server";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proxy);

        ((TextView) findViewById(R.id.proxy_des)).setText("测试自实现代理接口调用服务功能");
        int_result = (TextView) findViewById(R.id.proxy_int_result);
        int_result.setText("ori data: " + testInt);
        str_result = (TextView) findViewById(R.id.proxy_str_result);
        str_result.setText("ori data: " + testStr);
    }

    public void bindService(View v) {
        Log.d(TAG, "bindService");
        Intent intent = new Intent();
        intent.setPackage(PACKAGE_NAME);
        intent.setAction(ACTION);
        bindService(intent, connection, BIND_AUTO_CREATE);
    }

    public void unbindService(View v) {
        Log.d(TAG, "unbindService");
        if (isBinded) {
            unbindService(connection);
            isBinded = false;
        }
    }

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            isBinded = true;
            mService = new MyProxy(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            isBinded = false;
            mService = null;
        }
    };

    public void testInt(View v) {
        Log.d(TAG, "testInt");
        if (isBinded) {
            try {
                if (mService != null) {
                    int resInt = mService.testInt(testInt);
                    int_result.setText("service returned: " + resInt);
                    Log.d(TAG, "service returned int: " + resInt);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void testString(View v) {
        Log.d(TAG, "testString");
        if (isBinded) {
            try {
                if (mService != null) {
                    String resStr = mService.testString(testStr);
                    str_result.setText("service returned: " + resStr);
                    Log.d(TAG, "service returned string: " + resStr);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
