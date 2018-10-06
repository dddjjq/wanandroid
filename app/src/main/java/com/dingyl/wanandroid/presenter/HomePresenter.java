package com.dingyl.wanandroid.presenter;

import android.content.Context;
import android.util.Log;

import com.dingyl.wanandroid.data.BannerData;
import com.dingyl.wanandroid.data.BannerDataBean;
import com.dingyl.wanandroid.data.BaseData;
import com.dingyl.wanandroid.data.HomeData;
import com.dingyl.wanandroid.data.HomeDataBean;
import com.dingyl.wanandroid.data.HomeZipData;
import com.dingyl.wanandroid.greendaoutil.BannerDataDaoUtil;
import com.dingyl.wanandroid.retrofit.RetrofitHelper;
import com.dingyl.wanandroid.greendaoutil.HomeDataDaoUtil;
import com.dingyl.wanandroid.util.SharedPreferenceUtil;
import com.dingyl.wanandroid.util.ToastUtil;
import com.dingyl.wanandroid.view.BaseView;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.schedulers.Schedulers;

public class HomePresenter extends BasePresenter {

    private static final String TAG = "HomePresenter";
    private BaseView<HomeZipData> baseView;
    private RetrofitHelper retrofitHelper;
    private BannerData mBannerData;
    private HomeZipData mHomeZipData;
    private ArrayList<HomeDataBean> homeDataBeans;
    private ArrayList<BannerDataBean> bannerDataBeans;
    private ExecutorService fixdThreadPool = Executors.newSingleThreadExecutor();
    private ExecutorService singleThreadPool = Executors.newSingleThreadExecutor();
    private SharedPreferenceUtil sharedPreferenceUtil;
    private BaseData baseData;
    private ToastUtil toastUtil;

    public HomePresenter(Context context){
        retrofitHelper = new RetrofitHelper();
        sharedPreferenceUtil = SharedPreferenceUtil.getInstance(context);
        toastUtil = new ToastUtil(context);
    }

    @Override
    public void attachView(BaseView view) {
        baseView = view;
    }

    @Override
    public void detachView() {
        if(baseView != null){
            baseView = null;
        }
    }

    public void getBannerData(){
        retrofitHelper.getBannerData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BannerData>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onNext(BannerData bannerData) {
                        mBannerData = bannerData;
                        Log.d(TAG,"onNext");
                    }

                    @Override
                    public void onError(Throwable e) {
                        baseView.showError();
                        Log.d(TAG,"onError");
                    }

                    @Override
                    public void onComplete() {
                        //baseView.showSuccess(mBannerData);
                        Log.d(TAG,"onComplete");
                    }
                });
    }

    public void addCollect(int id){
        retrofitHelper.addCollectWithId(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseData>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                        Log.d(TAG,"addCollect onSubscribe");
                    }

                    @Override
                    public void onNext(BaseData mBaseData) {
                        baseData = mBaseData;
                        Log.d(TAG,"addCollect onNext");
                        Log.d(TAG,"addCollect baseData.getErrorCode() : " + baseData.getErrorCode());
                        if (baseData.getErrorCode() == 0){
                            toastUtil.makeText("收藏成功");
                        }else {
                            toastUtil.makeText("收藏失败");
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        toastUtil.makeText("收藏失败");
                        Log.d(TAG,"addCollect onError");
                        Log.d(TAG,"addCollect" + e.getStackTrace());
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
    public void getHomeZipData(int page) {
        baseView.showLoading();
        Observable.zip(retrofitHelper.getBannerData(), retrofitHelper.getHomeData(page),
                new BiFunction<BannerData, HomeData, HomeZipData>() {
                    @Override
                    public HomeZipData apply(BannerData bannerData, HomeData homeData) {
                        HomeZipData homeZipData = new HomeZipData();
                        homeZipData.setBannerData(bannerData);
                        homeZipData.setHomeData(homeData);
                        return homeZipData;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<HomeZipData>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onNext(HomeZipData homeData) {
                        mHomeZipData = homeData;
                        homeDataBeans = mHomeZipData.getHomeData().getData().getDatas();
                        Log.d(TAG,"dataBeanArrayList size : " + homeDataBeans.size());
                        bannerDataBeans = mHomeZipData.getBannerData().getData();
                        if(!sharedPreferenceUtil.getHomeDataRefreshFlag()){
                            fixdThreadPool.execute(insertRunnable);
                        }else {
                            fixdThreadPool.execute(deleteRunnable);
                            fixdThreadPool.execute(insertRunnable);
                        }
                        singleThreadPool.execute(executeBannerData);
                    }

                    @Override
                    public void onError(Throwable e) {
                        baseView.showError();
                        e.printStackTrace();
                        Log.d(TAG,"cause is : " + e.getCause());
                    }

                    @Override
                    public void onComplete() {
                        baseView.showSuccess(mHomeZipData);
                        Log.d(TAG,"onComplete");
                    }
                });
    }

    private Runnable insertRunnable = new Runnable() {
        @Override
        public void run() {
            for(HomeDataBean hdd : homeDataBeans){
                HomeDataDaoUtil.getInstance().insertData(hdd);
                sharedPreferenceUtil.addHomeDataId();
            }
            Log.d(TAG,"onNext thread 1 : " + Thread.currentThread().getName());
        }
    };

    private Runnable deleteRunnable = new Runnable() {
        @Override
        public void run() {
            HomeDataDaoUtil.getInstance().deleteAllData();
            sharedPreferenceUtil.resetHomeDataId();
            Log.d(TAG,"onNext thread 2 : " + Thread.currentThread().getName());
        }
    };

    private Runnable executeBannerData = new Runnable() {
        @Override
        public void run() {
            BannerDataDaoUtil.getInstance().deleteAllData();
            BannerDataDaoUtil.getInstance().insertDataList(bannerDataBeans);
        }
    };
}
