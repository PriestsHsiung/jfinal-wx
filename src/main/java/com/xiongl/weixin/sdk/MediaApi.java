package com.xiongl.weixin.sdk;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * Created by Administrator on 2016-11-2.
 */
public interface MediaApi {
    @Streaming
    @GET("media/get")
    Call<ResponseBody> getVoiceFile(@Query("access_token") String accessToken, @Query("media_id") String mediaId);

    @GET
    Call<ResponseBody> getImageFile(@Url String url);
}
