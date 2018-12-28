package com.zwdbj.server.shopadmin.shiro;

import com.zwdbj.server.tokencenter.TokenCenterManager;
import com.zwdbj.server.tokencenter.model.AuthUser;
import com.zwdbj.server.utility.common.shiro.JWTToken;
import com.zwdbj.server.utility.model.ServiceStatusInfo;
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
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@Service
public class MyShiroRealm extends AuthorizingRealm {
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
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        String url = request.getRequestURI();
        List<String> roles = new ArrayList<>(Arrays.asList(checkStatus.getData().getRoles()));
        logger.info("用户{"+userId+"}的角色是{"+roles.toString()+"},请求url{"+url+"}");
            if (roles.size()==0) {
                throw new AuthenticationException("未被授权访问");
            } else if (roles.size()==1) {
                if (roles.contains("normal")) throw new AuthenticationException("未被授权访问");
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
