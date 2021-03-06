package com.zwdbj.server.mobileapi;

import com.zwdbj.server.common.mq.DelayMQWorkSender;
import com.zwdbj.server.common.mq.MQWorkSender;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;

public class ApplicationStartedEventLister implements ApplicationListener<ApplicationStartedEvent> {

    @Override
    public void onApplicationEvent(ApplicationStartedEvent applicationStartedEvent) {
        MQWorkSender.shareSender().connect();
        DelayMQWorkSender.shareSender().connect();
    }
}
