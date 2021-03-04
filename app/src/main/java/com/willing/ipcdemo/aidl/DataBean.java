package com.willing.ipcdemo.aidl;

import android.os.Parcel;
import android.os.Parcelable;

public class DataBean implements Parcelable {

    private String data;

    public DataBean() {}

    protected DataBean(Parcel in) {
        data = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(data);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<DataBean> CREATOR = new Creator<DataBean>() {
        @Override
        public DataBean createFromParcel(Parcel in) {
            return new DataBean(in);
        }

        @Override
        public DataBean[] newArray(int size) {
            return new DataBean[size];
        }
    };

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "DataBean{" +
                "data='" + data + '\'' +
                '}';
    }
}