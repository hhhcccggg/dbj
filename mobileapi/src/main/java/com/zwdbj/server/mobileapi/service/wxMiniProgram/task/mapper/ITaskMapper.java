package com.zwdbj.server.mobileapi.service.wxMiniProgram.task.mapper;

import com.zwdbj.server.mobileapi.service.wxMiniProgram.task.model.InviteesModel;
import com.zwdbj.server.mobileapi.service.wxMiniProgram.task.model.TaskModel;
import com.zwdbj.server.mobileapi.service.wxMiniProgram.task.model.UserTaskModel;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ITaskMapper {

    @Select("select t.*,ut.userId as userId,ut.state as state from core_tasks t " +
            "left join core_userTasks ut on ut.taskId=t.id " +
            "where t.type=#{type} and ut.userId=#{userId}")
    List<UserTaskModel> getUserTasks(@Param("userId")long userId, @Param("type")String type);
    @Select("select t.*,ut.userId as userId,ut.state as state from core_tasks t " +
            "left join core_userTasks ut on ut.taskId=t.id " +
            "where t.type=#{type} and ut.userId=#{userId} and to_days(createTime)=to_days(now())")
    List<UserTaskModel> getUserTasks1(@Param("userId")long userId, @Param("type")String type);

    @Select("select * from core_tasks where id=#{id} and isDeleted=0")
    TaskModel getTaskById(@Param("id")String id);

    @Insert("insert into core_userTasks(id,taskId,userId,coins,state,`desc`) " +
            "values(#{id},#{model.id},#{userId},#{model.coins},#{state},#{model.desc})")
    int addNewTaskById(@Param("id")long id,@Param("userId")long userId,@Param("state")String state,@Param("model")TaskModel taskModel);

    @Select("select ui.*,u.nickName as nickName," +
            "(select coins from core_userTasks ut where ut.userId=#{initiatorUserId} and state='DONE' and taskId='INVITENEWUSER' order by ut.id limit 0,1) as coins " +
            "from core_userInvitations ui " +
            "left join core_users u on u.id=ui.receivedUserId " +
            "where ui.initiatorUserId=#{initiatorUserId} and ui.state='PETS'")
    List<InviteesModel> findMyInvitees(@Param("initiatorUserId") long initiatorUserId);
}
