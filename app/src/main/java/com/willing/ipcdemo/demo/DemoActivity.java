package com.willing.ipcdemo.demo;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.willing.ipcdemo.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class DemoActivity extends AppCompatActivity {

    private String TAG = DemoActivity.class.getSimpleName();

    private static TextView count, result, log;
    private boolean isBinded;
    private DemoService mService;
    private DemoService.DemoBinder mBinder;

    public final static int UPDATE_COUNT = 1000, UPDATE_RESULT = 1001, UPDATE_LOG = 1002;
    public static Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case UPDATE_COUNT:
                    count.setText("service count started：" + msg.arg1);
                    break;
                case UPDATE_RESULT:
                    result.setText("test service result：" + msg.arg1);
                    break;
                case UPDATE_LOG:
                    log.append("\n" + msg.obj);
                    break;
                default:
                    break;
            }
            return false;
        }
    });

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);

        ((TextView) findViewById(R.id.demo_des)).setText("测试服务启动和绑定相关功能");
        count = (TextView) findViewById(R.id.demo_count);
        result = (TextView) findViewById(R.id.demo_result);
        log = (TextView) findViewById(R.id.demo_log);
        log.setMovementMethod(ScrollingMovementMethod.getInstance());
    }

    public void startService(View v) {
        startService(new Intent(this, DemoService.class));
        toast("start service");
    }

    public void stopService(View v) {
        stopService(new Intent(this, DemoService.class));
        toast("stop service");
        count.setText("reset to 0");
    }

    public void bindService(View v) {
        bindService(new Intent(this, DemoService.class), connection, BIND_AUTO_CREATE);
        toast("bind service");
    }

    public void unbindService(View v) {
        if (isBinded) {
            unbindService(connection);
            isBinded = false;
            mBinder = null;
            mService = null;
            toast("unbind service");
            result.setText("service unbinded");
        } else {
            toast("service is unbinded");
        }
    }

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            toast("service connected");
            Log.d(TAG, "ComponentName = " + name);
            isBinded = true;
            mBinder = (DemoService.DemoBinder)service;
            mService = mBinder.getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            toast("service unconnected");
            isBinded = false;
            mBinder = null;
            mService = null;
        }
    };

    public void testService(View v) {
        if (isBinded) {
            /**
             * 测试服务接口
             */
//            mService.runService();
            /**
             * 测试binder接口
             */
//            mBinder.testCount();
            mBinder.testCallback("hello from activity", new DemoService.DemoCallback() {
                @Override
                public void onCall(Object object) {
                    result.setText((String)object);
                }
            });
        } else {
            toast("service is unbinded");
        }
    }

    private void toast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        if (isBinded) {
            unbindService(connection);
            isBinded = false;
            mBinder = null;
            mService = null;
        }
        super.onDestroy();
    }
}

