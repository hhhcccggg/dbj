/*
package com.zwdbj.server.adminserver.middleware.mq;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.zwdbj.server.adminserver.service.shop.service.productOrder.service.ProductOrderService;
import com.zwdbj.server.probuf.middleware.mq.QueueWorkInfoModel;
import com.zwdbj.server.utility.common.SpringContextUtil;

import java.io.IOException;

public class DelayMQworkReceiver extends MQConnection{

    private DefaultConsumer consumer;
    @Override
    public void connect() {
        super.connect();
        try {
            if (this.channel != null) {
                this.channel.queueDeclare(MQConfig.delayedQueueTimeConsuming, true, false, false, null);
                this.channel.queueBind(MQConfig.delayedQueueTimeConsuming, MQConfig.delayedExchangeTimeMachine, MQConfig.delayedROUTING_KEY);
                this.channel.basicQos(0,1,false);
                this.initConsumer();
                this.logger.info("[DMQ]连接到消息队列");
            }
        } catch (IOException ex) {
            this.logger.error("[DMQ]"+ex.getMessage());
            this.logger.error("[DMQ]"+ex.getStackTrace());
            asyncWaitAndReConnect();
        }
    }
    private static volatile DelayMQworkReceiver receiverMgr = null;
    public static DelayMQworkReceiver shareReceiverMgr() {
        if (receiverMgr==null) {
            synchronized (DelayMQworkReceiver.class) {
                if (receiverMgr == null) {
                    receiverMgr = new DelayMQworkReceiver();
                }
            }
        }
        return receiverMgr;
    }

    @Override
    protected void asyncWaitAndReConnect() {
        super.asyncWaitAndReConnect();
    }

    protected void initConsumer() {
        this.getConsumer();
    }

    protected void getConsumer() {
        try {
            consumer = new DefaultConsumer(this.channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    logger.info("[DMQ]收到数据");
                    QueueWorkInfoModel.QueueWorkInfo workInfo = null;
                    try {
                        workInfo = QueueWorkInfoModel.QueueWorkInfo.parseFrom(body);
                    } catch (Exception ex) {
                        workInfo = null;
                        logger.info("[DMQ]解析数据出错"+ex.getMessage()+ex.getStackTrace());
                    } finally {
                        if (workInfo != null){
                            try {
                                processData(workInfo, envelope);
                            } catch (IOException ex) {
                                channel.basicAck(envelope.getDeliveryTag(),false);
                                logger.error("[DMQ]"+ex.getMessage());
                                logger.error("[DMQ]"+ex.getStackTrace());
                            }
                        } else {
                            channel.basicAck(envelope.getDeliveryTag(), false);
                        }
                    }
                }
            };
            this.channel.basicConsume(MQConfig.delayedQueueTimeConsuming, false, consumer);
        } catch (IOException ex) {
            this.logger.error("[DMQ]"+ex.getMessage());
            this.logger.error("[DMQ]"+ex.getStackTrace());
        }
    }

    protected void processData(QueueWorkInfoModel.QueueWorkInfo info,Envelope envelope) throws IOException {
        logger.info("[DMQ]收到数据类型:"+info.getWorkType());
        if (info.getWorkType()==QueueWorkInfoModel.QueueWorkInfo.WorkTypeEnum.USER_ORDER_TIME) {
            ProductOrderService orderService = SpringContextUtil.getBean(ProductOrderService.class);
            orderService.orderUnPay(info.getOrderTimeData().getOrderId(),info.getOrderTimeData().getUserId());
            logger.info("[DMQ]发送手机" + info.getPhoneCode().getPhone() + "的验证码" + info.getPhoneCode().getCode() + "成功");
            channel.basicAck(envelope.getDeliveryTag(), false);
        }else {
            logger.info("[DMQ]收到数据类型:"+info.getWorkType()+"后端暂时没有合适的服务处理");
            channel.basicAck(envelope.getDeliveryTag(),false);
        }

    }
}
*/
