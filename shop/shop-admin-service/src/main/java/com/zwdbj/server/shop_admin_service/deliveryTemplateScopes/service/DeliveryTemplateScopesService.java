package com.zwdbj.server.shop_admin_service.deliveryTemplateScopes.service;

import com.zwdbj.server.shop_admin_service.deliveryTemplateScopes.mapper.IDeliveryTemplateScopesMapper;
import com.zwdbj.server.shop_admin_service.deliveryTemplateScopes.model.DeliveryTemplateScopesModel;
import com.zwdbj.server.utility.common.UniqueIDCreater;
import com.zwdbj.server.utility.model.ServiceStatusInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional
public class DeliveryTemplateScopesService implements IDeliveryTemplateScopesService {
    @Resource
    IDeliveryTemplateScopesMapper deliveryTemplateScopesMapper;
    @Override
    public List<DeliveryTemplateScopesModel> findAllDeliveryTemplateScopes() {
        List<DeliveryTemplateScopesModel> lists = deliveryTemplateScopesMapper.findAllDeliveryTemplateScopes();
        return lists;
    }

    @Override
    public ServiceStatusInfo<DeliveryTemplateScopesModel> findDeliveryTemplateScopesById(long id) {
        try {
            DeliveryTemplateScopesModel deliveryTemplateScopesModel = deliveryTemplateScopesMapper.findDeliveryTemplateScopesById(id);
            return new ServiceStatusInfo<>(0,"",deliveryTemplateScopesModel);
        }catch (Exception e){
            return new ServiceStatusInfo<>(1,"获取失败",null);
        }
    }

    @Override
    public ServiceStatusInfo<Integer> addDeliveryTemplates(DeliveryTemplateScopesModel model) {
        try {
            long id = UniqueIDCreater.generateID();
            int result = deliveryTemplateScopesMapper.addDeliveryTemplateScopes(id,model);
            if (result==0){
                return new ServiceStatusInfo<>(1,"添加失败",result);
            }
            return new ServiceStatusInfo<>(0,"",result);
        }catch (Exception e){
            return new ServiceStatusInfo<>(1,"添加失败",null);
        }
    }

    @Override
    public ServiceStatusInfo<Integer> deleteDeliveryTemplateScopesById(long id) {
        try {
            int result = deliveryTemplateScopesMapper.deleteDeliveryTemplateScopesById(id);
            if (result==0){
                return new ServiceStatusInfo<>(1,"删除失败",result);
            }
            return new ServiceStatusInfo<>(0,"",result);
        }catch (Exception e){
            return new ServiceStatusInfo<>(1,"删除失败",null);
        }
    }

    @Override
    public ServiceStatusInfo<Integer> updateDeliveryTemplatesScopes(DeliveryTemplateScopesModel model) {
        try {
            int result = deliveryTemplateScopesMapper.updateDeliveryTemplateScopes(model);
            if (result==0){
                return new ServiceStatusInfo<>(1,"修改失败",result);
            }
            return new ServiceStatusInfo<>(0,"",result);
        }catch (Exception e){
            return new ServiceStatusInfo<>(1,"修改失败",null);
        }
    }
}
