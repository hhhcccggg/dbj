package com.zwdbj.server.adminserver.shiro;

import com.zwdbj.server.adminserver.identity.RoleIdentity;
import com.zwdbj.server.adminserver.service.ServiceStatusInfo;
import com.zwdbj.server.adminserver.service.user.model.UserAuthInfoModel;
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
import com.zwdbj.server.adminserver.service.user.service.UserService;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import com.zwdbj.server.utility.common.shiro.JWTUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Service
public class MyShiroRealm extends AuthorizingRealm {
    @Autowired
    @Lazy
    private UserService userService;
    private Logger logger = LoggerFactory.getLogger(MyShiroRealm.class);

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JWTToken;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //TODO 增加数据缓存
        String token = (String)authenticationToken.getCredentials();
        String id = JWTUtil.getId(token);
        if (id == null) {
            throw new AuthenticationException("token invalid");
        }
        if (!JWTUtil.verify(token, id)) {
            throw new AuthenticationException("token invalid");
        }
        ServiceStatusInfo<UserAuthInfoModel> checkStatus = userService.checkUserAuth(Long.parseLong(id));
        if (!checkStatus.isSuccess()) {
            throw new AuthenticationException(checkStatus.getMsg());
        }
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        String url = request.getRequestURI();
        List<String> roles = checkStatus.getData().getRoles();
        logger.info("用户{"+id+"}的角色是{"+roles.toString()+"},请求url{"+url+"}");
            if (roles.size()==0) {
                throw new AuthenticationException("未被授权访问");
            } else if (roles.size()==1) {
                if (roles.contains(RoleIdentity.NORMAL_ROLE)) throw new AuthenticationException("未被授权访问");
            }
        return new SimpleAuthenticationInfo(token, token, "my_realm");
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //TODO 增加数据缓存
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        String username = JWTUtil.getId(principalCollection.toString());
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
