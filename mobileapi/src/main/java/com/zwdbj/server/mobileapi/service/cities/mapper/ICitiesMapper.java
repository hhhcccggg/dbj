package com.zwdbj.server.mobileapi.service.cities.mapper;

import com.zwdbj.server.mobileapi.service.cities.model.Cities;
import com.zwdbj.server.mobileapi.service.cities.model.LevelType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ICitiesMapper {

    @Select("select * from core_cities where level = #{levelType} and parentId=#{parenId}")
    List<Cities> selectCondition(@Param("levelType") LevelType levelType, @Param("parenId")long parenId);
}
