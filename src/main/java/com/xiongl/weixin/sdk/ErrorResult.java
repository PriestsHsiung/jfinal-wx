package com.xiongl.weixin.sdk;

/**
 * Created by Administrator on 2016-11-2.
 */
public class ErrorResult {
    protected Integer errcode;
    protected String errmsg;

    public Integer getErrcode() {
        return errcode;
    }

    public void setErrcode(Integer errcode) {
        this.errcode = errcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }
}
