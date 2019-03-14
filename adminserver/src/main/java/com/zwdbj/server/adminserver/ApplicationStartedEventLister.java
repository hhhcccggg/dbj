package com.zwdbj.server.adminserver;

import com.zwdbj.server.adminserver.identity.UserIdentityInit;
//import com.zwdbj.server.adminserver.middleware.mq.DelayMQworkReceiver;
import com.zwdbj.server.adminserver.middleware.mq.DelayMQworkReceiver;
import com.zwdbj.server.adminserver.middleware.mq.MQWorkReceiverMgr;
import com.zwdbj.server.adminserver.service.setting.service.SettingService;
import com.zwdbj.server.common.mq.DelayMQWorkSender;
import com.zwdbj.server.common.mq.MQWorkSender;
import com.zwdbj.server.utility.common.SpringContextUtil;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;

public class ApplicationStartedEventLister implements ApplicationListener<ApplicationStartedEvent> {

    @Override
    public void onApplicationEvent(ApplicationStartedEvent applicationStartedEvent) {
        MQWorkSender.shareSender().connect();
        DelayMQWorkSender.shareSender().connect();
        DelayMQworkReceiver.shareReceiverMgr().connect();
        MQWorkReceiverMgr.shareReceiverMgr().initReceivers(10);
        UserIdentityInit userIdentityInit = SpringContextUtil.getBean(UserIdentityInit.class);
        userIdentityInit.init();
        SettingService settingService = SpringContextUtil.getBean(SettingService.class);
        settingService.initUserSetting();
    }
}
