package com.xiongl.weixin.service;

import com.heyuo.qy.model.ConsultRecords;

import java.util.Calendar;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.quartz.*;

/**
 * Created by Administrator on 2016-11-3.
 */
public class DownloadJob implements Job {
    private static final Logger LOGGER = LoggerFactory.getLogger(DownloadJob.class);

    public void execute(JobExecutionContext ctx) {
        Calendar cal = Calendar.getInstance();
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        if (hour >= 1 && hour <= 3) {
            LOGGER.info("定时下载任务启动...");
            List<ConsultRecords> crList =
                    ConsultRecords.dao.find("select * from consult_records" +
                            "where crawl = 0 and (type = 'image' or type = 'voice' or type = 'file')" +
                            "order by date desc");

            DownloadService ds = new DownloadService();
            Integer count = 0;
            for (ConsultRecords cr : crList) {
                ds.download(cr.getType(), cr.getMsgId(), cr.getContent());
                count++;

                if (count > 200) {
                    hour = cal.get(Calendar.HOUR_OF_DAY);
                    if (hour >= 7) {
                        LOGGER.info("定时下载任务强制结束...");
                        break;
                    }
                }
            }
            LOGGER.info("定时下载任务结束...");
        }
    }
}
