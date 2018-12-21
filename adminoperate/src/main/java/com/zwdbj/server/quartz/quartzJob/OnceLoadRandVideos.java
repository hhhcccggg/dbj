package com.zwdbj.server.quartz.quartzJob;

import com.zwdbj.server.quartz.quartzService.QuartzService;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public class OnceLoadRandVideos extends QuartzJobBean implements Serializable {
    @Autowired
    private QuartzService quartzService;
    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) {
        this.quartzService.onceLoadRandVideos();
    }
}
