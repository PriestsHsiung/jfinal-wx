package com.jfinal.qy.weixin.sdk.api;

import com.jfinal.qy.weixin.sdk.utils.HttpUtils;

/**
 * Created by Administrator on 2016-10-23.
 */
public class KfApi {
    // 发消息
    private static String sendUrl = "https://qyapi.weixin.qq.com/cgi-bin/kf/send?access_token=ACCESS_TOKEN";

    // 获得内部客服列表
    private static String internalkfListUrl = "https://qyapi.weixin.qq.com/cgi-bin/kf/list?access_token=ACCESS_TOKEN&type=internal";

    // 获得外部客服列表
    private static String externalkfListUrl = "https://qyapi.weixin.qq.com/cgi-bin/kf/list?access_token=ACCESS_TOKEN&type=external";

    public static ApiResult sendMsg(String data){
        String url = sendUrl.replace("ACCESS_TOKEN", AccessTokenApi.getAccessTokenStr());
        String jsonStr = HttpUtils.post(url, data);
        return new ApiResult(jsonStr);
    }

    public static ApiResult getInternalkfList(){
        String url = internalkfListUrl.replace("ACCESS_TOKEN", AccessTokenApi.getAccessTokenStr());
        String jsonStr = HttpUtils.get(url);
        return new ApiResult(jsonStr);
    }

    public static ApiResult getExternalkfList(){
        String url = externalkfListUrl.replace("ACCESS_TOKEN", AccessTokenApi.getAccessTokenStr());
        String jsonStr = HttpUtils.get(url);
        return new ApiResult(jsonStr);
    }
}
