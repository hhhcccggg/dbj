package com.zwdbj.server.adminserver.service.userBind.service;

import com.zwdbj.server.adminserver.service.ServiceStatusInfo;
import com.zwdbj.server.adminserver.service.user.model.BindThirdPartyAccountInput;
import com.zwdbj.server.adminserver.service.userBind.mapper.IUserBindMapper;
import com.zwdbj.server.adminserver.service.userBind.model.UserThirdAccountBindDto;
import com.zwdbj.server.adminserver.utility.UniqueIDCreater;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserBindService {
    @Autowired
    IUserBindMapper userBindMapper;

    public List<UserThirdAccountBindDto> list(long userId) {
        List<UserThirdAccountBindDto> dtos = this.userBindMapper.list(userId);
        return dtos;
    }
    public UserThirdAccountBindDto get(long userId,int type) {
        UserThirdAccountBindDto dto = this.userBindMapper.findByOpenId(userId, type);
        return dto;
    }
    public ServiceStatusInfo<Long> delete(long userId,int type) {
        this.userBindMapper.delete(userId, type);
        return new ServiceStatusInfo<>(0,"",null);
    }
    public ServiceStatusInfo<Long> add(BindThirdPartyAccountInput input,long userId) {
        long id=UniqueIDCreater.generateID();
        this.userBindMapper.add(input,userId,id);
        return new ServiceStatusInfo<>(0,"",id);
    }
    public ServiceStatusInfo<Long> update(BindThirdPartyAccountInput input,long id) {
        this.userBindMapper.update(input,id);
        return new ServiceStatusInfo<>(0,"",id);
    }
    public ServiceStatusInfo<Long> update2(BindThirdPartyAccountInput input,long userId,int type) {
        this.userBindMapper.update2(input,userId,type);
        return new ServiceStatusInfo<>(0,"",null);
    }
}
