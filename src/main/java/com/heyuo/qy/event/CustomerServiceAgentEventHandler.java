package com.heyuo.qy.event;

import com.google.common.eventbus.Subscribe;
import com.heyuo.qy.service.ArchiveService;
import com.jfinal.log.Log;
import com.jfinal.qy.weixin.sdk.api.ApiResult;
import com.jfinal.qy.weixin.sdk.api.SendMessageApi;
import com.jfinal.qy.weixin.sdk.msg.kf.in.*;
import com.jfinal.qy.weixin.sdk.msg.send.*;

/**
 * Created by Administrator on 2016-10-31.
 */
public class CustomerServiceAgentEventHandler {
    private static Log LOGGER = Log.getLog(CustomerServiceAgentEventHandler.class);
    private static final String CORP_APP_Id = "2";
    private static final ArchiveService archiveService = new ArchiveService();

    // 处理接收到的文本消息
    @Subscribe
    public void processKfInTextMsg(KfInTextMsg inTextMsg) {
//        QiYeTextMsg msg = new QiYeTextMsg();
//        msg.setSafe("0");
//        msg.setAgentid(CORP_APP_Id);
//        msg.setTouser(inTextMsg.getReceiver().getId());
//
//        Text t = new Text(inTextMsg.getContent());
//        msg.setText(t);
//
//        ApiResult result = SendMessageApi.sendTextMsg(msg);
//        LOGGER.info(result.getJson());

        archiveService.archive(inTextMsg.getFromUserName(), inTextMsg.getReceiver().getId(),
                "text",  inTextMsg.getMsgId(), inTextMsg.getContent(), false, true);
    }

    @Subscribe
    protected void processKfInImageMsg(KfInImageMsg inImageMsg) {
//        QiYeImageMsg msg = new QiYeImageMsg();
//        msg.setSafe("0");
//        msg.setAgentid(CORP_APP_Id);
//        msg.setTouser(inImageMsg.getReceiver().getId());
//
//        Media m = new Media();
//        m.setMedia_id(inImageMsg.getMediaId());
//        msg.setImage(m);
//
//        ApiResult result = SendMessageApi.sendImageMsg(msg);
//        LOGGER.info(result.getJson());

        archiveService.archive(inImageMsg.getFromUserName(), inImageMsg.getReceiver().getId(),
                "image",  inImageMsg.getMsgId(), inImageMsg.getPicUrl(), false, true);
    }

    @Subscribe
    protected void processKfInVoiceMsg(KfInVoiceMsg inVoiceMsg) {
//        QiYeVoiceMsg msg = new QiYeVoiceMsg();
//        msg.setSafe("0");
//        msg.setAgentid(CORP_APP_Id);
//        msg.setTouser(inVoiceMsg.getReceiver().getId());
//
//        Media m = new Media();
//        m.setMedia_id(inVoiceMsg.getMediaId());
//        msg.setVoice(m);
//
//        ApiResult result = SendMessageApi.sendVoiceMsg(msg);
//        LOGGER.info(result.getJson());

        archiveService.archive(inVoiceMsg.getFromUserName(), inVoiceMsg.getReceiver().getId(),
                "voice",  inVoiceMsg.getMsgId(), inVoiceMsg.getMediaId(), false, true);
    }

    @Subscribe
    protected void processKfInFileMsg(KfInFileMsg inFileMsg) {
//        QiYeFileMsg msg = new QiYeFileMsg();
//        msg.setSafe("0");
//        msg.setTouser(inFileMsg.getReceiver().getId());
//        msg.setAgentid(CORP_APP_Id);
//
//        Media m = new Media();
//        m.setMedia_id(inFileMsg.getMediaId());
//        msg.setFile(m);
//
//        ApiResult result = SendMessageApi.sendFileMsg(msg);
//        LOGGER.info(result.getJson());

        archiveService.archive(inFileMsg.getFromUserName(), inFileMsg.getReceiver().getId(),
                "file",  inFileMsg.getMsgId(), inFileMsg.getMediaId(), false, true);
    }

}
