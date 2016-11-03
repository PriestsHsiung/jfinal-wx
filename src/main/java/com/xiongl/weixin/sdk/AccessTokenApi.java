package com.xiongl.weixin.sdk;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Administrator on 2016-11-2.
 */
public interface AccessTokenApi {
    @GET("gettoken")
    Call<AccessTokenInfo> getAccessToken(@Query("corpid") String corpid, @Query("corpsecret") String corpsecret);
}
