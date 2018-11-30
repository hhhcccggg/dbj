package com.zwdbj.server.adminserver.service.tag.mapper;

import com.zwdbj.server.adminserver.model.ResourceOpenInput;
import com.zwdbj.server.adminserver.service.homepage.model.AdFindHotTagsDto;
import com.zwdbj.server.adminserver.service.tag.model.*;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ITagMapper {

    @SelectProvider(type = TagSqlProvider.class,method = "getVideoTagAd")
    List<AdVideoTagDto> getVideoTagAd(@Param("input") AdVideoTagInput input);

    @Insert("insert into core_tags (id,name,`desc`) values(#{id},#{name},#{desc})")
    Long addVideoTagAd(@Param("id") Long id,@Param("name")String name,@Param("desc")String desc);

    @Select("select count(*) from core_tags where name=#{name}")
    int  findTagByName(@Param("name")String name);

    @Select("select name as tagName,resNumber from core_tags order by resNumber desc  ")
    List<AdFindHotTagsDto> findHotTags();

    @Update("update core_tags set isHot=#{input.isOpen} where id=#{input.id}")
    int addHotTag(@Param("input")ResourceOpenInput<Long> input);
}
