package com.zwdbj.server.adminserver.service.music.mapper;

import com.zwdbj.server.adminserver.service.music.model.AdMusicInput;
import org.apache.ibatis.jdbc.SQL;

import java.util.Map;

public class MusicSqlProvider {
    public String videoMusicListAd(Map params){
        AdMusicInput input = (AdMusicInput) params.get("input");
        SQL sql = new SQL()
                .SELECT("m.coverUrl,m.title,m.isDeleted,m.duration,count(v.musicId) a")
                .FROM("core_musics m,core_videos v")
                .WHERE("m.id=v.musicId");
        if (input.getIsDeleted()!=-1){
            sql.WHERE("m.isDeleted=#{input.isDeleted}");
        }
        if (input.getKeywords()!=null && input.getKeywords().length()>0){
            sql.WHERE(String.format("m.title like '%s'",("%"+input.getKeywords()+"%")));
        }
        sql.GROUP_BY("v.musicId").ORDER_BY("a desc");
        return sql.toString();
    }
}
