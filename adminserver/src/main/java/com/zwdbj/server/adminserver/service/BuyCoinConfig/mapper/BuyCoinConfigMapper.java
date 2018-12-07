package com.zwdbj.server.adminserver.service.BuyCoinConfig.mapper;

import com.zwdbj.server.adminserver.service.BuyCoinConfig.model.BuyCoinConfig;
import com.zwdbj.server.adminserver.service.BuyCoinConfig.model.BuyCoinConfigAdd;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface BuyCoinConfigMapper {

    @Select("select * from core_basic_buyCoinConfigs where isDeleted=0 ")
    List<BuyCoinConfig> searchAll();

    @Select("select * from core_basic_buyCoinConfigs where id=#{id}")
    BuyCoinConfig searchById(@Param("id") Long id);

    @Update("update core_basic_buyCoinConfigs set isDeleted=1, deleteTime=now() where id=#{id}")
    Long deleteById(@Param("id") Long id);

    @Update("update core_basic_buyCoinConfigs set coins=#{buyCoinConfig.coins}," +
            "rmbs=#{buyCoinConfig.rmbs},title=#{buyCoinConfig.title}," +
            "orderIndex=#{buyCoinConfig.orderIndex}")
    Long update(@Param("buyCoinConfig") BuyCoinConfig buyCoinConfig);

    @Insert("insert into core_basic_buyCoinConfigs(id,coins,rmbs,title,orderIndex) " +
            "values(#{id},#{buyCoinConfig.coins},#{buyCoinConfig.rmbs},#{buyCoinConfig.title},#{buyCoinConfig.orderIndex}) ")
    Long create(@Param("id") Long id, @Param("buyCoinConfig") BuyCoinConfigAdd buyCoinConfig);


}
