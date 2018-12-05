package com.zwdbj.server.shop_admin_service.offlineStoreExtraServices.mapper;


import com.zwdbj.server.shop_admin_service.offlineStoreExtraServices.model.OfflineStoreExtraServices;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface OfflineStoreExtraServicesMapper {
    @Insert("insert into o2o_offlineStoreExtraServices (id,offlineStoreId,extraServiceId,status,notes) " +
            "values(#{id},#{offlineStoreExtraServices.offlineStoreId},#{offlineStoreExtraServices.extraServiceId}," +
            "#{offlineStoreExtraServices.status},#{offlineStoreExtraServices.notes})")
    Long create(@Param("id")Long id, @Param("offlineStoreExtraServices") OfflineStoreExtraServices offlineStoreExtraServices);

    @Update("update o2o_offlineStoreExtraServices set isDeleted=1,deleteTIme=now() where id=#{id}")
    Long deleteById(@Param("id") Long id);

    @Update("update o2o_offlineStoreExtraServices set offlineStoreId=#{offlineStoreExtraServices.offlineStoreId}," +
            "extraServiceId=#{offlineStoreExtraServices.extraServiceId},status=#{offlineStoreExtraServices.status}," +
            "notes=#{offlineStoreExtraServices.notes} where id=#{offlineStoreExtraServices.id}")
    Long update(@Param("offlineStoreExtraServices") OfflineStoreExtraServices offlineStoreExtraServices);

    @Select("select * from o2o_offlineStoreExtraServices where isDeleted=0 order by createTime")
    List<OfflineStoreExtraServices> select();

    @Select("select * from o2o_offlineStoreExtraServices where id=#{id} order by createTime")
    OfflineStoreExtraServices selectById(@Param("id") Long id);


}
