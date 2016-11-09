package com.heyuo.qy.service;

import com.heyuo.qy.bo.api.PageInfo;
import com.heyuo.qy.bo.api.QyConsultRecord;
import com.heyuo.qy.model.ConsultRecords;
import com.heyuo.qy.model.MsgMedia;
import com.heyuo.qy.model.QyLevel;
import com.jfinal.plugin.activerecord.Page;
import org.apache.commons.lang.StringUtils;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Administrator on 2016-11-7.
 */
public class QyConsultRecordService {
    private String concateIn(List<QyLevel> qyLevelList) {
        StringBuilder sb = new StringBuilder();
        for (QyLevel qyLevel : qyLevelList) {
            sb.append("\"").append(qyLevel.getWxUser()).append("\"").append(",");
        }
        sb.deleteCharAt(sb.length() - 1);

        return sb.toString();
    }

    public PageInfo<QyConsultRecord> getRecords(String qyName, Date beg, Date end,
                                                Integer pageNum, Integer pageSize) {
        StringBuilder sb = new StringBuilder();
        if (StringUtils.isNotBlank(qyName)) {
            List<QyLevel> qyLevelList = QyLevel.dao.find("select * from qy_level where qy like " + "\"%" + qyName + "%\"");
            if (!qyLevelList.isEmpty()) {
                sb.append("(");

                sb.append("from_user in (");
                sb.append(concateIn(qyLevelList)).append(")");

                sb.append(" or ");

                sb.append("to_user in (");
                sb.append(concateIn(qyLevelList)).append(")");

                sb.append(")");
            } else {
                PageInfo<QyConsultRecord> p = new PageInfo<QyConsultRecord>();
                p.setData(Collections.EMPTY_LIST);
                p.setTotal(0);
                p.setPageTotal(0);
                p.setPageSize(pageSize);
                p.setPageNum(pageNum);
                return p;
            }
        }

        if (null != beg || null != end) {
            if (sb.length() > 0) {
                sb.append(" and ");
            }

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            if (null != beg) {
                sb.append(" date >='").append(sdf.format(beg)).append("'");
            }

            if (null != end) {
                if (null != beg) {
                    sb.append(" and ");
                }
                Calendar c = Calendar.getInstance();
                c.setTime(end);
                c.set(Calendar.DATE, c.get(Calendar.DATE) + 1);
                end = c.getTime();
                sb.append(" date <='").append(sdf.format(end)).append("'");
            }
        }

        String sqlExpect = "from consult_records";
        if (sb.length() > 0) {
            sqlExpect += " where ";
            sqlExpect += sb.toString();
        }

        Long begTime = System.currentTimeMillis();
        Long endTime;

        Page<ConsultRecords> crPage
                = ConsultRecords.dao.paginate(pageNum, pageSize, "select * ", sqlExpect);
        endTime = System.currentTimeMillis();
        System.out.println("ConsultRecords.dao.paginate cost:" + (endTime - begTime));

        List<ConsultRecords> crList = crPage.getList();
        PageInfo<QyConsultRecord> qycrPage = new PageInfo<QyConsultRecord>();
        qycrPage.setTotal(crPage.getTotalRow());
        qycrPage.setPageTotal(crPage.getTotalPage());
        qycrPage.setPageNum(crPage.getPageNumber());
        qycrPage.setPageSize(crPage.getPageSize());

        List<QyConsultRecord> qycrList = new LinkedList<QyConsultRecord>();
        qycrPage.setData(qycrList);

        begTime = System.currentTimeMillis();
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
        endTime = System.currentTimeMillis();
        System.out.println("ConsultRecords assemble cost:" + (endTime - begTime));

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
