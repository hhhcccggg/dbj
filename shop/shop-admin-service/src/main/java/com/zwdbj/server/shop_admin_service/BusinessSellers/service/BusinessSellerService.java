package com.zwdbj.server.shop_admin_service.BusinessSellers.service;

import com.zwdbj.server.shop_admin_service.BusinessSellers.model.BusinessSellerAddInput;
import com.zwdbj.server.shop_admin_service.BusinessSellers.model.BusinessSellerModel;
import com.zwdbj.server.shop_admin_service.BusinessSellers.model.BusinessSellerModifyInput;
import com.zwdbj.server.utility.model.ServiceStatusInfo;

import java.util.List;

public interface BusinessSellerService {
    List<BusinessSellerModel> findAllBusinessSellers();
    ServiceStatusInfo<Integer> modifyBusinessSellers(BusinessSellerModifyInput input);
    ServiceStatusInfo<Integer> addBusinessSellers(BusinessSellerAddInput input);
    ServiceStatusInfo<Integer> deleteBusinessSellers(long businessSellerId);
    ServiceStatusInfo<BusinessSellerModel> getBusinessSellerById(long businessSellerId);


}
