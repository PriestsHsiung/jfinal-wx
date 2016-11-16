
package com.heyuo.qy.controller;

import com.alibaba.fastjson.JSONArray;
import com.heyuo.qy.QyWeiXinConfig;
import com.heyuo.qy.event.ClientConsultEvent;
import com.heyuo.qy.service.QyLevelService;
import com.jfinal.kit.JsonKit;
import com.jfinal.kit.PropKit;
import com.jfinal.log.Log;
import com.jfinal.qy.weixin.sdk.api.*;
import com.jfinal.qy.weixin.sdk.jfinal.MsgController;
import com.jfinal.qy.weixin.sdk.msg.in.*;
import com.jfinal.qy.weixin.sdk.msg.in.event.*;
import com.jfinal.qy.weixin.sdk.msg.kf.in.KfInFileMsg;
import com.jfinal.qy.weixin.sdk.msg.kf.in.KfInImageMsg;
import com.jfinal.qy.weixin.sdk.msg.kf.in.KfInTextMsg;
import com.jfinal.qy.weixin.sdk.msg.kf.in.KfInVoiceMsg;
import com.jfinal.qy.weixin.sdk.msg.kf.out.KfImageMsg;
import com.jfinal.qy.weixin.sdk.msg.kf.out.KfTextMsg;
import com.jfinal.qy.weixin.sdk.msg.kf.out.KfVoiceMsg;
import com.jfinal.qy.weixin.sdk.msg.out.OutTextMsg;
import com.jfinal.qy.weixin.sdk.msg.send.QiYeTextMsg;
import com.jfinal.qy.weixin.sdk.msg.send.Text;
import org.apache.commons.lang.StringUtils;

import java.util.Map;

public class QyConsultController extends MsgController {

	static Log logger = Log.getLog(QyConsultController.class);
	private static final String helpStr = "\t发送 help 可获得帮助，发送 \"美女\" 可看美女 ，发送新闻可看新版本消息。公众号功能持续完善中";

	private QyLevelService qyLevelService = new QyLevelService();

	public boolean isKf() {
		return false;
	}

	public ApiConfig getApiConfig() {
		ApiConfig ac = new ApiConfig();
		
		// 配置微信 API 相关常量
		ac.setToken(PropKit.get("token"));
		ac.setCorpId(PropKit.get("corpId"));
		ac.setCorpSecret(PropKit.get("kf_secret"));
		
		/**
		 *  是否对消息进行加密，对应于微信平台的消息加解密方式：
		 *  1：true进行加密且必须配置 encodingAesKey
		 *  2：false采用明文模式，同时也支持混合模式
		 *  
		 *  目前企业号只支持加密且必须配置
		 */
		ac.setEncryptMessage(PropKit.getBoolean("encryptMessage", true));
		ac.setEncodingAesKey(PropKit.get("encodingAesKey", "setting it in config file"));
		return ac;
	}

	/**
	 * 实现父类抽方法，处理文本消息
	 * 本例子中根据消息中的不同文本内容分别做出了不同的响应，同时也是为了测试 jfinal qyweixin sdk的基本功能：
	 *     本方法仅测试了 OutTextMsg、OutNewsMsg、OutMusicMsg 三种类型的OutMsg，
	 *     其它类型的消息会在随后的方法中进行测试
	 */

	private String getKf(InMsg inMsg) {
		ApiResult result = KfApi.getkfList();

		Map<String, Object> internal = result.getMap("internal");
		if(null == internal) {
			OutTextMsg outMsg = new OutTextMsg(inMsg);
			outMsg.setContent("客服人员正忙，请稍候再试");
			render(outMsg);
			return "";
		}

		JSONArray kfList = (JSONArray)internal.get("user");
		if (kfList.isEmpty()) {
			OutTextMsg outMsg = new OutTextMsg(inMsg);
			outMsg.setContent("客服人员正忙，请稍候再试");
			render(outMsg);
			return "";
		}

		return (String)kfList.get(0);

	}

	private void renderPermissionMsg(InMsg inMsg) {
		OutTextMsg outMsg = new OutTextMsg(inMsg);
		outMsg.setContent("非常抱歉，您不能发送此类消息，请联系管理员提升套餐，谢谢");
		render(outMsg);
	}

	@Override
	protected void processInTextMsg(InTextMsg inTextMsg) {
		String kf = getKf(inTextMsg);
		if (StringUtils.isBlank(kf)) {
			return;
		}

		Boolean canSend = qyLevelService.canSendText(inTextMsg.getFromUserName());
		if (!canSend) {
			renderPermissionMsg(inTextMsg);
		}

		ClientConsultEvent event = new ClientConsultEvent();
		event.setForbidden(!canSend);
		event.setCustomerServiceAgentId(kf);
		event.setMsg(inTextMsg);
		QyWeiXinConfig.eventBus.post(event);

		if (canSend) {
			KfTextMsg kfTextMsg = new KfTextMsg();
			kfTextMsg.getSender().setType("userid");
			kfTextMsg.getSender().setId(inTextMsg.getFromUserName());
			kfTextMsg.getReceiver().setType("kf");
			kfTextMsg.getReceiver().setId(kf);
			kfTextMsg.getText().setContent(inTextMsg.getContent());
			ApiResult result = KfApi.sendMsg(JsonKit.toJson(kfTextMsg).toString());
			System.out.println(result.getJson());


			String msgContent = inTextMsg.getContent().trim();
			System.out.println("收到的信息：" + msgContent);
		}
	}

	/**
	 * 实现父类抽方法，处理图片消息
	 */
	@Override
	protected void processInImageMsg(InImageMsg inImageMsg) {
		String kf = getKf(inImageMsg);
		if (StringUtils.isBlank(kf)) {
			return;
		}

		Boolean canSend = qyLevelService.canSendImage(inImageMsg.getFromUserName());
		if (!canSend) {
			renderPermissionMsg(inImageMsg);
		}

		ClientConsultEvent event = new ClientConsultEvent();
		event.setForbidden(!canSend);
		event.setCustomerServiceAgentId(kf);
		event.setMsg(inImageMsg);
		QyWeiXinConfig.eventBus.post(event);

		if (canSend) {
			KfImageMsg kfImageMsg = new KfImageMsg();
			kfImageMsg.getSender().setType("userid");
			kfImageMsg.getSender().setId(inImageMsg.getFromUserName());
			kfImageMsg.getReceiver().setType("kf");
			kfImageMsg.getReceiver().setId(kf);
			kfImageMsg.getImage().setMedia_id(inImageMsg.getMediaId());
			ApiResult result = KfApi.sendMsg(JsonKit.toJson(kfImageMsg).toString());
			System.out.println(result.getJson());
		}
	}

	/**
	 * 实现父类抽方法，处理语音消息
	 */
	protected void processInVoiceMsg(InVoiceMsg inVoiceMsg) {
		String kf = getKf(inVoiceMsg);
		if (StringUtils.isBlank(kf)) {
			return;
		}

		Boolean canSend = qyLevelService.canSendVoice(inVoiceMsg.getFromUserName());
		if (!canSend) {
			renderPermissionMsg(inVoiceMsg);
		}

		ClientConsultEvent event = new ClientConsultEvent();
		event.setForbidden(!canSend);
		event.setCustomerServiceAgentId(kf);
		event.setMsg(inVoiceMsg);
		QyWeiXinConfig.eventBus.post(event);

		if (canSend) {
			KfVoiceMsg kfImageMsg = new KfVoiceMsg();
			kfImageMsg.getSender().setType("userid");
			kfImageMsg.getSender().setId(inVoiceMsg.getFromUserName());
			kfImageMsg.getReceiver().setType("kf");
			kfImageMsg.getReceiver().setId(kf);
			kfImageMsg.getVoice().setMedia_id(inVoiceMsg.getMediaId());
			ApiResult result = KfApi.sendMsg(JsonKit.toJson(kfImageMsg).toString());
			System.out.println(result.getJson());
		}
	}

	/**
	 * 实现父类抽方法，处理视频消息
	 */
	protected void processInVideoMsg(InVideoMsg inVideoMsg) {
		/* 腾讯 api 有 bug，无法回复视频消息，暂时回复文本消息代码测试
		OutVideoMsg outMsg = new OutVideoMsg(inVideoMsg);
		outMsg.setTitle("OutVideoMsg 发送");
		outMsg.setDescription("刚刚发来的视频再发回去");
		// 将刚发过来的视频再发回去，经测试证明是腾讯官方的 api 有 bug，待 api bug 却除后再试
		outMsg.setMediaId(inVideoMsg.getMediaId());
		render(outMsg);
		*/
		OutTextMsg outMsg = new OutTextMsg(inVideoMsg);
		outMsg.setContent("\t视频消息已成功接收，该视频的 mediaId 为: " + inVideoMsg.getMediaId());
		render(outMsg);
	}

	@Override
	protected void processInShortVideoMsg(InShortVideoMsg inShortVideoMsg)
	{
		OutTextMsg outMsg = new OutTextMsg(inShortVideoMsg);
		outMsg.setContent("\t视频消息已成功接收，该视频的 mediaId 为: " + inShortVideoMsg.getMediaId());
		render(outMsg);
	}

	/**
	 * 实现父类抽方法，处理地址位置消息
	 */
	protected void processInLocationMsg(InLocationMsg inLocationMsg) {
		OutTextMsg outMsg = new OutTextMsg(inLocationMsg);
		outMsg.setContent("已收到地理位置消息:" +
							"\nlocation_X = " + inLocationMsg.getLocation_X() +
							"\nlocation_Y = " + inLocationMsg.getLocation_Y() +
							"\nscale = " + inLocationMsg.getScale() +
							"\nlabel = " + inLocationMsg.getLabel());
		render(outMsg);
	}

	/**
	 * 实现父类抽方法，处理关注/取消关注消息
	 */
	protected void processInFollowEvent(InFollowEvent inFollowEvent) {
		if (InFollowEvent.EVENT_INFOLLOW_SUBSCRIBE.equals(inFollowEvent.getEvent()))
		{
			OutTextMsg outMsg = new OutTextMsg(inFollowEvent);
			outMsg.setContent("尊敬的会员，你好！ \n 欢迎加入海邀网在线人力资源服务平台，您已选择海邀网旗下产品“嘿好管”在线咨询服务");
			render(outMsg);
		}// 如果为取消关注事件，将无法接收到传回的信息
		if (InFollowEvent.EVENT_INFOLLOW_UNSUBSCRIBE.equals(inFollowEvent.getEvent()))
		{
			logger.debug("取消关注：" + inFollowEvent.getFromUserName());
		}
	}
	/**
	 * 实现父类抽方法，处理上报地理位置事件
	 */
	protected void processInLocationEvent(InLocationEvent inLocationEvent) {
		OutTextMsg outMsg = new OutTextMsg(inLocationEvent);
		outMsg.setContent("processInLocationEvent() 方法测试成功\nLatitude:"
		+inLocationEvent.getLatitude()+"\n Longitude:"
		+inLocationEvent.getLongitude()+"\n Precision:"
		+inLocationEvent.getPrecision());
		render(outMsg);
	}

	/**
	 * 实现父类抽方法，处理自定义菜单事件
	 */
	protected void processInMenuEvent(InMenuEvent inMenuEvent) {
		OutTextMsg outMsg = new OutTextMsg(inMenuEvent);
		outMsg.setContent("菜单事件内容是：" + inMenuEvent.getEventKey());
		render(outMsg);
		if (InMenuEvent.EVENT_INMENU_CLICK.equals(inMenuEvent.getEvent())) {
			
		}
		
	}
	/**
	 * 实现父类抽方法，处理扫码推事件
	 */
	@Override
	protected void processInQrCodeEvent(InQrCodeEvent inQrCodeEvent) {
		ScanCodeInfo scanCodeInfo = inQrCodeEvent.getScanCodeInfo();
		String scanResult=scanCodeInfo.getScanResult();
		String scantype=scanCodeInfo.getScanType();
		OutTextMsg outMsg = new OutTextMsg(inQrCodeEvent);
		outMsg.setContent("处理扫码推事件\n 扫描结果:" + scanResult+"扫描类型:"+scantype);
		render(outMsg);
		
	}
	/**
	 * 实现父类抽方法，处理成员进入应用的事件
	 */
	@Override
	protected void processInEnterAgentEvent(InEnterAgentEvent inAgentEvent) {
		OutTextMsg outMsg = new OutTextMsg(inAgentEvent);
		outMsg.setContent("欢迎:"+inAgentEvent.getFromUserName()+"再次进入");
		render(outMsg);
	}

	/**
	 * 实现父类抽方法，处理异步任务完成事件
	 */
	@Override
	protected void processInJobEvent(InJobEvent inJobEvent) {
		BatchJob batchJob = inJobEvent.getBatchJob();
		
		ApiResult batchGetResult = ConBatchApi.batchGetResult(batchJob.getJobId());
		
		QiYeTextMsg text=new QiYeTextMsg();
		text.setAgentid("16");
		text.setSafe("0");
		text.setTouser("Javen");
		text.setText(new Text("异步任务完成:\n JobType:"+batchJob.getJobType()
				+"\n JobId:"+batchJob.getJobId()
				+"\n 执行结果："+batchGetResult.getJson()));
		SendMessageApi.sendTextMsg(text);
		renderNull();
	}

	protected void processKfInTextMsg(KfInTextMsg inTextMsg) {

	}

	protected void processKfInImageMsg(KfInImageMsg inImageMsg) {

	}

	protected void processKfInVoiceMsg(KfInVoiceMsg inVoiceMsg) {

	}

	protected void processKfInFileMsg(KfInFileMsg inFileMsg) {

	}


}






