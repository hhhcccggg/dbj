package com.zwdbj.server.adminserver.service.quartzService;

import com.zwdbj.server.adminserver.service.quartzService.job.*;
import com.zwdbj.server.utility.common.SpringContextUtil;
import org.quartz.*;
import org.quartz.impl.StdScheduler;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;


@Component
public class MyScheduler {

    public void scheduleJobs() throws SchedulerException {
        ApplicationContext annotationContext = SpringContextUtil.getApplicationContext();
        StdScheduler stdScheduler = (StdScheduler) annotationContext.getBean("mySchedulerFactoryBean");//获得上面创建的bean
        Scheduler myScheduler =stdScheduler;
        JobKey jobKey1  = new JobKey("job1", "group01");
        JobKey jobKey2  = new JobKey("job2", "group02");
        JobKey jobKey3  = new JobKey("job3", "group03");
        JobKey jobKey4  = new JobKey("job4", "group04");
        JobKey jobKey5  = new JobKey("job5", "group05");
        JobKey jobKey6  = new JobKey("job6", "group06");
        JobKey jobKey7  = new JobKey("job7", "group07");
        JobKey jobKey8  = new JobKey("job8", "group08");
        if (!myScheduler.checkExists(jobKey1)) startJob1(myScheduler);
        if (!myScheduler.checkExists(jobKey2)) startJob2(myScheduler);
        if (!myScheduler.checkExists(jobKey3)) startJob3(myScheduler);
        if (!myScheduler.checkExists(jobKey4)) startJob4(myScheduler);
        if (!myScheduler.checkExists(jobKey5)) startJob5(myScheduler);
        if (!myScheduler.checkExists(jobKey6)) startJob6(myScheduler);
        //if (!myScheduler.checkExists(jobKey7)) startJob7(myScheduler);
        if (!myScheduler.checkExists(jobKey8)) startJob8(myScheduler);
        myScheduler.start();

    }
    private void startJob1(Scheduler scheduler) throws SchedulerException{
        JobDetail jobDetail = JobBuilder.newJob(IncreaseHeartAndPlayCountJob.class)
                .withIdentity("job1", "group01")
                .build();
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule("0 0/12 8-23 * * ?");
        CronTrigger cronTrigger = TriggerBuilder.newTrigger()
                .withIdentity("trigger1", "group1")
                .withSchedule(scheduleBuilder)
                .build();
        scheduler.scheduleJob(jobDetail,cronTrigger);
    }


    private void startJob2(Scheduler scheduler) throws SchedulerException{
        JobDetail jobDetail = JobBuilder.newJob(EverydayInsertTimeJob.class)
                .withIdentity("job2", "group02")
                .build();
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule("0 0 5 * * ?");
        CronTrigger cronTrigger = TriggerBuilder.newTrigger().withIdentity("trigger2", "group1")
                .withSchedule(scheduleBuilder).build();
        scheduler.scheduleJob(jobDetail,cronTrigger);
    }

    private void startJob3(Scheduler scheduler) throws SchedulerException{
        JobDetail jobDetail = JobBuilder.newJob(EveryIncreasedUsersAndVideos.class)
                .withIdentity("job3", "group03")
                .build();
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule("0 0 3 * * ?");
        CronTrigger cronTrigger = TriggerBuilder.newTrigger().withIdentity("trigger3", "group1")
                .withSchedule(scheduleBuilder).build();
        scheduler.scheduleJob(jobDetail,cronTrigger);
    }

    private void startJob4(Scheduler scheduler) throws SchedulerException{
        JobDetail jobDetail = JobBuilder.newJob(CommentReviewJob.class)
                .withIdentity("job4", "group04")
                .build();
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule("0/20 * * * * ?");
        CronTrigger cronTrigger = TriggerBuilder.newTrigger().withIdentity("trigger4", "group1")
                .withSchedule(scheduleBuilder).build();
        scheduler.scheduleJob(jobDetail,cronTrigger);
    }

    private void startJob5(Scheduler scheduler) throws SchedulerException{
        JobDetail jobDetail = JobBuilder.newJob(UserAllCountJob.class)
                .withIdentity("job5", "group05")
                .build();
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule("0 0/3 * * * ?");
        CronTrigger cronTrigger = TriggerBuilder.newTrigger().withIdentity("trigger5", "group1")
                .withSchedule(scheduleBuilder).build();
        scheduler.scheduleJob(jobDetail,cronTrigger);
    }

    private void startJob6(Scheduler scheduler) throws SchedulerException{
        JobDetail jobDetail = JobBuilder.newJob(DeleteResourceNeedReviewJob.class)
                .withIdentity("job6", "group06")
                .build();
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule("0 0 4 * * ?");
        CronTrigger cronTrigger = TriggerBuilder.newTrigger().withIdentity("trigger6", "group6")
                .withSchedule(scheduleBuilder).build();
        scheduler.scheduleJob(jobDetail,cronTrigger);
    }
    private void startJob7(Scheduler scheduler) throws SchedulerException{
        JobDetail jobDetail = JobBuilder.newJob(IncreaseHeartAndPlayCountJob.class)
                .withIdentity("job7", "group07")
                .build();
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule("0 0 0-2,10-23 * * ?");
        CronTrigger cronTrigger = TriggerBuilder.newTrigger()
                .withIdentity("trigger7", "group7")
                .withSchedule(scheduleBuilder)
                .build();
        scheduler.scheduleJob(jobDetail,cronTrigger);
    }
    private void startJob8(Scheduler scheduler) throws SchedulerException{
        JobDetail jobDetail = JobBuilder.newJob(GreatVestUserJob.class)
                .withIdentity("job8", "group08")
                .build();
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule("0 25 5 * * ?");
        CronTrigger cronTrigger = TriggerBuilder.newTrigger()
                .withIdentity("trigger8", "group7")
                .withSchedule(scheduleBuilder)
                .build();
        scheduler.scheduleJob(jobDetail,cronTrigger);
    }

}
