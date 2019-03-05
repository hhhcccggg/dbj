package com.zwdbj.server.adminserver.service.shop.service.shopdetail.mapper;

import com.zwdbj.server.adminserver.service.shop.service.shopdetail.model.*;
import org.apache.ibatis.annotations.*;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface ShopDetailMapper {
    @Select("select a.id,a.name,a.contactName,a.contactPhone,l.reviewed,l.status,l.reviewMsg,a.logoUrl,a.mainConverImage,a.coverImages " +
            "from shop_stores as a,shop_legalSubjects as l " +
            "where a.legalSubjectId=#{legalSubjectId} and l.id=#{legalSubjectId}")
    StoreDto findStoreDetail(@Param("legalSubjectId") long legalSubjectId);


    @Select("select o.day,o.storeId,o.openTime,o.closeTime from o2o_offlineStoreOpeningHours as o, shop_stores as s " +
            "where o.storeId=s.id and s.legalSubjectId=#{legalSubjectId} and o.isDeleted=0 order by day asc")
    List<OpeningHours> findOpeningHours(@Param("legalSubjectId") long legalSubjectId);

    @Insert("insert into o2o_offlineStoreOpeningHours (id,day,storeId,openTime,closeTime) values(#{id},#{day},#{storeId},#{openTime},#{closeTime})")
    long createOpeningHours(@Param("id") long id, @Param("day") int day, @Param("openTime") int openTime, @Param("closeTime") int closeTime, @Param("storeId") long storeId);

    @Insert("insert  into o2o_offlineStoreOpeningHours (id,day,openTime,closeTime,storeId) values(#{id},#{day}," +
            "#{openTime},#{closeTime},#{storeId})")
    Long modifyOpeningHours(@Param("id") long id, @Param("openTime") int openTime, @Param("closeTime") int closeTime,
                            @Param("day") int day, @Param("storeId") long storeId);

    @Update("update o2o_offlineStoreOpeningHours set isDeleted=1 ,deleteTime=now() where storeId=#{storeId}")
    Long deletedOpeningHours(@Param("storeId") long storeId);


    @Select("select longitude,latitude,address,cityId,cityLevel from shop_stores where legalSubjectId=#{legalSubjectId}")
    LocationInfo showLocation(@Param("legalSubjectId") long legalSubjectId);

    @Update("update shop_stores set longitude=#{info.longitude},latitude=#{info.latitude}," +
            "address=#{info.address},cityId=#{info.cityId},cityLevel=#{info.cityLevel} where id=#{storeId}")
    Long modifyLocation(@Param("info") LocationInfo info, @Param("storeId") long storeId);


    //查询店铺服务范围id
    @Select("select o2o.serviceScopeId from o2o_offlineStoreServiceScopes as o2o,shop_stores as s where " +
            "o2o.storeId=s.id and s.legalSubjectId=#{legalSubjectId}")
    List<Long> selectServiceScopeId(@Param("legalSubjectId") long legalSubjectId);

    @Update("update o2o_offlineStoreServiceScopes set isDeleted=1,deleteTime=now() where storeId=#{storeId}")
    Long deleteStoreServiceScopes(@Param("storeId") long storeId);

    @Insert("insert into o2o_offlineStoreServiceScopes (id,storeId,serviceScopeId) " +
            "values(#{id},#{legalSubjectId},#{serviceScopeId})")
    Long createStoreServiceScopes(@Param("id") long id, @Param("legalSubjectId") long legalSubjectId,
                                  @Param("serviceScopeId") long serviceScopeId);


    @Select("select o2o.extraServiceId from o2o_offlineStoreExtraServices as o2o,shop_stores as s where " +
            "o2o.storeId=s.id and s.legalSubjectId=#{legalSubjectId}")
    List<Long> selectExtraServiceId(@Param("legalSubjectId") long legalSubjectId);


    @Update("update o2o_offlineStoreExtraServices set isDeleted=1,deleteTime=now() where storeId=#{storeId} ")
    Long deleteStoreExtraService(@Param("storeId") long storeId);

    @Insert("insert into o2o_offlineStoreExtraServices (id,storeId,extraServiceId) " +
            "values(#{id},(select id from shop_stores where legalSubjectId=#{legalSubjectId}),#{extraServiceId})")
    Long createStoreExtraService(@Param("id") long id, @Param("legalSubjectId") long legalSubjectId,
                                 @Param("extraServiceId") long extraServiceId);


    @Insert("insert into shop_legalSubjectReviews (id,identifyId,title,keyId,reviewData,status,legalSubjectId) " +
            "values(#{id},#{qualificationInput.identifyId},#{qualificationInput.title}," +
            "#{qualificationInput.keyId},#{qualificationInput.reviewData},#{qualificationInput.status}," +
            "#{legalSubjectId})")
    Long uploadCheck(@Param("id") long id,
                     @Param("qualificationInput") QualificationInput qualificationInput,
                     @Param("legalSubjectId") long legalSubjectId);

    @Select("select * from shop_stores as s where s.legalSubjectId=#{legalSubjectId}")
    ShopInfo storeDeatilInfo(@Param("legalSubjectId") long legalSubjectId);

    @UpdateProvider(method = "modifyStoreImage", type = ShopDetailSql.class)
    Long modifyStoreImage(@Param("imageType") String imageType, @Param("imageUrl") String url, @Param("legalSubjectId") long legalSubjectId);
}