package com.zwdbj.server.adminserver.service.userDeviceTokens.service;

import com.zwdbj.server.adminserver.service.ServiceStatusInfo;
import com.zwdbj.server.adminserver.service.userDeviceTokens.mapper.IUserDeviceTokensMapper;
import com.zwdbj.server.adminserver.service.userDeviceTokens.model.AdDeviceTokenDto;
import com.zwdbj.server.adminserver.service.userDeviceTokens.model.AdUserDeviceTokenDto;
import com.zwdbj.server.adminserver.service.userDeviceTokens.model.AdUserDeviceTokensInput;
import com.zwdbj.server.utility.common.UniqueIDCreater;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDeviceTokensService {
    @Autowired
    private IUserDeviceTokensMapper userDeviceTokensMapper;

    public AdUserDeviceTokenDto getDeviceTokenByUserId(Long userId){
        AdUserDeviceTokenDto deviceTokenDto = this.userDeviceTokensMapper.getDeviceTokenByUserId(userId);
        return deviceTokenDto;
    }

    public List<AdDeviceTokenDto> getTokenList(){
        return this.userDeviceTokensMapper.getTokenList();
    }

    public ServiceStatusInfo<Long> delTokenById(Long id){
        Long result = 0L;
        try {
            result = this.userDeviceTokensMapper.delTokenById(id);
            return new ServiceStatusInfo<>(0,"删除成功-service",result);
        }catch (Exception e){
            return new ServiceStatusInfo<>(1,"删除失败"+e.getMessage(),result);
        }
    }
}
