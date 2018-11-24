package com.zwdbj.server.service.dailyIncreaseAnalysises.mapper;

import org.apache.ibatis.annotations.*;


@Mapper
public interface IDailyIncreaseAnalysisesMapper {

    @Select("select count(createTime) from core_dailyIncreaseAnalysises where date(createTime)=curDate()")
    int isExistToday();

    @Insert("insert into core_dailyIncreaseAnalysises(id) values(#{id})")
    int everydayInsertTime(@Param("id") Long id);

    @Update("update core_dailyIncreaseAnalysises set newUsers=#{newUsers},newVideos=#{newVideos} where date(createTime)=curDate()-1")
    int everyIncreasedUsersAndVideos(@Param("newUsers") Long increasedUsers, @Param("newVideos") Long increasedVideos);


}
