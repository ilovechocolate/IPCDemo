package com.willing.ipcdemo.aidl;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Iterator;
import java.util.List;

public class AidlBean implements Parcelable {

    private int i;
    private String str;
    private DataBean dataBean;
    private List<ListBean> listBeans;

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }

    public DataBean getDataBean() {
        return dataBean;
    }

    public void setDataBean(DataBean dataBean) {
        this.dataBean = dataBean;
    }

    public List<ListBean> getListBeans() {
        return listBeans;
    }

    public void setListBeans(List<ListBean> listBeans) {
        this.listBeans = listBeans;
    }

    public AidlBean() {}

    protected AidlBean(Parcel in) {
        readFromParcel(in);
    }

    public static final Creator<AidlBean> CREATOR = new Creator<AidlBean>() {
        @Override
        public AidlBean createFromParcel(Parcel in) {
            return new AidlBean(in);
        }

        @Override
        public AidlBean[] newArray(int size) {
            return new AidlBean[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(i);
        dest.writeString(str);
        dest.writeParcelable(dataBean, flags);
        dest.writeList(listBeans);
    }

    public void readFromParcel(Parcel in) {
        i = in.readInt();
        str = in.readString();
        dataBean = in.readParcelable(DataBean.class.getClassLoader());
        listBeans = in.readArrayList(ListBean.class.getClassLoader());
    }

    @Override
    public String toString() {
        return "AidlBean{" +
                "i=" + i +
                ", str='" + str + '\'' +
                ", dataBean='" + (dataBean==null?"null":dataBean.toString()) + '\'' +
                ", listBeans='" + (listBeans==null?"null":listToString(listBeans)) + "\'" +
                '}';
    }

    private String listToString(List<ListBean> listBeans) {
        String res = "";
        Iterator iterator = listBeans.iterator();
        while (iterator.hasNext()) {
            res = res + iterator.next().toString() + " ";
        }
        return res;
    }


}