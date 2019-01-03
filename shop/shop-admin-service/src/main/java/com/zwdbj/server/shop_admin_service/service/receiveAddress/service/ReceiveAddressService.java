package com.zwdbj.server.shop_admin_service.service.receiveAddress.service;

import com.zwdbj.server.shop_admin_service.service.receiveAddress.model.ReceiveAddressAddInput;
import com.zwdbj.server.shop_admin_service.service.receiveAddress.model.ReceiveAddressModel;
import com.zwdbj.server.shop_admin_service.service.receiveAddress.model.ReceiveAddressModifyInput;
import com.zwdbj.server.utility.model.ServiceStatusInfo;

import java.util.List;

public interface ReceiveAddressService {
    List<ReceiveAddressModel> findAllReceiveAddresses();
    ServiceStatusInfo<ReceiveAddressModel> getReceiveAddressById(long id);
    ServiceStatusInfo<Integer> addReceiveAddress(ReceiveAddressAddInput input);
    ServiceStatusInfo<Integer> modifyReceiveAddress(long id, ReceiveAddressModifyInput input);
    ServiceStatusInfo<Integer> notRealDeleteReceiveAddress(long id);

}
