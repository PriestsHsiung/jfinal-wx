package com.xiongl.weixin.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import sun.dc.pr.PRError;

/**
 * Created by Administrator on 2016-11-2.
 */
public class RetrofitService {
    private static Retrofit retrofitWithJson;
    private static Retrofit retrofit;
    private static final String BASE_URL = "https://qyapi.weixin.qq.com/cgi-bin/";

    static {
        retrofitWithJson = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()).build();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL).build();
    }

    public static Retrofit getInstance(Boolean withJson) {
        if (withJson) {
            return retrofitWithJson;
        } else {
            return retrofit;
        }
    }
}
