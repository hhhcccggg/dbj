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

    @Select("select * from data_videos_inventories where isPublish=false and isTop100=false limit 1")
    DataVideosDto getOneDataVideo1();
    @Select("select * from data_videos_inventories where isPublish=false and isTop100=true limit 1")
    DataVideosDto getOneDataVideo2();
    @Select("select count(id) from data_videos_inventories where isTop100=true and isPublish=false")
    int getTopVideosNum();
}
