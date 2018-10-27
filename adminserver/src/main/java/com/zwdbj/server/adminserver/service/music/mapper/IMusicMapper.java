package com.zwdbj.server.adminserver.service.music.mapper;

import com.zwdbj.server.adminserver.service.music.model.*;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface IMusicMapper {
    @Select("select * from core_musics")
    List<MusicModel> list();
    @Select("select * from core_musics where id=#{id}")
    MusicDto get(@Param("id") long id);

    @SelectProvider(type = MusicSqlProvider.class,method = "videoMusicListAd")
    List<AdMusicDto> videoMusicListAd(@Param("input")AdMusicInput input);

    @Insert("insert into core_musics(id,title,artist,coverUrl,musicUrl,duration,categoryId,creatorUserId) " +
            "values(#{id},#{input.title},#{input.artist},#{input.coverUrl},#{input.musicUrl},#{input.duration},#{input.categoryId},#{userId})")
    Long addVideoMusicAd(@Param("id")Long id,@Param("userId")Long userId,@Param("input")AdNewMusicInput input);
}
