package com.dingyl.wanandroid;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.dingyl.wanandroid.activity.MainActivity;
import com.dingyl.wanandroid.util.Tools;

import java.lang.ref.WeakReference;

public class SplashActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = "SplashActivity";

    private Button skipBtn;

    private int time = 3;

    private static final int MESSAGE_WHAT = 0;

    private SplashHandler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        initView();
        initListener();
        handler = new SplashHandler(this);
        handler.sendEmptyMessage(MESSAGE_WHAT);
    }

    public void initView(){
        skipBtn = findViewById(R.id.skip_button);
    }

    private void initListener(){
        skipBtn.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.skip_button:
                startMain();
                handler.removeMessages(MESSAGE_WHAT);
                break;
        }
    }

    @Override
    public void onPause(){
        super.onPause();
        handler.removeMessages(MESSAGE_WHAT);
    }

    private void startMain(){
        Tools.startActivityWithNothing(this, MainActivity.class);
        finish();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
        Log.d(TAG,"onSaveInstanceState");
    }

    private static class SplashHandler extends Handler{

        WeakReference<Context> reference;

        SplashHandler(Context context){
            reference = new WeakReference<>(context);
        }

        @Override
        public void handleMessage(Message msg){
            SplashActivity activity = (SplashActivity) reference.get();
            if(activity.time > 0){
                String text = activity.getResources().getString(R.string.skip_text1) + activity.time + activity.getResources().getString(R.string.skip_text2);
                activity.skipBtn.setText(text);
                this.sendEmptyMessageDelayed(0,1000);
            }else {
                activity.startMain();
            }
            activity.time--;
            super.handleMessage(msg);
        }
    }
}
