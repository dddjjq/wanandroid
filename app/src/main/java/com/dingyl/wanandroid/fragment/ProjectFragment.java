package com.dingyl.wanandroid.fragment;

import android.support.design.widget.TabLayout;
import android.view.View;

import com.dingyl.wanandroid.R;
import com.dingyl.wanandroid.data.ProjData;
import com.dingyl.wanandroid.data.ProjDataBean;
import com.dingyl.wanandroid.presenter.ProjPresenter;
import com.dingyl.wanandroid.view.BaseView;

import java.util.ArrayList;

public class ProjectFragment extends BaseFragment implements BaseView<ProjData>{

    private ProjPresenter presenter;
    private ArrayList<ProjDataBean> projDataBeanArrayList;
    private ArrayList<String> titles;
    private TabLayout tabLayout;

    @Override
    protected int setLayoutId() {
        return R.layout.project_fragment;
    }

    @Override
    protected void initView(View view) {

    }

    @Override
    protected void initData() {
        presenter = new ProjPresenter();
        presenter.getProjData();
    }

    @Override
    public void showSuccess(ProjData data) {
        projDataBeanArrayList = data.getData();
        for (ProjDataBean pdb : projDataBeanArrayList){
            titles.add(pdb.getName());
        }

    }

    @Override
    public void showError() {

    }

    @Override
    public void showEmpty() {

    }

    @Override
    public void showLoading() {

    }
}
