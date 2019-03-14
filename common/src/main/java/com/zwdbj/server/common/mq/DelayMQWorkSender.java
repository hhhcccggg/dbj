package com.zwdbj.server.common.mq;

import com.rabbitmq.client.AMQP;
import com.zwdbj.server.probuf.middleware.mq.QueueWorkInfoModel;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class DelayMQWorkSender extends MQConnection{
    @Override
    public void connect() {
        super.connect();
        try {
            if (this.channel != null) {
                this.channel.queueDeclare(MQConfig.delayedQueueTimeConsuming, true, false, false, null);
                // 声明x-delayed-type类型的exchange
                Map<String, Object> args = new HashMap<>();
                args.put("x-delayed-type", "direct");
                this.channel.exchangeDeclare(MQConfig.delayedExchangeTimeMachine, "x-delayed-message", true, false, args);
                this.logger.info("[DMQ]连接到消息队列");
            }
        } catch (IOException ex) {
            this.logger.error("[DMQ]"+ex.getMessage());
            this.logger.error("[DMQ]"+ex.getStackTrace());
            asyncWaitAndReConnect();
        }
    }

/**
 *
 * @param workInfo
 * @param times ,延迟时间,单位为秒
 * @throws Exception
 */

    public void send(QueueWorkInfoModel.QueueWorkInfo workInfo,long times) throws Exception {
        if (workInfo == null) return;
        if (this.channel == null) {
            throw new Exception("[DMQ]连接异常");
        }
        Map<String, Object> headers = new HashMap<>();

        headers.put("x-delay", times*1000);

        AMQP.BasicProperties.Builder props = new AMQP.BasicProperties.Builder().headers(headers);
        this.channel.basicPublish(MQConfig.delayedExchangeTimeMachine, MQConfig.delayedROUTING_KEY, props.build(), workInfo.toByteArray());

    }

    private static volatile DelayMQWorkSender workSender = null;
    public static DelayMQWorkSender shareSender() {
        if (workSender==null) {
            synchronized (DelayMQWorkSender.class) {
                if (workSender == null) {
                    workSender = new DelayMQWorkSender();
                }
            }
        }
        return workSender;
    }
}