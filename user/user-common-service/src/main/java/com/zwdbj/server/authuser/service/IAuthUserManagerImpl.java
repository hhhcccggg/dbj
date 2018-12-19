package com.zwdbj.server.authuser.service;

import com.zwdbj.server.authuser.mapper.AuthUserMapper;
import com.zwdbj.server.tokencenter.IAuthUserManager;
import com.zwdbj.server.tokencenter.model.AuthUser;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Service
public class IAuthUserManagerImpl implements IAuthUserManager {
    @Resource
    private AuthUserMapper authUserMapper;


    @Override
    public AuthUser get(String id) {
        AuthUser authUser = new AuthUser();

        authUser = authUserMapper.findAuthUser(id);
        String[] roleNames = authUserMapper.findRoleNames(id);
        authUser.setRoles(roleNames);
        String roles = String.join(",", roleNames);
        String[] permissionNames = authUserMapper.findPermissionName(roles);
        authUser.setPermissions(permissionNames);
        return authUser;

    }
}
