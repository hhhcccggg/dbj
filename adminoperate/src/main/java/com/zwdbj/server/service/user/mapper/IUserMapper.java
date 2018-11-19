package com.zwdbj.server.service.user.mapper;

import org.apache.ibatis.annotations.*;

import java.util.List;


@Mapper
public interface IUserMapper {
    @Insert("insert into core_users(id,phone,username,nickName,avatarUrl,IsPhoneVerification) values(#{id},#{phone}," +
            "#{username},#{nickName},#{avatarUrl},true)")
    long newVestUser(@Param("phone")String phone,@Param("id") long id,@Param("username")String username,@Param("avatarUrl")String avatarUrl,@Param("nickName")String nickName);

    @Select("select id  from core_users where phone like '56%'")
    List<Long> getVestUserIds1();
    @Select("select id  from core_users where phone like '56%' and username='爪子用户'")
    List<Long> getVestUserIds2();
}
