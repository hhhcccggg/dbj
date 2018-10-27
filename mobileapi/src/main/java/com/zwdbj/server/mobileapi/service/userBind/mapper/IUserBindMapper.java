package com.zwdbj.server.mobileapi.service.userBind.mapper;

import com.zwdbj.server.mobileapi.service.user.model.BindThirdPartyAccountInput;
import com.zwdbj.server.mobileapi.service.userBind.model.UserThirdAccountBindDto;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface IUserBindMapper {
    @Select("select * from core_userThirdAccountBinds where userId=#{userId}")
    List<UserThirdAccountBindDto> list(@Param("userId") long userId);
    @Select("select * from core_userThirdAccountBinds where userId=#{userId} and accountType=#{type}")
    UserThirdAccountBindDto findByOpenId(@Param("userId") long userId, @Param("type") int type);
    @Delete("delete from core_userThirdAccountBinds where userId=#{userId} and accountType=#{type}")
    long delete(@Param("userId") long userId, @Param("type") int type);
    @Insert("insert into core_userThirdAccountBinds(id,userId,thirdOpenId,accountType,accessToken,exipreIn,nickName) " +
            "values(#{id},#{userId},#{input.openUserId},#{input.thirdType},#{input.accessToken},#{input.expireIn}," +
            "#{input.nickName})")
    long add(@Param("input") BindThirdPartyAccountInput input,@Param("userId") long userId,@Param("id") long id);
    @Update("update core_userThirdAccountBinds set thirdOpenId=#{input.openUserId}," +
            "accessToken=#{input.accessToken}," +
            "exipreIn=#{input.expireIn}," +
            "nickName=#{input.nickName}  where id=#{id}")
    long update(@Param("input") BindThirdPartyAccountInput input,@Param("id") long id);
    @Update("update core_userThirdAccountBinds set thirdOpenId=#{input.openUserId}," +
            "accessToken=#{input.accessToken}," +
            "exipreIn=#{input.expireIn}," +
            "nickName=#{input.nickName}  where userId=#{userId} and accountType=#{type}")
    long update2(@Param("input") BindThirdPartyAccountInput input,@Param("userId") long userId, @Param("type") int type);

    @Select("select count(id) from core_userThirdAccountBinds where thirdOpenId=#{thirdOpenId} and accountType=#{accountType}")
    int thirdIsExist(@Param("thirdOpenId")String thirdOpenId,@Param("accountType")int accountType);

    @Select("select * from core_userThirdAccountBinds where thirdOpenId=#{thirdOpenId} and accountType=#{accountType}")
    UserThirdAccountBindDto findUserByOpenId(@Param("thirdOpenId")String thirdOpenId,@Param("accountType")int accountType);
}
