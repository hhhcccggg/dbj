package com.zwdbj.server.shop_admin_service.deliveryTemplates.service;

import com.zwdbj.server.shop_admin_service.deliveryTemplates.mapper.IDeliveryTemplatesMapper;
import com.zwdbj.server.shop_admin_service.deliveryTemplates.model.DeliveryTemplatesModel;
import com.zwdbj.server.utility.common.UniqueIDCreater;
import com.zwdbj.server.utility.model.ServiceStatusInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional
public class DeliveryTemplatesServiceImpl implements IDeliveryTemplatesService {
    @Resource
    IDeliveryTemplatesMapper deliveryTemplatesMapper;

    @Override
    public List<DeliveryTemplatesModel> findAllDeliveryTemplates() {
        List<DeliveryTemplatesModel> lists = deliveryTemplatesMapper.findAllDeliveryTemplates();
        return lists;
    }

    @Override
    public ServiceStatusInfo<DeliveryTemplatesModel> getDeliveryTemplatesById(long id) {
        try {
            DeliveryTemplatesModel deliveryTemplatesModel = deliveryTemplatesMapper.getDeliveryTemplatesById(id);
            return new ServiceStatusInfo<>(0,"",deliveryTemplatesModel);
        }catch (Exception e){
            return new ServiceStatusInfo<>(1,"获取失败",null);
        }
    }

    @Override
    public ServiceStatusInfo<Integer> addDeliveryTemplates(DeliveryTemplatesModel model) {
        try {
            long id = UniqueIDCreater.generateID();
            int result = deliveryTemplatesMapper.addDeliveryTemplates(id,model);
            if (result==0){
                return new ServiceStatusInfo<>(1,"添加失败",result);
            }
            return new ServiceStatusInfo<>(0,"",result);
        }catch (Exception e){
            return new ServiceStatusInfo<>(1,"添加失败",null);
        }
    }

    @Override
    public ServiceStatusInfo<Integer> deleteDeliveryTemplatesById(long id) {
        try {
            int result = deliveryTemplatesMapper.deleteDeliveryTemplatesById(id);
            if (result==0){
                return new ServiceStatusInfo<>(1,"删除失败",result);
            }
            return new ServiceStatusInfo<>(0,"",result);
        }catch (Exception e){
            return new ServiceStatusInfo<>(1,"删除失败",null);
        }
    }

    @Override
    public ServiceStatusInfo<Integer> updateDeliveryTemplates(DeliveryTemplatesModel model) {
        try {
            int result = deliveryTemplatesMapper.updateDeliveryTemplates(model);
            if (result==0){
                return new ServiceStatusInfo<>(1,"修改失败",result);
            }
            return new ServiceStatusInfo<>(0,"",result);
        }catch (Exception e){
            return new ServiceStatusInfo<>(1,"修改失败",null);
        }
    }
}
