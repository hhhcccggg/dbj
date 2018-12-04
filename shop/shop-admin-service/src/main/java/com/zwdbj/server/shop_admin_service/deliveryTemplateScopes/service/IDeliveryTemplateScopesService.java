package com.zwdbj.server.shop_admin_service.deliveryTemplateScopes.service;

import com.zwdbj.server.shop_admin_service.deliveryTemplateScopes.model.DeliveryTemplateScopesModel;
import com.zwdbj.server.utility.model.ServiceStatusInfo;

import java.util.List;

public interface IDeliveryTemplateScopesService {
    List<DeliveryTemplateScopesModel> findAllDeliveryTemplateScopes();
    ServiceStatusInfo<DeliveryTemplateScopesModel> findDeliveryTemplateScopesById(long id);
    ServiceStatusInfo<Integer> addDeliveryTemplates(DeliveryTemplateScopesModel model);
    ServiceStatusInfo<Integer> deleteDeliveryTemplateScopesById(long id);
    ServiceStatusInfo<Integer> updateDeliveryTemplatesScopes(DeliveryTemplateScopesModel model);

}
