package com.zwdbj.server.mobileapi.service.tag.mapper;
import com.zwdbj.server.mobileapi.service.tag.model.*;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ITagMapper {
    @SelectProvider(type = TagSqlProvider.class,method = "search")
    List<TagDto> search(@Param("input") TagSearchInput input);

    @Select("select count(*) from core_tags where name=#{name}")
    int  findTagByName(@Param("name")String name);

    @Insert("insert into core_tags (id,name,resNumber) values(#{id},#{name},1)")
    Long addVideoTag(@Param("id") Long id,@Param("name")String name);

    @Update("update core_tags set resNumber=resNumber+1 where name=#{name}")
    int updateTagResNumber(@Param("name")String name);
}
