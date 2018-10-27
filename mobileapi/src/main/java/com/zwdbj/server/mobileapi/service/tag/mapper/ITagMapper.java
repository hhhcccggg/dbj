package com.zwdbj.server.mobileapi.service.tag.mapper;
import com.zwdbj.server.mobileapi.service.tag.model.*;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ITagMapper {
    @SelectProvider(type = TagSqlProvider.class,method = "search")
    List<TagDto> search(@Param("input") TagSearchInput input);
}
