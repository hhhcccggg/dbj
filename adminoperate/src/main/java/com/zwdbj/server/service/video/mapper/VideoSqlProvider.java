package com.zwdbj.server.service.video.mapper;



import org.apache.ibatis.jdbc.SQL;

import java.util.Map;

public class VideoSqlProvider {

    public String updateVideoField(Map params) {
        Long id = (Long)params.get("id");
        String fields = (String)params.get("fields");
        SQL sql = new SQL()
                .UPDATE("core_videos")
                .SET(fields)
                .WHERE("id=#{id}");
        return sql.toString();
    }

}
