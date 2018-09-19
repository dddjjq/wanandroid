package com.dingyl.wanandroid;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.dingyl.wanandroid.activity.MainActivity;
import com.dingyl.wanandroid.util.Tools;

public class SplashActivity extends AppCompatActivity implements View.OnClickListener{

    private Button skipBtn;

    private int time = 3;

    private static final String COUNT = "count";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        initView();
        initListener();
        handler.sendEmptyMessage(0);
    }

    public void initView(){
        skipBtn = findViewById(R.id.skip_button);
    }

    private void initListener(){
        skipBtn.setOnClickListener(this);
    }
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message message){
            if(time >= 0){
                skipBtn.setText("跳过 " + time + "s");
                handler.sendEmptyMessageDelayed(0,1000);
            }else {
                Tools.startActivityWithNothing(SplashActivity.this, MainActivity.class);
            }
            time--;
            super.handleMessage(message);
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.skip_button:
                Tools.startActivityWithNothing(this, MainActivity.class);
        }
    }
}
