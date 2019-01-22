package com.zwdbj.server.mobileapi.service.wxMiniProgram.task.service;

import com.zwdbj.server.mobileapi.service.wxMiniProgram.task.mapper.ITaskMapper;
import com.zwdbj.server.mobileapi.service.wxMiniProgram.task.model.TaskModel;
import com.zwdbj.server.mobileapi.service.wxMiniProgram.task.model.UserTaskModel;
import com.zwdbj.server.utility.common.UniqueIDCreater;
import com.zwdbj.server.utility.model.ServiceStatusInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {
    @Autowired
    private ITaskMapper taskMapper;

    public List<UserTaskModel> getUserTasks(long userId, String type){
        List<UserTaskModel> userTaskModels = this.taskMapper.getUserTasks(userId,type);
        return userTaskModels;
    }

    public ServiceStatusInfo<Integer> doUserTask(String id,String type){
        if ("DAILY".equals(type)){
            if ("FIRSTADDPET".equals(id))return new ServiceStatusInfo<>(0,"",101);
            return new ServiceStatusInfo<>(0,"",102);
        }else if ("INVITATION".equals(type)){
            return new ServiceStatusInfo<>(0,"",201);
        }else {
            return new ServiceStatusInfo<>(0,"",301);
        }
    }

    public TaskModel getTaskById(String id){
        TaskModel taskModel = this.taskMapper.getTaskById(id);
        return taskModel;
    }
    public int addNewTaskById(String taskId,long userId,String state){
        TaskModel taskModel = this.getTaskById(taskId);
        long id = UniqueIDCreater.generateID();
        int result = this.taskMapper.addNewTaskById(id,userId,state,taskModel);
        return result;
    }
}
