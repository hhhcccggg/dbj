package com.zwdbj.server.adminserver.service.shop.service.offlineStoreStaffs.mapper;

import com.zwdbj.server.adminserver.service.shop.service.offlineStoreStaffs.model.OfflineStoreStaffs;
import org.apache.ibatis.annotations.*;

import java.util.Date;
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

    @Select("SELECT u.name,u.phone,u.id,t.createTime,t.legalSubjectId FROM core_users as u, " +
            "( SELECT id,legalSubjectId,createTime  from core_user_tenants  where isDeleted=0 and legalSubjectId=#{legalSubjectId} ) as t " +
            "WHERE u.tenantId=t.id ORDER BY t.createTime")
    List<OfflineStoreStaffs> selectStaffs(@Param("legalSubjectId") long legalSubjectId);

    @Select("select createTime from o2o_offlineStoreStaffs where storeId=#{storeId} and userId=#{userId} and isDeleted=0 ")
    Date selectSuperStarCreateTime(@Param("storeId") long storeId, @Param("userId") long userId);

    @Select("SELECT u.name,u.phone,u.id,t.createTime,t.legalSubjectId FROM core_users as u, " +
            "( SELECT id,legalSubjectId,createTime from core_user_tenants  where isDeleted=0 and legalSubjectId=#{legalSubjectId} ) as t " +
            "WHERE u.tenantId=t.id and u.name like '%#{search}%' ORDER BY t.createTime")
    List<OfflineStoreStaffs> searchStaffs(@Param("legalSubjectId") long legalSubjectId, @Param("search") String search);

    /**
     * 批量删除
     *
     * @param id
     * @return
     */
    @UpdateProvider(type = OfflineStoreStaffsSqlProvide.class, method = "cancelRepresent")
    Long cancelRepresent(@Param("id") long[] id, @Param("storeId") long storeId);

    /**
     * 批量设置代言人
     *
     * @param id
     * @return
     */
    @InsertProvider(type = OfflineStoreStaffsSqlProvide.class, method = "setRepresent")
    Long setRepresent(@Param("id") long[] id, @Param("storeId") long storeId);
}
