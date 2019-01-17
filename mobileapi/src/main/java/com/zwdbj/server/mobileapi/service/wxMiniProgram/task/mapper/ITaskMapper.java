package com.zwdbj.server.mobileapi.service.wxMiniProgram.task.mapper;

import com.zwdbj.server.mobileapi.service.wxMiniProgram.task.model.TaskModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ITaskMapper {

    @Select("select t.*,ut.userId as userId,ut.state as state from core_tasks t " +
            "left join core_userTasks ut on ut.taskId=u.id " +
            "where t.type=#{type} and ut.userId=#{userId}")
    List<TaskModel>  getUserTasks(@Param("userId")long userId,@Param("type")String type);
}
