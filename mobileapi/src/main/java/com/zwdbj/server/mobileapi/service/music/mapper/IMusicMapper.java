package com.zwdbj.server.mobileapi.service.music.mapper;

import com.zwdbj.server.mobileapi.service.music.model.*;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface IMusicMapper {
    @Select("select * from core_musics")
    List<MusicModel> list();
    @Select("select * from core_musics where id=#{id}")
    MusicDto get(@Param("id") long id);
}
