package com.willing.ipcdemo.cursor;

import android.database.Cursor;
import android.database.MatrixCursor;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;

public class MyCursor extends MatrixCursor {

    private static String KEY = "BINDER";

    Bundle mBundle = new Bundle();

    public MyCursor(String[] columnNames, IBinder binder) {
        super(columnNames);
        if (binder != null) {
            mBundle.putParcelable(KEY, new MyParcelable(binder));
        }
    }

    public class MyParcelable implements Parcelable {

        IBinder mBinder;

        public MyParcelable(IBinder binder) {
            mBinder = binder;
        }

        protected MyParcelable(Parcel in) {
            mBinder = in.readStrongBinder();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeStrongBinder(mBinder);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public final Creator<MyParcelable> CREATOR = new Creator<MyParcelable>() {
            @Override
            public MyParcelable createFromParcel(Parcel in) {
                return new MyParcelable(in);
            }

            @Override
            public MyParcelable[] newArray(int size) {
                return new MyParcelable[size];
            }
        };
    }

    @Override
    public Bundle getExtras() {
        return mBundle;
    }

    public static IBinder getBinder(Cursor cursor) {
        Bundle bundle = cursor.getExtras();
        bundle.setClassLoader(MyCursor.class.getClassLoader());
        MyParcelable parcelable = bundle.getParcelable(KEY);
        return parcelable.mBinder;
    }

    public static Cursor setBinder(IBinder binder) {
        String[] columnNames = {"this", "is", "not", "important"};
        return new MyCursor(columnNames, binder);
    }
}
