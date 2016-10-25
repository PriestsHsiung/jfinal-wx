package com.jfinal.qy.weixin.sdk.msg.kf.out;

/**
 * Created by Administrator on 2016-10-23.
 */
public class KfFileMsg extends KfBaseInfo {
    private String msgtype = "file";
    private KfImageInfo file = new KfImageInfo();

    public String getMsgtype() {
        return msgtype;
    }

    public void setMsgtype(String msgtype) {
        this.msgtype = msgtype;
    }

    public KfImageInfo getFile() {
        return file;
    }

    public void setFile(KfImageInfo file) {
        this.file = file;
    }
}
