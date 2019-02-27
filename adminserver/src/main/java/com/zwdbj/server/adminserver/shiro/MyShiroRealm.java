package com.zwdbj.server.adminserver.shiro;

import com.zwdbj.server.tokencenter.TokenCenterManager;
import com.zwdbj.server.tokencenter.model.AuthUser;
import com.zwdbj.server.basemodel.model.ServiceStatusInfo;
import com.zwdbj.server.utility.common.shiro.JWTToken;
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

import java.util.*;

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
        if(checkStatus.getData().getType()==null || checkStatus.getData().getType().trim().equals("") || checkStatus.getData().getType().equals("NORMAL")) {
            throw new AuthenticationException("用户禁止登陆,联系客服！");
        }
//        if (checkStatus.getData().getType().equals("PLATFORM") && checkStatus.getData().getTenantId()>0) {
//            throw new AuthenticationException("用户账号异常，禁止登陆,联系客服！");
//        }
//        if (checkStatus.getData().getType().equals("BUSINESS") && checkStatus.getData().getTenantId()<=0) {
//            throw new AuthenticationException("用户账号异常，禁止登陆,联系客服！");
//        }
//        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//        HttpServletRequest request = requestAttributes.getRequest();
//        String url = request.getRequestURI();
        if (checkStatus.getData().getRoles().length==0) {
            throw new AuthenticationException("未被授权访问");
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
