
package com.heyuo.qy.controller;

import com.heyuo.qy.QyWeiXinConfig;
import com.jfinal.kit.PropKit;
import com.jfinal.log.Log;
import com.jfinal.qy.weixin.sdk.api.ApiConfig;
import com.jfinal.qy.weixin.sdk.api.ApiResult;
import com.jfinal.qy.weixin.sdk.api.ConBatchApi;
import com.jfinal.qy.weixin.sdk.api.SendMessageApi;
import com.jfinal.qy.weixin.sdk.jfinal.MsgController;
import com.jfinal.qy.weixin.sdk.msg.in.*;
import com.jfinal.qy.weixin.sdk.msg.in.event.*;
import com.jfinal.qy.weixin.sdk.msg.kf.in.KfInFileMsg;
import com.jfinal.qy.weixin.sdk.msg.kf.in.KfInImageMsg;
import com.jfinal.qy.weixin.sdk.msg.kf.in.KfInTextMsg;
import com.jfinal.qy.weixin.sdk.msg.kf.in.KfInVoiceMsg;
import com.jfinal.qy.weixin.sdk.msg.out.OutImageMsg;
import com.jfinal.qy.weixin.sdk.msg.out.OutTextMsg;
import com.jfinal.qy.weixin.sdk.msg.out.OutVoiceMsg;
import com.jfinal.qy.weixin.sdk.msg.send.*;
import com.xiongl.weixin.api.FwhApi;
import com.xiongl.weixin.service.RetrofitService;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;

public class QyExKfController extends MsgController {

	static Log logger = Log.getLog(QyExKfController.class);

	public boolean isKf() {
		return true;
	}

	public ApiConfig getApiConfig() {
		ApiConfig ac = new ApiConfig();
		
		// 配置微信 API 相关常量
		ac.setToken(PropKit.get("token"));
		ac.setCorpId(PropKit.get("corpId"));
		ac.setCorpSecret(PropKit.get("yy_secret"));

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
	@Override
	protected void processInTextMsg(InTextMsg inTextMsg) {
//		ApiResult result = KfApi.getkfList();
//
//		Map<String, Object> internal = result.getMap("internal");
//		if(null == internal) {
//			OutTextMsg outMsg = new OutTextMsg(inTextMsg);
//			outMsg.setContent("客服人员正忙，请稍候再试");
//			render(outMsg);
//		}
//
//		JSONArray kfList = (JSONArray)internal.get("user");
//		if (kfList.isEmpty()) {
//			OutTextMsg outMsg = new OutTextMsg(inTextMsg);
//			outMsg.setContent("客服人员正忙，请稍候再试");
//			render(outMsg);
//		}
//
//		String kf = (String)kfList.get(0);
//
//		KfTextMsg kfTextMsg = new KfTextMsg();
//		kfTextMsg.getSender().setType("userid");
//		kfTextMsg.getSender().setId(inTextMsg.getFromUserName());
//		kfTextMsg.getReceiver().setType("kf");
//		kfTextMsg.getReceiver().setId(kf);
//		kfTextMsg.getText().setContent(inTextMsg.getContent());
//		result = KfApi.sendMsg(JsonKit.toJson(kfTextMsg).toString());
//		System.out.println(result.getJson());
////		List in
////		getList("internal");
////		if (internalKf.isEmpty()) {
////			OutTextMsg outMsg = new OutTextMsg(inTextMsg);
////			outMsg.setContent("客服人员正忙，请稍候再试");
////			render(outMsg);
////		}
////		StringinternalKf.get(0);
//		String msgContent = inTextMsg.getContent().trim();
//		System.out.println("收到的信息："+msgContent);
//
//		renderText("");
	}

	/**
	 * 实现父类抽方法，处理图片消息
	 */
	@Override
	protected void processInImageMsg(InImageMsg inImageMsg) {
		OutImageMsg outMsg = new OutImageMsg(inImageMsg);
		// 将刚发过来的图片再发回去
		outMsg.setMediaId(inImageMsg.getMediaId());
		render(outMsg);
		
	}

	/**
	 * 实现父类抽方法，处理语音消息
	 */
	protected void processInVoiceMsg(InVoiceMsg inVoiceMsg) {
		OutVoiceMsg outMsg = new OutVoiceMsg(inVoiceMsg);
		// 将刚发过来的语音再发回去
		outMsg.setMediaId(inVoiceMsg.getMediaId());
		render(outMsg);
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
			outMsg.setContent("感谢关注 JFinal Weixin 极速开发企业号，为您节约更多时间，去陪恋人、家人和朋友 :) \n\n\n ");
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
		FwhApi fwhApi = RetrofitService.getFwhRetrofit().create(FwhApi.class);
		Call<Object> call =
				fwhApi.sendMsg(inTextMsg.getReceiver().getId(), inTextMsg.getMsgType(),
									inTextMsg.getMsgId(), inTextMsg.getContent());

		try {
			Response<Object> resp = call.execute();
			if (resp.isSuccessful()) {
			} else {
				logger.error(resp.message());
			}
		}catch(IOException e){
			logger.error(e.getMessage());
		}

		QyWeiXinConfig.eventBus.post(inTextMsg);
	}

	protected void processKfInImageMsg(KfInImageMsg inImageMsg) {
		FwhApi fwhApi = RetrofitService.getFwhRetrofit().create(FwhApi.class);
		Call<Object> call =
				fwhApi.sendMsg(inImageMsg.getReceiver().getId(), inImageMsg.getMsgType(),
									inImageMsg.getMediaId(), "");

		try {
			Response<Object> resp = call.execute();
			if (resp.isSuccessful()) {
			} else {
				logger.error(resp.message());
			}
		}catch(IOException e){
			logger.error(e.getMessage());
		}

		QyWeiXinConfig.eventBus.post(inImageMsg);
	}

	protected void processKfInVoiceMsg(KfInVoiceMsg inVoiceMsg) {
		FwhApi fwhApi = RetrofitService.getFwhRetrofit().create(FwhApi.class);
		Call<Object> call =
				fwhApi.sendMsg(inVoiceMsg.getReceiver().getId(), inVoiceMsg.getMsgType(),
								inVoiceMsg.getMediaId(), "");
									//"WuO8dimR0-1CiNb8mSC22zkZ6PMapL9JUajKkDnWvNLxSZFpUbXjJnnkTr-kLldM", "");

		try {
			Response<Object> resp = call.execute();
			if (resp.isSuccessful()) {
			} else {
				logger.error(resp.message());
			}
		}catch(IOException e){
			logger.error(e.getMessage());
		}

		QyWeiXinConfig.eventBus.post(inVoiceMsg);
	}

	protected void processKfInFileMsg(KfInFileMsg inFileMsg) {
		QiYeFileMsg msg = new QiYeFileMsg();
		msg.setSafe("0");
		msg.setTouser(inFileMsg.getReceiver().getId());
		msg.setAgentid("2");

		Media m = new Media();
		m.setMedia_id(inFileMsg.getMediaId());
		msg.setFile(m);

		ApiResult result = SendMessageApi.sendFileMsg(msg);
		logger.error(result.getJson());

		QyWeiXinConfig.eventBus.post(inFileMsg);
	}


}






