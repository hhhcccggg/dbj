package com.zwdbj.server.mobileapi.service.rankingList.mapper;

import com.zwdbj.server.mobileapi.service.rankingList.model.RankingListInfo;
import com.zwdbj.server.mobileapi.service.rankingList.model.Recommend;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface RankingListMapper {

    @Select("SELECT u.id as userId,u.nickName,u.avatarUrl,ua.totalCoins,(SELECT COUNT(*) FROM core_userAssets WHERE totalCoins>=ua.totalCoins) as rank " +
            "from core_userAssets as ua,core_users as u WHERE ua.userId=u.id ORDER BY ua.totalCoins DESC LIMIT 0,20")
    List<RankingListInfo> searchTotalRank();

    @Select("SELECT u.id as userId,u.nickName,u.avatarUrl,ua.totalCoins,(SELECT COUNT(*) FROM core_userAssets WHERE totalCoins>=ua.totalCoins )as rank " +
            "from core_userAssets as ua,core_users as u WHERE ua.userId=u.id and u.id=#{userId}")
    RankingListInfo searchByUser(@Param("userId") long userId);

    @Select("SELECT u.id as userId,u.nickName,u.avatarUrl,ua.totalCoins ,(SELECT COUNT(*) FROM core_userAssets WHERE totalCoins>=ua.totalCoins and " +
            "userId in (SELECT followerUserId FROM core_followers WHERE userId=#{userId})) as rank " +
            "from core_userAssets as ua,core_users as u  WHERE ua.userId=u.id and u.id in(SELECT followerUserId FROM core_followers WHERE userId=#{userId}) " +
            "ORDER BY  ua.totalCoins DESC,u.createTime")
    List<RankingListInfo> searchFriendRank(@Param("userId") long userId);

    @Select("select u.id as userId,u.nickName,u.avatarUrl from core_users as u where u.id not in (SELECT followerUserId FROM core_followers WHERE userId=#{userId}) order by createTime DESC LIMIT 0,20")
    List<Recommend> recommend(@Param("userId") long userId);


}
