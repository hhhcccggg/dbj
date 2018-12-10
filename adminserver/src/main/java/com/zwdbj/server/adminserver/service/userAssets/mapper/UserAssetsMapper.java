package com.zwdbj.server.adminserver.service.userAssets.mapper;


import com.zwdbj.server.adminserver.service.userAssets.model.UserAssets;
import com.zwdbj.server.adminserver.service.userAssets.model.UserCoinDetail;
import com.zwdbj.server.adminserver.service.userAssets.model.UserCoinType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserAssetsMapper {
    @Select("select *  from core_userAssets where userId=#{userId} ")
    UserAssets searchUserAssetsByUserId(@Param("userId") Long userId);

    @Select("select * from core_userAssets ")
    List<UserAssets> searchAllUserAssets();

    @Select("select * from core_userCoinDetails ")
    List<UserCoinDetail> searchAllUserCoinDetail();

    @Select("select * from core_userCoinDetails where userId=#{userId}")
    UserCoinDetail searchUserCoinDetailByUserId(@Param("userId") Long userId);


    @Select("select * from core_userCoinTypes")
    List<UserCoinType> searchAllUserCoinTypes();

    @Select("select * from core_userCoinTypes where userId=#{userId}")
    UserCoinType searchUserCoinTpyesByUserId(@Param("userId") Long userId);
}
