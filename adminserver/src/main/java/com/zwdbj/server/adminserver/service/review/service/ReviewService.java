package com.zwdbj.server.adminserver.service.review.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zwdbj.server.common.mq.MQWorkSender;
import com.zwdbj.server.probuf.middleware.mq.QueueWorkInfoModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


@Service
public class ReviewService {

    protected Logger logger = LoggerFactory.getLogger(ReviewService.class);

    public void getResponse(String response){
        JSONObject dataObject= JSON.parseObject(response);
        String inputKey = dataObject.getString("inputKey");
        if (inputKey.startsWith("qiniu_censor_tmp") || inputKey.startsWith("http://")) return;
        QueueWorkInfoModel.QueueWorkQiniuReviewResult reviewResult = QueueWorkInfoModel.QueueWorkQiniuReviewResult.newBuilder()
                .setResultContent(response)
                .build();
        QueueWorkInfoModel.QueueWorkInfo workInfo = QueueWorkInfoModel.QueueWorkInfo.newBuilder()
                .setWorkType(QueueWorkInfoModel.QueueWorkInfo.WorkTypeEnum.QINIU_VIDEO_IMG_REVIEW_RESULT)
                .setQiniuReviewResult(reviewResult)
                .build();
        try {
            MQWorkSender.shareSender().send(workInfo);
            logger.info("图片,视频审核回调+++++++加入MQ成功:"+response);
        }catch (Exception e){
            logger.error("图片,视频审核回调发送消息队列失败"+response);
        }
    }

    public void getLivingResponse(String response){
        QueueWorkInfoModel.QueueWorkQiniuReviewResult reviewResult = QueueWorkInfoModel.QueueWorkQiniuReviewResult.newBuilder()
                .setResultContent(response)
                .build();
        QueueWorkInfoModel.QueueWorkInfo workInfo = QueueWorkInfoModel.QueueWorkInfo.newBuilder()
                .setWorkType(QueueWorkInfoModel.QueueWorkInfo.WorkTypeEnum.QINIU_LIVE_REVIEW_RESULT)
                .setQiniuReviewResult(reviewResult)
                .build();
        try {
            MQWorkSender.shareSender().send(workInfo);
            logger.info("直播鉴黄审核回调+++++++加入MQ成功:"+response);
        }catch (Exception e){
            logger.error("直播鉴黄审核回调发送消息队列失败"+response);
        }
    }



}
