package com.xiongl.weixin.sdk;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2016-11-2.
 */
public class AccessTokenInfo extends ErrorResult {
    @SerializedName("access_token")
    private String accessToken;	// 正确获取到 access_token 时有值

    @SerializedName("expires_in")
    private Integer expiresIn;		// 正确获取到 access_token 时有值

    private Long expiredTime;		// 正确获取到 access_token 时有值，存放过期时间

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public Integer getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(Integer expiresIn) {
        if (expiresIn != null)
            this.expiredTime = System.currentTimeMillis() + ((expiresIn -5) * 1000);

        this.expiresIn = expiresIn;
    }

    public boolean isAvailable() {
		if (expiredTime == null)
			return false;
		if (errcode != null)
			return false;
		if (expiredTime < System.currentTimeMillis())
			return false;
		return accessToken != null;
	}
}
