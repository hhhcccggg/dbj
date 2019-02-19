package com.zwdbj.server.usercommonservice.authuser.service;

import com.zwdbj.server.usercommonservice.authuser.mapper.AuthUserMapper;
import com.zwdbj.server.tokencenter.IAuthUserManager;
import com.zwdbj.server.tokencenter.model.AuthUser;
import com.zwdbj.server.usercommonservice.authuser.model.TenantInfo;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Service
@Primary
public class AuthUserManagerImpl implements IAuthUserManager {
    @Resource
    private AuthUserMapper authUserMapper;


    @Override
    public AuthUser get(String id) {
        AuthUser authUser = new AuthUser();

        authUser = authUserMapper.findAuthUser(id);
        String[] roleNames = authUserMapper.findRoleNames(id);
        TenantInfo tenantInfo = authUserMapper.findTenant(Long.parseLong(id));
        if (roleNames!=null || roleNames.length!=0){
            authUser.setRoles(roleNames);
            String roles = String.join(",", roleNames);
            String[] permissionNames = authUserMapper.findPermissionName(roles);
            authUser.setPermissions(permissionNames);
        }

        if (tenantInfo!=null) {
            authUser.setTenantId(tenantInfo.getId());
            authUser.setIdentifyTenantName(tenantInfo.getIdentifyName());
            authUser.setLegalSubjectId(tenantInfo.getLegalSubjectId());
        }
        return authUser;

    }
}
