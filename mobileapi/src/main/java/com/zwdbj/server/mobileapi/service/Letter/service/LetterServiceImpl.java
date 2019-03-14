package com.zwdbj.server.mobileapi.service.Letter.service;

import com.zwdbj.server.basemodel.model.ServiceStatusInfo;
import com.zwdbj.server.config.settings.AppSettingConfigs;
import com.zwdbj.server.mobileapi.service.user.model.UserFollowInfoDto;
import com.zwdbj.server.mobileapi.service.user.model.UserFollowInfoSearchInput;
import com.zwdbj.server.mobileapi.service.user.service.UserService;
import com.zwdbj.server.utility.common.shiro.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Primary
@Service
public class LetterServiceImpl implements ILetterService {

    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private AppSettingConfigs appSettingConfigs;
    @Autowired
    private UserService userService;

    @Override
    public ServiceStatusInfo<Integer> isSend(long receiverId) {
        long userId = JWTUtil.getCurrentId();
        if (userId<=0) return new ServiceStatusInfo<>(1,"请重新登录",null);

        //判断是否相互关注
        UserFollowInfoDto userFollowInfoDto=userService.followStatusSearch(new UserFollowInfoSearchInput(userId,receiverId));
        if(userFollowInfoDto.isFollowed()&&userFollowInfoDto.isMyFollower()){
            return new ServiceStatusInfo<>(0, "", -1);
        }
        String key="letter_"+userId + "_" + receiverId;
        //判断是否有消息缓存
        int maxCount=this.appSettingConfigs.getLetterSendConfigs().getSendMaxCount();
        if(redisTemplate.hasKey(key)){
            int remainCount=maxCount-(Integer)redisTemplate.opsForValue().get(key);
            return new ServiceStatusInfo<>(0, "", remainCount>0?remainCount:0);
        }
        return new ServiceStatusInfo<>(0, "", maxCount);
    }

    @Override
    public ServiceStatusInfo<Boolean> saveLetterCount(long receiverId) {
        try{
            long userId = JWTUtil.getCurrentId();
            if (userId<=0) return new ServiceStatusInfo<>(1,"请重新登录",null);

            String key="letter_"+userId+"_"+receiverId;
            if(redisTemplate.hasKey(key)){
                redisTemplate.opsForValue().set("letter_"+userId+"_"+receiverId,(int)redisTemplate.opsForValue().get(key)+1);
            }
            redisTemplate.opsForValue().set("letter_"+userId+"_"+receiverId,1);
            return new ServiceStatusInfo<>(0, "", true);
        }catch (Exception exception){
            return new ServiceStatusInfo<>(1, "私信条数保存失败", false);
        }
    }
}
