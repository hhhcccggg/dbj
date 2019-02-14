package com.zwdbj.server.adminserver.service.shop.service.offlineStoreExtraServices.mapper;


import com.zwdbj.server.adminserver.service.shop.service.offlineStoreExtraServices.model.OfflineStoreExtraServices;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface OfflineStoreExtraServicesMapper {
    @Insert("insert into o2o_offlineStoreExtraServices (id,storeId,extraServiceId,status,notes) " +
            "values(#{id},#{offlineStoreExtraServices.storeId},#{offlineStoreExtraServices.extraServiceId}," +
            "#{offlineStoreExtraServices.status},#{offlineStoreExtraServices.notes})")
    Long create(@Param("id") Long id, @Param("offlineStoreExtraServices") OfflineStoreExtraServices offlineStoreExtraServices);

    @Update("update o2o_offlineStoreExtraServices set isDeleted=1,deleteTIme=now() where id=#{id}")
    Long deleteById(@Param("id") Long idd);

    @Update("update o2o_offlineStoreExtraServices set " +
            "status=#{offlineStoreExtraServices.status}," +
            "notes=#{offlineStoreExtraServices.notes} where storeId=#{offlineStoreExtraServices.storeId} " +
            "and extraServiceId=#{offlineStoreExtraServices.extraServiceId} ")
    Long update(@Param("offlineStoreExtraServices") OfflineStoreExtraServices offlineStoreExtraServices);

    @Select("select * from o2o_offlineStoreExtraServices where isDeleted=0 order by createTime")
    List<OfflineStoreExtraServices> select();

    @Select("select id,storeId,extraServiceId,status,notes from o2o_offlineStoreExtraServices where storeId=#{storeId} and isDeleted=0")
    List<OfflineStoreExtraServices> selectByofflineStoreId(@Param("storeId") Long storeId);


}
