package com.zwdbj.server.adminserver.service.quartzService.job;

import com.zwdbj.server.adminserver.service.quartzService.QuartzService;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.io.Serializable;

public class AddPetCateGoriesJob extends QuartzJobBean implements Serializable {
    private static final Long serialversionUID = 1L;
    @Autowired
    QuartzService quartzService;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext){
        this.quartzService.addPetCateGories();
    }
}
