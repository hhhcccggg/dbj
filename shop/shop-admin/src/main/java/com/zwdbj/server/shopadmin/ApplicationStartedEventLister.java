package com.zwdbj.server.shopadmin;

import com.zwdbj.server.shop_admin_service.mq.MQWorkReceiverMgr;
import com.zwdbj.server.shop_admin_service.mq.MQWorkSender;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;

public class ApplicationStartedEventLister implements ApplicationListener<ApplicationStartedEvent> {

    @Override
    public void onApplicationEvent(ApplicationStartedEvent applicationStartedEvent) {
        MQWorkSender.shareSender().connect();
        MQWorkReceiverMgr.shareReceiverMgr().initReceivers(10);
    }
}
