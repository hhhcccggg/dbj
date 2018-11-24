package com.zwdbj.server.quartz.quartzConfig;

import com.zwdbj.server.quartz.quartzJob.GreatVestUser2Job;
import com.zwdbj.server.quartz.quartzJob.IncreaseHeartAndPlayCountJob;
import com.zwdbj.server.quartz.quartzJob.VideosToUsersJob;
import com.zwdbj.server.quartz.quartzJob.MyFollowersJob;
import com.zwdbj.server.utility.common.SpringContextUtil;
import org.quartz.*;
import org.quartz.impl.StdScheduler;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;


@Component
public class MyScheduler {

    public void scheduleJobs() throws SchedulerException {
        ApplicationContext annotationContext = SpringContextUtil.getApplicationContext();
        StdScheduler stdScheduler = (StdScheduler) annotationContext.getBean("mySchedulerFactoryBean2");//获得上面创建的bean
        Scheduler myScheduler =stdScheduler;
        JobKey jobKey1  = new JobKey("job1", "group02");
        JobKey jobKey2  = new JobKey("job2", "group02");
        JobKey jobKey3  = new JobKey("job3", "group02");
        JobKey jobKey4  = new JobKey("job4", "group02");
        if (!myScheduler.checkExists(jobKey1)) startJob1(myScheduler);
        if (!myScheduler.checkExists(jobKey2)) startJob2(myScheduler);
        if (!myScheduler.checkExists(jobKey3)) startJob3(myScheduler);
        if (!myScheduler.checkExists(jobKey4)) startJob4(myScheduler);
        myScheduler.start();

    }
    private void startJob1(Scheduler scheduler) throws SchedulerException{
        JobDetail jobDetail = JobBuilder.newJob(IncreaseHeartAndPlayCountJob.class)
                .withIdentity("job1", "group02")
                .build();
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule("0 0/12 8-23 * * ?");
        CronTrigger cronTrigger = TriggerBuilder.newTrigger()
                .withIdentity("trigger1", "group2")
                .withSchedule(scheduleBuilder)
                .build();
        scheduler.scheduleJob(jobDetail,cronTrigger);
    }


    private void startJob2(Scheduler scheduler) throws SchedulerException{
        JobDetail jobDetail = JobBuilder.newJob(GreatVestUser2Job.class)
                .withIdentity("job2", "group02")
                .build();
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule("0 5 1 * * ?");
        CronTrigger cronTrigger = TriggerBuilder.newTrigger()
                .withIdentity("trigger2", "group2")
                .withSchedule(scheduleBuilder)
                .build();
        scheduler.scheduleJob(jobDetail,cronTrigger);
    }
    private void startJob3(Scheduler scheduler) throws SchedulerException{
        JobDetail jobDetail = JobBuilder.newJob(MyFollowersJob.class)
                .withIdentity("job3", "group02")
                .build();
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule("0 25 1 * * ?");
        CronTrigger cronTrigger = TriggerBuilder.newTrigger()
                .withIdentity("trigger3", "group2")
                .withSchedule(scheduleBuilder)
                .build();
        scheduler.scheduleJob(jobDetail,cronTrigger);
    }
    private void startJob4(Scheduler scheduler) throws SchedulerException{
        JobDetail jobDetail = JobBuilder.newJob(VideosToUsersJob.class)
                .withIdentity("job4", "group02")
                .build();
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule("0 5 6 * * ?");
        CronTrigger cronTrigger = TriggerBuilder.newTrigger()
                .withIdentity("trigger4", "group2")
                .withSchedule(scheduleBuilder)
                .build();
        scheduler.scheduleJob(jobDetail,cronTrigger);
    }

}
