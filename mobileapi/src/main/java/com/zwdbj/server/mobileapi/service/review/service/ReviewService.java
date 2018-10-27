package com.zwdbj.server.mobileapi.service.review.service;

import com.zwdbj.server.mobileapi.middleware.mq.MQWorkSender;
import com.zwdbj.server.mobileapi.middleware.mq.QueueWorkInfoModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ReviewService {
    private Logger logger = LoggerFactory.getLogger(ReviewService.class);

    public void reviewQiniuRes(QueueWorkInfoModel.QueueWorkQiniuWaitReviewResData resData) {
        if (resData==null) return;
        try {
            QueueWorkInfoModel.QueueWorkInfo workInfo = QueueWorkInfoModel.QueueWorkInfo.newBuilder()
                    .setWorkType(QueueWorkInfoModel.QueueWorkInfo.WorkTypeEnum.QINIU_RES_WAIT_REVIEW_DATA)
                    .setQiniuWaitReviewResData(resData)
                    .build();
            MQWorkSender.shareSender().send(workInfo);
        } catch (Exception ex) {
            logger.warn(ex.getStackTrace().toString());
            logger.warn(ex.getMessage());
        }
    }
}
