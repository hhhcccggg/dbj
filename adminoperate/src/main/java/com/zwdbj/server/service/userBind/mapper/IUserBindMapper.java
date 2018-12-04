package com.zwdbj.server.service.userBind.mapper;

import org.apache.ibatis.annotations.*;

import java.util.Date;


@Mapper
public interface IUserBindMapper {

    @Insert("insert into core_userThirdAccountBinds(id,userId,thirdOpenId,accountType,accessToken,exipreIn,nickName,isManualData) " +
            "values(#{id},#{userId},#{thirdOpenId},#{accountType},#{accessToken},7200,#{nickName},true)")
    int newThirdBind(@Param("id") long id,@Param("userId") long userId,@Param("thirdOpenId") String thirdOpenId,
                     @Param("accountType") int type,@Param("accessToken") String accessToken,@Param("nickName") String nickName);
    @Insert("insert into core_userThirdAccountBinds(id,userId,thirdOpenId,accountType,accessToken,exipreIn,nickName,isManualData) " +
            "values(#{id},#{userId},#{thirdOpenId},#{accountType},#{accessToken},7200,#{nickName},true)")
    int newThirdBind2(@Param("id") long id, @Param("userId") long userId, @Param("thirdOpenId") String thirdOpenId,
                      @Param("accountType") int type, @Param("accessToken") String accessToken, @Param("nickName") String nickName, @Param("createTime")Date createTime);
}
