package com.zwdbj.server.mobileapi.service.wxMiniProgram.receiveAddress.service;

import com.zwdbj.server.mobileapi.service.wxMiniProgram.receiveAddress.model.ReceiveAddressInput;
import com.zwdbj.server.mobileapi.service.wxMiniProgram.receiveAddress.model.ReceiveAddressModel;
import com.zwdbj.server.basemodel.model.ServiceStatusInfo;

import java.util.List;

public interface ReceiveAddressService {

    /**
     * 新增
     * @param receiveAddressInput
     * @return
     */
    ServiceStatusInfo<Long> createReceiveAddress(ReceiveAddressInput receiveAddressInput);

    /**
     * 修改
     * @param receiveAddressInput
     * @param id
     * @return
     */
    ServiceStatusInfo<Long> updateReceiveAddress(ReceiveAddressInput receiveAddressInput);

    /**
     * 设置默认收货地址
     * @param id
     * @return
     */
    ServiceStatusInfo<Long> setDefalue(long id);

    /**
     * 查询单个
     * @param id
     * @return
     */
    ServiceStatusInfo<ReceiveAddressModel> findById(long id);

    /**
     * 分页查询
     * @return
     */
    ServiceStatusInfo<List<ReceiveAddressModel>> findByPage();

    /**
     * 删除收货地址
     * @param id
     * @return
     */
    ServiceStatusInfo<Long> deleteById(long id);

    /**
     * 获取默认收货地址
     * @return
     */
    ServiceStatusInfo<ReceiveAddressModel> getDefault();
}
