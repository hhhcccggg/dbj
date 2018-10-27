package com.zwdbj.server.adminserver.service.dailyIncreaseAnalysises.mapper;

import com.zwdbj.server.adminserver.service.homepage.model.AdFindIncreasedInput;
import com.zwdbj.server.adminserver.service.homepage.model.AdUserOrVideoGrowthDto;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface IDailyIncreaseAnalysisesMapper {

    @Select("select count(createTime) from core_dailyIncreaseAnalysises where date(createTime)=curDate()")
    int isExistToday();

    @Insert("insert into core_dailyIncreaseAnalysises(id) values(#{id})")
    int everydayInsertTime(@Param("id")Long id);

    @Update("update core_dailyIncreaseAnalysises set newUsers=#{newUsers},newVideos=#{newVideos} where date(createTime)=curDate()-1")
    int everyIncreasedUsersAndVideos(@Param("newUsers") Long increasedUsers,@Param("newVideos") Long increasedVideos);

    @SelectProvider(type =DailyincreaseaSqlProvider.class,method = "userGrowthAd")
    List<AdUserOrVideoGrowthDto> userGrowthAd(@Param("input")AdFindIncreasedInput input);

    @SelectProvider(type =DailyincreaseaSqlProvider.class,method = "videoGrowthAd")
    List<AdUserOrVideoGrowthDto> videoGrowthAd(@Param("input")AdFindIncreasedInput input);

}
