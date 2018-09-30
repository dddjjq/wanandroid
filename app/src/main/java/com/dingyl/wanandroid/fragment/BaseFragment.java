package com.dingyl.wanandroid.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;


import com.dingyl.wanandroid.R;
import com.dingyl.wanandroid.view.BaseView;

import me.yokeyword.fragmentation.SupportFragment;

public abstract class BaseFragment extends SupportFragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState){
        super.onCreateView(inflater,parent,savedInstanceState);
        View view = inflater.inflate(setLayoutId(),parent,false);
        return view;
    }

    @Override
    public void onViewCreated(View view,Bundle savedInstanceState){
        super.onViewCreated(view,savedInstanceState);
        initView(view);
        initAnim();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        initData();
    }



    protected abstract int setLayoutId();

    protected abstract void initView(View view);

    protected abstract void initData();

    protected abstract void initAnim();

}
