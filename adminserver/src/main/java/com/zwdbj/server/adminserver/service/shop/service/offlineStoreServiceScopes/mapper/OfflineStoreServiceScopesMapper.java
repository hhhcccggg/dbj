package com.zwdbj.server.adminserver.service.shop.service.offlineStoreServiceScopes.mapper;

import com.zwdbj.server.adminserver.service.shop.service.offlineStoreServiceScopes.model.OfflineStoreServiceScopes;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface OfflineStoreServiceScopesMapper {
    @Insert("insert into o2o_offlineStoreServiceScopes" +
            "(id,storeId,serviceScopeId,notes,status) values" +
            "(#{id},#{offlineStoreServiceScopes.storeId}," +
            "#{offlineStoreServiceScopes.serviceScopeId}," +
            "#{offlineStoreServiceScopes.notes},#{offlineStoreServiceScopes.status})")
    Long create(@Param("id") Long id, @Param("offlineStoreServiceScopes") OfflineStoreServiceScopes offlineStoreServiceScopes);

    @Update("update o2o_offlineStoreServiceScopes set " +
            "notes=#{offlineStoreServiceScopes.notes}," +
            "status=#{offlineStoreServiceScopes.status} " +
            "where storeId=#{offlineStoreServiceScopes.storeId} " +
            "and serviceScopeId=#{offlineStoreServiceScopes.serviceScopeId}")
    Long update(@Param("offlineStoreServiceScopes") OfflineStoreServiceScopes offlineStoreServiceScopes);


    @Update("update o2o_offlineStoreServiceScopes set isDeleted=1,deleteTime=now() where id=#{id}")
    Long deleteById(@Param("id") Long id);

    @Select("select * from o2o_offlineStoreServiceScopes where isDeleted=0 order by createTime")
    List<OfflineStoreServiceScopes> select();

    @Select(("select id,storeId,serviceScopeId,notes,status from o2o_offlineStoreServiceScopes where storeId=#{storeId} and isDeleted=0"))
    List<OfflineStoreServiceScopes> selectByofflineStoreId(@Param("storeId") Long storeId);


}
