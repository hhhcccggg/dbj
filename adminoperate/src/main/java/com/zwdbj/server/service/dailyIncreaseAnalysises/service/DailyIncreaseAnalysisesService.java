package com.zwdbj.server.service.dailyIncreaseAnalysises.service;

import com.zwdbj.server.service.dailyIncreaseAnalysises.mapper.IDailyIncreaseAnalysisesMapper;
import com.zwdbj.server.utility.common.UniqueIDCreater;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DailyIncreaseAnalysisesService {
    @Autowired
    IDailyIncreaseAnalysisesMapper dailyIncreaseAnalysisesMapper;


    //后台首页
    public int isExistToday() {
        return this.dailyIncreaseAnalysisesMapper.isExistToday();
    }

    public int everydayInsertTime() {
        Long id = UniqueIDCreater.generateID();
        return this.dailyIncreaseAnalysisesMapper.everydayInsertTime(id);
    }

    public int everyIncreasedUsersAndVideos(Long increasedUsers, Long increasedVideos) {
        return this.dailyIncreaseAnalysisesMapper.everyIncreasedUsersAndVideos(increasedUsers, increasedVideos);
    }

    public int updateVideoNum(int videoNum) {
        return this.dailyIncreaseAnalysisesMapper.updateVideoNum(videoNum);
    }

    public Long userGrowthAd() {
        Long userGrowthed = this.dailyIncreaseAnalysisesMapper.userGrowthAd();
        return userGrowthed;
    }

//    public Long videoGrowthAd() {
//        Long videoGrowthed = this.dailyIncreaseAnalysisesMapper.videoGrowthAd();
//        return videoGrowthed;
//    }

}
