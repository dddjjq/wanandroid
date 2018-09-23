package com.dingyl.wanandroid.data;

import com.google.gson.annotations.SerializedName;

import org.greenrobot.greendao.annotation.Entity;

import java.util.ArrayList;

public class BannerData {

    private ArrayList<BannerDataBean> data;
    private int errorCode;
    private String errorMsg;

    public ArrayList<BannerDataBean> getData() {
        return data;
    }

    public void setData(ArrayList<BannerDataBean> data) {
        this.data = data;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }



}
