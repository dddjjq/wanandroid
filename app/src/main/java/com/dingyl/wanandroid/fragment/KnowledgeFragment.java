package com.dingyl.wanandroid.fragment;

import android.view.View;

import com.dingyl.wanandroid.R;

public class KnowledgeFragment extends BaseFragment {

    private static KnowledgeFragment knowledgeFragment;

    public static KnowledgeFragment getInstance(){
        if(knowledgeFragment == null){
            knowledgeFragment = new KnowledgeFragment();
        }
        return knowledgeFragment;
    };

    @Override
    protected int setLayoutId() {
        return R.layout.know_fragment;
    }

    @Override
    protected void initView(View view) {

    }

    @Override
    protected void initData() {

    }
}
