package com.xiongl.weixin.sdk;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Administrator on 2016-11-23.
 */
public interface TagApi {
    @GET("tag/get")
    Call<TagClientList> getTagClientList(@Query("access_token") String accessToken, @Query("tagid")String tagid);
}
