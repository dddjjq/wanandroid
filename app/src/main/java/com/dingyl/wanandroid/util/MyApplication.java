package com.dingyl.wanandroid.util;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.dingyl.wanandroid.greendao.DaoMaster;
import com.dingyl.wanandroid.greendao.DaoSession;

public class MyApplication extends Application {

    private static MyApplication myApplication;
    private DaoMaster.DevOpenHelper helper;
    private SQLiteDatabase db;
    private DaoMaster daoMaster;
    private DaoSession  daoSession;

    @Override
    public void onCreate(){
        super.onCreate();
        myApplication = this;
        setDatabase();
    }

    public static MyApplication getInstance(){
        return myApplication;
    }

    private void setDatabase(){
        helper = new DaoMaster.DevOpenHelper(this,"home-db",null);
        db = helper.getWritableDatabase();
        daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
    }
}
