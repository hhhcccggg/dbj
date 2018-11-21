package com.zwdbj.server.service.userDeviceTokens.service;

import com.zwdbj.server.service.userDeviceTokens.mapper.IUserDeviceTokensMapper;
import com.zwdbj.server.utility.common.UniqueIDCreater;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserDeviceTokensService {
    @Autowired
    private IUserDeviceTokensMapper userDeviceTokensMapper;

    public void newDeviceToken(long userId,String deviceToken,String deviceType){
        long id = UniqueIDCreater.generateID();
        this.userDeviceTokensMapper.newDeviceToken(id,userId,deviceToken,deviceType);
    }

}
