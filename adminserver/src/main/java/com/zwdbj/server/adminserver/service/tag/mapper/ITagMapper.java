package com.zwdbj.server.adminserver.service.tag.mapper;

import com.zwdbj.server.adminserver.service.homepage.model.AdFindHotTagsDto;
import com.zwdbj.server.adminserver.service.tag.model.*;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ITagMapper {
    @SelectProvider(type = TagSqlProvider.class,method = "search")
    List<TagDto> search(@Param("input") TagSearchInput input);

    @SelectProvider(type = TagSqlProvider.class,method = "getVideoTagAd")
    List<AdVideoTagDto> getVideoTagAd(@Param("input") AdVideoTagInput input);

    @Insert("insert into core_tags (id,name) values(#{id},#{name})")
    Long addVideoTagAd(@Param("id") Long id,@Param("name")String name);
    @Insert("insert into core_tags (id,name,resNumber) values(#{id},#{name},1)")
    Long addVideoTag(@Param("id") Long id,@Param("name")String name);

    @Select("select count(*) from core_tags where name=#{name}")
    int  findTagByName(@Param("name")String name);

    @Select("select resNumber from core_tags where name=#{name}")
    Long everyTagCount(@Param("name")String name);

    @Update("update core_tags set resNumber=resNumber+1 where name=#{name}")
    int updateTagResNumber(@Param("name")String name);

    @Select("select name as tagName,resNumber from core_tags order by resNumber desc  ")
    List<AdFindHotTagsDto> findHotTags();
}
