package com.dingyl.wanandroid.fragment;

import android.view.View;

import com.dingyl.wanandroid.R;

public class UserFragment extends BaseFragment {

    private static UserFragment userFragment;

    public static UserFragment getInstance() {
        if(userFragment == null){
            userFragment = new UserFragment();
        }
        return userFragment;
    }

    @Override
    protected int setLayoutId() {
        return R.layout.user_fragment;
    }

    @Override
    protected void initView(View view) {

    }

    @Override
    protected void initData() {

    }
}
