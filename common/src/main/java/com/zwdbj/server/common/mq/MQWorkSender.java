package com.zwdbj.server.common.mq;

import com.rabbitmq.client.MessageProperties;
import com.zwdbj.server.probuf.middleware.mq.QueueWorkInfoModel;

import java.io.IOException;

public class MQWorkSender extends MQConnection {


    @Override
    public void connect() {
        super.connect();
        try {
            if (this.channel != null) {
                this.channel.queueDeclare(MQConfig.queueTimeConsuming, true, false, false, null);
                this.logger.info("[MQ]连接到消息队列");
            }
        } catch (IOException ex) {
            this.logger.error("[MQ]"+ex.getMessage());
            this.logger.error("[MQ]"+ex.getStackTrace());
            asyncWaitAndReConnect();
        }
    }

    public void send(QueueWorkInfoModel.QueueWorkInfo workInfo) throws Exception {
        if (workInfo == null) return;
        if (this.channel == null) {
            throw new Exception("[MQ]连接异常");
        }
        this.channel.basicPublish("",MQConfig.queueTimeConsuming,MessageProperties.PERSISTENT_TEXT_PLAIN,workInfo.toByteArray());
    }

    private static volatile MQWorkSender workSender = null;
    public static MQWorkSender shareSender() {
        if (workSender==null) {
            synchronized (MQWorkSender.class) {
                if (workSender == null) {
                    workSender = new MQWorkSender();
                }
            }
        }
        return workSender;
    }

}