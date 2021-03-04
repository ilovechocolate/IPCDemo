package com.willing.ipcdemo.aidl;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.willing.ipcdemo.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class AidlActivity extends AppCompatActivity {

    private String TAG = AidlActivity.class.getSimpleName();

    private TextView int_result, str_result, callback_result;

    private String PACKAGE_NAME = "com.willing.ipcdemo";
    private String ACTION = "ipcdemo.action.start.AidlService";

    private IAidlInterface mService;
    private boolean isBinded = false;
    private int testInt = 234;
    private String testStr = "hello, server";
    private String testData = "start test data";
    private String test = "start test callback";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aidl);

        ((TextView) findViewById(R.id.aidl_des)).setText("测试通过AIDL接口调用服务功能");
        int_result = (TextView) findViewById(R.id.aidl_int_result);
        int_result.setText("ori data: " + testInt);
        str_result = (TextView) findViewById(R.id.aidl_str_result);
        str_result.setText(testStr);
        callback_result = (TextView) findViewById(R.id.aidl_callback_result);
        callback_result.setText(test);
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
            mService = IAidlInterface.Stub.asInterface(service);
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
            } catch (RemoteException e) {
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
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public void testBeanIn(View v) {
        Log.d(TAG, "testBeanIn");
        if (isBinded) {
            try {
                if (mService != null) {
                    AidlBean aidlBean = getAidlBean();
                    Log.d(TAG, "send before: " + aidlBean.toString());
                    AidlBean retAidlBean = mService.testBeanIn(aidlBean);
                    Log.d(TAG, "send after: " + aidlBean.toString());
                    Log.d(TAG, "return after: " + retAidlBean.toString());
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public void testBeanOut(View v) {
        Log.d(TAG, "testBeanOut");
        if (isBinded) {
            try {
                if (mService != null) {
                    AidlBean aidlBean = getAidlBean();
                    Log.d(TAG, "send before: " + aidlBean.toString());
                    AidlBean retAidlBean = mService.testBeanOut(aidlBean);
                    Log.d(TAG, "send after: " + aidlBean.toString());
                    Log.d(TAG, "return after: " + retAidlBean.toString());
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public void testBeanInout(View v) {
        Log.d(TAG, "testBeanInout");
        if (isBinded) {
            try {
                if (mService != null) {
                    AidlBean aidlBean = getAidlBean();
                    Log.d(TAG, "send before: " + aidlBean.toString());
                    AidlBean retAidlBean = mService.testBeanInout(aidlBean);
                    Log.d(TAG, "send after: " + aidlBean.toString());
                    Log.d(TAG, "return after: " + retAidlBean.toString());
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    private AidlBean getAidlBean() {
        DataBean dataBean = new DataBean();
        dataBean.setData(testData);

        List<ListBean> listBeans = new ArrayList<>();
        ListBean listBean1 = new ListBean();
        listBean1.setNums(new int[] {1,2,3,4,5});
        listBeans.add(listBean1);
        ListBean listBean2 = new ListBean();
        listBean2.setNums(new int[] {6,7,8,9,0});
        listBeans.add(listBean2);

        AidlBean aidlBean = new AidlBean();
        aidlBean.setI(testInt);
        aidlBean.setStr(testStr);
        aidlBean.setDataBean(dataBean);
        aidlBean.setListBeans(listBeans);
        return aidlBean;
    }

    public void testCallback(View v) {
        Log.d(TAG, "testCallback");
        if (isBinded) {
            try {
                if (mService != null) {
                    mService.testCallback(test, new ICallback.Stub() {
                        @Override
                        public void onCall(String str) throws RemoteException {
                            Log.d(TAG, "received from server: " + str);
                            callback_result.setText(str);
                        }
                    });
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

}
