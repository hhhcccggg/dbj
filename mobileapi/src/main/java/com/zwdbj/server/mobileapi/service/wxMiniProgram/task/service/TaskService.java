package com.zwdbj.server.mobileapi.service.wxMiniProgram.task.service;

import com.zwdbj.server.mobileapi.service.wxMiniProgram.task.mapper.ITaskMapper;
import com.zwdbj.server.mobileapi.service.wxMiniProgram.task.model.TaskModel;
import com.zwdbj.server.utility.model.ServiceStatusInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {
    @Autowired
    private ITaskMapper taskMapper;

    public List<TaskModel> getUserTasks(long userId,String type){
        List<TaskModel> taskModels = this.taskMapper.getUserTasks(userId,type);
        return taskModels;
    }

    public ServiceStatusInfo<Integer> doUserTask(String id,String type){
        if ("DAILY".equals(type)){
            //暂定首次添加宠物的任务id为'firstAddPet_dbj'
            if ("firstAddPet_dbj".equals(id))return new ServiceStatusInfo<>(0,"",101);
            return new ServiceStatusInfo<>(0,"",102);
        }else if ("INVITATION".equals(type)){
            return new ServiceStatusInfo<>(0,"",201);
        }else {
            return new ServiceStatusInfo<>(0,"",301);
        }
    }
}
