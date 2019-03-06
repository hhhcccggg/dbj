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

    @Update("update o2o_offlineStoreExtraServices set isDeleted=1,deleteTIme=now() where extraServiceId=#{extraServiceId} and storeId=#{storeId}")
    Long deleteById(@Param("extraServiceId") Long extraServiceId, @Param("storeId") long storeId);

    @Update("update o2o_offlineStoreExtraServices set " +
            "status=#{offlineStoreExtraServices.status}," +
            "notes=#{offlineStoreExtraServices.notes} where storeId=#{offlineStoreExtraServices.storeId} " +
            "and extraServiceId=#{offlineStoreExtraServices.extraServiceId} ")
    Long update(@Param("offlineStoreExtraServices") OfflineStoreExtraServices offlineStoreExtraServices);

    @Select("select * from o2o_offlineStoreExtraServices where isDeleted=0 order by createTime")
    List<OfflineStoreExtraServices> select();

    @Select("select os.id,os.storeId,os.extraServiceId,os.status,os.notes,c.name as categoryName from o2o_offlineStoreExtraServices as os ," +
            "core_categories as c where os.storeId=#{storeId} and os.extraServiceId=c.id and isDeleted=0")
    List<OfflineStoreExtraServices> selectByofflineStoreId(@Param("storeId") Long storeId);


}
