package com.dingyl.wanandroid.presenter;

import com.dingyl.wanandroid.view.BaseView;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public abstract class BasePresenter<T extends BaseView> {

    private CompositeDisposable compositeDisposable;

    public abstract void attachView(T view);

    public abstract void detachView();

    public void addDisposable(Disposable disposable){
        if(compositeDisposable == null){
            compositeDisposable = new CompositeDisposable();
        }
        compositeDisposable.add(disposable);
    }

    public void removeAllDisposable(){
        if(compositeDisposable != null && !compositeDisposable.isDisposed()){
            compositeDisposable.dispose();
        }
    }
}
