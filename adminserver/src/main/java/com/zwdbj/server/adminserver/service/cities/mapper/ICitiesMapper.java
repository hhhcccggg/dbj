package com.zwdbj.server.adminserver.service.cities.mapper;

import com.zwdbj.server.adminserver.service.cities.model.Cities;
import com.zwdbj.server.adminserver.service.cities.model.LevelType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ICitiesMapper {

    @Select("select * from core_cities where level = #{levelType} and parentId=#{parenId}")
    List<Cities> selectCondition(@Param("levelType") LevelType levelType, @Param("parenId") long parenId);

    @Select("select name from core_cities where id=#{cityId}")
    String selectCityName(@Param("cityId") long cityId);


}
