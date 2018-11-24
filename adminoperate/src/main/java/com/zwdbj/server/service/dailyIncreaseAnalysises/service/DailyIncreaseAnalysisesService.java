package com.zwdbj.server.service.dailyIncreaseAnalysises.service;

import com.zwdbj.server.service.dailyIncreaseAnalysises.mapper.IDailyIncreaseAnalysisesMapper;
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

}
