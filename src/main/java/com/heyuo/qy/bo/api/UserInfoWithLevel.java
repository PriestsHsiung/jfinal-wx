package com.heyuo.qy.bo.api;

/**
 * Created by Administrator on 2016-11-23.
 */
public class UserInfoWithLevel {
    private String userId;
    private String name;
    private Integer level;

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

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }
}
