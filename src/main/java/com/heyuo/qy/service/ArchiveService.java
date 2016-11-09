package com.heyuo.qy.service;

import com.heyuo.qy.model.ConsultRecords;

/**
 * Created by Administrator on 2016-10-31.
 */
public class ArchiveService {
    public void archive(String fromUser, String toUser, String type, String msgId,
                        String content, Boolean forbidden, Boolean sendSuccess,
                        Boolean isKf) {
        ConsultRecords cr = new ConsultRecords();
        cr.setFromUser(fromUser);
        cr.setToUser(toUser);
        cr.setType(type);
        cr.setMsgId(msgId);
        cr.setContent(content);
        cr.setForbidden(forbidden);
        cr.setSendSuccess(sendSuccess);
        cr.setFromUserKf(isKf);
        cr.save();
    }
}
