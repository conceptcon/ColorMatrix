package com.matrix.yukun.matrix.movie_module.util;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by yukun on 17-2-20.
 */
public class RetrofitApi {
    static String baseUrl = "https://api.douban.com/";
    static String weabaseuri="https://free-api.heweather.com/v5/";
    public static RetrofitApi getInstance(){
        return new RetrofitApi();
    }

    public Retrofit retrofituil(){

        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        httpClientBuilder.connectTimeout(10000, TimeUnit.SECONDS);

        Retrofit retrofit = new Retrofit.Builder()
                .client(httpClientBuilder.build())
                .baseUrl(baseUrl)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())  //使用RxJava不能忘记
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit;
    }

    public Retrofit retrofitWeaUil(){

        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        httpClientBuilder.connectTimeout(10000, TimeUnit.SECONDS);

        Retrofit retrofit = new Retrofit.Builder()
                .client(httpClientBuilder.build())
                .baseUrl(weabaseuri)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())  //使用RxJava不能忘记
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit;
    }
}