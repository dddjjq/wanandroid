package com.dingyl.wanandroid.presenter;

import android.util.Log;

import com.dingyl.wanandroid.data.CollectData;
import com.dingyl.wanandroid.retrofit.RetrofitHelper;
import com.dingyl.wanandroid.view.BaseView;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class CollectPresenter extends BasePresenter {

    private static final String TAG = "CollectPresenter";
    private BaseView<CollectData> baseView;
    private RetrofitHelper retrofitHelper;
    private CollectData collectData;

    public CollectPresenter(){
        retrofitHelper = new RetrofitHelper();
    }

    @Override
    public void attachView(BaseView view) {
        baseView = view;
    }

    @Override
    public void detachView() {
        if (baseView != null){
            baseView = null;
        }
    }

    public void getCollectData(int page){
        retrofitHelper.getCollectData(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CollectData>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onNext(CollectData mCollectData) {
                        collectData = mCollectData;
                        Log.d(TAG,"onNext");
                    }

                    @Override
                    public void onError(Throwable e) {
                        baseView.showError();
                        Log.d(TAG,"onError");
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                        if (collectData.getData()!= null){}
                            baseView.showSuccess(collectData);
                    }
                });
    }
}
