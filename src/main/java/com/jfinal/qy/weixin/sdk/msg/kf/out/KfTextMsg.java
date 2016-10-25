package com.jfinal.qy.weixin.sdk.msg.kf.out;

/**
 * Created by Administrator on 2016-10-23.
 */
public class KfTextMsg extends KfBaseInfo {
    private String msgtype = "text";
    private KfTextInfo text = new KfTextInfo();

    public String getMsgtype() {
        return msgtype;
    }

    public void setMsgtype(String msgtype) {
        this.msgtype = msgtype;
    }

    public KfTextInfo getText() {
        return text;
    }

    public void setText(KfTextInfo text) {
        this.text = text;
    }
}
