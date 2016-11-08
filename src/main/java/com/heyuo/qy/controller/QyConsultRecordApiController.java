package com.heyuo.qy.controller;

import com.heyuo.qy.bo.api.PageInfo;
import com.heyuo.qy.bo.api.QyConsultRecord;
import com.heyuo.qy.service.QyConsultRecordService;
import com.jfinal.core.Controller;
import com.jfinal.kit.PropKit;
import com.jfinal.log.Log;

import java.util.Date;

/**
 * Created by Administrator on 2016-11-7.
 */
public class QyConsultRecordApiController extends Controller {
    static Log logger = Log.getLog(QyConsultController.class);

    private QyConsultRecordService qycrService = new QyConsultRecordService();

	public void index() {
        String serverToken = PropKit.get("serverToken");
        String token = getPara("token");
        if (null == token || !token.equals(serverToken)) {
            renderJson("{\"error\":\"invalid token\"}");
            return;
        }

        String qyName = getPara("qy");
        Date beg = getParaToDate("beg");
        Date end = getParaToDate("end");
        Integer pageNo = Integer.valueOf(getPara("pageNum", "1"));
        pageNo = 0 == pageNo ? 1 : pageNo;

        Integer pageSize = Integer.valueOf(getPara("pageSize", "10"));
        PageInfo<QyConsultRecord> qycrPage = qycrService.getRecords(qyName, beg, end, pageNo, pageSize);
        renderJson(qycrPage);
    }
}
