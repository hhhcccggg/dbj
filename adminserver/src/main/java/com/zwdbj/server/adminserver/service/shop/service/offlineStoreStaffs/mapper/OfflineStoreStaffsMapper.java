package com.zwdbj.server.adminserver.service.shop.service.offlineStoreStaffs.mapper;

import com.zwdbj.server.adminserver.service.shop.service.offlineStoreStaffs.model.OfflineStoreStaffs;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface OfflineStoreStaffsMapper {

    @Insert("insert into o2o_offlineStoreStaffs (id,storeId,userId,isSuperStar) values(" +
            "#{id},#{offlineStoreStaffs.storeId},#{offlineStoreStaffs.userId},#{offlineStoreStaffs.isSuperStar})")
    Long create(@Param("id") Long id, @Param("offlineStoreStaffs") OfflineStoreStaffs offlineStoreStaffs);

    @Update("update o2o_offlineStoreStaffs set storeId=#{offlineStoreStaffs.storeId}," +
            "userId=#{offlineStoreStaffs.userId},isSuperStar=#{offlineStoreStaffs.isSuperStar} where id=#{offlineStoreStaffs.id}")
    Long update(@Param("offlineStoreStaffs") OfflineStoreStaffs offlineStoreStaffs);

    @Update("update o2o_offlineStoreStaffs set isDeleted=1,deleteTime=now() where id=#{id}")
    Long deleteById(@Param("id") Long id);

    @Select("select * from o2o_offlineStoreStaffs where isDeleted=0 order by createTime")
    List<OfflineStoreStaffs> select();

    @Select("select * from o2o_offlineStoreStaffs where id=#{id}")
    OfflineStoreStaffs selectById(@Param("id") Long id);

    /**
     * 批量删除
     * @param id
     * @return
     */
    @UpdateProvider(type = OfflineStoreStaffsSqlProvide.class , method = "cancelRepresent")
    Long cancelRepresent(@Param("id") long[] id,@Param("tenantId") long tenantId);

    /**
     * 批量设置代言人
     * @param id
     * @return
     */
    @InsertProvider(type = OfflineStoreStaffsSqlProvide.class , method = "setRepresent")
    Long setRepresent(@Param("id") long[] id,@Param("tenantId")long tenantId);
}
