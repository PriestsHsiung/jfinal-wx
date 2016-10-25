package com.jfinal.qy.weixin.sdk.msg.send;

/**
 * 语音消息
 * @author Javen
 *
 */
public class QiYeVoiceMsg extends QiYeBaseMsg {
	/**
	 * 媒体资源文件 ID
	 */
	private Media voice;

	public Media getVoice() {
		return voice;
	}

	public void setVoice(Media voice) {
		this.voice = voice;
	}

	public QiYeVoiceMsg() {
		this.msgtype=MessageType.voice.name();
	}
	
}
