package com.zwdbj.server.mobileapi.service.userBind.service;

import com.zwdbj.server.basemodel.model.ServiceStatusInfo;
import com.zwdbj.server.mobileapi.service.user.model.BindThirdPartyAccountInput;
import com.zwdbj.server.mobileapi.service.userBind.mapper.IUserBindMapper;
import com.zwdbj.server.mobileapi.service.userBind.model.UserThirdAccountBindDto;
import com.zwdbj.server.utility.common.UniqueIDCreater;
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
        Long result = this.userBindMapper.delete(userId, type);
        if (result==0L) return new ServiceStatusInfo<>(1,"userBind解绑失败",null);
        return new ServiceStatusInfo<>(0,"",result);
    }
    public ServiceStatusInfo<Long> add(BindThirdPartyAccountInput input,long userId) {
        long id=UniqueIDCreater.generateID();
        Long result = this.userBindMapper.add(input,userId,id);
        if (result==0L) return new ServiceStatusInfo<>(1,"userBind添加失败",null);
        return new ServiceStatusInfo<>(0,"",result);
    }
    public ServiceStatusInfo<Long> update(BindThirdPartyAccountInput input,long id) {
        Long result = this.userBindMapper.update(input,id);
        if (result==0L) return new ServiceStatusInfo<>(1,"userBind更新失败",null);
        return new ServiceStatusInfo<>(0,"",result);
    }
    public ServiceStatusInfo<Long> update2(BindThirdPartyAccountInput input,long userId,int type) {
        Long result = this.userBindMapper.update2(input,userId,type);
        if (result==0L) return new ServiceStatusInfo<>(1,"userBind更新失败",null);
        return new ServiceStatusInfo<>(0,"",result);
    }

    public int thirdIsExist(String openId,int thirdType){
        int count = this.userBindMapper.thirdIsExist(openId,thirdType);
       /* if (count==0)return new ServiceStatusInfo<>(0,"",count);
        return new ServiceStatusInfo<>(1,"此账号已经绑定了其他账号",null);*/
       return count;
    }

    public UserThirdAccountBindDto findUserByOpenId(String openId,int thirdType){
        UserThirdAccountBindDto userThirdAccountBindDto = this.userBindMapper.findUserByOpenId(openId,thirdType);
        return userThirdAccountBindDto;
    }
}
