package com.zwdbj.server.mobileapi.service.wxMiniProgram.receiveAddress.service;

import com.zwdbj.server.mobileapi.service.wxMiniProgram.receiveAddress.model.ReceiveAddressAddInput;
import com.zwdbj.server.mobileapi.service.wxMiniProgram.receiveAddress.model.ReceiveAddressModel;
import com.zwdbj.server.mobileapi.service.wxMiniProgram.receiveAddress.model.ReceiveAddressModifyInput;
import com.zwdbj.server.utility.model.ServiceStatusInfo;

import java.util.List;

public interface ReceiveAddressService {
    List<ReceiveAddressModel> findAllReceiveAddresses();
    ServiceStatusInfo<ReceiveAddressModel> getReceiveAddressById(long id);
    ServiceStatusInfo<Integer> addReceiveAddress(ReceiveAddressAddInput input);
    ServiceStatusInfo<Integer> modifyReceiveAddress(long id, ReceiveAddressModifyInput input);
    ServiceStatusInfo<Integer> notRealDeleteReceiveAddress(long id);

}
