package com.zwdbj.server.quartz.quartzJob;

import com.zwdbj.server.quartz.quartzService.QuartzService;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.io.Serializable;

public class VideosToRedisJob extends QuartzJobBean implements Serializable {
    private static final Long serialversionUID = 1L;
    @Autowired
    QuartzService quartzService;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext){

        this.quartzService.videosToRedis();
    }
}
