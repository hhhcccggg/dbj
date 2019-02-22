package com.zwdbj.server.mobileapi.shiro;

import com.zwdbj.server.mobileapi.service.userAssets.model.UserCoinDetailAddInput;
import com.zwdbj.server.mobileapi.service.userAssets.service.IUserAssetService;
import com.zwdbj.server.mobileapi.service.userAssets.service.UserAssetServiceImpl;
import com.zwdbj.server.tokencenter.TokenCenterManager;
import com.zwdbj.server.tokencenter.model.AuthUser;
import com.zwdbj.server.utility.model.ServiceStatusInfo;
import com.zwdbj.server.mobileapi.service.user.model.UserAuthInfoModel;
import com.zwdbj.server.utility.common.shiro.JWTToken;
import com.zwdbj.server.utility.common.shiro.JWTUtil;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import com.zwdbj.server.mobileapi.service.user.service.UserService;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.time.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class MyShiroRealm extends AuthorizingRealm {
    @Autowired
    @Lazy
    private TokenCenterManager tokenCenterManager;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private UserAssetServiceImpl userAssetServiceImpl;


    private Logger logger = LoggerFactory.getLogger(MyShiroRealm.class);

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JWTToken;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String token = (String)authenticationToken.getCredentials();
        ServiceStatusInfo<Object> checkTokenResult = this.tokenCenterManager.checkToken(token);
        if (!checkTokenResult.isSuccess()) {
            throw new AuthenticationException(checkTokenResult.getMsg());
        }
        String userId = checkTokenResult.getData().toString();

        ServiceStatusInfo<AuthUser> checkStatus = null;
        try {
            checkStatus = tokenCenterManager.fetchUser(userId);
        } catch ( Exception ex ) {
            logger.info(ex.getMessage());
        }
        if (checkStatus == null||!checkStatus.isSuccess()||checkStatus.getData().isLocked()) {
            throw new AuthenticationException(checkStatus.getMsg());
        }
        if(!checkStatus.getData().getType().equals("NORMAL")) {
            throw new AuthenticationException("用户禁止登陆,联系客服！");
        }
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        String url = request.getRequestURI();
        logger.info("用户{"+userId+"}请求url{"+url+"}");
        boolean keyExist = this.redisTemplate.hasKey("user_everyDay:"+userId);
        if (!keyExist){
            LocalTime midnight = LocalTime.MIDNIGHT;
            LocalDate today = LocalDate.now(ZoneId.of("Asia/Shanghai"));
            LocalDateTime todayMidnight = LocalDateTime.of(today, midnight);
            LocalDateTime tomorrowMidnight = todayMidnight.plusDays(1);
            long s = TimeUnit.NANOSECONDS.toSeconds(Duration.between(LocalDateTime.now(ZoneId.of("Asia/Shanghai")), tomorrowMidnight).toNanos());
            this.redisTemplate.opsForValue().set("user_everyDay:"+userId,token,s, TimeUnit.SECONDS);
            this.userAssetServiceImpl.userIsExist(Long.valueOf(userId));
            UserCoinDetailAddInput input = new UserCoinDetailAddInput();
            input.setStatus("SUCCESS");
            input.setNum(1);
            input.setTitle("每日登录获得小饼干"+1+"个");
            input.setType("TASK");
            this.userAssetServiceImpl.userPlayCoinTask(input,Long.valueOf(userId),"TASK",1,"EVERYDAYFIRSTLOGIN","DONE");
        }
        return new SimpleAuthenticationInfo(token, token, "my_realm");
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        String token = principalCollection.toString();
        ServiceStatusInfo<Object> checkTokenResult = this.tokenCenterManager.checkToken(token);
        if (!checkTokenResult.isSuccess()) {
            throw new AuthenticationException(checkTokenResult.getMsg());
        }
        String userId = checkTokenResult.getData().toString();

        ServiceStatusInfo<AuthUser> checkStatus = null;
        try {
            checkStatus = tokenCenterManager.fetchUser(userId);
        } catch ( Exception ex ) {
            logger.info(ex.getMessage());
        }
        if (checkStatus == null||!checkStatus.isSuccess()||checkStatus.getData().isLocked()) {
            throw new AuthenticationException(checkStatus.getMsg());
        }

        List<String> roles = new ArrayList<>(Arrays.asList(checkStatus.getData().getRoles()));
        List<String> permissions = new ArrayList<>(Arrays.asList(checkStatus.getData().getPermissions()));

        simpleAuthorizationInfo.addRoles(new HashSet<>(roles));
        simpleAuthorizationInfo.addStringPermissions(new HashSet<>(permissions));
        return simpleAuthorizationInfo;
    }
}
