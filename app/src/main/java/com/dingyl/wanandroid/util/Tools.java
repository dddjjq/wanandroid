package com.dingyl.wanandroid.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

public class Tools {


    public static void startActivityWithNothing(Context context, Class<? extends Activity> clazz){
        Intent intent = new Intent(context,clazz);
        context.startActivity(intent);
    }
}
