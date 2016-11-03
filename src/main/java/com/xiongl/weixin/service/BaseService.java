package com.xiongl.weixin.service;

import com.xiongl.weixin.sdk.Constant;
import com.xiongl.weixin.sdk.ErrorResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;

/**
 * Created by Administrator on 2016-11-2.
 */
public class BaseService {
    private static final Logger LOGGER = LoggerFactory.getLogger(BaseService.class);

    protected <T>T getResult (Call<T> callable, String trace) {
        T t = null;
        try {
            Response<T> resp = callable.execute();
            if (resp.isSuccessful()) {
                t = resp.body();
            } else {
                LOGGER.error(trace + "请求失败：code" + resp.code() + ",err" + resp.errorBody().string());
            }
        } catch (IOException e) {
            LOGGER.error(trace + "请求出错：" + e.getMessage());
        }

        if (null == t) {
            throw new RuntimeException(trace + "请求错误");
        }

        return t;
    }
}
