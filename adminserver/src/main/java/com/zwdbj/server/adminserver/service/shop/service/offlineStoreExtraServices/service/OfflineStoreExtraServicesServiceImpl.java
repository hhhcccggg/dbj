package com.zwdbj.server.adminserver.service.shop.service.offlineStoreExtraServices.service;

import com.zwdbj.server.adminserver.middleware.mq.MQWorkSender;
import com.zwdbj.server.adminserver.service.shop.service.offlineStoreExtraServices.mapper.OfflineStoreExtraServicesMapper;
import com.zwdbj.server.adminserver.service.shop.service.offlineStoreExtraServices.model.OfflineStoreExtraServices;
import com.zwdbj.server.probuf.middleware.mq.QueueWorkInfoModel;
import com.zwdbj.server.utility.common.UniqueIDCreater;
import com.zwdbj.server.utility.model.ServiceStatusInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional
public class OfflineStoreExtraServicesServiceImpl implements OfflineStoreExtraServicesService {
    private Logger logger = LoggerFactory.getLogger(OfflineStoreExtraServicesServiceImpl.class);
    @Resource
    private OfflineStoreExtraServicesMapper mapper;

    @Override
    public ServiceStatusInfo<Long> create(OfflineStoreExtraServices offlineStoreExtraServices) {
        Long result = 0L;
        Long id = UniqueIDCreater.generateID();

        result = mapper.create(id, offlineStoreExtraServices);
        try {
            //进入消息队列
            QueueWorkInfoModel.QueueWorkModifyShopInfo pushData = QueueWorkInfoModel.QueueWorkModifyShopInfo.newBuilder()
                    .setStoreId(id)
                    .build();
            QueueWorkInfoModel.QueueWorkInfo workInfo = QueueWorkInfoModel.QueueWorkInfo.newBuilder()
                    .setWorkType(QueueWorkInfoModel.QueueWorkInfo.WorkTypeEnum.MODIFY_SHOP_INFO)
                    .setModifyShopInfo(pushData)
                    .build();
            MQWorkSender.shareSender().send(workInfo);
            logger.info("[MQ]商家" + offlineStoreExtraServices.getStoreId() + "更新额外服务信息");
            return new ServiceStatusInfo<>(0, "", result);
        } catch (Exception e) {
            logger.error("发送商家更新信息失败");

        }
        return new ServiceStatusInfo<>(0, "", result);

    }

    @Override
    public ServiceStatusInfo<Long> update(OfflineStoreExtraServices offlineStoreExtraServices) {
        Long result = 0L;
        try {
            result = mapper.update(offlineStoreExtraServices);
            return new ServiceStatusInfo<>(0, "", result);
        } catch (Exception e) {
            return new ServiceStatusInfo<>(1, "修改门店其他服务失败" + e.getMessage(), result);
        }

    }

    @Override
    public ServiceStatusInfo<Long> deleteById(Long id) {
        Long result = 0L;
        try {
            result = mapper.deleteById(id);
            return new ServiceStatusInfo<>(0, "", result);
        } catch (Exception e) {
            return new ServiceStatusInfo<>(1, "删除门店其他服务失败" + e.getMessage(), result);
        }

    }

    @Override
    public ServiceStatusInfo<List<OfflineStoreExtraServices>> select() {
        List<OfflineStoreExtraServices> result = null;
        try {
            result = mapper.select();
            return new ServiceStatusInfo<>(0, "", result);
        } catch (Exception e) {
            return new ServiceStatusInfo<>(1, "查询所有门店其他服务失败" + e.getMessage(), result);
        }

    }

    @Override
    public ServiceStatusInfo<List<OfflineStoreExtraServices>> selectByofflineStoreId(Long offlineStoreId) {
        List<OfflineStoreExtraServices> result = null;
        try {
            result = mapper.selectByofflineStoreId(offlineStoreId);
            return new ServiceStatusInfo<>(0, "", result);
        } catch (Exception e) {
            return new ServiceStatusInfo<>(1, "通过id查询门店其他服务失败" + e.getMessage(), result);
        }
    }
}
