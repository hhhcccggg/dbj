package com.zwdbj.server.adminserver.service.shop.service.offlineStoreOpeningHour.mapper;

import com.zwdbj.server.adminserver.service.shop.service.offlineStoreOpeningHour.model.OfflineStoreOpeningHours;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface OfflineStoreOpeningHoursMapper {
    @Insert("insert into o2o_offlineStoreOpeningHours (id,day,storeId,openTime,closeTime)" +
            "values(#{id},#{offlineStoreOpeningHours.day},#{offlineStoreOpeningHours.storeId}," +
            "#{offlineStoreOpeningHours.openTime}," +
            "#{offlineStoreOpeningHours.closeTime})")
    Long create(@Param("id") Long id, @Param("offlineStoreOpeningHours") OfflineStoreOpeningHours offlineStoreOpeningHours);

    @Update("update o2o_offlineStoreOpeningHours set day=#{offlineStoreOpeningHours.day}," +
            "storeId=#{offlineStoreOpeningHours.storeId}," +
            "openTime=#{offlineStoreOpeningHours.openTime}," +
            "closeTime=#{offlineStoreOpeningHours.closeTime}" +
            " where id=#{offlineStoreOpeningHours.id}")
    Long update(@Param("offlineStoreOpeningHours") OfflineStoreOpeningHours offlineStoreOpeningHours);

    @Update("update o2o_offlineStoreOpeningHours set isDeleted=1,deleteTime=now() where id=#{id} ")
    Long deleteById(@Param("id") Long id);

    @Select("select * from o2o_offlineStoreOpeningHours where isDeleted=0 order by createTime")
    List<OfflineStoreOpeningHours> select1();

    @Select("select id,day,storeId,openTime,closeTime from o2o_offlineStoreOpeningHours where isDeleted=0 and storeId=#{storeId} order by createTime")
    List<OfflineStoreOpeningHours> select(@Param("storeId") long storeId);

    @Select("select * from o2o_offlineStoreOpeningHours where id=#{id} order by createTime")
    OfflineStoreOpeningHours selectById(@Param("id") Long id);

}
