package com.dingyl.wanandroid.fragment;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.dingyl.wanandroid.R;
import com.dingyl.wanandroid.adapter.KnowRecyclerAdapter;
import com.dingyl.wanandroid.data.KnowData;
import com.dingyl.wanandroid.presenter.KnowPresenter;
import com.dingyl.wanandroid.util.Constants;
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
    private View loadingView;
    private View errorView;
    private View emptyView;
    private int currentState;

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
        showLoading();
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
    protected void initAnim() {
        ViewGroup parent = (ViewGroup) smartRefreshLayout.getParent();
        View.inflate(getActivity(),R.layout.loading_layout,parent);
        View.inflate(getActivity(),R.layout.error_layout,parent);
        View.inflate(getActivity(),R.layout.empty_layout,parent);
        loadingView = parent.findViewById(R.id.loading_layout);
        errorView = parent.findViewById(R.id.error_layout);
        emptyView = parent.findViewById(R.id.empty_layout);
        loadingView.setVisibility(View.GONE);
        errorView.setVisibility(View.GONE);
        emptyView.setVisibility(View.GONE);
        smartRefreshLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void showSuccess(KnowData data) {
        hideCurrentView();
        currentState = Constants.STATE_SUCCESS;
        smartRefreshLayout.setVisibility(View.VISIBLE);
        if (knowDataArrayList == null){
            showEmpty();
        }
        smartRefreshLayout.finishRefresh();
        smartRefreshLayout.finishLoadmore();
        knowDataArrayList.addAll(data.getData());
        Log.d(TAG,"knowDataArrayList size is : " + knowDataArrayList.size());
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showError() {
        if (knowDataArrayList == null){
            hideCurrentView();
            currentState = Constants.STATE_ERROR;
            errorView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void showEmpty() {
        hideCurrentView();
        currentState = Constants.STATE_EMPTY;
        emptyView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showLoading() {
        if (knowDataArrayList == null){
            hideCurrentView();
            currentState = Constants.STATE_LOADING;
            loadingView.setVisibility(View.VISIBLE);
        }
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

    private void hideCurrentView(){
        switch (currentState){
            case Constants.STATE_SUCCESS:
                smartRefreshLayout.setVisibility(View.GONE);
                break;
            case Constants.STATE_LOADING:
                loadingView.setVisibility(View.GONE);
                break;
            case Constants.STATE_ERROR:
                errorView.setVisibility(View.GONE);
                break;
            case Constants.STATE_EMPTY:
                emptyView.setVisibility(View.GONE);
                break;
        }
    }
}
