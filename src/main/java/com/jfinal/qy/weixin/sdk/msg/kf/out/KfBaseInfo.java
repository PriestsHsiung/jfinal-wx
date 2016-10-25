package com.jfinal.qy.weixin.sdk.msg.kf.out;


/**
 * Created by Administrator on 2016-10-23.
 */
public class KfBaseInfo {
    private UserInfo sender = new UserInfo();
    private UserInfo receiver = new UserInfo();

    public UserInfo getSender() {
        return sender;
    }

    public void setSender(UserInfo sender) {
        this.sender = sender;
    }

    public UserInfo getReceiver() {
        return receiver;
    }

    public void setReceiver(UserInfo receiver) {
        this.receiver = receiver;
    }
}
