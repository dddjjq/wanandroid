package com.dingyl.wanandroid.fragment;

import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.dingyl.wanandroid.R;
import com.dingyl.wanandroid.adapter.ProjectEntryRecyclerAdapter;
import com.dingyl.wanandroid.data.ProjEntryData;
import com.dingyl.wanandroid.data.ProjEntryDataBean;
import com.dingyl.wanandroid.presenter.ProjEntryPresenter;
import com.dingyl.wanandroid.util.ToastUtil;
import com.dingyl.wanandroid.view.BaseView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;

public class ProjectEntryFragment extends BaseFragment implements BaseView<ProjEntryData>{

    private RecyclerView recyclerView;
    private SmartRefreshLayout smartRefreshLayout;
    private ArrayList<ProjEntryDataBean> projEntryDataBeans;
    private ProjEntryPresenter presenter;
    private ProjectEntryRecyclerAdapter adapter;
    private int id;
    private int page = 0;
    private int totalCount;
    private ToastUtil toastUtil;
    private boolean isRefresh = true;

    @Override
    protected int setLayoutId() {
        return R.layout.project_entry_layout;
    }

    @Override
    protected void initView(View view) {
        recyclerView = view.findViewById(R.id.proj_entry_recycler);
        smartRefreshLayout = view.findViewById(R.id.proj_entry_smart_layout);
        toastUtil = new ToastUtil(getContext());
    }

    @Override
    protected void initData() {
        id = getArguments().getInt("id");
        presenter = new ProjEntryPresenter();
        presenter.attachView(this);
        presenter.getProjEntryData(0,id);
        projEntryDataBeans = new ArrayList<>();
        adapter = new ProjectEntryRecyclerAdapter(getContext(),projEntryDataBeans);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
        initListener();
    }

    @Override
    public void showSuccess(ProjEntryData data) {
        smartRefreshLayout.finishRefresh();
        smartRefreshLayout.finishLoadmore();
        if (isRefresh){
            projEntryDataBeans.clear();
            projEntryDataBeans.addAll(data.getData().getDatas());
        }else {
            projEntryDataBeans.addAll(data.getData().getDatas());
        }
        totalCount = data.getData().getTotal();
        adapter.notifyDataSetChanged();
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

    private void initListener(){
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                isRefresh = true;
                presenter.getProjEntryData(0,id);
            }
        });
        smartRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                isRefresh = false;
                if(page < totalCount/20){
                    page++;
                    presenter.getProjEntryData(page,id);
                }else {
                    smartRefreshLayout.finishLoadmore();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            toastUtil.makeText("已经到底了");
                        }
                    },1000);
                }
            }
        });

    }
}
