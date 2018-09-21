package com.dingyl.wanandroid.fragment;

import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.dingyl.wanandroid.R;
import com.dingyl.wanandroid.adapter.HomeRecyclerAdapter;
import com.dingyl.wanandroid.data.BannerData;
import com.dingyl.wanandroid.data.HomeData;
import com.dingyl.wanandroid.data.HomeZipData;
import com.dingyl.wanandroid.presenter.HomePresenter;
import com.dingyl.wanandroid.view.BannerImageLoader;
import com.dingyl.wanandroid.view.BaseView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import java.util.ArrayList;

public class HomeFragment extends BaseFragment implements BaseView<HomeZipData>{

    private static final String TAG = "HomeFragment";
    private static HomeFragment homeFragment;
    private HomePresenter presenter;
    private Banner banner;
    private LinearLayout bannerView;
    private SmartRefreshLayout smartRefreshLayout;
    private int page = 1;
    private RecyclerView recyclerView;
    private HomeRecyclerAdapter adapter;
    private ArrayList<BannerData.DataBean> dataBeans;
    private ArrayList<String> urlList;
    private ArrayList<String> titleList;
    private ArrayList<HomeData.DataBeans.DataBean> homeDataBeans;
    private boolean isRefresh = true;

    public static HomeFragment getInstance() {
        if(homeFragment == null){
            homeFragment = new HomeFragment();
        }
        return homeFragment;
    }

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
    }

    @Override
    protected void initData() {
        dataBeans = new ArrayList<>();
        urlList = new ArrayList<>();
        titleList = new ArrayList<>();
        homeDataBeans = new ArrayList<>();
        presenter = new HomePresenter(getContext());
        presenter.attachView(this);
        presenter.getHomeZipData(page);
        adapter = new HomeRecyclerAdapter(getContext(),homeDataBeans);
        adapter.setHeaderView(bannerView);
        recyclerView.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        initListener();
    }

    private void initListener(){
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                isRefresh = true;
                presenter.getHomeZipData(1);
            }
        });
        smartRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                page++;
                isRefresh = false;
                presenter.getHomeZipData(page);
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
        smartRefreshLayout.finishRefresh();
        smartRefreshLayout.finishLoadmore();
        if(!dataBeans.containsAll(homeZipData.getBannerData().getData())){
            dataBeans = homeZipData.getBannerData().getData();
            Log.d(TAG,"dataBeans size : " + dataBeans.size());
        }
        if(isRefresh){
            homeDataBeans.clear();
            homeDataBeans.addAll(homeZipData.getHomeData().getData().getDatas());
        }else{
            homeDataBeans.addAll(homeZipData.getHomeData().getData().getDatas());
        }
        Log.d(TAG,"homeDataBeans size : " + homeDataBeans.size());


        //adapter.notifyItemChanged(page * 20);
        urlList.clear();
        titleList.clear();
        for(BannerData.DataBean db:dataBeans){
            urlList.add(db.getImagePath());
            titleList.add(db.getTitle());
        }
        banner.setImageLoader(new BannerImageLoader())
                .setBannerStyle(BannerConfig.NUM_INDICATOR_TITLE)
                .setImages(urlList)
                .setBannerTitles(titleList)
                .isAutoPlay(true)
                .setDelayTime(3000)
                .setIndicatorGravity(BannerConfig.RIGHT)
                .start();
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
