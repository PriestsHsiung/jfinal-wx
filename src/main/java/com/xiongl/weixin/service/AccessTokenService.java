package com.xiongl.weixin.service;

import com.xiongl.weixin.sdk.AccessTokenInfo;
import com.xiongl.weixin.sdk.AccessTokenApi;
import com.xiongl.weixin.sdk.ApiConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import retrofit2.Call;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016-11-2.
 */
public class AccessTokenService extends BaseService {
    public static Map<String, ApiConfig> configMap = new HashMap<String, ApiConfig>();

    private static AccessTokenApi accessTokenApi = RetrofitService.getInstance(true).create(AccessTokenApi.class);
    private static Map<String, AccessTokenInfo> cache = new HashMap<String, AccessTokenInfo>();
    private static final Logger LOGGER = LoggerFactory.getLogger(AccessTokenService.class);

    private AccessTokenService(){}

    private static final AccessTokenService instance = new AccessTokenService();
    public static AccessTokenService getInstance() {
        return instance;
    }

    private String obtain(String key, ApiConfig ac) {
        Call<AccessTokenInfo> atCall = accessTokenApi.getAccessToken(ac.getCorpId(), ac.getCorpSecret());
        AccessTokenInfo at = getResult(atCall, "getAccessToken");

        if (null != at.getErrcode()) {
            String str = "获取accessToken错误：code" + at.getErrcode() + ",msg" + at.getErrmsg();
            LOGGER.error(str);
            throw new RuntimeException(str);
        }

        cache.put(key, at);
        return at.getAccessToken();
    }

    public String getAccessToken(String key) {
        AccessTokenInfo ati = cache.get(key);
        if (null != ati && ati.isAvailable()) {
            return ati.getAccessToken();
        }

        ApiConfig ac = configMap.get(key);
        if (null == ac) {
            throw new RuntimeException("没有key[" + key + "]对应的配置");
        }

        return obtain(key, ac);
    }

    public String refreshAccessToken(String key) {
        ApiConfig ac = configMap.get(key);
        if (null == ac) {
            throw new RuntimeException("没有key[" + key + "]对应的配置");
        }

        return obtain(key, ac);
    }
}
