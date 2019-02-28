package com.zwdbj.server.adminserver.middleware.mq;

import com.zwdbj.server.probuf.middleware.mq.QueueWorkInfoModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VideoUtil {

    private static Logger logger = LoggerFactory.getLogger(VideoUtil.class);

    /**
     * 视频修改时修改es
     * @param operationEnum
     * @param videoId
     */
    public static void QueueWorkVideoInfoSend(QueueWorkInfoModel.QueueWorkVideoInfo.OperationEnum operationEnum,long videoId){

        /*try {
            QueueWorkInfoModel.QueueWorkVideoInfo queueWorkVideoInfo = QueueWorkInfoModel.QueueWorkVideoInfo.newBuilder()
                    .setOperation(operationEnum).setVideoId(videoId).build();
            QueueWorkInfoModel.QueueWorkInfo queueWorkInfo = QueueWorkInfoModel.QueueWorkInfo.newBuilder()
                    .setWorkType(QueueWorkInfoModel.QueueWorkInfo.WorkTypeEnum.VIDEO_INFO)
                    .setVideoInfo(queueWorkVideoInfo)
                    .build();
            MQWorkSender.shareSender().send(queueWorkInfo);
            logger.info("[MQ]视频" + videoId + "发送信息成功");
        } catch (Exception e) {
            logger.error("[MQ]视频" + videoId + "发送信息失败");
        }*/
    }
}
