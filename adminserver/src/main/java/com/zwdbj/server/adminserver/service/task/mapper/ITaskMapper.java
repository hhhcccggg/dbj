package com.zwdbj.server.adminserver.service.task.mapper;

import com.zwdbj.server.adminserver.service.task.model.TaskAddInput;
import com.zwdbj.server.adminserver.service.task.model.TaskModel;
import com.zwdbj.server.adminserver.service.task.model.TaskModifyInput;
import com.zwdbj.server.adminserver.service.task.model.TaskSearchInput;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ITaskMapper {
    @SelectProvider(type = TaskSqlProvider.class,method = "getTasks")
    List<TaskModel> getAllTasks(@Param("input")TaskSearchInput input);

    @Insert("insert into core_tasks(id,type,title,coins,desc) " +
            "values(#{input.id},#{input.type},#{input.title},#{input.coins},#{input.desc})")
    int addNewTask(@Param("input")TaskAddInput input);

    @Select("select * from core_tasks where id=#{id}")
    TaskModel getTaskById(@Param("id")String id);

    @Update("update core_tasks set title=#{input.title},coins=#{input.coins},desc=#{input.desc} where id=#{id}")
    int modifyTaskById(@Param("id")String id, @Param("input")TaskModifyInput input);

    @Update("update core_tasks set isDeleted=1 where id=#{id}")
    int delTaskById(@Param("id")String id);
}
