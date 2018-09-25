package com.dingyl.wanandroid.util;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dingyl.wanandroid.R;

public class ToastUtil {

    private Context context;
    private View toastView;
    private TextView toastText;
    private ImageView toastIamge;//TODO 添加图像toast
    private Toast toast;
    private static final int X_OFF_SET  = 0;
    private static final int Y_OFF_SET  = 180;

    public ToastUtil(Context context){
        this.context = context;
        init();
    }

    private void init(){
        toast = new Toast(context);
        toastView = LayoutInflater.from(context).inflate(R.layout.toast_view,null);
        toastText = toastView.findViewById(R.id.toast_content);
        toastIamge =toastView.findViewById(R.id.toast_image);
        toast.setGravity(Gravity.BOTTOM,X_OFF_SET,Y_OFF_SET);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(toastView);
    }

    public void makeText(String text){
        toastText.setText(text);
        toast.show();
    }
}
