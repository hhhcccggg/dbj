package com.zwdbj.server.mobileapi.service.userAssets.mapper;

import com.zwdbj.server.mobileapi.service.userAssets.model.UserAssetModel;
import org.apache.ibatis.annotations.*;

@Mapper
public interface IUserAssetMapper {
    @Select("select * from core_userAssets where userId=#{userId}")
    UserAssetModel getCoinsByUserId(long userId);

    @Update("update core_userAssets set coins=#{coins},remainBalance=#{remainBalance} where userId=#{userId}")
    int updateUserAsset(@Param("userId") long userId,@Param("coins") long coins,@Param("remainBalance") long remainBalance);
    @Insert("insert into core_userAssets(id,coins,remainBalance,userId) values(#{id},0,0,#{userId})")
    int greatUserAsset(@Param("id")long id,@Param("userId")long UserId);
    @Select("select count(id) from core_userAssets where userId=#{userId}")
    int userAssetIsExistOrNot(@Param("userId")long userId);
}
