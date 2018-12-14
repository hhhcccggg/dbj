package com.zwdbj.server.service.user.mapper;

import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;


@Mapper
public interface IUserMapper {
    @Insert("insert into core_users(id,phone,username,nickName,avatarUrl,password,loginType,isManualData,IsPhoneVerification) values(#{id},#{phone}," +
            "#{username},#{nickName},#{avatarUrl},#{password},2,true,true)")
    long newVestUser(@Param("phone") String phone, @Param("id") long id, @Param("password") String password, @Param("username") String username,
                     @Param("avatarUrl") String avatarUrl, @Param("nickName") String nickName);

    @Insert("insert into core_users(id,username,nickName,phone,avatarUrl,loginType,thirdOpenId,isManualData,IsPhoneVerification) values(#{id}," +
            "#{username},#{nickName},#{phone},#{avatarUrl},#{loginType},#{thirdOpenId},true,true)")
    long newThirdUsers(@Param("id") long id, @Param("username") String username, @Param("phone") String phone, @Param("avatarUrl") String avatarUrl,
                       @Param("nickName") String nickName, @Param("loginType") int loginType, @Param("thirdOpenId") String thirdOpenId);

    @Insert("insert into core_users(id,createTime,username,nickName,phone,avatarUrl,loginType,thirdOpenId,isManualData,IsPhoneVerification) " +
            "values(#{id},#{createTime},#{username},#{nickName},#{phone},#{avatarUrl},#{loginType},#{thirdOpenId},true,true)")
    long newThirdUsers2(@Param("id") long id, @Param("username") String username, @Param("phone") String phone, @Param("avatarUrl") String avatarUrl,
                        @Param("nickName") String nickName, @Param("loginType") int loginType, @Param("thirdOpenId") String thirdOpenId, @Param("createTime") Date createTime);

    @Select("select id  from core_users where isManualData=true")
    List<Long> getVestUserIds1();

    @Select("select id  from core_users where phone like '56%' and username='爪子用户'")
    List<Long> getVestUserIds2();

    @Select("select count(id) from core_users where date(createTime)=curDate()-1")
    Long everyIncreasedUsers();

    @UpdateProvider(type = UserSqlProvider.class, method = "updateField")
    long updateField(@Param("fields") String fields, @Param("id") long id);

    @Select("select id from core_users where totalFans=0 and totalMyFocuses=0 and isManualData=true")
    List<Long> getNoFollowersUser();

    @Select("select count(id) from core_users where phone=#{phone}")
    int phoneIsExist(@Param("phone") String phone);

    @Select("select id from core_users where phone is null and isManualData=true")
    List<Long> getNullPhone();

    @Select("select count(id) as growthed,createTime from core_users where isManualData=0 and createTime between date_add(now(),INTERVAL -1 HOUR) and now()")
    Long userGrowthAd();
}
