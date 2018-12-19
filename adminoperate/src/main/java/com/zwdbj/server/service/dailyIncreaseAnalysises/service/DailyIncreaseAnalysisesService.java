package com.zwdbj.server.service.dailyIncreaseAnalysises.service;

import com.zwdbj.server.service.dailyIncreaseAnalysises.mapper.IDailyIncreaseAnalysisesMapper;
import com.zwdbj.server.service.user.mapper.IUserMapper;
import com.zwdbj.server.service.user.service.UserService;
import com.zwdbj.server.service.video.service.VideoService;
import com.zwdbj.server.utility.common.UniqueIDCreater;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class DailyIncreaseAnalysisesService {
    @Autowired
    IDailyIncreaseAnalysisesMapper dailyIncreaseAnalysisesMapper;
    @Autowired
    UserService userService;
    @Autowired
    VideoService videoService;

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

    public long userGrowthAd() {
        long userGrowthed = this.userService.userGrowth();
        return userGrowthed;
    }

    public Long videoGrowthAd() {
        long videoGrowthed = this.videoService.videoGrowth();
        return videoGrowthed;
    }

    public void addUserAndVideoDayGrowth(Date date, int userNowDayGrowth, int videoNowDayGrowth) {
        Long id = UniqueIDCreater.generateID();
        this.dailyIncreaseAnalysisesMapper.createDaily(id, date, userNowDayGrowth, videoNowDayGrowth);
    }
}
