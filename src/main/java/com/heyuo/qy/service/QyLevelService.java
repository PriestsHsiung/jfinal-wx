package com.heyuo.qy.service;

import com.heyuo.qy.model.QyLevel;

/**
 * Created by Administrator on 2016-10-26.
 */
public class QyLevelService {
    public Boolean canSendText(String userId) {
        QyLevel level = QyLevel.dao.findById(userId);
        return null != level ? Boolean.TRUE : Boolean.FALSE;
    }

    public Boolean canSendImage(String userId) {
        QyLevel level = QyLevel.dao.findById(userId);
        return (null != level && 2 == level.getLevel())? Boolean.TRUE : Boolean.FALSE;
    }

    public Boolean canSendVoice(String userId) {
        QyLevel level = QyLevel.dao.findById(userId);
        return (null != level) ? Boolean.TRUE : Boolean.FALSE;
    }

    public Integer getLeve(String userId) {
        QyLevel level = QyLevel.dao.findById(userId);
        return (null != level) ? level.getLevel() : 0;
    }
}
