package com.dingyl.wanandroid.activity;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;

import com.dingyl.wanandroid.R;
import com.dingyl.wanandroid.adapter.CollectRecyclerAdapter;
import com.dingyl.wanandroid.data.CollectData;
import com.dingyl.wanandroid.data.CollectDataBean;
import com.dingyl.wanandroid.presenter.CollectPresenter;
import com.dingyl.wanandroid.util.ToastUtil;
import com.dingyl.wanandroid.view.BaseView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;

public class CollectActivity extends AppCompatActivity implements BaseView<CollectData>{

    private static final String TAG = "CollectActivity";
    private CollectRecyclerAdapter adapter;
    private RecyclerView recyclerView;
    private SmartRefreshLayout smartRefreshLayout;
    private ArrayList<CollectDataBean> collectDataBeans;
    private CollectPresenter presenter;
    private int page = 0;
    private boolean isRefresh = true;
    private ActionBar actionBar;
    private int totalPage = 1;
    private ToastUtil toastUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collect);
        initView();
        initData();
        initListener();
    }

    @Override
    public void onResume(){
        super.onResume();
        toastUtil = new ToastUtil(this);
        actionBar = getSupportActionBar();
        actionBar.setTitle("收藏");
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    private void initView(){
        smartRefreshLayout = findViewById(R.id.collect_smart_layout);
        recyclerView = findViewById(R.id.collect_recycler);
    }

    private void initData(){
        collectDataBeans = new ArrayList<>();
        presenter = new CollectPresenter();
        presenter.attachView(this);
        presenter.getCollectData(0);
    }

    private void initListener(){
        adapter = new CollectRecyclerAdapter(this,collectDataBeans);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(adapter);
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                isRefresh = true;
                presenter.getCollectData(0);
            }
        });
        smartRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                isRefresh = false;
                page++;
                Log.d(TAG,"page is : " + page);
                Log.d(TAG,"totalPage is : " + totalPage);
                if (page <= totalPage){
                    presenter.getCollectData(page);
                }else {
                    smartRefreshLayout.finishLoadmore();
                    new Thread(){
                        @Override
                        public void run(){
                            try{
                                Thread.sleep(1000); // smartRefreshLayout 停止刷新需要1s
                            }catch (InterruptedException e){
                                e.printStackTrace();
                            }
                            toastUtil.makeText("已经到底了!");
                        }
                    }.start();
                }
            }
        });
    }


    @Override
    public void onStop(){
        super.onStop();
        presenter.detachView();
    }

    @Override
    public void showSuccess(CollectData data) {
        smartRefreshLayout.finishLoadmore();
        smartRefreshLayout.finishRefresh();
        totalPage = data.getData().getTotal()/20;
        if (isRefresh){
            collectDataBeans.clear();
        }
        collectDataBeans.addAll(data.getData().getDatas());
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showError() {
        smartRefreshLayout.finishLoadmore();
        smartRefreshLayout.finishRefresh();
    }

    @Override
    public void showEmpty() {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem){
        switch (menuItem.getItemId()){
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(menuItem);
    }
}
