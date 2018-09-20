package com.dingyl.wanandroid.view;


public interface BaseView<T> {

    void showSuccess(T data);

    void showError();

    void showEmpty();

    void showLoading();

}
