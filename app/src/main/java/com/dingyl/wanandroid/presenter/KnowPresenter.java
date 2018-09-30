package com.dingyl.wanandroid.presenter;

import android.content.Context;
import android.util.Log;

import com.dingyl.wanandroid.data.KnowData;
import com.dingyl.wanandroid.retrofit.RetrofitHelper;
import com.dingyl.wanandroid.view.BaseView;

import java.util.ArrayList;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class KnowPresenter extends BasePresenter {

    private static final String TAG = "KnowPresenter";
    private BaseView<KnowData> baseView;
    private Context context;
    private RetrofitHelper retrofitHelper;
    private KnowData knowData;

    public KnowPresenter(Context context){
        this.context = context;
        retrofitHelper = new RetrofitHelper();
    }
    @Override
    public void attachView(BaseView view) {
        this.baseView = view;
    }

    @Override
    public void detachView() {
        if (baseView != null){
            baseView = null;
        }
    }

    public void getKnowData(){
        baseView.showLoading();
        retrofitHelper.getKnowData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<KnowData>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onNext(KnowData mKnowData) {
                        knowData = mKnowData;
                        Log.d(TAG,"onNext()");
                    }

                    @Override
                    public void onError(Throwable e) {
                        baseView.showError();
                        e.printStackTrace();
                        Log.d(TAG,e.getMessage());
                        Log.d(TAG,"onError()");
                    }

                    @Override
                    public void onComplete() {
                        baseView.showSuccess(knowData);
                        Log.d(TAG,"onComplete()");
                    }
                });
    }
}
