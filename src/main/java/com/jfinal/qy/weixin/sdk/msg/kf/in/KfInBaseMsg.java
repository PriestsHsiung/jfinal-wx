package com.jfinal.qy.weixin.sdk.msg.kf.in;

/**
 * Created by Administrator on 2016-10-23.
 */
public class KfInBaseMsg {
    protected String fromUserName;
    protected String createTime;
    protected String msgId;
    protected String msgType;
    protected Receiver receiver = new Receiver();

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public String getFromUserName() {
        return fromUserName;
    }

    public void setFromUserName(String fromUserName) {
        this.fromUserName = fromUserName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public Receiver getReceiver() {
        return receiver;
    }

    public void setReceiver(Receiver receiver) {
        this.receiver = receiver;
    }
}
