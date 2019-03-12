package com.zwdbj.server.mobileapi.service.userDeviceTokens.service;

import com.zwdbj.server.basemodel.model.ServiceStatusInfo;
import com.zwdbj.server.mobileapi.service.userDeviceTokens.mapper.IUserDeviceTokensMapper;
import com.zwdbj.server.mobileapi.service.userDeviceTokens.model.UserDeviceTokenDto;
import com.zwdbj.server.mobileapi.service.userDeviceTokens.model.UserDeviceTokensInput;
import com.zwdbj.server.utility.common.UniqueIDCreater;
import com.zwdbj.server.utility.common.shiro.JWTUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class UserDeviceTokensService {
    @Autowired
    private IUserDeviceTokensMapper userDeviceTokensMapper;
    @Autowired
    private RedisTemplate redisTemplate;

    private Logger logger = LoggerFactory.getLogger(UserDeviceTokensService.class);

    public ServiceStatusInfo<Object> bindingUserId(UserDeviceTokensInput input){
        Long userId = input.getUserId();
        int result=0;
        if (userId!=0){
           int userCount =  this.findUserIdIsExist(userId);
           if (userCount == 0){
               result = this.insertDeviceToken(input);
               if (result==0)return new ServiceStatusInfo<>(1,"插入失败","插入失败");
               try {
                   if (input.getDeviceType().equals("ios"))
                       this.redisTemplate.opsForZSet().add("IOSSettingPush_All", String.valueOf(userId), 0);
                   if (input.getDeviceType().equals("android"))
                       this.redisTemplate.opsForZSet().add("ANDROIDSettingPush_All", String.valueOf(userId), 0);
               } catch (Exception e){
               e.printStackTrace();
            }
               return new ServiceStatusInfo<>(0,"插入成功","插入成功"+result+"条");
           }else {
                 result =  this.updateTokenByUserId(input);
                 if (result==0)return new ServiceStatusInfo<>(1,"更新失败","更新失败");

                 return new ServiceStatusInfo<>(0,"更新成功","更新成功"+result+"条");
           }
        }else {
            result = this.deleteIt(input);
            if (result==0)return new ServiceStatusInfo<>(1,"删除失败","删除失败");
            try {
                if (input.getDeviceType().equals("ios"))
                    this.redisTemplate.opsForZSet().remove("IOSSettingPush_All",String.valueOf(JWTUtil.getCurrentId()));
                if (input.getDeviceType().equals("android"))
                    this.redisTemplate.opsForZSet().remove("ANDROIDSettingPush_All",String.valueOf(JWTUtil.getCurrentId()));
            } catch (Exception e){
                e.printStackTrace();
            }
            return new ServiceStatusInfo<>(0,"删除成功","删除成功"+result+"条");
        }
    }

    public int deleteIt(UserDeviceTokensInput input){
        try {
            return this.userDeviceTokensMapper.deleteIt(input.getDeviceType(),input.getDeviceToken());
        }catch (RuntimeException e){
             logger.warn("删除数据失败");
             return 0;
        }

    }

    public int deviceTokenIsExist(long userId,String deviceType,String deviceToken){
        return this.userDeviceTokensMapper.deviceTokenIsExist(userId,deviceType,deviceToken);
    }

    public int insertDeviceToken(UserDeviceTokensInput input){
        Long id = UniqueIDCreater.generateID();
        try {
            int count = this.userDeviceTokensMapper.insertDeviceToken(id,input);
            return count;
        }catch (RuntimeException e){
            logger.warn("插入数据失败");
            return 0;
        }
    }

    public int updateTokenByUserId(UserDeviceTokensInput input){
        try {
            int count = this.userDeviceTokensMapper.updateTokenByUserId(input);
            return count;
        }catch (RuntimeException e){
            logger.warn("更新数据失败");
            return 0;
        }
    }
    public int findUserIdIsExist(Long userId){
        return this.userDeviceTokensMapper.findUserIdIsExist(userId);
    }


    public UserDeviceTokenDto getDeviceTokenByUserId(Long userId){
        UserDeviceTokenDto deviceTokenDto = this.userDeviceTokensMapper.getDeviceTokenByUserId(userId);
        return deviceTokenDto;
    }
}
