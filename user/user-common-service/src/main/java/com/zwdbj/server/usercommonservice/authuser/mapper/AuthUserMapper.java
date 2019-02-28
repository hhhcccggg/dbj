package com.zwdbj.server.usercommonservice.authuser.mapper;

import com.zwdbj.server.tokencenter.model.AuthUser;
import com.zwdbj.server.usercommonservice.authuser.model.TenantInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;

@Mapper
public interface AuthUserMapper {
    @Select("SELECT u.id,u.username,u.email,u.phone,u.islocked,u.tenantId,u.type FROM core_users as u where u.id=#{id}")
    AuthUser findAuthUser(@Param("id") String id);
    @Select("select tenant.* from core_users as u left join core_user_tenants as tenant on u.tenantId=tenant.id where u.id=#{userId} and tenant.id is not NULL")
    TenantInfo findTenant(@Param("userId") long userId);

    @SelectProvider(type = AuthUserSqlProvider.class,method = "findRoles")
    String[] findRoleNames(@Param("id") String id, @Param("tenantId") long tenantId);
    @SelectProvider(type = AuthUserSqlProvider.class,method = "findPermissions")
    String[] findPermissionName(@Param("rolenames") String rolenames,@Param("tenantId") long tenantId);

}
