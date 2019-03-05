package com.zwdbj.server.mobileapi.middleware.mq;

import com.zwdbj.server.probuf.middleware.mq.QueueWorkInfoModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ESUtil {

    private static Logger logger = LoggerFactory.getLogger(ESUtil.class);

    /**
     *      * int64 id=1;//主键id
     *      * string type = 2;//user:用户,video:视频,store:商家,product:商品
     *      * string action = 3;//c:增加,r:读取,u:更新,d:删除
     * @param id
     * @param type
     * @param action
     */
    public static void QueueWorkInfoModelSend(long id, String type, String action){

        try {
            QueueWorkInfoModel.QueueWorkESAdminInfo queueWorkInfoModel = QueueWorkInfoModel.QueueWorkESAdminInfo.newBuilder()
                    .setAction(action).setId(id).setType(type).build();
            QueueWorkInfoModel.QueueWorkInfo queueWorkInfo = QueueWorkInfoModel.QueueWorkInfo.newBuilder()
                    .setWorkType(QueueWorkInfoModel.QueueWorkInfo.WorkTypeEnum.ES_ADMIN_INFO)
                    .setEsAdminInfo(queueWorkInfoModel)
                    .build();
            DelayMQWorkSender.shareSender().send(queueWorkInfo,60);
            logger.info("[MQ]ES " + id + " " +type+ " " + action + "发送信息成功");
        } catch (Exception e) {
            logger.error("[MQ]ES" + id + " " +type+ " " + action + "发送信息失败");
        }
    }
}
