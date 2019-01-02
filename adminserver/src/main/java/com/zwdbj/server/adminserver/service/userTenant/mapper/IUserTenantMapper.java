package com.zwdbj.server.adminserver.service.userTenant.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface IUserTenantMapper {
    @Select("select count(id) from core_user_tenants where identifyName=#{identifyName}")
    int identifyNameIsExsit(@Param("identifyName")String identifyName);
    @Insert("insert into core_user_tenants(id,name,identifyName,legalSubjectId) " +
            "values(#{id},#{name},#{identifyName},#{legalSubjectId})")
    int addUserTenant(@Param("id")long id,@Param("name")String name,@Param("identifyName")String identifyName,@Param("legalSubjectId")long legalSubjectId);
}
