package com.jfinal.qy.weixin.sdk.msg.kf.out;

/**
 * Created by Administrator on 2016-10-23.
 */
public class KfVoiceMsg extends KfBaseInfo {
    private String msgtype = "voice";
    private KfImageInfo voice = new KfImageInfo();

    public String getMsgtype() {
        return msgtype;
    }

    public void setMsgtype(String msgtype) {
        this.msgtype = msgtype;
    }

    public KfImageInfo getVoice() {
        return voice;
    }

    public void setVoice(KfImageInfo voice) {
        this.voice = voice;
    }
}
