package com.zwdbj.server.mobileapi.service.wxMiniProgram.receiveAddress.service;

import com.zwdbj.server.mobileapi.service.wxMiniProgram.receiveAddress.mapper.IReceiveAddressMapper;
import com.zwdbj.server.mobileapi.service.wxMiniProgram.receiveAddress.model.ReceiveAddressInput;
import com.zwdbj.server.mobileapi.service.wxMiniProgram.receiveAddress.model.ReceiveAddressModel;
import com.zwdbj.server.utility.common.UniqueIDCreater;
import com.zwdbj.server.utility.common.shiro.JWTUtil;
import com.zwdbj.server.utility.model.ServiceStatusCode;
import com.zwdbj.server.utility.model.ServiceStatusInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ReceiveAddressServiceImpl implements ReceiveAddressService{

    @Autowired
    private IReceiveAddressMapper iReceiveAddressMapper;

    @Override
    public ServiceStatusInfo<Long> createReceiveAddress(ReceiveAddressInput receiveAddressInput) {
        try{
            long userId = JWTUtil.getCurrentId();
            receiveAddressInput.setUserId(userId);
            if(receiveAddressInput.isDefaultAddr()){
                iReceiveAddressMapper.updateCancelAllDefalue(userId);
            }
            long result = iReceiveAddressMapper.createReceiveAddress(UniqueIDCreater.generateID() , receiveAddressInput);
            return new ServiceStatusInfo<>(ServiceStatusCode.STATUS_NORMAL,"",result);
        }catch(Exception e){
            return new ServiceStatusInfo<>(ServiceStatusCode.STATUS_ERROR,"新增失败"+e.getMessage(),null);
        }
    }

    @Override
    public ServiceStatusInfo<Long> updateReceiveAddress(ReceiveAddressInput receiveAddressInput,long id) {
        try{
            long userId = JWTUtil.getCurrentId();
            receiveAddressInput.setUserId(userId);
            if(receiveAddressInput.isDefaultAddr()){
                iReceiveAddressMapper.updateCancelAllDefalue(userId);
            }
            long result = iReceiveAddressMapper.updateReceiveAddress(receiveAddressInput,id);
            return new ServiceStatusInfo<>(ServiceStatusCode.STATUS_NORMAL,"",result);
        }catch(Exception e){
            return new ServiceStatusInfo<>(ServiceStatusCode.STATUS_ERROR,"修改失败"+e.getMessage(),null);
        }
    }

    @Override
    public ServiceStatusInfo<Long> setDefalue(long id) {
        try{
            long userId = JWTUtil.getCurrentId();
            iReceiveAddressMapper.updateCancelAllDefalue(userId);
            long result = iReceiveAddressMapper.updateDefalue(id,userId);
            return new ServiceStatusInfo<>(ServiceStatusCode.STATUS_NORMAL,"",result);
        }catch(Exception e){
            return new ServiceStatusInfo<>(ServiceStatusCode.STATUS_ERROR,"默认收货地址设置失败"+e.getMessage(),null);
        }
    }

    @Override
    public ServiceStatusInfo<ReceiveAddressModel> findById(long id) {
        try{
            long userId = JWTUtil.getCurrentId();
            return new ServiceStatusInfo<>(ServiceStatusCode.STATUS_NORMAL,"",iReceiveAddressMapper.selectById(id,userId));
        }catch(Exception e){
            return new ServiceStatusInfo<>(ServiceStatusCode.STATUS_ERROR,"查询失败"+e.getMessage(),null);
        }
    }

    @Override
    public ServiceStatusInfo<List<ReceiveAddressModel>> findByPage() {
        try{
            long userId = JWTUtil.getCurrentId();
            return new ServiceStatusInfo<>(ServiceStatusCode.STATUS_NORMAL,"",iReceiveAddressMapper.selectUserId(userId));
        }catch(Exception e){
            return new ServiceStatusInfo<>(ServiceStatusCode.STATUS_ERROR,"查询失败"+e.getMessage(),null);
        }
    }

    @Override
    public ServiceStatusInfo<Long> deleteById(long id) {
        try{
            long userId = JWTUtil.getCurrentId();
            return new ServiceStatusInfo<>(ServiceStatusCode.STATUS_NORMAL,"",iReceiveAddressMapper.deleteReceiveAddress(id,userId));
        }catch(Exception e){
            return new ServiceStatusInfo<>(ServiceStatusCode.STATUS_ERROR,"删除失败"+e.getMessage(),null);
        }
    }
}
