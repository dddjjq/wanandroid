package com.dingyl.wanandroid.fragment;

import android.view.View;

import com.dingyl.wanandroid.R;

public class ProjectFragment extends BaseFragment {

    private static ProjectFragment projectFragment;

    public static ProjectFragment getInstance() {
        if(projectFragment == null){
            projectFragment = new ProjectFragment();
        }
        return projectFragment;
    }

    @Override
    protected int setLayoutId() {
        return R.layout.project_fragment;
    }

    @Override
    protected void initView(View view) {

    }

    @Override
    protected void initData() {

    }
}
