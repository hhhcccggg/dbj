package com.zwdbj.server.adminserver.middleware.mq;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.zwdbj.server.adminserver.service.push.service.AppPushService;
import com.zwdbj.server.adminserver.service.review.service.LivingReviewService;
import com.zwdbj.server.adminserver.service.review.service.VideoReviewService;
import com.zwdbj.server.adminserver.service.user.service.UserService;
import com.zwdbj.server.adminserver.utility.SpringContextUtil;

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
        if (info.getWorkType()==QueueWorkInfoModel.QueueWorkInfo.WorkTypeEnum.SEND_PHONE_CODE) {
            UserService userService = SpringContextUtil.getBean(UserService.class);
            userService.sendSms(info.getPhoneCode().getPhone(),info.getPhoneCode().getCode());
            logger.info("[MQ]发送手机"+info.getPhoneCode().getPhone()+"的验证码"+info.getPhoneCode().getCode()+"成功");
            channel.basicAck(envelope.getDeliveryTag(),false);
        } else if (info.getWorkType()==QueueWorkInfoModel.QueueWorkInfo.WorkTypeEnum.PUSH) {
            QueueWorkInfoModel.QueueWorkPush pushInfo = info.getPushData();
            //处理推送数据
            AppPushService pushService = SpringContextUtil.getBean(AppPushService.class);
            pushService.push(pushInfo);
            logger.info("[MQ]处理完推送数据");
            channel.basicAck(envelope.getDeliveryTag(),false);
        } else if (info.getWorkType()==QueueWorkInfoModel.QueueWorkInfo.WorkTypeEnum.QINIU_VIDEO_IMG_REVIEW_RESULT){ //七牛图片&视频审核返回的结果
            VideoReviewService reviewService = SpringContextUtil.getBean(VideoReviewService.class);
            reviewService.reviewMQ(info.getQiniuReviewResult());
            logger.info("[MQ]七牛图片&视频审核返回的结果");
            channel.basicAck(envelope.getDeliveryTag(),false);
        } else if (info.getWorkType()==QueueWorkInfoModel.QueueWorkInfo.WorkTypeEnum.QINIU_LIVE_REVIEW_RESULT){ //七牛直播审核返回的结果
            LivingReviewService livingReviewService = SpringContextUtil.getBean(LivingReviewService.class);
            livingReviewService.livingReviewMQ(info.getQiniuReviewResult());
            logger.info("[MQ]七牛直播鉴黄审核返回的结果");
            channel.basicAck(envelope.getDeliveryTag(),false);
        } else if (info.getWorkType()==QueueWorkInfoModel.QueueWorkInfo.WorkTypeEnum.QINIU_RES_WAIT_REVIEW_DATA){
            VideoReviewService reviewService = SpringContextUtil.getBean(VideoReviewService.class);
            reviewService.reviewMQ2(info.getQiniuWaitReviewResData());
            logger.info("[MQ]收到数据类型:"+info.getWorkType()+"七牛图片&视频审核返回的结果");
            channel.basicAck(envelope.getDeliveryTag(),false);
        }else {
            logger.info("[MQ]收到数据类型:"+info.getWorkType()+"后端暂时没有合适的服务处理");
            channel.basicAck(envelope.getDeliveryTag(),false);
        }

    }
}
