package com.zwdbj.server.tokencenter;

import com.zwdbj.server.tokencenter.model.AuthUser;
import com.zwdbj.server.tokencenter.model.UserToken;
import com.zwdbj.server.utility.common.shiro.JWTUtil;
import com.zwdbj.server.utility.model.ServiceStatusCode;
import com.zwdbj.server.utility.model.ServiceStatusInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class TokenCenterManager {
    @Autowired
    private RedisTemplate redisTemplate;
    private Logger logger = LoggerFactory.getLogger(TokenCenterManager.class);

    private String userTokenKey(String id) {
        return "user_t_"+id;
    }
    private String userInfoKey(String id) {
        return "user_info_"+id;
    }

    /**
     * 校验token是否有效
     * @param accessToken token
     * @return 返回结果
     */
    public ServiceStatusInfo<Object> checkToken(String accessToken) {
        String id = JWTUtil.getId(accessToken);
        if (id == null || id.isEmpty()) {
            return new ServiceStatusInfo<>(ServiceStatusCode.STATUS_UNAUTH,"凭证无效",null);
        }
        if (!JWTUtil.verify(accessToken,id)) {
            return new ServiceStatusInfo<>(ServiceStatusCode.STATUS_UNAUTH,"凭证无效",null);
        }
        ServiceStatusInfo<Object> objectServiceStatusInfo = accessToken(id);
        if (!objectServiceStatusInfo.isSuccess()) {
            return new ServiceStatusInfo<>(ServiceStatusCode.STATUS_UNAUTH,"凭证无效",null);
        }
        if (!objectServiceStatusInfo.getData().toString().equals(accessToken)) {
            return new ServiceStatusInfo<>(ServiceStatusCode.STATUS_UNAUTH,"凭证无效",null);
        }
        return new ServiceStatusInfo<>(ServiceStatusCode.STATUS_NORMAL,"OK",id);
    }

    /**
     * 为指定用户颁发token
     * @param id
     * @param authUserManager
     * @return
     */
    public ServiceStatusInfo<UserToken> fetchToken(String id,IAuthUserManager authUserManager) {
        String token = JWTUtil.sign(id);
        this.redisTemplate.opsForValue().set(userTokenKey(id),token,JWTUtil.EXPIRE_TIME,TimeUnit.SECONDS);
        this.redisTemplate.opsForValue().set(userInfoKey(id),authUserManager.get(id),JWTUtil.EXPIRE_TIME,TimeUnit.SECONDS);
        UserToken userToken = new UserToken();
        userToken.setAccessToken(token);
        userToken.setExpireTime(JWTUtil.EXPIRE_TIME);
        return new ServiceStatusInfo<>(ServiceStatusCode.STATUS_NORMAL,"OK",userToken);
    }

    /**
     * 退出
     * @param id
     * @return
     */
    public ServiceStatusInfo<Object> logout(String id) {
        this.redisTemplate.delete(userTokenKey(id));
        this.redisTemplate.delete(userInfoKey(id));
        return new ServiceStatusInfo<>(ServiceStatusCode.STATUS_NORMAL,"OK",null);
    }

    /**
     * 刷新用户信息
     * @param id
     * @param authUserManager
     * @return
     */
    public ServiceStatusInfo<Object> refreshUserInfo(String id,IAuthUserManager authUserManager) {
        this.redisTemplate.opsForValue().set(userInfoKey(id),authUserManager.get(id),JWTUtil.EXPIRE_TIME,TimeUnit.SECONDS);
        return new ServiceStatusInfo<>(ServiceStatusCode.STATUS_NORMAL,"OK",null);
    }

    public ServiceStatusInfo<AuthUser> fetchUser(String id) {
        Object obj = this.redisTemplate.opsForValue().get(userInfoKey(id));
        if (obj == null) {
            return new ServiceStatusInfo<>(ServiceStatusCode.STATUS_UNAUTH,"未找到用户",null);
        }
        return new ServiceStatusInfo<>(ServiceStatusCode.STATUS_NORMAL,"OK",(AuthUser)obj);
    }

    /**
     * 获取指定用户token
     * @param id 用户id
     * @return 返回凭证
     */
    protected ServiceStatusInfo<Object> accessToken(String id) {
        if (!this.redisTemplate.hasKey(userTokenKey(id))) {
            return new ServiceStatusInfo<>(ServiceStatusCode.STATUS_UNAUTH,"凭证无效",null);
        }
        Object token = this.redisTemplate.opsForValue().get(userTokenKey(id));
        if (token == null || ((String)token).isEmpty()) {
            return new ServiceStatusInfo<>(ServiceStatusCode.STATUS_UNAUTH,"凭证无效",null);
        }
        return new ServiceStatusInfo<>(ServiceStatusCode.STATUS_NORMAL,"OK",token);
    }

}
