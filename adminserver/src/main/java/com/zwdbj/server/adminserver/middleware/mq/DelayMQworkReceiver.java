package com.zwdbj.server.adminserver.middleware.mq;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.zwdbj.server.adminserver.service.shop.service.productOrder.service.ProductOrderService;
import com.zwdbj.server.adminserver.service.shop.service.store.service.StoreServiceImpl;
import com.zwdbj.server.adminserver.service.video.service.VideoService;
import com.zwdbj.server.common.mq.MQConfig;
import com.zwdbj.server.common.mq.MQConnection;
import com.zwdbj.server.es.common.ESIndex;
import com.zwdbj.server.es.service.ESUtilService;
import com.zwdbj.server.probuf.middleware.mq.QueueWorkInfoModel;
import com.zwdbj.server.utility.common.SpringContextUtil;
import org.apache.commons.beanutils.BeanMap;

import java.io.IOException;

public class DelayMQworkReceiver extends MQConnection {

    private DefaultConsumer consumer;

    @Override
    public void connect() {
        super.connect();
        try {
            if (this.channel != null) {
                this.channel.queueDeclare(MQConfig.delayedQueueTimeConsuming, true, false, false, null);
                this.channel.queueBind(MQConfig.delayedQueueTimeConsuming, MQConfig.delayedExchangeTimeMachine, MQConfig.delayedROUTING_KEY);
                this.channel.basicQos(0, 1, false);
                this.initConsumer();
                this.logger.info("[DMQ]连接到消息队列");
            }
        } catch (IOException ex) {
            this.logger.error("[DMQ]" + ex.getMessage());
            this.logger.error("[DMQ]" + ex.getStackTrace());
            asyncWaitAndReConnect();
        }
    }

    private static volatile DelayMQworkReceiver receiverMgr = null;

    public static DelayMQworkReceiver shareReceiverMgr() {
        if (receiverMgr == null) {
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
                        logger.info("[DMQ]解析数据出错" + ex.getMessage() + ex.getStackTrace());
                    } finally {
                        if (workInfo != null) {
                            try {
                                processData(workInfo, envelope);
                            } catch (IOException ex) {
                                channel.basicAck(envelope.getDeliveryTag(), false);
                                logger.error("[DMQ]" + ex.getMessage());
                                logger.error("[DMQ]" + ex.getStackTrace());
                            }
                        } else {
                            channel.basicAck(envelope.getDeliveryTag(), false);
                        }
                    }
                }
            };
            this.channel.basicConsume(MQConfig.delayedQueueTimeConsuming, false, consumer);
        } catch (IOException ex) {
            this.logger.error("[DMQ]" + ex.getMessage());
            this.logger.error("[DMQ]" + ex.getStackTrace());
        }
    }

    protected void processData(QueueWorkInfoModel.QueueWorkInfo info, Envelope envelope) throws IOException {
        logger.info("[DMQ]收到数据类型:" + info.getWorkType());
        if (info.getWorkType() == QueueWorkInfoModel.QueueWorkInfo.WorkTypeEnum.USER_ORDER_TIME) {
            ProductOrderService orderService = SpringContextUtil.getBean(ProductOrderService.class);
            orderService.orderUnPay(info.getOrderTimeData().getOrderId(), info.getOrderTimeData().getUserId());
            logger.info("[DMQ]处理订单" + info.getOrderTimeData().getOrderId() + ",用户" + info.getOrderTimeData().getUserId());
            channel.basicAck(envelope.getDeliveryTag(), false);
        } else if (info.getWorkType() == QueueWorkInfoModel.QueueWorkInfo.WorkTypeEnum.USER_ORDER_COMMENT_TIME) {
            ProductOrderService orderService = SpringContextUtil.getBean(ProductOrderService.class);
            if (info.getOrderCommentTimeData().getType()==1){
                orderService.orderUnComment(info.getOrderCommentTimeData().getOrderId());
                logger.info("[DMQ]处理订单评价" + info.getOrderTimeData().getOrderId());
                channel.basicAck(envelope.getDeliveryTag(), false);
            }else if (info.getOrderCommentTimeData().getType()==2){
                orderService.deliverOrderByUser(info.getOrderCommentTimeData().getOrderId());
                logger.info("[DMQ]处理订单收货" + info.getOrderTimeData().getOrderId());
                channel.basicAck(envelope.getDeliveryTag(), false);
            }

        } else if (info.getWorkType() == QueueWorkInfoModel.QueueWorkInfo.WorkTypeEnum.ES_ADMIN_INFO) {
            if (info.getEsAdminInfo().getType().equals(ESIndex.VIDEO)) {
                VideoService videoService = SpringContextUtil.getBean(VideoService.class);
                videoService.operationByIdES(info.getEsAdminInfo().getId(), info.getEsAdminInfo().getAction(), channel, envelope);
                //确认消费
                channel.basicAck(envelope.getDeliveryTag(),false);
            } else if (info.getEsAdminInfo().getType().equals(ESIndex.SHOP) ) {
                logger.info("shop店铺:"+info.getEsAdminInfo().getId());
                StoreServiceImpl storeService = SpringContextUtil.getBean(StoreServiceImpl.class);
                ESUtilService esService = SpringContextUtil.getBean(ESUtilService.class);
                if (storeService == null || esService == null) {
                    logger.error("[DMQ]找不到服务");
                } else {
                    logger.info("11111");
                    long storeId = info.getEsAdminInfo().getId();

                    switch (info.getEsAdminInfo().getAction()) {
                        case "c":
                            esService.indexData(storeService.selectByStoreId(storeId).getData(), ESIndex.SHOP, "shopinfo", String.valueOf(storeId));

                            logger.info("ccccccccccccccc");
                            channel.basicAck(envelope.getDeliveryTag(), false);
                            break;

                        case "u":
                            esService.updateIndexData(ESIndex.SHOP, "shopinfo", String.valueOf(storeId), new BeanMap(storeService.selectByStoreId(storeId).getData()));
                            logger.info("uuuuuuuuuuuuuuuuu");
                            channel.basicAck(envelope.getDeliveryTag(), false);
                            break;

                        case "d":
                            esService.deleteIndexData(ESIndex.SHOP, "shopinfo", String.valueOf(storeId));
                            logger.info("ddddddddddddddddddd");
                            channel.basicAck(envelope.getDeliveryTag(), false);
                            break;

                    }
                    logger.info("[DMQ]商家" + info.getEsAdminInfo().getId() + "信息更新成功");
                }

            }
        } else {
            logger.info("[DMQ]收到数据类型:" + info.getWorkType() + "后端暂时没有合适的服务处理");
            channel.basicAck(envelope.getDeliveryTag(), false);
        }

    }
}