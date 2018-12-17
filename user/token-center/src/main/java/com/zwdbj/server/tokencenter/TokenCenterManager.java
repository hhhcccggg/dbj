package com.zwdbj.server.tokencenter;

<<<<<<< HEAD
import com.zwdbj.server.tokencenter.model.AuthUser;
import com.zwdbj.server.tokencenter.model.UserToken;
import com.zwdbj.server.utility.common.shiro.JWTUtil;
import com.zwdbj.server.utility.model.ServiceStatusCode;
=======
import com.zwdbj.server.utility.common.shiro.JWTUtil;
>>>>>>> 剥离授权业务
import com.zwdbj.server.utility.model.ServiceStatusInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
<<<<<<< HEAD
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
=======

import java.util.concurrent.TimeUnit;

>>>>>>> 剥离授权业务
public class TokenCenterManager {
    @Autowired
    private RedisTemplate redisTemplate;
    private Logger logger = LoggerFactory.getLogger(TokenCenterManager.class);
<<<<<<< HEAD

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
=======
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
>>>>>>> 剥离授权业务
    }

}
