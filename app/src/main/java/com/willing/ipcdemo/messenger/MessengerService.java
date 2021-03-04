package com.willing.ipcdemo.messenger;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class MessengerService extends Service {

    private String TAG = MessengerService.class.getSimpleName();
    public static String KEY = "lalala";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "MessengerService onBind");
        return messenger.getBinder();
    }

    Messenger messenger = new Messenger(new MessengerHandler());

    class MessengerHandler extends Handler {
        @Override
        public void handleMessage(@NonNull Message msg) {
            Log.d(TAG, "handleMessage: " + msg.getData().getString(KEY));
            // 回复客户端的消息
            Message message = Message.obtain();
            Bundle bundle = new Bundle();
            bundle.putString(KEY, "hello, client");
            message.setData(bundle);
            try {
                msg.replyTo.send(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
