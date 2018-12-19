package com.zwdbj.server.adminserver.service.userAssets.mapper;


import com.zwdbj.server.adminserver.service.userAssets.model.EnCashMentDetailModel;
import com.zwdbj.server.adminserver.service.userAssets.model.UserAssets;
import com.zwdbj.server.adminserver.service.userAssets.model.UserCoinDetail;
import com.zwdbj.server.adminserver.service.userAssets.model.UserCoinType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface UserAssetsMapper {
    @Select("select *  from core_userAssets where userId=#{userId} ")
    UserAssets searchUserAssetsByUserId(@Param("userId") Long userId);

    @Select("select a.*,b.nickName from core_userAssets as a,core_users as b where a.userId=b.id ")
    List<UserAssets> searchAllUserAssets();

    @Select("select * from core_userCoinDetails ")
    List<UserCoinDetail> searchAllUserCoinDetail();

    @Select("select * from core_userCoinDetails where userId=#{userId}")
    List<UserCoinDetail> searchUserCoinDetailByUserId(@Param("userId") Long userId);


    @Select("select * from core_userCoinTypes")
    List<UserCoinType> searchAllUserCoinTypes();

    @Select("select * from core_userCoinTypes where userId=#{userId}")
    UserCoinType searchUserCoinTpyesByUserId(@Param("userId") Long userId);

    @Select("select * from core_enCashMentDetails where isAllowedEnCash=0 and status='REVIEWING'")
    List<EnCashMentDetailModel> getAllVerifyEnCashs();
    @Select("select * from core_enCashMentDetails where id=#{id}")
    EnCashMentDetailModel getVerifyEnCashById(@Param("id")long id);

    @Select("select uniqueId from core_enCashAccounts where id=#{id}")
    String getUniqueIdById(@Param("id")long id);

    @Update("update core_enCashMentDetails set isAllowedEnCash=#{isAllowedEnCash},status=#{status} where id=#{id}")
    int updateEnCashStatus(@Param("id")long id,@Param("status")String status,@Param("isAllowedEnCash")boolean isAllowedEnCash);

    @Update("update core_userCoinDetails set status=#{status} where tradeNo=#{tradeNo}")
    int updateCoinDetailStatus(@Param("tradeNo")String tradeNo,@Param("status")String status);

    @Update("update core_userCoinTypes set coins=coins+#{coins},lockedCoins=lockedCoins+#{lockedCoins} where userId=#{userId} and type=#{type}")
    int updateUserCoinTypeByUserId(@Param("userId")long userId,@Param("type")String type,@Param("coins")int coins,@Param("lockedCoins")int lockedCoins );

    @Update("update core_userAssets set coins=coins+#{coins} where userId=#{userId}")
    int updateUserCoinByUserId(@Param("userId")long userId,@Param("coins")int coins);
}
