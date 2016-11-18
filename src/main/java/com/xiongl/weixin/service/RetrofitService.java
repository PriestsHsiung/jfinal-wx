package com.xiongl.weixin.service;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 2016-11-2.
 */
public class RetrofitService {
    private static Retrofit retrofitWithJson;
    private static Retrofit retrofit;
    private static Retrofit fwhRetrofit;

    private static final String QY_BASE_URL = "https://qyapi.weixin.qq.com/cgi-bin/";
    private static final String FWH_BASE_URL = "http://192.168.1.79:80/";

    static {
        retrofitWithJson = new Retrofit.Builder()
                .baseUrl(QY_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()).build();

        retrofit = new Retrofit.Builder()
                .baseUrl(QY_BASE_URL).build();

        fwhRetrofit = new Retrofit.Builder()
                .baseUrl(FWH_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static Retrofit getFwhRetrofit() {
        return fwhRetrofit;
    }


    public static Retrofit getQyApiInstance(Boolean withJson) {
        if (withJson) {
            return retrofitWithJson;
        } else {
            return retrofit;
        }
    }
}
