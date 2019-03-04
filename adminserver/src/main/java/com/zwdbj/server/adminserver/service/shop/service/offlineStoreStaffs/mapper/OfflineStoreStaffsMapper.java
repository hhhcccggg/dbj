package com.zwdbj.server.adminserver.service.shop.service.offlineStoreStaffs.mapper;

import com.zwdbj.server.adminserver.service.shop.service.offlineStoreStaffs.model.OfflineStoreStaffs;
import com.zwdbj.server.adminserver.service.shop.service.offlineStoreStaffs.model.SuperStarDto;
import com.zwdbj.server.adminserver.service.shop.service.offlineStoreStaffs.model.SuperStarInfo;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;

@Mapper
public interface OfflineStoreStaffsMapper {


    @Update("update core_users set isDeleted=1,deleteTime=now() where id=#{userId} ")
    Long cancelStaff(@Param("userId") long userId);

    @Select("select createTime from o2o_offlineStoreStaffs where storeId=#{storeId} and userId=#{userId} and isDeleted=0 ")
    Date selectSuperStarCreateTime(@Param("storeId") long storeId, @Param("userId") long userId);

    @SelectProvider(type = OfflineStoreStaffsSqlProvide.class, method = "searchStaffs")
    List<OfflineStoreStaffs> searchStaffs(@Param("legalSubjectId") long legalSubjectId, @Param("search") String search);

    @SelectProvider(type = OfflineStoreStaffsSqlProvide.class, method = "searchSuperStars")
    List<OfflineStoreStaffs> searchSuperStar(@Param("storeId") long storeId, @Param("search") String search);

    @Insert("insert into o2o_offlineStoreStaffs (id,storeId,userId) values(#{id},#{storeId},#{userId})")
    Long setSuperStar(@Param("id") long id, @Param("storeId") long storeId, @Param("userId") long userId);

    @Update("update o2o_offlineStoreStaffs set isDeleted=1,deleteTime=now() where userId=#{userId} and storeId=#{storeId}")
    Long cancelSuperStar(@Param("userId") long userId, @Param("storeId") long storeId);

    @Select("select count(*) from o2o_offlineStoreStaffs where isDeleted=0 and userId=#{userId} and storeId=#{storeId}")
    int isSuperStar(@Param("userId") long userId, @Param("storeId") long storeId);

    /**
     * 获取代言人详情
     *
     * @param search
     * @param rank
     * @param sort
     * @param legalSubjectId
     * @return
     */

    @SelectProvider(type = OfflineStoreStaffsSqlProvide.class, method = "getSuperStarDetail")
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
    @Select("select u.id,u.fullName,u.nickName,u.totalHearts,u.totalFans," +
            "(select count(*) from core_pets where userId=u.id and isDeleted=0) as pets ," +
            "(select count(*) from core_videos where userId=#{userId} and isDeleted=0 ) as videos, " +
            "(SELECT count(*) from core_comments WHERE userId=#{userId} and isDeleted=0) as commentCounts" +
            " from core_users as u where u.id=#{userId} and isDeleted=0")
    SuperStarDto videoListStaff(@Param("userId") long userId);

    @Select("SELECT u.fullName,u.phone,u.id,u.createTime,u.tenantId,u.notes " +
            "from core_users as u,(select userId from o2o_offlineStoreStaffs where storeId=#{storeId} and isDeleted=0 ) as s" +
            " where u.id=s.userId and u.tenantId=#{tenantId} and u.isDeleted=0 and u.id=#{id}")
    OfflineStoreStaffs selectStaffById(@Param("id")long id, @Param("tenantId") long tenantId, @Param("storeId") long storeId);
}
