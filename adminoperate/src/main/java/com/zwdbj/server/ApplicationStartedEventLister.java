package com.zwdbj.server;

import com.zwdbj.server.operate.oprateService.OperateService;
import com.zwdbj.server.utility.common.SpringContextUtil;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.data.redis.core.StringRedisTemplate;

public class ApplicationStartedEventLister implements ApplicationListener<ApplicationStartedEvent> {

    @Override
    public void onApplicationEvent(ApplicationStartedEvent applicationStartedEvent) {
        OperateService operateService = SpringContextUtil.getBean(OperateService.class);
        StringRedisTemplate stringRedisTemplate = SpringContextUtil.getBean(StringRedisTemplate.class);
        operateService.getRedisComment();
        operateService.userNumber();
        operateService.videoNumber();
        if (!stringRedisTemplate.hasKey("OPERATE_ALL_VIDEO_NUM")){
            operateService.allVideoNum();
            System.out.println("allVideoNum");
        }
    }
}
