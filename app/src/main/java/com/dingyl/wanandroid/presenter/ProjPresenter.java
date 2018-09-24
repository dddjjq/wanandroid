package com.dingyl.wanandroid.presenter;

import com.dingyl.wanandroid.data.ProjData;
import com.dingyl.wanandroid.retrofit.RetrofitHelper;
import com.dingyl.wanandroid.view.BaseView;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ProjPresenter extends BasePresenter {

    private BaseView view;
    private RetrofitHelper retrofitHelper;
    private ProjData projData;

    public ProjPresenter(){
        retrofitHelper = new RetrofitHelper();
    }

    @Override
    public void attachView(BaseView view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        if (view != null){
            view = null;
        }
    }

    public void getProjData(){
        retrofitHelper.getProjData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ProjData>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onNext(ProjData mProjData) {
                        projData = mProjData;
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showError();
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                        view.showSuccess(projData);
                    }
                });
    }
}
