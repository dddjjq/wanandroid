package com.dingyl.wanandroid.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.dingyl.wanandroid.activity.WebActivity;

public class Tools {


    public static void startActivityWithNothing(Context context, Class<? extends Activity> clazz){
        Intent intent = new Intent(context,clazz);
        context.startActivity(intent);
    }

    public static void startWebActivity(Context context,String url,String title){
        Intent intent = new Intent(context, WebActivity.class);
        intent.putExtra("web_url",url);
        intent.putExtra("web_title",title);
        context.startActivity(intent);
    }
}
