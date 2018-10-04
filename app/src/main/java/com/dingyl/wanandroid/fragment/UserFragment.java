package com.dingyl.wanandroid.fragment;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dingyl.wanandroid.R;
import com.dingyl.wanandroid.activity.LoginActivity;
import com.dingyl.wanandroid.util.SharedPreferenceUtil;
import com.dingyl.wanandroid.util.ToastUtil;
import com.dingyl.wanandroid.view.CircleButton;

public class UserFragment extends BaseFragment {

    private static final String TAG = "UserFragment";
    private CircleButton userIcon;
    private TextView userNameText;
    private LinearLayout layout;
    private String userName;
    private boolean isLogin = false;
    private View popView;
    private PopupWindow popupWindow;
    private Button logoutButton,cancelButton;
    private ToastUtil toastUtil;

    @Override
    protected void initAnim() {

    }

    @Override
    protected int setLayoutId() {
        return R.layout.user_fragment;
    }

    @Override
    protected void initView(View view) {
        userIcon = view.findViewById(R.id.user_button);
        userNameText = view.findViewById(R.id.user_name_text);
        layout = view.findViewById(R.id.user_fragment_layout);
        showLogoutPopWindow();
    }

    @Override
    protected void initData() {
        toastUtil = new ToastUtil(getContext());
        userIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isLogin = SharedPreferenceUtil.getInstance(getContext()).isLogin();
                if (!isLogin){
                    Intent intent = new Intent(getContext(), LoginActivity.class);
                    startActivityForResult(intent,1);
                }else {
                    if (!popupWindow.isShowing()) {
                        popupWindow.showAtLocation(layout, Gravity.BOTTOM, 0, 0);
                        //Log.d(TAG,"popwindow isShowing : " + popupWindow.isShowing());
                    }
                }
            }
        });
    }

    @Override
    public void onResume(){
        super.onResume();
        isLogin = SharedPreferenceUtil.getInstance(getContext()).isLogin();
        if (isLogin){
            userName = SharedPreferenceUtil.getInstance(getContext()).getUserName();
            userNameText.setText(userName);
        }
    }

    @Override
    public void onActivityResult(int requestCode,int resultCode,Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        Log.d(TAG,"UserFragment onActivityResult");
        if (resultCode == RESULT_OK){
            userName = data.getStringExtra("username");
            Log.d(TAG,"userName : " + userName);
            userNameText.setText(userName);
        }
    }

    private void showLogoutPopWindow(){
        popView = getLayoutInflater().inflate(R.layout.logout_pop_layout,null);
        popupWindow = new PopupWindow(popView, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setOutsideTouchable(true);
        popupWindow.setTouchable(true);
        popupWindow.setAnimationStyle(R.style.logout_pop_window_style);
        popupWindow.setFocusable(true); // 否则点击头像部分就会重复调用pop window
        logoutButton = popView.findViewById(R.id.logout);
        cancelButton = popView.findViewById(R.id.cancel);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferenceUtil.getInstance(getContext()).clearUserData();
                popupWindow.dismiss();
                userNameText.setText("点击头像登录");
                toastUtil.makeText("退出成功");
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (popupWindow != null)
                    popupWindow.dismiss();
            }
        });
    }

}
