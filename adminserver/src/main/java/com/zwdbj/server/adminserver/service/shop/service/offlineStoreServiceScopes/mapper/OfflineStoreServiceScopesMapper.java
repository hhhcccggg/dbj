package com.zwdbj.server.adminserver.service.shop.service.offlineStoreServiceScopes.mapper;

import com.zwdbj.server.adminserver.service.shop.service.offlineStoreServiceScopes.model.OfflineStoreServiceScopes;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface OfflineStoreServiceScopesMapper {
    @Insert("insert into o2o_offlineStoreServiceScopes" +
            "(id,storeId,serviceScopeId,notes,status) values" +
            "(#{id},#{storeId}," +
            "#{offlineStoreServiceScopes.serviceScopeId}," +
            "#{offlineStoreServiceScopes.notes},#{offlineStoreServiceScopes.status})")
    Long create(@Param("id") Long id, @Param("storeId") long storeId, @Param("offlineStoreServiceScopes") OfflineStoreServiceScopes offlineStoreServiceScopes);

    @Update("update o2o_offlineStoreServiceScopes set " +
            "notes=#{offlineStoreServiceScopes.notes}," +
            "status=#{offlineStoreServiceScopes.status} " +
            "where storeId=#{storeId} " +
            "and serviceScopeId=#{offlineStoreServiceScopes.serviceScopeId}")
    Long update(@Param("offlineStoreServiceScopes") OfflineStoreServiceScopes offlineStoreServiceScopes,@Param("storeId")long storeId);


    @Update("update o2o_offlineStoreServiceScopes set isDeleted=1,deleteTime=now() where serviceScopeId=#{serviceScopeId} and storeId=#{storeId}")
    Long deleteById(@Param("serviceScopeId") Long serviceScopeId, @Param("storeId") long storeId);

    @Select("select * from o2o_offlineStoreServiceScopes where isDeleted=0 order by createTime")
    List<OfflineStoreServiceScopes> select();

    @Select(("select os.id,os.storeId,os.serviceScopeId,os.notes,os.status,c.name as categoryName from o2o_offlineStoreServiceScopes os " +
            "left join core_categories c on c.id=os.serviceScopeId where os.storeId=#{storeId} and os.isDeleted=0"))
    List<OfflineStoreServiceScopes> selectByofflineStoreId(@Param("storeId") Long storeId);

    @Select(("select c.name as categoryName from o2o_offlineStoreServiceScopes os " +
            "left join core_categories c on c.id=os.serviceScopeId where os.storeId=#{storeId} and os.isDeleted=0"))
    List<String> selectCateNameByofflineStoreId(@Param("storeId") Long storeId);


}
