package com.willing.ipcdemo.proxy;

import android.os.IInterface;
import android.os.RemoteException;

public interface IMyInterface extends IInterface {
    int testInt(int i) throws RemoteException;
    String testString(String str) throws RemoteException;
}