package com.dingyl.wanandroid.retrofit;

import com.dingyl.wanandroid.data.BannerData;
import com.dingyl.wanandroid.data.BaseData;
import com.dingyl.wanandroid.data.CollectData;
import com.dingyl.wanandroid.data.HomeData;
import com.dingyl.wanandroid.data.KnowData;
import com.dingyl.wanandroid.data.LoginData;
import com.dingyl.wanandroid.data.ProjData;
import com.dingyl.wanandroid.data.ProjEntryData;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
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

    @FormUrlEncoded
    @POST("user/login")
    public Observable<LoginData> getLoginData(@Field("username")String username,@Field("password")String password);

    @GET("lg/collect/list/{page}/json")
    public Observable<CollectData> getCollectList(@Path("page")int page);

    @POST("lg/collect/{id}/json")
    public Observable<BaseData> addCollectWithId(@Path("id")int id);

}
