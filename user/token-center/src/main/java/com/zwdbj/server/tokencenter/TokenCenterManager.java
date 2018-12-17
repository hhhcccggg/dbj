package com.zwdbj.server.tokencenter;

import com.zwdbj.server.utility.common.shiro.JWTUtil;
import com.zwdbj.server.utility.model.ServiceStatusInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.TimeUnit;

public class TokenCenterManager {
    @Autowired
    private RedisTemplate redisTemplate;
    private Logger logger = LoggerFactory.getLogger(TokenCenterManager.class);
    private String tokenKey(long id) {
        return "tokencenter_token_"+String.valueOf(id);
    }
    private String userInfoKey(long id) {
        return "tokencenter_userinfo_"+String.valueOf(id);
    }
    /**
     * 获取token
     * @param infoModel 基本用户信息
     * @return token信息
     */
    public ServiceStatusInfo<TokenInfoModel> fetchToken(AuthUserInfoModel infoModel) {
        TokenInfoModel tokenInfoModel = new TokenInfoModel();
        tokenInfoModel.setAccessToken(JWTUtil.sign(String.valueOf(infoModel.getId())));
        tokenInfoModel.setExpireTime(JWTUtil.EXPIRE_TIME);
        this.redisTemplate.opsForValue().set(tokenKey(infoModel.getId()),tokenInfoModel,JWTUtil.EXPIRE_TIME,TimeUnit.SECONDS);
        this.redisTemplate.opsForValue().set(userInfoKey(infoModel.getId()),infoModel,JWTUtil.EXPIRE_TIME,TimeUnit.SECONDS);
        return new ServiceStatusInfo<>(0,"OK",tokenInfoModel);
    }

    /**
     * 依据token获取用户信息
     * @param accessToken token
     * @return 返回用户信息，如果token已失效，返回失败
     */
    public ServiceStatusInfo<AuthUserInfoModel> userInfo(String accessToken) {
        String userId =JWTUtil.getId(accessToken);
        if (userId == null || userId.isEmpty()) {
            return new ServiceStatusInfo<>(1,"token无效",null);
        }
        long id = Long.parseLong(userId);
        return userInfo(id);
    }

    /**
     *
     * @param userId
     * @return
     */
    public ServiceStatusInfo<AuthUserInfoModel> userInfo(long id) {
        try {
            if (!this.redisTemplate.hasKey(tokenKey(id))) {
                return new ServiceStatusInfo<>(401,"token无效",null);
            }
            if (!this.redisTemplate.hasKey(userInfoKey(id))) {
                return new ServiceStatusInfo<>(401,"token无效",null);
            }
            AuthUserInfoModel userInfoModel = (AuthUserInfoModel)this.redisTemplate.opsForValue().get(userInfoKey(id));
            if (userInfoModel == null) {
                this.redisTemplate.delete(userInfo(id));
                this.redisTemplate.delete(tokenKey(id));
                return new ServiceStatusInfo<>(401,"token无效",null);
            }
            return new ServiceStatusInfo<>(0,"OK",userInfoModel);
        }catch ( Exception ex ) {
            this.redisTemplate.delete(userInfo(id));
            this.redisTemplate.delete(tokenKey(id));
            logger.warn(ex.getMessage());
            return new ServiceStatusInfo<>(500,ex.getMessage(),null);
        }
    }
    /**
     * 刷新用户信息
     * @param infoModel
     * @return
     */
    public ServiceStatusInfo<Object> updateUserInfo(AuthUserInfoModel infoModel) {
        this.redisTemplate.opsForValue().set(userInfoKey(infoModel.getId()),infoModel,JWTUtil.EXPIRE_TIME,TimeUnit.SECONDS);
        return new ServiceStatusInfo<>(0,"OK",null);
    }
    /**
     * 注销
     * @param accessToken
     * @return
     */
    public ServiceStatusInfo<Object> logout(String accessToken) {
        String userId =JWTUtil.getId(accessToken);
        if (userId == null || userId.isEmpty()) {
            return new ServiceStatusInfo<>(0,"OK",null);
        }
        return logout(Long.parseLong(userId));
    }

    /**
     * 注销
     * @param userId
     * @return
     */
    public ServiceStatusInfo<Object> logout(long userId) {
        this.redisTemplate.delete(tokenKey(userId));
        this.redisTemplate.delete(userInfo(userId));
        return new ServiceStatusInfo<>(0,"OK",null);
    }

}
