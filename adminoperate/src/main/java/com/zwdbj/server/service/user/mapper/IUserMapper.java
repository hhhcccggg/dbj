package com.zwdbj.server.service.user.mapper;

import org.apache.ibatis.annotations.*;

import java.util.List;


@Mapper
public interface IUserMapper {
    @Insert("insert into core_users(id,phone,username,nickName,avatarUrl,password,loginType,isManualData,IsPhoneVerification) values(#{id},#{phone}," +
            "#{username},#{nickName},#{avatarUrl},#{password},2,true,true)")
    long newVestUser(@Param("phone")String phone,@Param("id") long id,@Param("password")String password,@Param("username")String username,
                     @Param("avatarUrl")String avatarUrl,@Param("nickName")String nickName);

    @Insert("insert into core_users(id,username,nickName,avatarUrl,loginType,thirdOpenId,isManualData,IsPhoneVerification) values(#{id}," +
            "#{username},#{nickName},#{avatarUrl},#{loginType},#{thirdOpenId},true,true)")
    long newThirdUsers(@Param("id") long id,@Param("username")String username,@Param("avatarUrl")String avatarUrl,
                       @Param("nickName")String nickName,@Param("loginType")int loginType,@Param("thirdOpenId")String thirdOpenId);
    @Select("select id  from core_users where isManualData=true")
    List<Long> getVestUserIds1();
    @Select("select id  from core_users where phone like '56%' and username='爪子用户'")
    List<Long> getVestUserIds2();
    @Select("select count(id) from core_users where date(createTime)=curDate()-1")
    Long everyIncreasedUsers();
    @UpdateProvider(type = UserSqlProvider.class,method = "updateField")
    long updateField(@Param("fields") String fields,@Param("id") long id);
    @Select("select id from core_users where totalFans=0 and totalMyFocuses=0 and isManualData=true")
    List<Long> getNoFollowersUser();
}
