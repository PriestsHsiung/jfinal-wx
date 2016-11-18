package com.heyuo.qy.service;

import com.alibaba.fastjson.JSONArray;
import com.heyuo.qy.QyWeiXinConfig;
import com.heyuo.qy.bo.api.QyConsultMsg;
import com.heyuo.qy.event.ClientConsultEvent;
import com.jfinal.kit.JsonKit;
import com.jfinal.qy.weixin.sdk.api.ApiResult;
import com.jfinal.qy.weixin.sdk.api.KfApi;
import com.jfinal.qy.weixin.sdk.msg.in.InImageMsg;
import com.jfinal.qy.weixin.sdk.msg.in.InTextMsg;
import com.jfinal.qy.weixin.sdk.msg.in.InVoiceMsg;
import com.jfinal.qy.weixin.sdk.msg.kf.out.KfImageMsg;
import com.jfinal.qy.weixin.sdk.msg.kf.out.KfTextMsg;
import com.jfinal.qy.weixin.sdk.msg.kf.out.KfVoiceMsg;
import org.apache.commons.lang.StringUtils;

import java.util.Map;

/**
 * Created by Administrator on 2016-11-15.
 */
public class QyConsultSendService {

	private QyLevelService qyLevelService = new QyLevelService();

    public Boolean sendMsg(QyConsultMsg msg, String errTip) {
		String kf = getExternalKf(msg.fromUser);
		if (StringUtils.isBlank(kf)) {
			errTip = "客服人员正忙，请稍候再试";
			return false;
		}

        Boolean res = false;
		errTip = "非常抱歉，您不能发送此类消息，请联系管理员提升套餐，谢谢";

		msg.kf = kf;
        if ("text".equals(msg.msgType)) {
            res = sendTxtMsg(msg);
        } else if ("voice".equals(msg.msgType)) {
            res  = sendVoiceMsg(msg);
        } else if ("image".equals(msg.msgType)) {
            res = sendImageMsg(msg);
        }

		return res;
    }

    public boolean sendTxtMsg(QyConsultMsg msg) {
        // Boolean canSend = qyLevelService.canSendText(msg.fromUser);
        Boolean canSend = true;

        InTextMsg inTextMsg = new InTextMsg(msg.toUser, msg.fromUser, msg.createTime, msg.msgType, msg.agentId);
        inTextMsg.setMsgId(msg.msgId);
        inTextMsg.setContent(msg.content);

		ClientConsultEvent event = new ClientConsultEvent();
		event.setForbidden(!canSend);
		event.setCustomerServiceAgentId(msg.kf);
		event.setMsg(inTextMsg);
		QyWeiXinConfig.eventBus.post(event);

        if (!canSend) {
            return false;
        }

        KfTextMsg kfTextMsg = new KfTextMsg();
        kfTextMsg.getSender().setType(msg.userType);
        kfTextMsg.getSender().setId(msg.fromUser);
        kfTextMsg.getReceiver().setType("kf");
        kfTextMsg.getReceiver().setId(msg.kf);
        kfTextMsg.getText().setContent(msg.content);
        ApiResult result = KfApi.sendMsg(JsonKit.toJson(kfTextMsg).toString());

        System.out.println(result.getJson());
        String msgContent = inTextMsg.getContent().trim();
        System.out.println("收到的信息：" + msgContent);

        return true;
    }

    public boolean sendImageMsg(QyConsultMsg msg) {
        Boolean canSend = qyLevelService.canSendVoice(msg.fromUser);
        InImageMsg inImageMsg = new InImageMsg(msg.toUser, msg.fromUser, msg.createTime, msg.msgType, msg.agentId);
        inImageMsg.setMsgId(msg.msgId);
        inImageMsg.setMediaId(msg.mediaId);
        inImageMsg.setPicUrl(msg.picUrl);

        ClientConsultEvent event = new ClientConsultEvent();
		event.setForbidden(!canSend);
		event.setCustomerServiceAgentId(msg.kf);
		event.setMsg(inImageMsg);
		QyWeiXinConfig.eventBus.post(event);

        if (!canSend) {
            return false;
        }

        KfImageMsg kfImageMsg = new KfImageMsg();
        kfImageMsg.getSender().setType(msg.userType);
        kfImageMsg.getSender().setId(msg.fromUser);
        kfImageMsg.getReceiver().setType("kf");
        kfImageMsg.getReceiver().setId(msg.kf);
        kfImageMsg.getImage().setMedia_id(msg.mediaId);
        ApiResult result = KfApi.sendMsg(JsonKit.toJson(kfImageMsg).toString());

        System.out.println(result.getJson());
        String msgContent = inImageMsg.getMediaId();
        System.out.println("收到的信息：" + msgContent);

        return true;
    }

    public boolean sendVoiceMsg(QyConsultMsg msg) {
        Boolean canSend = qyLevelService.canSendImage(msg.fromUser);

        InVoiceMsg inVoiceMsg = new InVoiceMsg(msg.toUser, msg.fromUser, msg.createTime, msg.msgType, msg.agentId);
        inVoiceMsg.setMsgId(msg.msgId);
        inVoiceMsg.setFromUserName(msg.format);
        inVoiceMsg.setMediaId(msg.mediaId);

        ClientConsultEvent event = new ClientConsultEvent();
		event.setForbidden(!canSend);
		event.setCustomerServiceAgentId(msg.kf);
		event.setMsg(inVoiceMsg);
		QyWeiXinConfig.eventBus.post(event);

        if (!canSend) {
            return false;
        }

        KfVoiceMsg kfImageMsg = new KfVoiceMsg();
        kfImageMsg.getSender().setType(msg.userType);
        kfImageMsg.getSender().setId(msg.fromUser);
        kfImageMsg.getReceiver().setType("kf");
        kfImageMsg.getReceiver().setId(msg.kf);
        kfImageMsg.getVoice().setMedia_id(msg.mediaId);
        ApiResult result = KfApi.sendMsg(JsonKit.toJson(kfImageMsg).toString());

        System.out.println(result.getJson());
        String msgContent = inVoiceMsg.getMediaId();
        System.out.println("收到的信息：" + msgContent);

        return true;
    }

    private String getExternalKf(String userId) {
		ApiResult result = KfApi.getExternalkfList();

		Map<String, Object> internal = result.getMap("external");
		if(null == internal) {
			return null;
		}

		JSONArray kfList = (JSONArray)internal.get("user");
		if (kfList.isEmpty()) {
			return null;
		}

		return (String)kfList.get(0);

	}
}
