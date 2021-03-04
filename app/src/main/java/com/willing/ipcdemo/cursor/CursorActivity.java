package com.willing.ipcdemo.cursor;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.willing.ipcdemo.R;
import com.willing.ipcdemo.aidl.IAidlInterface;

import androidx.appcompat.app.AppCompatActivity;

public class CursorActivity extends AppCompatActivity {

    private String TAG = CursorActivity.class.getSimpleName();

    public static String AUTHORITY = "com.willing.ipcdemo.cursor.MyContentProvider";
    public static Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    private TextView int_result, str_result;
    private int testInt = 234;
    private String testStr = "hello, server";
    private IAidlInterface mService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);

        ((TextView) findViewById(R.id.content_des)).setText("测试通过Cursor进程间通信");
        int_result = (TextView) findViewById(R.id.content_int_result);
        int_result.setText("ori data: " + testInt);
        str_result = (TextView) findViewById(R.id.content_str_result);
        str_result.setText("ori data: " + testStr);
    }

    public void getBinder(View v) {
        Cursor cursor = this.getContentResolver().query(CONTENT_URI, null, null, null, null);
        IBinder binder = MyCursor.getBinder(cursor);
        mService = IAidlInterface.Stub.asInterface(binder);
        Log.d(TAG, "getBinder succeed");
    }

    public void testInt(View v) {
        Log.d(TAG, "testInt");
        try {
            if (mService != null) {
                int resInt = mService.testInt(testInt);
                int_result.setText("service returned: " + resInt);
                Log.d(TAG, "service returned int: " + resInt);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void testString(View v) {
        Log.d(TAG, "testString");
        try {
            if (mService != null) {
                String resStr = mService.testString(testStr);
                str_result.setText("service returned: " + resStr);
                Log.d(TAG, "service returned string: " + resStr);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
