package com.zwdbj.server.service.dailyIncreaseAnalysises.mapper;

import org.apache.ibatis.annotations.*;

import java.util.Date;


@Mapper
public interface IDailyIncreaseAnalysisesMapper {

    @Select("select count(createTime) from core_dailyIncreaseAnalysises where date(createTime)=curDate() and type='FAKE'")
    int isExistToday();

    @Insert("insert into core_dailyIncreaseAnalysises(id,type) values(#{id},'FAKE')")
    int everydayInsertTime(@Param("id") Long id);

    @Update("update core_dailyIncreaseAnalysises set newUsers=#{newUsers},newVideos=#{newVideos} where date(createTime)=curDate()-1 and type='FAKE'")
    int everyIncreasedUsersAndVideos(@Param("newUsers") Long increasedUsers, @Param("newVideos") Long increasedVideos);

    @Update("update core_dailyIncreaseAnalysises set newVideos=#{newVideos} where date(createTime)=curDate()-1 and type='FAKE'")
    int updateVideoNum(@Param("newVideos") int videoNum);

//    @Select("select sum(newUsers) as growthed,createTime from core_dailyIncreaseAnalysises where  createTime between date_add(now(),INTERVAL -1 HOUR) and now()")
//    Long userGrowthAd();

    //    @Select("select sum(newVideos) as growthed,createTime from core_dailyIncreaseAnalysises where createTime between date_add(now(),INTERVAL -1 HOUR) and now()")
//    Long videoGrowthAd();
    @Insert("insert into core_dailyIncreaseAnalysises (id,createTime,newUsers,newVideos,type)" +
            " values(#{id},#{createTime},#{userDayGrowth},#{videoDayGrowth},'TRUTH')")
    Long createDaily(@Param("id") Long id, @Param("createTime") Date createTime, @Param("userDayGrowth") int userDayGrowth, @Param("videoDayGrowth") int videoDayGrowth);

}
