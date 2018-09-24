package com.dingyl.wanandroid.fragment;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.dingyl.wanandroid.R;
import com.dingyl.wanandroid.adapter.KnowRecyclerAdapter;
import com.dingyl.wanandroid.data.KnowData;
import com.dingyl.wanandroid.presenter.KnowPresenter;
import com.dingyl.wanandroid.view.BaseView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;

public class KnowledgeFragment extends BaseFragment implements BaseView<KnowData>{

    private static final String TAG = "KnowledgeFragment";
    private RecyclerView recyclerView;
    private SmartRefreshLayout smartRefreshLayout;
    private KnowPresenter presenter;
    private ArrayList<KnowData.DataBean> knowDataArrayList;
    private KnowRecyclerAdapter adapter;
    private FloatingActionButton topButton;

    @Override
    protected int setLayoutId() {
        return R.layout.know_fragment;
    }

    @Override
    protected void initView(View view) {
        recyclerView = view.findViewById(R.id.know_recycler);
        smartRefreshLayout = view.findViewById(R.id.know_smart_layout);
        topButton = view.findViewById(R.id.top_button);
    }

    @Override
    protected void initData() {
        presenter = new KnowPresenter(getContext());
        presenter.attachView(this);
        knowDataArrayList = new ArrayList<>();
        adapter = new KnowRecyclerAdapter(getContext(),knowDataArrayList);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        presenter.getKnowData();
        initListener();
    }

    @Override
    public void showSuccess(KnowData data) {
        smartRefreshLayout.finishRefresh();
        smartRefreshLayout.finishLoadmore();
        knowDataArrayList.addAll(data.getData());
        Log.d(TAG,"knowDataArrayList size is : " + knowDataArrayList.size());
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

    @Override
    public void onDestroy(){
        super.onDestroy();
        presenter.detachView();
    }

    private void initListener(){
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                presenter.getKnowData();
            }
        });
        topButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerView.smoothScrollToPosition(0);
            }
        });
        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                View view = recyclerView.getChildAt(0);
                LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
                if (view != null){
                    int position = manager.getPosition(view);
                    if (position < 1){
                        topButton.setVisibility(View.GONE);
                    }else {
                        topButton.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
    }
}
