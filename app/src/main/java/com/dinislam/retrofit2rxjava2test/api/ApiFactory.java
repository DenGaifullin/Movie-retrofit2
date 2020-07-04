package com.dinislam.retrofit2rxjava2test.api;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiFactory {
    private static ApiFactory apiFactory;
    private static Retrofit retrofit;

    private ApiFactory() {
        retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl("https://api.themoviedb.org/")
                .build();
    }

    public static synchronized ApiFactory getInstance(){
        if(apiFactory == null){
            apiFactory = new ApiFactory();
        }
        return apiFactory;
    }

    public ApiService getService(){
        return retrofit.create(ApiService.class);
    }
}