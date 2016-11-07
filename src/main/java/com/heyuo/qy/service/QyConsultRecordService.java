package com.heyuo.qy.service;

import com.heyuo.qy.bo.api.PageInfo;
import com.heyuo.qy.bo.api.QyConsultRecord;
import com.heyuo.qy.model.ConsultRecords;
import com.heyuo.qy.model.MsgMedia;
import com.heyuo.qy.model.QyLevel;
import com.jfinal.plugin.activerecord.Page;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Administrator on 2016-11-7.
 */
public class QyConsultRecordService {
    public PageInfo<QyConsultRecord> getRecords(Integer pageNum, Integer pageSize) {
        Page<ConsultRecords> crPage
                = ConsultRecords.dao.paginate(pageNum, pageSize, "select * ", "from consult_records");

        List<ConsultRecords> crList = crPage.getList();
        PageInfo<QyConsultRecord> qycrPage = new PageInfo<QyConsultRecord>();
        qycrPage.setTotal(crPage.getTotalRow());
        qycrPage.setPageTotal(crPage.getTotalPage());
        qycrPage.setPageNum(crPage.getPageNumber());
        qycrPage.setPageSize(crPage.getPageSize());

        List<QyConsultRecord> qycrList = new LinkedList<QyConsultRecord>();
        qycrPage.setData(qycrList);
        for (ConsultRecords cr : crList) {
            QyConsultRecord qycr = new QyConsultRecord();
            if (cr.getFromUserKf()) {
                qycr.setSender(cr.getFromUser());
                qycr.setReceiver(getName(cr.getToUser()));
            } else {
                qycr.setSender(getName(cr.getFromUser()));
                qycr.setReceiver(cr.getToUser());
            }

            qycr.setMsgId(cr.getMsgId());
            qycr.setDate(cr.getDate());
            qycr.setType(convertType(cr.getType()));
            qycr.setContent(cr.getContent());

            qycr.setUrl(getUrl(cr.getMsgId()));

            qycrList.add(qycr);
        }

        return qycrPage;
    }

    private String getName(String id) {
        QyLevel ql = QyLevel.dao.findFirst("select * from qy_level where wx_user = ?", id);
        return null == ql ? "" : ql.getQy();
    }

    private String getUrl(String msgId) {
        MsgMedia mm = MsgMedia.dao.findById(msgId);
        return null != mm ? mm.getUrlPath() : "";
    }

    private String convertType(String type) {
        String s = "";
        if ("text".equals(type)) {
            s = "文本";
        }
        else if ("image".equals(type)) {
            s = "图片";
        }
        else if ("voice".equals(type)) {
            s = "语音";
        }
        else if ("file".equals(type)) {
            s = "文件";
        }

        return s;
    }
}
