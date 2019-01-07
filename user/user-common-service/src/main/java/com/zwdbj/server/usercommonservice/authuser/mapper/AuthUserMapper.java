package com.zwdbj.server.usercommonservice.authuser.mapper;

import com.zwdbj.server.tokencenter.model.AuthUser;
import com.zwdbj.server.usercommonservice.authuser.model.TenantInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface AuthUserMapper {
    @Select("SELECT u.id,u.username,u.email,u.phone,u.islocked FROM core_users as u where u.id=#{id}")
    AuthUser findAuthUser(String id);

    @Select("select ur.rolename from core_userRoles as ur where ur.userid=#{id}")
    String[] findRoleNames(String id);

    @Select("select DISTINCT rp.permissionname from core_rolePermissions as rp where rp.rolename like #{rolenames}")
    String[] findPermissionName(String rolenames);
    @Select("select tenant.* from core_users as u left join core_user_tenants as tenant on u.tenantId=tenant.id where u.id=#{userId} and tenant.id is not NULL")
    TenantInfo findTenant(@Param("userId") long userId);

}
