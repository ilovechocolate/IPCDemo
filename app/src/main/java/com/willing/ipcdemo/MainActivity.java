package com.willing.ipcdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.willing.ipcdemo.aidl.AidlActivity;
import com.willing.ipcdemo.cursor.CursorActivity;
import com.willing.ipcdemo.demo.DemoActivity;
import com.willing.ipcdemo.messenger.MessengerActivity;
import com.willing.ipcdemo.proxy.ProxyActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void startDemo(View v) {
        startActivity(DemoActivity.class);
    }

    public void startMessenger(View v) {
        startActivity(MessengerActivity.class);
    }

    public void startAidl(View v) {
        startActivity(AidlActivity.class);
    }

    public void startProxy(View v) {
        startActivity(ProxyActivity.class);
    }

    public void startContent(View v) {
        startActivity(CursorActivity.class);
    }

    private void startActivity(Class clazz) {
        this.startActivity(new Intent(this, clazz));
    }

}
