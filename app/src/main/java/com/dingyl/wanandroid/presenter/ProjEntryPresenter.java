package com.dingyl.wanandroid.presenter;

import com.dingyl.wanandroid.data.ProjEntryData;
import com.dingyl.wanandroid.retrofit.RetrofitHelper;
import com.dingyl.wanandroid.view.BaseView;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ProjEntryPresenter extends BasePresenter {

    private BaseView<ProjEntryData> view;
    private RetrofitHelper retrofitHelper;
    private ProjEntryData projEntryData;

    public ProjEntryPresenter(){
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

    public void getProjEntryData(int page,int id){
        view.showLoading();
        retrofitHelper.getProjEntryData(page,id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ProjEntryData>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onNext(ProjEntryData mProjEntryData) {
                        projEntryData = mProjEntryData;
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showError();
                    }

                    @Override
                    public void onComplete() {
                        view.showSuccess(projEntryData);
                    }
                });
    }
}
