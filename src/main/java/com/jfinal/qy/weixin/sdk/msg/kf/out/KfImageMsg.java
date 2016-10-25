package com.jfinal.qy.weixin.sdk.msg.kf.out;

/**
 * Created by Administrator on 2016-10-23.
 */
public class KfImageMsg extends KfBaseInfo {
    private String msgtype = "image";
    private KfImageInfo image = new KfImageInfo();

    public String getMsgtype() {
        return msgtype;
    }

    public void setMsgtype(String msgtype) {
        this.msgtype = msgtype;
    }

    public KfImageInfo getImage() {
        return image;
    }

    public void setImage(KfImageInfo image) {
        this.image = image;
    }
}
