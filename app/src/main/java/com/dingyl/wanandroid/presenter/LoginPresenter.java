package com.dingyl.wanandroid.presenter;

import android.util.Log;

import com.dingyl.wanandroid.data.LoginData;
import com.dingyl.wanandroid.retrofit.RetrofitHelper;
import com.dingyl.wanandroid.view.BaseView;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class LoginPresenter extends BasePresenter {

    private static final String TAG = "LoginPresenter";
    private RetrofitHelper retrofitHelper;
    private BaseView<LoginData> baseView;
    private LoginData loginData;

    public LoginPresenter(){
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

    public void getLoginData(String username,String password){
        retrofitHelper.getLoginData(username, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<LoginData>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onNext(LoginData mLoginData) {
                        loginData = mLoginData;
                    }

                    @Override
                    public void onError(Throwable e) {
                        baseView.showError();
                        Log.d(TAG,"onError");
                    }

                    @Override
                    public void onComplete() {
                        if (loginData.getData() != null){
                            baseView.showSuccess(loginData);
                        }else {
                            baseView.showError();
                        }
                        Log.d(TAG,"onComplete");
                    }
                });

    }
}
