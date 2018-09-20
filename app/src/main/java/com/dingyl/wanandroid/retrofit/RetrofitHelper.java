package com.dingyl.wanandroid.retrofit;

import android.content.Context;
import android.util.Log;

import com.dingyl.wanandroid.data.BannerData;
import com.dingyl.wanandroid.util.Constants;
import com.google.gson.GsonBuilder;

import io.reactivex.Observable;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitHelper {

    private static final String TAG = "RetrofitHelper";
    private Context context;
    private OkHttpClient client = new OkHttpClient();
    GsonConverterFactory factory = GsonConverterFactory.create(new GsonBuilder().create());
    private static RetrofitHelper instance = null;
    private Retrofit retrofit = null;
    private ApiService apiService;

    private RetrofitHelper(Context context){
        this.context = context;
        init();
    }

    public static RetrofitHelper getInstance(Context context){
        if(instance == null){
            instance = new RetrofitHelper(context);
        }
        return instance;
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

}