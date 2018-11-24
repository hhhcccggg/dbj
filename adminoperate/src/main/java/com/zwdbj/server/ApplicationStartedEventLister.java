package com.zwdbj.server;

import com.zwdbj.server.operate.oprateService.OperateService;
import com.zwdbj.server.utility.common.SpringContextUtil;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;

public class ApplicationStartedEventLister implements ApplicationListener<ApplicationStartedEvent> {

    @Override
    public void onApplicationEvent(ApplicationStartedEvent applicationStartedEvent) {
        OperateService operateService = SpringContextUtil.getBean(OperateService.class);
        operateService.getRedisComment();
    }
}
