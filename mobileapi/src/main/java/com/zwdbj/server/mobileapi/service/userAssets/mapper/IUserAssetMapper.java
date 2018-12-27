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
    @Update("update core_userCoinTypes set coins=coins+#{num},lockedCoins=lockedCoins+#{lockedCoins} where userId=#{userId} and type=#{type}")
    int updateUserCoinTypeForEnCash(@Param("userId")long userId,@Param("type")String type,@Param("num")long num,@Param("lockedCoins")int lockedCoins);

    //coinDetails
    @Select("select * from core_userCoinDetails where userId=#{userId} order by createTime desc")
    List<UserCoinDetailsModel> getUserCoinDetails(@Param("userId")long userId);
    @Insert("insert into core_userCoinDetails(id,title,num,extraData,type,userId,status,tradeNo,tradeType) " +
            "values(#{id},#{input.title},#{input.num},#{input.extraData},#{input.type},#{userId},#{input.status},#{input.tradeNo},#{input.tradeType})")
    int addUserCoinDetail(@Param("id")long id, @Param("userId")long userId, @Param("input") UserCoinDetailAddInput input);

    @Insert("insert into core_userCoinDetails(id,title,num,extraData,type,userId,status,tradeNo) " +
            "values(#{id},#{input.title},#{input.num},#{input.extraData},#{input.type},#{userId},'PROCESSING',#{tradeNo})")
    int addUserCoinDetailForEnCash(@Param("id")long id, @Param("userId")long userId, @Param("input") UserCoinDetailAddInput input,@Param("tradeNo")String tradeNo);


    @Select("select count(id) from core_userCoinDetails where tradeNo=#{tradeNo} and tradeType=#{tradeType}")
    int findCoinDetailByTrade(@Param("tradeNo")String tradeNo,@Param("tradeType")String tradeType);
    @Insert("insert into core_userCoinDetails(id,title,num,extraData,type,userId,status) " +
            "values(#{id},#{input.title},#{input.num},#{input.extraData},#{input.type},#{userId},'SUCCESS')")
    int addUserCoinDetailSuccess(@Param("id")long id, @Param("userId")long userId, @Param("input") UserCoinDetailAddInput input);



    @Update("update core_userCoinDetails set status=#{input.status},statusMsg=#{input.statusMsg}, tradeNo=#{input.tradeNo} where id=#{input.id}")
    int updateUserCoinDetail(@Param("input") UserCoinDetailModifyInput input);

    @Select("select id,userId,num,status from core_userCoinDetails where id=#{id}")
    UserAssetNumAndStatus findUserCoinDetailById(long id);

    @Select("select * from core_basic_buyCoinConfigs where isDeleted=false and type=#{type}")
    List<BuyCoinConfigModel> findAllBuyCoinConfigs(@Param("type") String type);

    //视频

    @Select("select v.*,u.nickName from core_video_videoTipDetails as v," +
            "(select id,nickName from core_users) as u where v.videoId=#{videoId} and v.userId=u.id")
    List<VideoTipDetails> findVideoTipDetails(@Param("videoId") Long videoId);

    @Insert("insert into core_video_videoTipDetails(id,videoId,userId,tipCoin) values(#{id},#{videoId},#{userId},#{tipCoin})")
    int addVideoTipDetail(@Param("id") long id,@Param("videoId") long videoId,@Param("userId") long userId,@Param("tipCoin") int tipCoin);

    //提现：绑定第三方支付平台

    @Insert("insert into core_enCashAccounts(id,userId,type,uniqueId,name,avatarUrl,accessToken,expireIn) " +
            "values(#{id},#{userId},#{input.type},#{input.uniqueId},#{input.name},#{input.avatarUrl},#{input.accessToken},#{input.expireIn})")
    int bandingThird(@Param("id")long id,@Param("userId")long userId,@Param("input")BandingThirdInput input);

    @Delete("delete from core_enCashAccounts where id=#{id}")
    int unBandingThird(@Param("id")long id);

    @Select("select * from core_enCashAccounts where userId=#{userId}")
    List<EnCashAccountModel> getMyEnCashAccounts(@Param("userId")long userId);

    @Select("select * from core_enCashAccounts where id=#{id}")
    EnCashAccountModel getEnCashAccountById(@Param("id")long id);

    @Insert("insert into core_enCashMentDetails(id,userId,coins,rmbs,payAccountId,payAccountType,status) " +
            "values(#{id},#{userId},#{coins},10,#{input.payAccountId},#{input.payAccountType},'REVIEWING')")
    int addEnCashDetail(@Param("id")long id,@Param("userId")long userId,@Param("coins")int coins,@Param("input")EnCashInput input);

    @Select("select * from core_basic_buyCoinConfigs where productId=#{productId} and type=#{type}")
    BuyCoinConfigModel findCoinConfigByProductId(@Param("productId")String productId,@Param("type")String type);


}
