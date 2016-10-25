package com.jfinal.qy.weixin.sdk.msg.send;

/**
 * 文件消息
 * @author Javen
 *
 */
public class QiYeFileMsg extends QiYeBaseMsg {
	private Media file;

	public Media getFile() {
		return file;
	}

	public void setFile(Media file) {
		this.file = file;
	}

	//	public QiYeImageMsg(String mediaId) {
//		media_id = mediaId;
//		this.msgtype=MessageType.image.name();
//	}
	public QiYeFileMsg() {
		this.msgtype=MessageType.file.name();
	}
}
