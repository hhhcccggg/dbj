package com.zwdbj.server.adminserver.service.dailyIncreaseAnalysises.service;

import com.zwdbj.server.adminserver.service.dailyIncreaseAnalysises.mapper.IDailyIncreaseAnalysisesMapper;
import com.zwdbj.server.adminserver.service.homepage.model.AdFindIncreasedInput;
import com.zwdbj.server.adminserver.service.homepage.model.AdUserOrVideoGrowthDto;
import com.zwdbj.server.utility.common.UniqueIDCreater;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DailyIncreaseAnalysisesService {
    @Autowired
    IDailyIncreaseAnalysisesMapper dailyIncreaseAnalysisesMapper;


    //后台首页
    public int isExistToday(){
        return this.dailyIncreaseAnalysisesMapper.isExistToday();
    }
    public int everydayInsertTime(){
        Long id = UniqueIDCreater.generateID();
        return this.dailyIncreaseAnalysisesMapper.everydayInsertTime(id);
    }

    public int everyIncreasedUsersAndVideos(Long increasedUsers,Long increasedVideos){
        return this.dailyIncreaseAnalysisesMapper.everyIncreasedUsersAndVideos(increasedUsers,increasedVideos);
    }


    public List<AdUserOrVideoGrowthDto> userGrowthAd(AdFindIncreasedInput input,boolean flag){
        List<AdUserOrVideoGrowthDto>  growthDtos = this.dailyIncreaseAnalysisesMapper.userGrowthAd(input,flag);
        return growthDtos;
    }

    public List<AdUserOrVideoGrowthDto> videoGrowthAd(AdFindIncreasedInput input,boolean flag){
        List<AdUserOrVideoGrowthDto> growthDtos = this.dailyIncreaseAnalysisesMapper.videoGrowthAd(input,flag);
        return growthDtos;
    }

    public long dau(){
        return this.dailyIncreaseAnalysisesMapper.dau();
    }
}
