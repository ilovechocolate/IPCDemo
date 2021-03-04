// IAidlInterface.aidl
package com.willing.ipcdemo.aidl;

import com.willing.ipcdemo.aidl.AidlBean;
import com.willing.ipcdemo.aidl.ICallback;

interface IAidlInterface {

    int testInt(int i);
    String testString(String str);

    AidlBean testBeanIn(in AidlBean aidlBean);
    AidlBean testBeanOut(out AidlBean aidlBean);
    AidlBean testBeanInout(inout AidlBean aidlBean);

    void testCallback(String str, ICallback callback);
}