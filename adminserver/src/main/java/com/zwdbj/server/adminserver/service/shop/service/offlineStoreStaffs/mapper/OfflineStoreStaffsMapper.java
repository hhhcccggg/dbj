package com.zwdbj.server.adminserver.service.shop.service.offlineStoreStaffs.mapper;

import com.zwdbj.server.adminserver.service.shop.service.offlineStoreStaffs.model.OfflineStoreStaffs;
import com.zwdbj.server.adminserver.service.shop.service.offlineStoreStaffs.model.SuperStarDto;
import com.zwdbj.server.adminserver.service.shop.service.offlineStoreStaffs.model.SuperStarInfo;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;

@Mapper
public interface OfflineStoreStaffsMapper {


    @Update("update o2o_offlineStoreStaffs set storeId=#{offlineStoreStaffs.storeId}," +
            "userId=#{offlineStoreStaffs.userId},isSuperStar=#{offlineStoreStaffs.isSuperStar} where id=#{offlineStoreStaffs.id}")
    Long update(@Param("offlineStoreStaffs") OfflineStoreStaffs offlineStoreStaffs);

    @Update("update core_users set isDeleted=1,deleteTime=now() where id=#{userId} ")
    Long cancelStaff(@Param("userId") long userId);

    @Select("SELECT u.fullName,u.phone,u.id,u.createTime,u.tenantId,u.notes FROM core_users as u, " +
            "( SELECT id from core_user_tenants  where isDeleted=0 and legalSubjectId=#{legalSubjectId} ) as t " +
            "WHERE u.tenantId=t.id and u.isDeleted=0 ORDER BY u.fullName")
    List<OfflineStoreStaffs> getStaffs(@Param("legalSubjectId") long legalSubjectId);

    @Select("select createTime from o2o_offlineStoreStaffs where storeId=#{storeId} and userId=#{userId} and isDeleted=0 ")
    Date selectSuperStarCreateTime(@Param("storeId") long storeId, @Param("userId") long userId);

    @Select("SELECT u.fullName,u.phone,u.id,u.createTime,u.tenantId,u.notes FROM core_users as u, " +
            "( SELECT id from core_user_tenants  where isDeleted=0 and legalSubjectId=#{legalSubjectId} ) as t " +
            "WHERE u.tenantId=t.id and u.isDeleted=0 and u.fullName like %#{search}% or u.phone like %#{search}% ORDER BY u.fullName")
    List<OfflineStoreStaffs> searchStaffs(@Param("legalSubjectId") long legalSubjectId, @Param("search") String search);

    @Select("SELECT u.fullName,u.phone,u.id,s.createTime,u.tenantId,u.notes from core_users as u,(select userId from o2o_offlineStoreStaffs where storeId=#{storeId} and isDeleted=0 ) as s " +
            "where u.id=s.userId and u.fullName like %#{search}% or u.phone like %#{search}% and u.isDeleted=0")
    List<OfflineStoreStaffs> searchSuperStar(@Param("storeId") long storeId, @Param("search") String search);

    @Insert("insert into o2o_offlineStoreStaffs (id,storeId,userId) values(#{id},#{storeId},#{userId})")
    Long setSuperStar(@Param("id") long id, @Param("storeId") long storeId, @Param("userId") long userId);

    @Update("update o2o_offlineStoreStaffs set isDeleted=1 and deleteTime=now() where userId=#{userId} and storeId=#{legalSubjectId}")
    Long cancelSuperStar(@Param("userId") long userId, @Param("legalSubjectId") long legalSubjectId);


    /**
     * 获取代言人详情
     *
     * @param search
     * @param rank
     * @param sort
     * @param legalSubjectId
     * @return
     */

    @SelectProvider(type = OfflineStoreStaffsSqlProvide.class, method = "searchSuperStar")
    List<SuperStarInfo> getSuperStarDetail(@Param("search") String search, @Param("rank") String rank, @Param("sort") String sort, @Param("legalSubjectId") long legalSubjectId);

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

    /**
     * 代言人作品列表个人信息
     *
     * @param userId
     * @return
     */
    @Select("select u.userId,u.fullName,u.nickName,u.totalHearts,v.totalFans,v.pets " +
            "(select count(*) from core_videos where userId=#{userId} and isDeleted=0 ) as commentConunts " +
            "from core_users as u where u.id=#{userId} and isDeleted=0")
    SuperStarDto videoListStaff(@Param("userId") long userId);
}
