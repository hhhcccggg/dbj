package com.zwdbj.server.mobileapi.service.userAssets.mapper;

import com.zwdbj.server.mobileapi.service.userAssets.model.*;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface IUserAssetMapper {
    @Select("select coins from core_userAssets where userId=#{userId}")
    Long getCoinsByUserId(long userId);

    @Update("update core_userAssets set coins=coins+#{coins} where userId=#{userId}")
    int updateUserAsset(@Param("userId") long userId,@Param("coins") long coins);
    @Insert("insert into core_userAssets(id,coins,remainBalance,userId) values(#{id},0,0,#{userId})")
    int greatUserAsset(@Param("id")long id,@Param("userId")long UserId);
    @Select("select count(id) from core_userAssets where userId=#{userId}")
    int userAssetIsExistOrNot(@Param("userId")long userId);

    //coinType
    @Select("select count(id) from core_userCoinTypes where userId=#{userId} and type=#{type} ")
    int userCoinTypeIsExist(@Param("userId")long userId,@Param("type")String type);
    @Select("select * from core_userCoinTypes where userId=#{userId} and type=#{type}")
    UserCoinTypeModel getUserCoinType(@Param("userId")long userId, @Param("type")String type);
    @Insert("insert into core_userCoinTypes(id,type,coins,userId) " +
            "values(#{id},#{type},0,#{userId})")
    int greatUserCoinType(@Param("id")long id,@Param("userId")long userId,@Param("type")String type);
    @Update("update core_userCoinTypes set coins=coins+#{num} where userId=#{userId} and type=#{type}")
    int updateUserCoinType(@Param("userId")long userId,@Param("type")String type,@Param("num")long num);

    //coinDetails
    @Select("select * from core_userCoinDetails where userId=#{userId} order by createTime desc")
    List<UserCoinDetailsModel> getUserCoinDetails(@Param("userId")long userId);
    @Insert("insert into core_userCoinDetails(id,title,num,extraData,type,userId,status) " +
            "values(#{id},#{input.title},#{input.num},#{input.extraData},#{input.type},#{userId},'PROCESSING')")
    int addUserCoinDetail(@Param("id")long id, @Param("userId")long userId, @Param("input") UserCoinDetailAddInput input);
    @Insert("insert into core_userCoinDetails(id,title,num,extraData,type,userId,status) " +
            "values(#{id},#{input.title},#{input.num},#{input.extraData},#{input.type},#{userId},'SUCCESS')")
    int addUserCoinDetailSuccess(@Param("id")long id, @Param("userId")long userId, @Param("input") UserCoinDetailAddInput input);

    @Update("update core_userCoinDetails set status=#{input.status},statusMsg=#{input.statusMsg} where id=#{input.id}")
    int updateUserCoinDetail(@Param("input") UserCoinDetailModifyInput input);

    @Select("select id,userId,num,status from core_userCoinDetails where id=#{id}")
    UserAssetNumAndStatus findUserCoinDetailById(long id);

    @Select("select * from core_basic_buyCoinConfigs where isDeleted=false")
    List<BuyCoinConfigModel> findAllBuyCoinConfigs();
}
