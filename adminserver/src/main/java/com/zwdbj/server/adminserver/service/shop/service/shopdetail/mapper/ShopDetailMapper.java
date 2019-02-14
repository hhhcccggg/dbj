package com.zwdbj.server.adminserver.service.shop.service.shopdetail.mapper;

import com.zwdbj.server.adminserver.service.shop.service.shopdetail.model.*;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.mapstruct.Mapper;

import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ShopDetailMapper {
    @Select("select a.id,a.name,a.contactName,a.contactPhone from shop_stores as a where a.legalSubjectId=#{legalSubjectId}")
    StoreDto findStoreDetail(@Param("legalSubjectId") long legalSubjectId);


    @Select("select o.day,o.storeId,o.openTime,o.closeTime from o2o_offlineStoreOpeningHours as o, shop_stores as s where o.storeId=s.id and s.legalSubjectId=#{legalSubjectId}")
    List<OpeningHours> findOpeningHours(@Param("legalSubjectId") long legalSubjectId);

    @Update("update o2o_offlineStoreOpeningHours set day=#{openingHours.day}," +
            "openTime=#{openingHours.openTime},closeTime=#{openingHours.closeTime} " +
            "where storeId =#{openingHours.storeId}")
    Long modifyOpeningHours(@Param("openingHours") OpeningHours openingHours);

    @Insert("insert into o2o_offlineStoreOpeningHours (id,storeId,day,openTime,closeTime) " +
            "values(#{id},#{openingHours.storeId},#{openingHours.day},#{openingHours.openTime},#{openingHours.closeTime})")
    Long createOpeningHours(@Param("id") long id, @Param("openingHours") OpeningHours openingHours);


    @Select("select id,longitude,latitude,address,cityId,cityLevel from shop_stores where legalSubjectId=#{legalSubjectId}")
    LocationInfo showLocation(@Param("legalSubjectId") long legalSubjectId);

    @Update("update shop_stores set longitude=#{info.longitude},latitude=#{info.latitude}," +
            "address=#{info.address},cityId=#{info.cityId},cityLevel=#{info.cityLevel} where id=#{info.id}")
    Long modifyLocation(@Param("info") LocationInfo info);


    //查询店铺服务范围id
    @Select("select o2o.serviceScopeId from o2o_offlineStoreServiceScopes as o2o,shop_stores as s where " +
            "o2o.storeId=s.id and s.legalSubjectId=#{legalSubjectId}")
    List<Long> selectServiceScopeId(@Param("legalSubjectId") long legalSubjectId);

    @Delete("delete from o2o_offlineStoreServiceScopes  where storeId=(select id from shop_stores where legalSubjectId=#{legalSubjectId}) ")
    Long deleteStoreServiceScopes(@Param("legalSubjectId") long legalSubjectId);

    @Insert("insert into o2o_offlineStoreServiceScopes (id,storeId,serviceScopeId) " +
            "values(#{id},(select id from shop_stores where legalSubjectId=#{legalSubjectId}),#{serviceScopeId})")
    Long createStoreServiceScopes(@Param("id") long id, @Param("legalSubjectId") long legalSubjectId,
                                  @Param("serviceScopeId") long serviceScopeId);


    @Select("select o2o.extraServiceId from o2o_offlineStoreExtraServices as o2o,shop_stores as s where " +
            "o2o.storeId=s.id and s.legalSubjectId=#{legalSubjectId}")
    List<Long> selectExtraServiceId(@Param("legalSubjectId") long legalSubjectId);


    @Delete("delete from o2o_offlineStoreExtraServices  where storeId=(select id from shop_stores where legalSubjectId=#{legalSubjectId}) ")
    Long deleteStoreExtraService(@Param("legalSubjectId") long legalSubjectId);

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
}