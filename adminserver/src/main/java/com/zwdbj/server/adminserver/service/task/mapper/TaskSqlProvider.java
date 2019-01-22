package com.zwdbj.server.adminserver.service.task.mapper;

import com.zwdbj.server.adminserver.service.task.model.TaskSearchInput;
import org.apache.ibatis.jdbc.SQL;

import java.util.Map;

public class TaskSqlProvider {
    public String getTasks(Map params){
        TaskSearchInput input = (TaskSearchInput)params.get("input");
        SQL sql = new SQL()
                .SELECT("*")
                .FROM("core_tasks")
                .WHERE("isDeleted=0");
        if (input.getKeyWords() != null && input.getKeyWords().length()>0){
            sql.WHERE(String.format("title like '%s'",("%"+input.getKeyWords()+"%")))
                    .OR().WHERE(String.format("id like '%s'",("%"+input.getKeyWords()+"%")));
        }
        if (input.getType() !=null && input.getType().length()>0){
            sql.WHERE("`type`=#{input.type}");
        }
        if (input.getStartTime() !=null  && input.getStartTime().length()!=0 && input.getEndTime() !=null && input.getEndTime().length()!=0){
            sql.WHERE(String.format("createTime between '%s' and '%s'",input.getStartTime(),input.getEndTime()));
        }
        sql.ORDER_BY("createTime desc");
        return sql.toString();
    }
}
