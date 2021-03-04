package com.willing.ipcdemo.proxy;

import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;

import static com.willing.ipcdemo.proxy.MyStub.TRANSACTION_testInt;
import static com.willing.ipcdemo.proxy.MyStub.TRANSACTION_testString;

public class MyProxy implements IMyInterface {

    private IBinder mBinder;

    public MyProxy(IBinder binder) {
        this.mBinder = binder;
    }

    @Override
    public int testInt(int i) throws RemoteException {
        Parcel data = Parcel.obtain();
        Parcel reply = Parcel.obtain();
        int res = 0;
        try {
            data.writeInt(i);
            mBinder.transact(TRANSACTION_testInt, data, reply, 0);
            res = reply.readInt();
        } finally {
            data.recycle();
            reply.recycle();
        }
        return res;
    }

    @Override
    public String testString(String str) throws RemoteException {
        Parcel data = Parcel.obtain();
        Parcel reply = Parcel.obtain();
        String res = "";
        try {
            data.writeString(str);
            mBinder.transact(TRANSACTION_testString, data, reply, 0);
            res = reply.readString();
        } finally {
            data.recycle();
            reply.recycle();
        }
        return res;
    }

    @Override
    public IBinder asBinder() {
        return mBinder;
    }
}