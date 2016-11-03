package com.xiongl.weixin.service;

import org.quartz.*;
import org.quartz.impl.JobDetailImpl;
import org.quartz.impl.StdSchedulerFactory;

import java.text.ParseException;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * Created by Administrator on 2016-11-3.
 */
public class JobService {
    private static SchedulerFactory sf = new StdSchedulerFactory();

    public static void addJob(String jobName, String jobGroupName,
                              String triggerName, String triggerGroupName,
                              Class<? extends Job> jobClass, String time) throws SchedulerException, ParseException {
        Scheduler sched = sf.getScheduler();
        JobDetail jobDetail = newJob(jobClass).withIdentity(jobName, jobGroupName).build();

        //触发器
        CronTrigger trigger = newTrigger().withIdentity(triggerName, triggerGroupName)
                            .withSchedule(cronSchedule(time)).build();
        sched.scheduleJob(jobDetail,trigger);
        if(!sched.isShutdown()) {
            sched.start();
        }
    }
}
