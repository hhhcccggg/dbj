package com.zwdbj.server.adminserver.service.shop.service.receiveAddress.service;

import com.zwdbj.server.adminserver.service.shop.service.receiveAddress.mapper.IReceiveAddressMapper;
import com.zwdbj.server.adminserver.service.shop.service.receiveAddress.model.ReceiveAddressAddInput;
import com.zwdbj.server.adminserver.service.shop.service.receiveAddress.model.ReceiveAddressModel;
import com.zwdbj.server.adminserver.service.shop.service.receiveAddress.model.ReceiveAddressModifyInput;
import com.zwdbj.server.utility.common.UniqueIDCreater;
import com.zwdbj.server.utility.model.ServiceStatusInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional
public class ReceiveAddressServiceImpl implements ReceiveAddressService {
    @Resource
    IReceiveAddressMapper receiveAddressMapper;

    @Override
    public List<ReceiveAddressModel> findAllReceiveAddresses() {
        List<ReceiveAddressModel> receiveAddressModels = this.receiveAddressMapper.findAllReceiveAddresses();
        return receiveAddressModels;
    }

    @Override
    public ServiceStatusInfo<ReceiveAddressModel> getReceiveAddressById(long id) {
        try {
            ReceiveAddressModel receiveAddressModel = this.receiveAddressMapper.getReceiveAddressById(id);
            return new ServiceStatusInfo<>(0,"",receiveAddressModel);
        }catch (Exception e){
            return new ServiceStatusInfo<>(1,"获取地址失败",null);
        }
    }

    @Override
    public ServiceStatusInfo<Integer> addReceiveAddress(ReceiveAddressAddInput input) {
        try {
            long id = UniqueIDCreater.generateID();
            int result  = this.receiveAddressMapper.addReceiveAddress(id,input);
            if (result==0)return new ServiceStatusInfo<>(1,"增加地址失败",result);
            return new ServiceStatusInfo<>(0,"",result);
        }catch (Exception e){
            return new ServiceStatusInfo<>(1,"增加地址失败",null);
        }
    }

    @Override
    public ServiceStatusInfo<Integer> modifyReceiveAddress(long id, ReceiveAddressModifyInput input) {
        try {
            int result  = this.receiveAddressMapper.modifyReceiveAddress(id,input);
            if (result==0)return new ServiceStatusInfo<>(1,"修改地址失败",result);
            return new ServiceStatusInfo<>(0,"",result);
        }catch (Exception e){
            return new ServiceStatusInfo<>(1,"修改地址失败",null);
        }
    }

    @Override
    public ServiceStatusInfo<Integer> notRealDeleteReceiveAddress(long id) {
        try {
            int result  = this.receiveAddressMapper.notRealDeleteReceiveAddress(id);
            if (result==0)return new ServiceStatusInfo<>(1,"删除地址失败",result);
            return new ServiceStatusInfo<>(0,"",result);
        }catch (Exception e){
            return new ServiceStatusInfo<>(1,"删除地址失败",null);
        }
    }
}
