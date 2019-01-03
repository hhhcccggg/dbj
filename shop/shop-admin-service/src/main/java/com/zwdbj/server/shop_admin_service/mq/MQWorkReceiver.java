package com.zwdbj.server.shop_admin_service.mq;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.zwdbj.server.probuf.middleware.mq.QueueWorkInfoModel;
import com.zwdbj.server.shop_admin_service.service.legalSubject.service.ILegalSubjectService;
import com.zwdbj.server.shop_admin_service.service.legalSubject.service.LegalSubjectServiceImpl;
import com.zwdbj.server.utility.common.SpringContextUtil;

import java.io.IOException;

public class MQWorkReceiver extends MQConnection {

    private DefaultConsumer consumer;

    @Override
    public void connect() {
        super.connect();
        try {
            if (this.channel != null) {
                this.channel.queueDeclare(MQConfig.queueTimeConsuming, true, false, false, null);
                this.channel.basicQos(0,1,false);
                this.initConsumer();
                this.logger.info("[MQ]连接到消息队列");
            }
        } catch (IOException ex) {
            this.logger.error("[MQ]"+ex.getMessage());
            this.logger.error("[MQ]"+ex.getStackTrace());
            asyncWaitAndReConnect();
        }
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
                    logger.info("[MQ]收到数据");
                    QueueWorkInfoModel.QueueWorkInfo workInfo = null;
                    try {
                        workInfo = QueueWorkInfoModel.QueueWorkInfo.parseFrom(body);
                    } catch (Exception ex) {
                        workInfo = null;
                        logger.info("[MQ]解析数据出错"+ex.getMessage()+ex.getStackTrace());
                    } finally {
                        if (workInfo != null){
                            try {
                                processData(workInfo, envelope);
                            } catch (IOException ex) {
                                channel.basicAck(envelope.getDeliveryTag(),false);
                                logger.error("[MQ]"+ex.getMessage());
                                logger.error("[MQ]"+ex.getStackTrace());
                            }
                        } else {
                            channel.basicAck(envelope.getDeliveryTag(), false);
                        }
                    }
                }
            };
            this.channel.basicConsume(MQConfig.queueTimeConsuming, false, consumer);
        } catch (IOException ex) {
            this.logger.error("[MQ]"+ex.getMessage());
            this.logger.error("[MQ]"+ex.getStackTrace());
        }
    }

    protected void processData(QueueWorkInfoModel.QueueWorkInfo info,Envelope envelope) throws IOException {
        logger.info("[MQ]收到数据类型:"+info.getWorkType());
        if (info.getWorkType()==QueueWorkInfoModel.QueueWorkInfo.WorkTypeEnum.SHOP_LEGAL_SUBJECT) {
            ILegalSubjectService legalSubjectServiceImpl = SpringContextUtil.getBean(LegalSubjectServiceImpl.class);
            legalSubjectServiceImpl.handleLegalSubject(info.getShopLegalSubjectData());
            logger.info("[MQ]接收店铺和店主的信息返回的结果");
            channel.basicAck(envelope.getDeliveryTag(),false);
        }

    }
}
