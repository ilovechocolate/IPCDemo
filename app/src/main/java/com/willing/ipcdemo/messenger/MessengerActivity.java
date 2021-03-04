package com.willing.ipcdemo.messenger;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.willing.ipcdemo.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import static com.willing.ipcdemo.messenger.MessengerService.KEY;

public class MessengerActivity extends AppCompatActivity {

    private String TAG = MessengerActivity.class.getSimpleName();

    private TextView result;
    private boolean isBinded = false;
    private Messenger messenger;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messenger);

        ((TextView) findViewById(R.id.msg_des)).setText("测试通过Messenger进行进程间通信");
        result = ((TextView) findViewById(R.id.msg_result));
    }

    public void bindService(View v) {
        bindService(new Intent(this, MessengerService.class), connection, BIND_AUTO_CREATE);
        log("bind service");
    }

    public void unbindService(View v) {
        if (isBinded) {
            unbindService(connection);
            isBinded = false;
            messenger = null;
            log("unbind service");
        } else {
            log("service is unbinded");
        }
    }

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            log("service connected");
            /**
             * 通过服务端返回的IBinder对象创建Messenger，与服务端进行交互
             */
            messenger = new Messenger(service);
            isBinded = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            isBinded = false;
            messenger = null;
        }
    };

    public void sendMsg(View v) {
        if (isBinded) {
            Message msg = Message.obtain();
            Bundle bundle = new Bundle();
            bundle.putString(KEY, "hello, service");
            msg.setData(bundle);
            msg.replyTo = replyMessenger;
            try {
                messenger.send(msg);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            log("service is unbinded");
        }
    }

    private Messenger replyMessenger = new Messenger(new ReplyHandler());

    private class ReplyHandler extends Handler {
        @Override
        public void handleMessage(@NonNull Message msg) {
            Bundle bundle = msg.getData();
            log(bundle.getString(KEY));
            result.setText("received: " + bundle.getString(KEY));
            super.handleMessage(msg);
        }
    };

    private void log(String message) {
        Log.d(TAG, message);
    }

    @Override
    protected void onDestroy() {
        if (isBinded) {
            unbindService(connection);
            isBinded = false;
            messenger = null;
        }
        super.onDestroy();
    }
}