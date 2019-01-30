package com.zwdbj.server.mobileapi.service.rankingList.mapper;

import com.zwdbj.server.mobileapi.service.rankingList.model.RankingListInfo;
import com.zwdbj.server.mobileapi.service.rankingList.model.Recommend;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface RankingListMapper {

    @Select("SELECT u.id as userId,u.nickName,u.avatarUrl,ua.userId,ua.totalCoins ,@rownum:=@rownum+1 as rank" +
            "            from(SELECT @rownum:=0) r, core_userAssets as ua ,core_users as u WHERE ua.userId=u.id ORDER BY ua.totalCoins DESC  LIMIT 0,20")
    List<RankingListInfo> searchTotalRank();

    @Select("SELECT u.id as userId,u.nickName,u.avatarUrl,t.totalCoins,t.rank FROM " +
            "(SELECT ua.userId,ua.totalCoins ,@rownum:=@rownum+1 as rank" +
            "            from(SELECT @rownum:=0) r, core_userAssets as ua ORDER BY ua.totalCoins DESC ) as t" +
            ",core_users as u WHERE u.id=t.userId AND t.userId=#{userId}")
    RankingListInfo searchByUser(@Param("userId") long userId);

    @Select("SELECT u.id as userId,u.nickName,u.avatarUrl,t.totalCoins,@rownum:=@rownum+1 as rank FROM (SELECT @rownum:=0) r," +
            "(SELECT ua.userId,ua.totalCoins " +
            "            from core_userAssets as ua,(SELECT userId FROM core_followers WHERE followerUserId=#{userId} ) as f WHERE ua.userId =f.userId  ORDER BY ua.totalCoins DESC " +
            ") as t" +
            ",core_users as u WHERE u.id=t.userId ")
    List<RankingListInfo> searchFriendRank(@Param("userId") long userId);

    @Select("select u.id as userId,u.nickName,u.avatarUrl from core_users as u " +
            "where u.id not in (SELECT userId FROM core_followers WHERE followerUserId=#{userId}) order by createTime DESC LIMIT 0,20")
    List<Recommend> recommend(@Param("userId") long userId);


}
