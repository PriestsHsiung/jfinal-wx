package com.jfinal.qy.weixin.sdk.msg.kf.in;

/**
 * Created by Administrator on 2016-10-23.
 */
public class KfInImageMsg extends KfInBaseMsg {
    private String picUrl;
    private String mediaId;

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }
}
