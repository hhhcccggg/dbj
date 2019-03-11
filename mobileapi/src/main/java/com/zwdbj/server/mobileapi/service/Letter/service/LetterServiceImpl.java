package com.zwdbj.server.mobileapi.service.Letter.service;

import com.zwdbj.server.basemodel.model.ServiceStatusInfo;
import com.zwdbj.server.config.settings.AppSettingConfigs;
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

    @Override
    public ServiceStatusInfo<Boolean> isSend(long receiverId) {
        long userId = JWTUtil.getCurrentId();
        if (userId<=0) return new ServiceStatusInfo<>(1,"请重新登录",null);

        String key="letter_"+userId + "_" + receiverId;
        //判断是否有消息缓存
        if(redisTemplate.hasKey(key)){
            //发送超过规定条数，不能再进行私信操作
            int maxCount=this.appSettingConfigs.getLetterSendConfigs().getSendMaxCount();
            if((Integer)redisTemplate.opsForValue().get(key)>maxCount){
                return new ServiceStatusInfo<>(1, "需加为好友，才能继续发送消息", false);
            }
        }
        return new ServiceStatusInfo<>(0, "", true);
    }

    @Override
    public ServiceStatusInfo<Boolean> saveLetterCount(long receiverId, int count) {
        long userId = JWTUtil.getCurrentId();
        if (userId<=0) return new ServiceStatusInfo<>(1,"请重新登录",null);

        redisTemplate.opsForValue().set("letter_"+userId+"_"+receiverId,count);
        return new ServiceStatusInfo<>(0, "", true);
    }
}
