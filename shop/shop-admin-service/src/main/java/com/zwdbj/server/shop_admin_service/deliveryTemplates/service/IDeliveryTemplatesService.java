package com.zwdbj.server.shop_admin_service.deliveryTemplates.service;

import com.zwdbj.server.shop_admin_service.deliveryTemplates.model.DeliveryTemplatesModel;
import com.zwdbj.server.utility.model.ServiceStatusInfo;

import java.util.List;

public interface IDeliveryTemplatesService {
    List<DeliveryTemplatesModel> findAllDeliveryTemplates();
    ServiceStatusInfo<DeliveryTemplatesModel> getDeliveryTemplatesById(long id);
    ServiceStatusInfo<Integer> addDeliveryTemplates(DeliveryTemplatesModel model);
    ServiceStatusInfo<Integer> deleteDeliveryTemplatesById(long id);
    ServiceStatusInfo<Integer> updateDeliveryTemplates(DeliveryTemplatesModel model);
}
