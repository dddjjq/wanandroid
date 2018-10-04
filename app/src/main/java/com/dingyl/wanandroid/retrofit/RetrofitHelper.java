package com.dingyl.wanandroid.retrofit;

import android.content.Context;
import android.util.Log;

import com.dingyl.wanandroid.data.BannerData;
import com.dingyl.wanandroid.data.HomeData;
import com.dingyl.wanandroid.data.KnowData;
import com.dingyl.wanandroid.data.LoginData;
import com.dingyl.wanandroid.data.ProjData;
import com.dingyl.wanandroid.data.ProjEntryData;
import com.dingyl.wanandroid.util.Constants;
import com.google.gson.GsonBuilder;

import io.reactivex.Observable;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitHelper {

    private static final String TAG = "RetrofitHelper";
    private OkHttpClient client = new OkHttpClient();
    GsonConverterFactory factory = GsonConverterFactory.create(new GsonBuilder().create());
    private Retrofit retrofit = null;
    private ApiService apiService;

    public RetrofitHelper(){
        init();
    }

    private void init(){
        retrofit = new Retrofit.Builder()
                .baseUrl(Constants.WAN_ANDROID_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        apiService = retrofit.create(ApiService.class);
    }

    public Observable<BannerData> getBannerData(){
        Log.d("TAG",TAG + " getBannerData()");
        return apiService.getBannerData();
    }

    public Observable<HomeData> getHomeData(int page){
        Log.d(TAG,TAG + " getHomeData()");
        return apiService.getHomeData(page);
    }

    public Observable<KnowData> getKnowData(){
        Log.d(TAG,TAG + " getHomeData()");
        return apiService.getKnowData();
    }

    public Observable<ProjData> getProjData(){
        return apiService.getProjData();
    }

    public Observable<ProjEntryData> getProjEntryData(int page,int id){
        return apiService.getProjEntryData(page,id);
    }

    public Observable<LoginData> getLoginData(String username,String password){
        return apiService.getLoginData(username,password);
    }
}
