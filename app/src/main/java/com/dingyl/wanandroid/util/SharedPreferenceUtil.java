package com.dingyl.wanandroid.util;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferenceUtil {

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Context context;
    private SharedPreferenceUtil instance = null;

    private SharedPreferenceUtil(Context context){
        this.context = context;
    }

    public static SharedPreferenceUtil getInstance(Context context){
        return new SharedPreferenceUtil(context);
    }

    public void changeHomeDataRefreshFlag(boolean flag){
        sharedPreferences = context.getSharedPreferences(Constants.HOME_DATA_Preference,Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putBoolean(Constants.HOME_DATA_FLAG,flag);
        editor.apply();
    }

    public boolean getHomeDataRefreshFlag(){
        sharedPreferences = context.getSharedPreferences(Constants.HOME_DATA_Preference,Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(Constants.HOME_DATA_FLAG,true);
    }

    public void addHomeDataId(){
        sharedPreferences = context.getSharedPreferences(Constants.HOME_DATA_Preference,Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putInt(Constants.HOME_DATA_ID,getHomeDataId() + 1);
        editor.apply();
    }

    public int getHomeDataId(){
        sharedPreferences = context.getSharedPreferences(Constants.HOME_DATA_Preference,Context.MODE_PRIVATE);
        return sharedPreferences.getInt(Constants.HOME_DATA_ID,0);
    }

    public void resetHomeDataId(){
        sharedPreferences = context.getSharedPreferences(Constants.HOME_DATA_Preference,Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putInt(Constants.HOME_DATA_ID,0);
        editor.apply();
    }
}
