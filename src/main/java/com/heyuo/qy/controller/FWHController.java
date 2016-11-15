package com.heyuo.qy.controller;

import com.heyuo.qy.bo.api.QyConsultMsg;
import com.heyuo.qy.service.QyConsultSendService;
import com.jfinal.core.Controller;

/**
 * 接收从服务号传递过来的咨询数据
 * Created by Administrator on 2016-11-15.
 */
public class FWHController extends Controller {

    private QyConsultSendService qyConsultSendService = new QyConsultSendService();
    public void index() {
        QyConsultMsg msg = new QyConsultMsg();
        msg.userType = "openid";
        msg.fromUser = getPara("fromuser");
        msg.msgId = getPara("msgId");
        msg.msgType = getPara("msgType");
        msg.content = getPara("content");
        msg.mediaId = getPara("mediaId");
        msg.picUrl = getPara("picUrl");
        msg.createTime = getParaToInt("createTime");

        String errTip = "";
        Boolean res = qyConsultSendService.sendMsg(msg, errTip);
        if (!res) {
            renderText(errTip);
        }
    }
}
