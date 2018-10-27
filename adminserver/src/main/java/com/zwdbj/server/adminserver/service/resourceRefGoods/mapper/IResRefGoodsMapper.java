package com.zwdbj.server.adminserver.service.resourceRefGoods.mapper;

import org.apache.ibatis.annotations.*;

@Mapper
public interface IResRefGoodsMapper {
    @Select("select goods from core_resourceRefGoods where resourceId=#{resId}")
    String getGoods(@Param("resId") long resId);
    @Update("update core_resourceRefGoods set goods=#{goods} where resourceId=#{resId}")
    long update(@Param("resId") long resId,@Param("goods") String goods);
    @Insert("insert into core_resourceRefGoods(id,resourceId,resourceType,goods) values(" +
            "#{id}," +
            "#{resId}," +
            "#{type}," +
            "#{goods})")
    long add(@Param("id") long id,
             @Param("resId") long resId,
             @Param("goods") String goods,
             @Param("type") int type);
}
