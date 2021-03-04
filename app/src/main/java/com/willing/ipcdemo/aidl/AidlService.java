package com.willing.ipcdemo.aidl;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;

public class AidlService extends Service {

    private String TAG = AidlService.class.getSimpleName();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "AidlService onBind");
        return new AidlBinder();
    }

    public class AidlBinder extends IAidlInterface.Stub {

        @Override
        public int testInt(int i) {
            Log.d(TAG, "client send int: " + i);
            return i + 1000;
        }

        @Override
        public String testString(String str) {
            Log.d(TAG, "client send string: " + str);
            return "hello, client";
        }

        @Override
        public AidlBean testBeanIn(AidlBean aidlBean) throws RemoteException {
            Log.d(TAG, "received before: " + aidlBean.toString());
            updateBean(aidlBean);
            Log.d(TAG, "received after: " + aidlBean.toString());
            return aidlBean;
        }

        @Override
        public AidlBean testBeanOut(AidlBean aidlBean) throws RemoteException {
            Log.d(TAG, "received before: " + aidlBean.toString());
            updateBean(aidlBean);
            Log.d(TAG, "received after: " + aidlBean.toString());
            return aidlBean;
        }

        @Override
        public AidlBean testBeanInout(AidlBean aidlBean) throws RemoteException {
            Log.d(TAG, "received before: " + aidlBean.toString());
            updateBean(aidlBean);
            Log.d(TAG, "received after: " + aidlBean.toString());
            return aidlBean;
        }

        private AidlBean updateBean(AidlBean aidlBean) {
            aidlBean.setI(aidlBean.getI() + 1000);
            aidlBean.setStr("hello, client");
            DataBean dataBean = aidlBean.getDataBean();
            if (dataBean == null) {
                dataBean = new DataBean();
            }
            dataBean.setData("finish test data");
            aidlBean.setDataBean(dataBean);
            List<ListBean> listBeans = aidlBean.getListBeans();
            if (listBeans == null) {
                listBeans = new ArrayList<>();
            }
            ListBean listBean1 = new ListBean();
            listBean1.setNums(new int[] {11,12,13,14,15});
            listBeans.add(listBean1);
            ListBean listBean2 = new ListBean();
            listBean2.setNums(new int[] {16,17,18,19,20});
            listBeans.add(listBean2);
            aidlBean.setListBeans(listBeans);
            return aidlBean;
        }

        @Override
        public void testCallback(String str, ICallback callback) throws RemoteException {
            Log.d(TAG, "send from client: " + str);
            callback.onCall("finish test callback");
        }
    }
}
