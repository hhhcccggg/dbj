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
        JobKey jobKey2  = new JobKey("job2", "group01");
        JobKey jobKey3  = new JobKey("job3", "group01");
        JobKey jobKey4  = new JobKey("job4", "group01");
        JobKey jobKey5  = new JobKey("job5", "group01");
        if (!myScheduler.checkExists(jobKey1)) startJob1(myScheduler);
        if (!myScheduler.checkExists(jobKey2)) startJob2(myScheduler);
        if (!myScheduler.checkExists(jobKey3)) startJob3(myScheduler);
        if (!myScheduler.checkExists(jobKey4)) startJob4(myScheduler);
        if (!myScheduler.checkExists(jobKey5)) startJob5(myScheduler);
        myScheduler.start();

    }


    private void startJob1(Scheduler scheduler) throws SchedulerException{
        JobDetail jobDetail = JobBuilder.newJob(EverydayInsertTimeJob.class)
                .withIdentity("job1", "group01")
                .build();
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule("0 0 5 * * ?");
        CronTrigger cronTrigger = TriggerBuilder.newTrigger().withIdentity("trigger1", "group1")
                .withSchedule(scheduleBuilder).build();
        scheduler.scheduleJob(jobDetail,cronTrigger);
    }

    private void startJob2(Scheduler scheduler) throws SchedulerException{
        JobDetail jobDetail = JobBuilder.newJob(EveryIncreasedUsersAndVideos.class)
                .withIdentity("job2", "group01")
                .build();
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule("0 0 3 * * ?");
        CronTrigger cronTrigger = TriggerBuilder.newTrigger().withIdentity("trigger2", "group1")
                .withSchedule(scheduleBuilder).build();
        scheduler.scheduleJob(jobDetail,cronTrigger);
    }

    private void startJob3(Scheduler scheduler) throws SchedulerException{
        JobDetail jobDetail = JobBuilder.newJob(CommentReviewJob.class)
                .withIdentity("job3", "group01")
                .build();
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule("0/20 * * * * ?");
        CronTrigger cronTrigger = TriggerBuilder.newTrigger().withIdentity("trigger3", "group1")
                .withSchedule(scheduleBuilder).build();
        scheduler.scheduleJob(jobDetail,cronTrigger);
    }

    private void startJob4(Scheduler scheduler) throws SchedulerException{
        JobDetail jobDetail = JobBuilder.newJob(UserAllCountJob.class)
                .withIdentity("job4", "group01")
                .build();
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule("0 0/3 * * * ?");
        CronTrigger cronTrigger = TriggerBuilder.newTrigger().withIdentity("trigger4", "group1")
                .withSchedule(scheduleBuilder).build();
        scheduler.scheduleJob(jobDetail,cronTrigger);
    }

    private void startJob5(Scheduler scheduler) throws SchedulerException{
        JobDetail jobDetail = JobBuilder.newJob(DeleteResourceNeedReviewJob.class)
                .withIdentity("job5", "group01")
                .build();
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule("0 0 4 * * ?");
        CronTrigger cronTrigger = TriggerBuilder.newTrigger().withIdentity("trigger5", "group1")
                .withSchedule(scheduleBuilder).build();
        scheduler.scheduleJob(jobDetail,cronTrigger);
    }

}
