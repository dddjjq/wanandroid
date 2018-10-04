package com.dingyl.wanandroid.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.dingyl.wanandroid.R;
import com.dingyl.wanandroid.data.LoginData;
import com.dingyl.wanandroid.presenter.LoginPresenter;
import com.dingyl.wanandroid.util.SharedPreferenceUtil;
import com.dingyl.wanandroid.util.ToastUtil;
import com.dingyl.wanandroid.view.BaseView;

public class LoginActivity extends AppCompatActivity implements BaseView<LoginData>,View.OnClickListener{

    private static final String TAG = "LoginActivity";
    private EditText userName;
    private EditText password;
    private Button loginButton;
    private TextView registerLink;
    private LoginPresenter presenter;
    private String userNameStr,passwordStr;
    private ToastUtil toastUtil;
    private LoginData.LoginDataBean loginDataBean;
    private ActionBar actionBar;
    private ProgressDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }

    @Override
    public void onResume(){
        super.onResume();
        initData();
        initListener();
    }

    private void initView(){
        userName = findViewById(R.id.user_name);
        password = findViewById(R.id.user_password);
        loginButton = findViewById(R.id.login_button);
        registerLink = findViewById(R.id.register_link);
        registerLink.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        actionBar = getSupportActionBar();
        actionBar.setTitle("Login");
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    private void initData(){
        toastUtil = new ToastUtil(this);
        presenter = new LoginPresenter();
        presenter.attachView(this);
        dialog = new ProgressDialog(this);
        dialog.setTitle("登录");
        dialog.setMessage("登录中");
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setCancelable(false);
    }

    private void initListener(){
        loginButton.setOnClickListener(this);
        registerLink.setOnClickListener(this);
    }

    @Override
    public void onStop(){
        super.onStop();
        presenter.detachView();
    }

    @Override
    public void showSuccess(LoginData data) {
        //finish();
        loginDataBean = data.getData();
        if (loginDataBean != null){
            saveUserMessage(loginDataBean);
            toastUtil.makeText("登录成功");
            if (dialog.isShowing()){
                dialog.dismiss();
            }
            Intent intent = new Intent();
            intent.putExtra("username",loginDataBean.getUsername());
            setResult(RESULT_OK,intent);
            finish();
        }
    }

    @Override
    public void showError() {
        toastUtil.makeText("账号或密码有误!");
        if (dialog.isShowing()){
            dialog.dismiss();
        }
    }

    @Override
    public void showEmpty() {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login_button:
                Log.d(TAG,"login button click");
                userNameStr = userName.getText().toString();
                passwordStr = password.getText().toString();
                if (checkInfo(userNameStr,passwordStr)){
                    dialog.show();
                    presenter.getLoginData(userNameStr,passwordStr);
                }
                break;
            case R.id.register_link:
                break;

        }
    }

    private boolean checkInfo(String userNameStr,String passwordStr){
        if (userNameStr == null || passwordStr == null || userNameStr.length() == 0 || passwordStr.length() == 0){
            toastUtil.makeText("用户名或密码不能为空");
            return false;
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem){
        switch (menuItem.getItemId()){
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(menuItem);
    }

    private void saveUserMessage(LoginData.LoginDataBean loginDataBean){
        SharedPreferenceUtil.getInstance(this).saveLoginData(loginDataBean.getUsername(),loginDataBean.getPassword());
    }

}
