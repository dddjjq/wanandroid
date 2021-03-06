package com.dingyl.wanandroid.fragment;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.dingyl.wanandroid.R;
import com.dingyl.wanandroid.adapter.HomeRecyclerAdapter;
import com.dingyl.wanandroid.data.BannerDataBean;
import com.dingyl.wanandroid.data.HomeDataBean;
import com.dingyl.wanandroid.data.HomeZipData;
import com.dingyl.wanandroid.greendaoutil.BannerDataDaoUtil;
import com.dingyl.wanandroid.presenter.HomePresenter;
import com.dingyl.wanandroid.greendaoutil.HomeDataDaoUtil;
import com.dingyl.wanandroid.util.Constants;
import com.dingyl.wanandroid.util.NetworkUtil;
import com.dingyl.wanandroid.util.SharedPreferenceUtil;
import com.dingyl.wanandroid.util.ToastUtil;
import com.dingyl.wanandroid.util.Tools;
import com.dingyl.wanandroid.view.BannerImageLoader;
import com.dingyl.wanandroid.view.BaseView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;

public class HomeFragment extends BaseFragment implements BaseView<HomeZipData>{

    private static final String TAG = "HomeFragment";
    private HomePresenter presenter;
    private Banner banner;
    private LinearLayout bannerView;
    private SmartRefreshLayout smartRefreshLayout;
    private FloatingActionButton floatingActionButton;
    private int page = 0;
    private RecyclerView recyclerView;
    private HomeRecyclerAdapter adapter;
    private ArrayList<BannerDataBean> bannerDataBeans;
    private ArrayList<String> urlList;
    private ArrayList<String> titleList;
    private ArrayList<HomeDataBean> homeDataBeans;
    private boolean isRefresh;
    private SharedPreferenceUtil sharedPreferenceUtil;
    private ToastUtil toastUtil;
    private View loadingView;
    private View errorView;
    private View emptyView;
    private int currentState;

    @Override
    protected int setLayoutId() {
        return R.layout.home_fragment;
    }

    @Override
    protected void initView(View view) {
        recyclerView = view.findViewById(R.id.home_recycler);
        bannerView = (LinearLayout)getLayoutInflater().inflate(R.layout.view_banner,null);
        banner = bannerView.findViewById(R.id.view_banner);
        smartRefreshLayout = view.findViewById(R.id.home_refresh_layout);
        floatingActionButton = view.findViewById(R.id.top_button);
    }

    @Override
    protected void initData() {
        showLoading();
        sharedPreferenceUtil = SharedPreferenceUtil.getInstance(getContext());
        bannerDataBeans = new ArrayList<>();
        urlList = new ArrayList<>();
        titleList = new ArrayList<>();
        homeDataBeans = new ArrayList<>();
        toastUtil = new ToastUtil(getContext());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        initListener();
        presenter = new HomePresenter(getContext());
        presenter.attachView(this);
        adapter = new HomeRecyclerAdapter(getContext(),homeDataBeans,presenter);
        adapter.setHeaderView(bannerView);
        recyclerView.setAdapter(adapter);
        if(!NetworkUtil.isNetworkAvailable(getContext()) && (HomeDataDaoUtil.getInstance().queryDataList() != null) && (BannerDataDaoUtil.getInstance().queryDataList() != null)){
            homeDataBeans.addAll(HomeDataDaoUtil.getInstance().queryDataList());
            bannerDataBeans.addAll(BannerDataDaoUtil.getInstance().queryDataList());
            startBanner();
            hideNotNormalView();
        }else {
            presenter.getHomeZipData(0);
        }
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

    private void initListener(){
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                sharedPreferenceUtil.changeHomeDataRefreshFlag(true);
                presenter.getHomeZipData(0);
            }
        });
        smartRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                page++;
                sharedPreferenceUtil.changeHomeDataRefreshFlag(false);
                presenter.getHomeZipData(page);
            }
        });
        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
                View topView = manager.getChildAt(0);
                if (topView != null){
                    int position = manager.getPosition(topView);
                    if (position < 1){
                        floatingActionButton.setVisibility(View.GONE);
                    }else {
                        floatingActionButton.setVisibility(View.VISIBLE);
                    }
                    Log.d(TAG,"position is : " + position);
                }
            }
        });
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerView.smoothScrollToPosition(0);
            }
        });
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        presenter.detachView();
    }

    @Override
    public void showSuccess(HomeZipData homeZipData) {
        hideCurrentView();
        currentState = Constants.STATE_SUCCESS;
        smartRefreshLayout.setVisibility(View.VISIBLE);
        if (homeZipData == null){
            showEmpty();
        }
        smartRefreshLayout.finishRefresh();
        smartRefreshLayout.finishLoadmore();
        adapter.notifyDataSetChanged();
        if(!bannerDataBeans.containsAll(homeZipData.getBannerData().getData())){
            bannerDataBeans = homeZipData.getBannerData().getData();
            Log.d(TAG,"bannerDataBeans size : " + bannerDataBeans.size());
        }
        isRefresh = sharedPreferenceUtil.getHomeDataRefreshFlag();
        if(isRefresh){
            homeDataBeans.clear();
            homeDataBeans.addAll(homeZipData.getHomeData().getData().getDatas());
        }else{
            homeDataBeans.addAll(homeZipData.getHomeData().getData().getDatas());
        }
        Log.d(TAG,"homeDataBeans size : " + homeDataBeans.size());
        startBanner();
    }

    @Override
    public void showError() {
        if (homeDataBeans == null){
            hideCurrentView();
            currentState = Constants.STATE_ERROR;
            errorView.setVisibility(View.VISIBLE);
        }
        smartRefreshLayout.finishRefresh();
        smartRefreshLayout.finishLoadmore();
        toastUtil.makeText(getResources().getString(R.string.error_tips));
    }

    @Override
    public void showEmpty() {
        hideCurrentView();
        currentState = Constants.STATE_EMPTY;
        emptyView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showLoading() {
        if (homeDataBeans == null){
            hideCurrentView();
            currentState = Constants.STATE_LOADING;
            loadingView.setVisibility(View.VISIBLE);
        }
    }

    private void startBanner(){
        urlList.clear();
        titleList.clear();
        for(BannerDataBean db:bannerDataBeans){
            urlList.add(db.getImagePath());
            titleList.add(db.getTitle());
        }
        banner.setImageLoader(new BannerImageLoader())
                .setBannerStyle(BannerConfig.NUM_INDICATOR_TITLE)
                .setImages(urlList)
                .setBannerTitles(titleList)
                .isAutoPlay(true)
                .setDelayTime(5000)
                .setIndicatorGravity(BannerConfig.RIGHT)
                .start();

        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                String url = bannerDataBeans.get(position).getUrl();
                String title = bannerDataBeans.get(position).getTitle();
                Tools.startWebActivity(getContext(),url,title);
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

    private void hideNotNormalView(){
        loadingView.setVisibility(View.GONE);
        errorView.setVisibility(View.GONE);
        emptyView.setVisibility(View.GONE);
    }
}
