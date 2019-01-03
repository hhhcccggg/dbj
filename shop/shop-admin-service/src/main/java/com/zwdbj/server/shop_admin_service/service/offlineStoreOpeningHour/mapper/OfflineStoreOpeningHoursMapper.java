package com.zwdbj.server.shop_admin_service.service.offlineStoreOpeningHour.mapper;

import com.zwdbj.server.shop_admin_service.service.offlineStoreOpeningHour.model.OfflineStoreOpeningHours;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface OfflineStoreOpeningHoursMapper {
    @Insert("insert into o2o_offlineStoreOpeningHours (id,day,offlineStoreId,openTime,closeTime)" +
            "values(#{id},#{offlineStoreOpeningHours.day},#{offlineStoreOpeningHours.offlineStoreId}," +
            "#{offlineStoreOpeningHours.openTime}," +
            "#{offlineStoreOpeningHours.closeTime})")
    Long create(@Param("id") Long id, @Param("offlineStoreOpeningHours") OfflineStoreOpeningHours offlineStoreOpeningHours);

    @Update("update o2o_offlineStoreOpeningHours set day=#{offlineStoreOpeningHours.day}," +
            "offlineStoreId=#{offlineStoreOpeningHours.offlineStoreId}," +
            "openTime=#{offlineStoreOpeningHours.openTime}," +
            "closeTime=#{offlineStoreOpeningHours.closeTime}" +
            " where id=#{offlineStoreOpeningHours.id}")
    Long update(@Param("offlineStoreOpeningHours") OfflineStoreOpeningHours offlineStoreOpeningHours);

    @Update("update o2o_offlineStoreOpeningHours set isDeleted=1,deleteTime=now() where id=#{id} ")
    Long deleteById(@Param("id") Long id);

    @Select("select * from o2o_offlineStoreOpeningHours where isDeleted=0 order by createTime")
    List<OfflineStoreOpeningHours> select();

    @Select("select * from o2o_offlineStoreOpeningHours where id=#{id} order by createTime")
    OfflineStoreOpeningHours selectById(@Param("id") Long id);

}
