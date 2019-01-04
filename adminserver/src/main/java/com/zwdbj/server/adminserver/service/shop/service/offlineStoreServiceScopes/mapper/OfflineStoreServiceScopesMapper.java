package com.zwdbj.server.adminserver.service.shop.service.offlineStoreServiceScopes.mapper;

import com.zwdbj.server.adminserver.service.shop.service.offlineStoreServiceScopes.model.OfflineStoreServiceScopes;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface OfflineStoreServiceScopesMapper {
    @Insert("insert into o2o_offlineStoreServiceScopes" +
            "(id,offlineStoreId,serviceScopeId,notes,status) values" +
            "(#{id},#{offlineStoreServiceScopes.offlineStoreId}," +
            "#{offlineStoreServiceScopes.serviceScopeId}," +
            "#{offlineStoreServiceScopes.notes},#{offlineStoreServiceScopes.status})")
    Long create(@Param("id") Long id, @Param("offlineStoreServiceScopes") OfflineStoreServiceScopes offlineStoreServiceScopes);

    @Update("update o2o_offlineStoreServiceScopes set " +
            "notes=#{offlineStoreServiceScopes.notes}," +
            "status=#{offlineStoreServiceScopes.status} " +
            "where offlineStoreId=#{offlineStoreServiceScopes.offlineStoreId} " +
            "and serviceScopeId=#{offlineStoreServiceScopes.serviceScopeId}")
    Long update(@Param("offlineStoreServiceScopes") OfflineStoreServiceScopes offlineStoreServiceScopes);


    @Update("update o2o_offlineStoreServiceScopes set isDeleted=1,deleteTime=now() where id=#{id}")
    Long deleteById(@Param("id") Long id);

    @Select("select * from o2o_offlineStoreServiceScopes where isDeleted=0 order by createTime")
    List<OfflineStoreServiceScopes> select();

    @Select(("select * from o2o_offlineStoreServiceScopes where offlineStoreId=#{offlineStoreId} and isDeleted=0"))
    List<OfflineStoreServiceScopes> selectByofflineStoreId(@Param("offlineStoreId") Long offlineStoreId);


}
