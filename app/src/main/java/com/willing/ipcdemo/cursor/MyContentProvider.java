package com.willing.ipcdemo.cursor;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.RemoteException;
import android.util.Log;

import com.willing.ipcdemo.aidl.AidlBean;
import com.willing.ipcdemo.aidl.IAidlInterface;
import com.willing.ipcdemo.aidl.ICallback;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class MyContentProvider extends ContentProvider {

    private String TAG = MyContentProvider.class.getSimpleName();

    @Override
    public boolean onCreate() {
        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        return MyCursor.setBinder(new IAidlInterface.Stub() {
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
            public AidlBean testBeanIn(AidlBean aidlBean) {
                return null;
            }

            @Override
            public AidlBean testBeanOut(AidlBean aidlBean) {
                return null;
            }

            @Override
            public AidlBean testBeanInout(AidlBean aidlBean) {
                return null;
            }

            @Override
            public void testCallback(String str, ICallback callback) {

            }
        });
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
