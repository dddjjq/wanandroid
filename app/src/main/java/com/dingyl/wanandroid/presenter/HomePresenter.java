package com.dingyl.wanandroid.presenter;

import android.content.Context;
import android.util.Log;

import com.dingyl.wanandroid.data.BannerData;
import com.dingyl.wanandroid.data.HomeData;
import com.dingyl.wanandroid.data.HomeDataDaoBean;
import com.dingyl.wanandroid.data.HomeZipData;
import com.dingyl.wanandroid.retrofit.RetrofitHelper;
import com.dingyl.wanandroid.util.Constants;
import com.dingyl.wanandroid.util.HomeDataDaoUtil;
import com.dingyl.wanandroid.util.SharedPreferenceUtil;
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
    private ArrayList<HomeData.DataBeans.DataBean> dataBeanArrayList;
    private HomeDataDaoBean homeDataDaoBean;
    private ExecutorService fixdThreadPool = Executors.newSingleThreadExecutor();
    private SharedPreferenceUtil sharedPreferenceUtil;

    public HomePresenter(Context context){
        retrofitHelper = RetrofitHelper.getInstance(context);
        homeDataDaoBean = new HomeDataDaoBean();
        sharedPreferenceUtil = SharedPreferenceUtil.getInstance(context);
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
                        Log.d(TAG,"onSubscribe");
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

    public void getHomeZipData(int page) {
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
                        dataBeanArrayList = mHomeZipData.getHomeData().getData().getDatas();
                        Runnable insertRunnable = new Runnable() {
                            @Override
                            public void run() {
                                for(HomeData.DataBeans.DataBean hdd:dataBeanArrayList){
                                    homeDataDaoBean.setAuthor(hdd.getAuthor());
                                    homeDataDaoBean.setContent(hdd.getTitle());
                                    homeDataDaoBean.setIsLove(false);//TODO for love Button
                                    homeDataDaoBean.setIsProject(null);//TODO for project
                                    homeDataDaoBean.setPublishTime(hdd.getNiceDate());
                                    homeDataDaoBean.setRootTitle(hdd.getSuperChapterName());
                                    homeDataDaoBean.setId(sharedPreferenceUtil.getHomeDataId());
                                    HomeDataDaoUtil.insertData(homeDataDaoBean);
                                    sharedPreferenceUtil.addHomeDataId();
                                }
                                Log.d(TAG,"onNext thread 1 : " + Thread.currentThread().getName());
                            }
                        };
                        Runnable deleteRunnable = new Runnable() {
                            @Override
                            public void run() {
                                HomeDataDaoUtil.deleteAllData();
                                sharedPreferenceUtil.resetHomeDataId();
                                Log.d(TAG,"onNext thread 2 : " + Thread.currentThread().getName());
                            }
                        };
                        if(!sharedPreferenceUtil.getHomeDataRefreshFlag()){
                            fixdThreadPool.execute(insertRunnable);
                        }else {
                            fixdThreadPool.execute(deleteRunnable);
                            fixdThreadPool.execute(insertRunnable);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        baseView.showError();
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                        baseView.showSuccess(mHomeZipData);
                        Log.d(TAG,"onComplete");
                    }
                });
    }
}
