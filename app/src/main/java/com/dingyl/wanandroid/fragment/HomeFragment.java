package com.dingyl.wanandroid.fragment;

import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import com.dingyl.wanandroid.R;
import com.dingyl.wanandroid.adapter.BannerPageAdapter;
import com.dingyl.wanandroid.data.BannerData;
import com.dingyl.wanandroid.presenter.HomePresenter;
import com.dingyl.wanandroid.util.GlideUtil;
import com.dingyl.wanandroid.view.BaseView;
import com.youth.banner.Banner;

import java.util.ArrayList;

public class HomeFragment extends BaseFragment implements BaseView<BannerData>{

    private static HomeFragment homeFragment;
    private HomePresenter presenter;
    private ArrayList<BannerData.DataBean> dataBeans;
    private Banner banner;

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
        banner = view.findViewById(R.id.banner);
    }

    @Override
    protected void initData() {
        dataBeans = new ArrayList<>();
        presenter = new HomePresenter(getContext());
        presenter.attachView(this);
        presenter.getBannerData();
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        presenter.detachView();
        //presenter.removeAllDisposable();
    }

    @Override
    public void showSuccess(BannerData bannerData) {
        dataBeans = bannerData.getData();
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
