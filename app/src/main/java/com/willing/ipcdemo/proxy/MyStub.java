package com.willing.ipcdemo.proxy;

import android.os.Binder;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class MyStub extends Binder implements IMyInterface{

    private String TAG = MyStub.class.getSimpleName();

    public static final int TRANSACTION_testInt = IBinder.FIRST_CALL_TRANSACTION + 1;
    public static final int TRANSACTION_testString = IBinder.FIRST_CALL_TRANSACTION + 2;

    @Override
    protected boolean onTransact(int code, @NonNull Parcel data, @Nullable Parcel reply, int flags) throws RemoteException {
        switch (code) {
            case TRANSACTION_testInt:
                reply.writeInt(this.testInt(data.readInt()));
                return true;
            case TRANSACTION_testString:
                reply.writeString(this.testString(data.readString()));
                return true;
            default:
                break;
        }
        return super.onTransact(code, data, reply, flags);
    }

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
    public IBinder asBinder() {
        return null;
    }
}