package com.xiongl.weixin.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Administrator on 2016-11-2.
 */
public interface FwhApi {
    @GET("qyh/msg/accept")
    Call<Object> sendMsg(@Query("toUser") String toUser, @Query("msgType") String msgType,
                       @Query("mediaId") String mediaId, @Query("content") String content);
}
