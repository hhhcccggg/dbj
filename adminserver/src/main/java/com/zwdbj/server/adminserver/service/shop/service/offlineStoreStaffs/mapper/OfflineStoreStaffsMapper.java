package com.zwdbj.server.adminserver.service.shop.service.offlineStoreStaffs.mapper;

import com.zwdbj.server.adminserver.service.shop.service.offlineStoreStaffs.model.OfflineStoreStaffs;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface OfflineStoreStaffsMapper {

    @Insert("insert into o2o_offlineStoreStaffs (id,offlineStoreId,userId,isSuperStar) values(" +
            "#{id},#{offlineStoreStaffs.offlineStoreId},#{offlineStoreStaffs.userId},#{offlineStoreStaffs.isSuperStar})")
    Long create(@Param("id") Long id, @Param("offlineStoreStaffs") OfflineStoreStaffs offlineStoreStaffs);

    @Update("update o2o_offlineStoreStaffs set offlineStoreId=#{offlineStoreStaffs.offlineStoreId}," +
            "userId=#{offlineStoreStaffs.userId},isSuperStar=#{offlineStoreStaffs.isSuperStar} where id=#{offlineStoreStaffs.id}")
    Long update(@Param("offlineStoreStaffs") OfflineStoreStaffs offlineStoreStaffs);

    @Update("update o2o_offlineStoreStaffs set isDeleted=1,deleteTime=now() where id=#{id}")
    Long deleteById(@Param("id") Long id);

    @Select("select * from o2o_offlineStoreStaffs where isDeleted=0 order by createTime")
    List<OfflineStoreStaffs> select();

    @Select("select * from o2o_offlineStoreStaffs where id=#{id}")
    OfflineStoreStaffs selectById(@Param("id") Long id);
}
