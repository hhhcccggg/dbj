package com.zwdbj.server.shop_admin_service.receiveAddress.service;

import com.zwdbj.server.shop_admin_service.receiveAddress.model.ReceiveAddressAddInput;
import com.zwdbj.server.shop_admin_service.receiveAddress.model.ReceiveAddressModel;
import com.zwdbj.server.shop_admin_service.receiveAddress.model.ReceiveAddressModifyInput;
import com.zwdbj.server.utility.model.ServiceStatusInfo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ReceiveAddressService {
    List<ReceiveAddressModel> findAllReceiveAddresses();
    ServiceStatusInfo<ReceiveAddressModel> getReceiveAddressById(long id);
    ServiceStatusInfo<Integer> addReceiveAddress(ReceiveAddressAddInput input);
    ServiceStatusInfo<Integer> modifyReceiveAddress(long id, ReceiveAddressModifyInput input);
    ServiceStatusInfo<Integer> notRealDeleteReceiveAddress(long id);

}
