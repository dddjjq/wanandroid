package com.dingyl.wanandroid.retrofit;

import com.dingyl.wanandroid.data.BannerData;
import com.dingyl.wanandroid.data.HomeData;
import com.dingyl.wanandroid.data.KnowData;
import com.dingyl.wanandroid.data.ProjData;
import com.dingyl.wanandroid.data.ProjEntryData;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    @GET("banner/json")
    public Observable<BannerData> getBannerData();

    @GET("article/list/{page}/json")
    public Observable<HomeData> getHomeData(@Path("page")int page);

    @GET("tree/json")
    public Observable<KnowData> getKnowData();

    @GET("project/tree/json")
    public Observable<ProjData> getProjData();

    @GET("project/list/{page}/json")
    public Observable<ProjEntryData> getProjEntryData(@Path("page")int page,@Query("cid")int id);
}
