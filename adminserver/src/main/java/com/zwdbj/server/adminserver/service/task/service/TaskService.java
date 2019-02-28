package com.zwdbj.server.adminserver.service.task.service;

import com.zwdbj.server.adminserver.service.task.mapper.ITaskMapper;
import com.zwdbj.server.adminserver.service.task.model.TaskAddInput;
import com.zwdbj.server.adminserver.service.task.model.TaskModel;
import com.zwdbj.server.adminserver.service.task.model.TaskModifyInput;
import com.zwdbj.server.adminserver.service.task.model.TaskSearchInput;
import com.zwdbj.server.basemodel.model.ServiceStatusInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {
    @Autowired
    ITaskMapper taskMapper;

    public List<TaskModel> getAllTasks(TaskSearchInput input){
        List<TaskModel> taskModels = this.taskMapper.getAllTasks(input);
        return taskModels;
    }

    public ServiceStatusInfo<Integer> addNewTask(TaskAddInput input){
        try {
            int result = this.taskMapper.addNewTask(input);
            if (result==0)return new ServiceStatusInfo<>(1,"任务增加失败",result);
            return new ServiceStatusInfo<>(0,"",result);
        }catch (Exception e){
            return new ServiceStatusInfo<>(1,"任务增加失败:"+e.getMessage(),0);
        }
    }
    public ServiceStatusInfo<TaskModel> getTaskById(String id){
        TaskModel taskModel = this.taskMapper.getTaskById(id);
        if (taskModel==null)return new ServiceStatusInfo<>(1,"查询任务失败",null);
        return new ServiceStatusInfo<>(0,"",taskModel);
    }

    public ServiceStatusInfo<Integer> modifyTaskById(String id, TaskModifyInput input){
        try {
            int result = this.taskMapper.modifyTaskById(id,input);
            if (result==0)return new ServiceStatusInfo<>(1,"任务修改失败",result);
            return new ServiceStatusInfo<>(0,"",result);
        }catch (Exception e){
            return new ServiceStatusInfo<>(1,"任务修改失败:"+e.getMessage(),0);
        }
    }
    public ServiceStatusInfo<Integer> delTaskById(String id){
        try {
            int result = this.taskMapper.delTaskById(id);
            if (result==0)return new ServiceStatusInfo<>(1,"任务删除失败",result);
            return new ServiceStatusInfo<>(0,"",result);
        }catch (Exception e){
            return new ServiceStatusInfo<>(1,"任务删除失败:"+e.getMessage(),0);
        }
    }
}
