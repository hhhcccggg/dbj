package com.zwdbj.server.mobileapi.service.userCoinDetails.mapper;

import com.zwdbj.server.mobileapi.service.userCoinDetails.model.UserCoinDetailAddInput;
import com.zwdbj.server.mobileapi.service.userCoinDetails.model.UserCoinDetailsModel;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface IUserCoinDetailsMapper {
    @Select("select * from core_userCoinDetails where userId=#{userId}")
    List<UserCoinDetailsModel> getUserCoinDetails(@Param("userId")long userId);
    @Insert("insert into core_userCoinDetails(id,title,num,extraData,type,userId,status) " +
            "values(#{id},#{input.title},#{input.num},#{input.extraData},#{input.type},#{userId},'PROCESSING')")
    int addUserCoinDetail(@Param("id")long id, @Param("userId")long userId, @Param("input")UserCoinDetailAddInput input);

}
