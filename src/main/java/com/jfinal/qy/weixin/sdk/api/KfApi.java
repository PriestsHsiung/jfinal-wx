package com.jfinal.qy.weixin.sdk.api;

import com.jfinal.qy.weixin.sdk.utils.HttpUtils;

/**
 * Created by Administrator on 2016-10-23.
 */
public class KfApi {
    // 发消息
    private static String sendUrl = "https://qyapi.weixin.qq.com/cgi-bin/kf/send?access_token=ACCESS_TOKEN";

    // 获得客服列表
    private static String kfListUrl = "https://qyapi.weixin.qq.com/cgi-bin/kf/list?access_token=ACCESS_TOKEN&type=internal";

    public static ApiResult sendMsg(String data){
        String url = sendUrl.replace("ACCESS_TOKEN", AccessTokenApi.getAccessTokenStr());
        String jsonStr = HttpUtils.post(url, data);
        return new ApiResult(jsonStr);
    }

    public static ApiResult getkfList(){
        String url = kfListUrl.replace("ACCESS_TOKEN", AccessTokenApi.getAccessTokenStr());
        String jsonStr = HttpUtils.get(url);
        return new ApiResult(jsonStr);
    }
}
