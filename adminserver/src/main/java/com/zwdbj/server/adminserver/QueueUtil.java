package com.zwdbj.server.adminserver;

import com.zwdbj.server.adminserver.middleware.mq.MQWorkSender;
import com.zwdbj.server.probuf.middleware.mq.QueueWorkInfoModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class QueueUtil {
    private static Logger logger = LoggerFactory.getLogger(QueueUtil.class);

    public static final void sendQueue(long storeId, QueueWorkInfoModel.QueueWorkModifyShopInfo.OperationEnum operation) {
        try {
            QueueWorkInfoModel.QueueWorkModifyShopInfo pushData = QueueWorkInfoModel.QueueWorkModifyShopInfo.newBuilder()
                    .setOperation(operation)
                    .setStoreId(storeId)
                    .build();
            QueueWorkInfoModel.QueueWorkInfo workInfo = QueueWorkInfoModel.QueueWorkInfo.newBuilder()
                    .setWorkType(QueueWorkInfoModel.QueueWorkInfo.WorkTypeEnum.MODIFY_SHOP_INFO)
                    .setModifyShopInfo(pushData)
                    .build();
            MQWorkSender.shareSender().send(workInfo);
            logger.info("[MQ]商家" + storeId + "发送信息成功");
        } catch (Exception e) {
            logger.error("[MQ]发送商家" + storeId + "信息失败");
        }
    }
}
