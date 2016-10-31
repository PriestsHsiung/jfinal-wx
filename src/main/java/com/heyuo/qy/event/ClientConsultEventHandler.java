package com.heyuo.qy.event;

import com.google.common.eventbus.Subscribe;
import com.heyuo.qy.service.ArchiveService;
import com.jfinal.kit.JsonKit;
import com.jfinal.log.Log;
import com.jfinal.qy.weixin.sdk.api.ApiResult;
import com.jfinal.qy.weixin.sdk.api.KfApi;
import com.jfinal.qy.weixin.sdk.msg.in.*;
import com.jfinal.qy.weixin.sdk.msg.kf.out.KfImageMsg;
import com.jfinal.qy.weixin.sdk.msg.kf.out.KfTextMsg;
import com.jfinal.qy.weixin.sdk.msg.kf.out.KfVoiceMsg;

/**
 * Created by Administrator on 2016-10-31.
 */
public class ClientConsultEventHandler {
    private static Log LOGGER = Log.getLog(ClientConsultEventHandler.class);
    private static final ArchiveService archiveService = new ArchiveService();

    @Subscribe
    public void processMsg(ClientConsultEvent event) {
        Object msg = event.getMsg();
        if (msg instanceof InTextMsg)
            processInTextMsg(event);
        else if (msg instanceof InImageMsg)
            processInImageMsg(event);
        else if (msg instanceof InVoiceMsg)
            processInVoiceMsg(event);
        else {
            LOGGER.error("错误的消息类型");
        }
    }

    private void processInTextMsg(ClientConsultEvent textMsgEvent) {
        InTextMsg inTextMsg = (InTextMsg)textMsgEvent.getMsg();
        String clientServiceAgentId = textMsgEvent.getCustomerServiceAgentId();
        Boolean forbidden = textMsgEvent.getForbidden();
        Boolean sended = false;

//        if (!forbidden) {
//            KfTextMsg kfTextMsg = new KfTextMsg();
//            kfTextMsg.getSender().setType("userid");
//            kfTextMsg.getSender().setId(inTextMsg.getFromUserName());
//            kfTextMsg.getReceiver().setType("kf");
//            kfTextMsg.getReceiver().setId(clientServiceAgentId);
//            kfTextMsg.getText().setContent(inTextMsg.getContent());
//            ApiResult result = KfApi.sendMsg(JsonKit.toJson(kfTextMsg).toString());
//            sended = result.isSucceed();
//
//            LOGGER.info(result.getJson());
//        }

        archiveService.archive(inTextMsg.getFromUserName(), clientServiceAgentId,
                "text",  inTextMsg.getMsgId(), inTextMsg.getContent(),
                textMsgEvent.getForbidden(), sended);

    }

    private void processInImageMsg(ClientConsultEvent imageMsgEvent) {
        InImageMsg inImageMsg = (InImageMsg)imageMsgEvent.getMsg();
        String clientServiceAgentId = imageMsgEvent.getCustomerServiceAgentId();
        Boolean forbidden = imageMsgEvent.getForbidden();
        Boolean sended = false;

//        if (!forbidden) {
//            KfImageMsg kfImageMsg = new KfImageMsg();
//            kfImageMsg.getSender().setType("userid");
//            kfImageMsg.getSender().setId(inImageMsg.getFromUserName());
//            kfImageMsg.getReceiver().setType("kf");
//            kfImageMsg.getReceiver().setId(clientServiceAgentId);
//            kfImageMsg.getImage().setMedia_id(inImageMsg.getMediaId());
//            ApiResult result = KfApi.sendMsg(JsonKit.toJson(kfImageMsg).toString());
//            sended = result.isSucceed();
//
//            LOGGER.info(result.getJson());
//        }
        archiveService.archive(inImageMsg.getFromUserName(), clientServiceAgentId,
                "image",  inImageMsg.getMsgId(), inImageMsg.getPicUrl(),
                imageMsgEvent.getForbidden(), sended);
    }

    private void processInVoiceMsg(ClientConsultEvent voiceMsgEvent) {
        InVoiceMsg inVoiceMsg = (InVoiceMsg)voiceMsgEvent.getMsg();
        String clientServiceAgentId = voiceMsgEvent.getCustomerServiceAgentId();
        Boolean forbidden = voiceMsgEvent.getForbidden();
        Boolean sended = false;
//
//        if (!forbidden) {
//            KfVoiceMsg kfImageMsg = new KfVoiceMsg();
//            kfImageMsg.getSender().setType("userid");
//            kfImageMsg.getSender().setId(inVoiceMsg.getFromUserName());
//            kfImageMsg.getReceiver().setType("kf");
//            kfImageMsg.getReceiver().setId(clientServiceAgentId);
//            kfImageMsg.getVoice().setMedia_id(inVoiceMsg.getMediaId());
//            ApiResult result = KfApi.sendMsg(JsonKit.toJson(kfImageMsg).toString());
//            sended = result.isSucceed();
//
//            LOGGER.info(result.getJson());
//        }

        archiveService.archive(inVoiceMsg.getFromUserName(), clientServiceAgentId,
                "voice",  inVoiceMsg.getMsgId(), inVoiceMsg.getMediaId(),
                voiceMsgEvent.getForbidden(), sended);
    }
}
