package com.willing.ipcdemo.demo;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

import androidx.annotation.Nullable;

import static com.willing.ipcdemo.demo.DemoActivity.UPDATE_COUNT;
import static com.willing.ipcdemo.demo.DemoActivity.UPDATE_LOG;
import static com.willing.ipcdemo.demo.DemoActivity.UPDATE_RESULT;

public class DemoService extends Service {

    private String TAG = DemoService.class.getSimpleName();
    private boolean startFlag = false, bindFlag = false;
    private int count = 0;

    @Override
    public void onCreate() {
        log("DemoService onCreate");
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        log("DemoService onStartCommand");
        log("current thread = " + Thread.currentThread().getName());
        startFlag = true;
        runService();
        return super.onStartCommand(intent, flags, startId);
    }

    /**
     * 开启一个后台计数服务，通过发送Message的方式更新UI
     */
    public void runService() {
        log("Service is running");
        log("current thread = " + Thread.currentThread().getName());
        new Thread(new Runnable() {
            @Override
            public void run() {
                log("current thread = " + Thread.currentThread().getName());
                while (startFlag || bindFlag) {
                    try {
                        Message message = new Message();
                        message.what = UPDATE_COUNT;
                        message.arg1 = count++;
                        DemoActivity.mHandler.sendMessage(message);
                        Thread.sleep(1000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        log( "DemoService onBind");
        bindFlag = true;
        return new DemoBinder();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        log( "DemoService onUnbind");
        bindFlag = false;
        return super.onUnbind(intent);
    }

    /**
     * 因为 Binder 实现了 IBiner，因此可以直接继承 Binder 以减少工作量
     */
    public class DemoBinder extends Binder {

        /**
         * 获取 DemoService 服务的实例
         * @return
         */
        public DemoService getService() {
            return DemoService.this;
        }

        /**
         * 测试启动计数线程
         */
        public void testCount() {
            log("testCount called");
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while(bindFlag) {
                        try {
                            Message message = new Message();
                            message.what = UPDATE_RESULT;
                            message.arg1 = count++;
                            DemoActivity.mHandler.sendMessage(message);
                            Thread.sleep(1000);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();
        }

        /**
         * 测试回调
         */
        public void testCallback(String message, DemoCallback callback) {
            log("testCallback called");
            log("received message: " + message);
            callback.onCall("this is a callback");
        }
    }

    /**
     * 输出日志且发送到主线程中
     * @param content
     */
    private void log(String content) {
        Log.d(TAG, content);
        Message message = new Message();
        message.what = UPDATE_LOG;
        String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:dd").format(new Date(System.currentTimeMillis()));
        message.obj = time + " [" + TAG + "] " + content;
        DemoActivity.mHandler.sendMessage(message);
    }

    @Override
    public void onDestroy() {
        log("DemoService onDestroy");
        startFlag = false;
        bindFlag = false;
        super.onDestroy();
    }

    public interface DemoCallback {
        void onCall(Object object);
    }
}



