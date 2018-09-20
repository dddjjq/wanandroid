package com.dingyl.wanandroid.retrofit;

import com.dingyl.wanandroid.data.BannerData;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface ApiService {

    @GET("banner/json")
    public Observable<BannerData> getBannerData();

}
