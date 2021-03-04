package com.willing.ipcdemo.aidl;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Arrays;

public class ListBean implements Parcelable {

    private int[] nums;

    public int[] getNums() {
        return nums;
    }

    public void setNums(int[] nums) {
        this.nums = nums;
    }

    public ListBean() {}

    protected ListBean(Parcel in) {
        nums = in.createIntArray();
    }

    public static final Creator<ListBean> CREATOR = new Creator<ListBean>() {
        @Override
        public ListBean createFromParcel(Parcel in) {
            return new ListBean(in);
        }

        @Override
        public ListBean[] newArray(int size) {
            return new ListBean[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeIntArray(nums);
    }

    @Override
    public String toString() {
        return "ListBean{" +
                "nums=" + Arrays.toString(nums) +
                '}';
    }
}

