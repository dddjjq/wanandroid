package com.dingyl.wanandroid.data;

import java.util.ArrayList;

public class ProjData {

    private ArrayList<ProjDataBean> data;
    private int errorCode;
    private String errorMsg;

    public ArrayList<ProjDataBean> getData() {
        return data;
    }

    public void setData(ArrayList<ProjDataBean> data) {
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
