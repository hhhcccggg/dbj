package com.zwdbj.server.service.dataVideos.mapper;

import com.zwdbj.server.service.dataVideos.model.DataVideosDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface IDataVideosMapper {

    @Select("select count(id) from data_videos_inventories where isPublish=false")
    int getDataVideos();

    @Update("update data_videos_inventories set isPublish=true where id=#{id}")
    int updateDataVideoStatus(@Param("id")String id);

    @Select("select * from data_videos_inventories where isPublish=false order by id limit 1")
    DataVideosDto getOneDataVideo();
}
