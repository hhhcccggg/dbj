package com.zwdbj.server.adminserver.service.review.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zwdbj.server.probuf.middleware.mq.QueueWorkInfoModel;
import com.zwdbj.server.adminserver.service.living.service.LivingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LivingReviewService {

    @Autowired
    LivingService livingService;
    protected Logger logger = LoggerFactory.getLogger(LivingReviewService.class);
    public boolean livingReviewMQ(QueueWorkInfoModel.QueueWorkQiniuReviewResult livingReviewResult){
        try {
            if (livingReviewResult==null) return true;
            String dataResponse = livingReviewResult.getResultContent();
            JSONObject dataObject= JSON.parseObject(dataResponse);
            String livingId = dataObject.getString("stream");
            boolean status = dataObject.getBoolean("review");
            if (status==true){
                this.updateLivingStatus(livingId);
            }
            return true;
        }catch (Exception e){
            logger.error("直播鉴黄回调错误++++++"+e.getMessage());
            return true;
        }
    }

    public void updateLivingStatus(String livingId){
        try {
            this.livingService.updateLivingStatus(Long.parseLong(livingId));
        }catch (Exception e){
            e.printStackTrace();
            logger.error("直播鉴黄类型转换异常");
        }


    }

}

