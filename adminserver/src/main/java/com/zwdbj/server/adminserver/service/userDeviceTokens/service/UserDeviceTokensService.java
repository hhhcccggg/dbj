package com.zwdbj.server.adminserver.service.userDeviceTokens.service;

import com.zwdbj.server.adminserver.service.ServiceStatusInfo;
import com.zwdbj.server.adminserver.service.userDeviceTokens.mapper.IUserDeviceTokensMapper;
import com.zwdbj.server.adminserver.service.userDeviceTokens.model.AdDeviceTokenDto;
import com.zwdbj.server.adminserver.service.userDeviceTokens.model.AdUserDeviceTokenDto;
import com.zwdbj.server.adminserver.service.userDeviceTokens.model.AdUserDeviceTokensInput;
import com.zwdbj.server.adminserver.utility.UniqueIDCreater;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDeviceTokensService {
    @Autowired
    private IUserDeviceTokensMapper userDeviceTokensMapper;

    public ServiceStatusInfo<Object> bindingUserId(AdUserDeviceTokensInput input){
        Long userId = input.getUserId();
        int result=0;
        if (userId!=0){
           int userCount =  this.findUserIdIsExist(userId);
           if (userCount == 0){
               result = this.insertDeviceToken(input);
               return new ServiceStatusInfo<>(0,"插入成功","插入成功"+result+"条");
           }else {
              int tokenCount =  this.deviceTokenIsExist(input.getDeviceType(),input.getDeviceToken());
              if (tokenCount==0){
                 result =  this.updateTokenByUserId(input);
                 return new ServiceStatusInfo<>(0,"更新成功","更新成功"+result+"条");
              }else if (tokenCount!=0){
                  return new ServiceStatusInfo<>(0,"",null);
              }
           }
        }else {
            result = this.deleteIt(input);
            return new ServiceStatusInfo<>(0,"删除成功","删除成功"+result+"条");
        }
        return  null;
    }

    public int deleteIt(AdUserDeviceTokensInput input){
        try {
            return this.userDeviceTokensMapper.deleteIt(input.getDeviceType(),input.getDeviceToken());
        }catch (RuntimeException e){
            throw  new RuntimeException("删除数据失败");
        }

    }

    public int deviceTokenIsExist(String deviceType,String deviceToken){
        return this.userDeviceTokensMapper.deviceTokenIsExist(deviceType,deviceToken);
    }

    public int insertDeviceToken(AdUserDeviceTokensInput input){
        Long id = UniqueIDCreater.generateID();
        try {
            int count = this.userDeviceTokensMapper.insertDeviceToken(id,input);
            return count;
        }catch (RuntimeException e){
            throw  new RuntimeException("插入数据失败");
        }
    }

    public int updateTokenByUserId(AdUserDeviceTokensInput input){
        try {
            int count = this.userDeviceTokensMapper.updateTokenByUserId(input);
            return count;
        }catch (RuntimeException e){
            throw  new RuntimeException("更新数据失败");
        }
    }
    public int findUserIdIsExist(Long userId){
        return this.userDeviceTokensMapper.findUserIdIsExist(userId);
    }


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
