package com.zwdbj.server.adminserver.service.userTenant.mapper;

import com.zwdbj.server.adminserver.service.userTenant.model.UserTenantModel;
import com.zwdbj.server.adminserver.service.userTenant.model.UserTenantSearchInput;
import org.apache.ibatis.annotations.*;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Mapper
public interface IUserTenantMapper {
    @SelectProvider(type = UserTenantSqlProvider.class,method = "getUserTenants")
    List<UserTenantModel> getUserTenants(@Param("input")UserTenantSearchInput input);
    @Select("select count(id) from core_user_tenants where identifyName=#{identifyName}")
    int identifyNameIsExsit(@Param("identifyName")String identifyName);
    @Insert("insert into core_user_tenants(id,name,identifyName,legalSubjectId) " +
            "values(#{id},#{name},#{identifyName},#{legalSubjectId})")
    int addUserTenant(@Param("id")long id,@Param("name")String name,@Param("identifyName")String identifyName,@Param("legalSubjectId")long legalSubjectId);
    @Update("update core_user_tenants set name=#{name} where id=#{id}")
    int modifyUserTenant(@Param("id")long id,@Param("name")String name);
    @Select("select legalSubjectId from core_user_tenants where id=#{id}")
    long findLegalSubjectIdById(@Param("id")long id);
    @Select("select ut.*, u,nickName,u.phone from core_user_tenants ut left join core_users u on u.tenantId=ut.id where ut.id=#{id} and u.isSuper=1")
    UserTenantModel getUserTenantById(@Param("id")long id);
}
