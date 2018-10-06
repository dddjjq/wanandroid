package com.dingyl.wanandroid.util;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashSet;

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

    private int getHomeDataId(){
        sharedPreferences = context.getSharedPreferences(Constants.HOME_DATA_Preference,Context.MODE_PRIVATE);
        return sharedPreferences.getInt(Constants.HOME_DATA_ID,0);
    }

    public void resetHomeDataId(){
        sharedPreferences = context.getSharedPreferences(Constants.HOME_DATA_Preference,Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putInt(Constants.HOME_DATA_ID,0);
        editor.apply();
    }

    public void setProjectLinkType(int type){
        sharedPreferences = context.getSharedPreferences(Constants.PROJECT_DATA_PREFERENCE,Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putInt(Constants.PROJECT_LINK_TYPE,type);
        editor.apply();
    }

    public int getProjectLinkType(){
        sharedPreferences = context.getSharedPreferences(Constants.PROJECT_DATA_PREFERENCE,Context.MODE_PRIVATE);
        return sharedPreferences.getInt(Constants.PROJECT_LINK_TYPE,0);
    }

    public void saveLoginData(String username,String password){
        sharedPreferences = context.getSharedPreferences(Constants.USER_DATA_PREFERENCE,Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putString(Constants.USER_DATA_NAME,username);
        editor.putString(Constants.USER_DATA_PASSWORD,password);
        editor.putBoolean(Constants.IS_LOGIN,true);
        editor.apply();
    }

    public boolean isLogin(){
        sharedPreferences = context.getSharedPreferences(Constants.USER_DATA_PREFERENCE,Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(Constants.IS_LOGIN,false);
    }

    public String getUserName(){
        sharedPreferences = context.getSharedPreferences(Constants.USER_DATA_PREFERENCE,Context.MODE_PRIVATE);
        return sharedPreferences.getString(Constants.USER_DATA_NAME,"");
    }

    public void clearUserData(){
        sharedPreferences = context.getSharedPreferences(Constants.USER_DATA_PREFERENCE,Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }

    public void receiveCookies(HashSet<String> cookies){
        sharedPreferences = context.getSharedPreferences(Constants.COOKIES_PREFERENCE,Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putStringSet(Constants.COOKIES_STR,cookies);
        editor.apply();
    }

    public HashSet<String> getCookies(){
        sharedPreferences = context.getSharedPreferences(Constants.COOKIES_PREFERENCE,Context.MODE_PRIVATE);
        return (HashSet<String>) sharedPreferences.getStringSet(Constants.COOKIES_STR,new HashSet<String>());
    }
}
