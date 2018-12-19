package com.zwdbj.server.mobileapi.shiro;

import com.zwdbj.server.tokencenter.TokenCenterManager;
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
import org.springframework.stereotype.Service;
import com.zwdbj.server.mobileapi.service.user.service.UserService;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Service
public class MyShiroRealm extends AuthorizingRealm {
    @Autowired
    @Lazy
    private UserService userService;
    @Autowired
    @Lazy
    private TokenCenterManager tokenCenterManager;

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
        long userId = Long.parseLong(checkTokenResult.getData().toString());

        ServiceStatusInfo<UserAuthInfoModel> checkStatus = null;
        try {
            checkStatus = userService.checkUserAuth(userId);
        } catch ( Exception ex ) {
            logger.info(ex.getMessage());
            userService.clearCacheUserInfo(userId);
            checkStatus = userService.checkUserAuth(userId);
        }
        if (!checkStatus.isSuccess()) {
            throw new AuthenticationException(checkStatus.getMsg());
        }
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        String url = request.getRequestURI();
        List<String> roles = checkStatus.getData().getRoles();
        logger.info("用户{"+userId+"}请求url{"+url+"}");
        return new SimpleAuthenticationInfo(token, token, "my_realm");
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //TODO 增加数据缓存
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        String token = principalCollection.toString();
        String username = JWTUtil.getId(token);
        if(!userService.checkTokenValid(Long.parseLong(username),token).isSuccess()) {
            logger.info("用户{"+username+"}Token无效");
            throw new AuthenticationException("用户token无效");
        }
        ServiceStatusInfo<UserAuthInfoModel> serviceStatusInfo = userService.checkUserAuth(Long.parseLong(username));
        if(!serviceStatusInfo.isSuccess()) {
            throw new AuthenticationException(serviceStatusInfo.getMsg());
        }

        List<String> roles = serviceStatusInfo.getData().getRoles();
        List<String> permissions = serviceStatusInfo.getData().getPermissions();

        simpleAuthorizationInfo.addRoles(new HashSet<>(roles));
        simpleAuthorizationInfo.addStringPermissions(new HashSet<>(permissions));
        return simpleAuthorizationInfo;
    }
}
