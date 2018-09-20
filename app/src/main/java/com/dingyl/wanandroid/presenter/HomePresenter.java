package com.dingyl.wanandroid.presenter;

import android.arch.core.util.Function;
import android.content.Context;
import android.util.Log;

import com.dingyl.wanandroid.data.BannerData;
import com.dingyl.wanandroid.retrofit.RetrofitHelper;
import com.dingyl.wanandroid.view.BaseView;

import java.util.ArrayList;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class HomePresenter extends BasePresenter {

    private static final String TAG = "HomePresenter";
    private BaseView<BannerData> baseView;
    private RetrofitHelper retrofitHelper;
    private BannerData mBannerData;

    public HomePresenter(Context context){
        retrofitHelper = RetrofitHelper.getInstance(context);
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
                        baseView.showSuccess(mBannerData);
                        Log.d(TAG,"onComplete");
                    }
                });
    }
}
