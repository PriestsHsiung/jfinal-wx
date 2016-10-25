package com.jfinal.qy.weixin.sdk.msg.send;

/**
 * 图片消息
 * @author Javen
 *
 */
public class QiYeImageMsg extends QiYeBaseMsg {
	private Media image;

	public Media getImage() {
		return image;
	}

	public void setImage(Media image) {
		this.image = image;
	}

//	public QiYeImageMsg(String mediaId) {
//		media_id = mediaId;
//		this.msgtype=MessageType.image.name();
//	}
	public QiYeImageMsg() {
		this.msgtype=MessageType.image.name();
	}
	
}
