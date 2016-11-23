package com.xiongl.weixin.sdk;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2016-11-23.
 */
public class UserInfo {
    @SerializedName("userid")
    private String userId;
    private String name;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
