package com.jfinal.qy.weixin.sdk.msg.kf.in;

/**
 * Created by Administrator on 2016-10-23.
 */
public class KfInVoiceMsg extends KfInBaseMsg {
    private String mediaId;

    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }
}
