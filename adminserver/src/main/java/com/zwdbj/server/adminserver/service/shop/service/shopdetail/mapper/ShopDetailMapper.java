package com.zwdbj.server.adminserver.service.shop.service.shopdetail.mapper;

import com.zwdbj.server.adminserver.service.shop.service.shopdetail.model.OpeningHours;
import com.zwdbj.server.adminserver.service.shop.service.shopdetail.model.QualificationInput;
import com.zwdbj.server.adminserver.service.shop.service.shopdetail.model.StoreDto;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.mapstruct.Mapper;

import com.zwdbj.server.adminserver.service.shop.service.shopdetail.model.LocationInfo;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface ShopDetailMapper {
    @Select("select id,name,contactName,contactPhone from shop_stores where id=#{storeId}")
    StoreDto findStoreDetail(@Param("storeId") long storeId);



    @Select("select day,storeId,openTime,closeTime from o2o_offlineStoreOpeningHours where storeId=#{storeId}")
    List<OpeningHours> findOpeningHours(@Param("storeId") long storeId);

    @Update("update o2o_offlineStoreOpeningHours set day=#{openingHours.day}," +
            "openTime=#{openingHours.openTime},closeTime=#{openingHours.closeTime} " +
            "where storeId =#{openingHours.storeId}")
    Long modifyOpeningHours(@Param("openingHours") OpeningHours openingHours);

    @Insert("insert into o2o_offlineStoreOpeningHours (id,storeId,day,openTime,closeTime) " +
            "values(#{id},#{storeId},#{openingHours.day},#{openingHours.openTime},#{openingHours.closeTime})")
    Long createOpeningHours(@Param("id") long id, @Param("storeId") long storeId,
                            @Param("openingHours") OpeningHours openingHours);



    @Select("select longitude,latitude,address,cityId,cityLevel where storeId=#{storeId}")
    LocationInfo showLocation(@Param("storeId") long storeId);

    @Update("update shop_stores set longitude=#{info.longitude},latitude=#{info.latitude}," +
            "address=#{info.address},city=#{info.cityId},cityLevel=#{info.cityLevel} where storeId=#{storeId}")
    Long modifyLocation(@Param("info") LocationInfo info, @Param("storeId") long storeId);



    //查询店铺服务范围id
    @Select("select o2o.serviceScopeId from o2o_offlineStoreServiceScopes as o2o where " +
            "o2o.storeId=#{storeId} and o2o.status=0")
    List<Long> selectServiceScopeId(@Param("storeId") long storeId);

    @Delete("delete from o2o_offlineStoreServiceScopes where storeId=#{storeId} ")
    Long deleteStoreServiceScopes(@Param("storeId") long storeId);

    @Insert("insert into o2o_offlineStoreServiceScopes (id,storeId,serviceScopeId) " +
            "values(#{id},#{storeId},#{serviceScopeId})")
    Long createStoreServiceScopes(@Param("id") long id, @Param("storeId") long storeId,
                                  @Param("serviceScopeId") long serviceScopeId);


    @Select("select o2o.extraServiceId from o2o_offlineStoreExtraServices as o2o where " +
            "o2o.storeId=#{storeId} and o2o.status=0")
    List<Long> selectExtraServiceId(@Param("storeId") long storeId);


    @Delete("delete from o2o_offlineStoreExtraServices  where storeId=#{storeId} ")
    Long deleteStoreExtraService(@Param("storeId") long storeId);

    @Insert("insert into o2o_offlineStoreExtraServices (id,storeId,extraServiceId) " +
            "values(#{id},#{storeId},#{extraServiceId})")
    Long createStoreExtraService(@Param("id") long id, @Param("storeId") long storeId,
                                 @Param("extraServiceId") long extraServiceId);



    @Insert("insert into shop_legalSubjectReviews (id,identifyId,title,keyId,reviewData,status,legalSubjectId) " +
            "values(#{id},#{qualificationInput.identifyId},#{qualificationInput.title}," +
            "#{qualificationInput.keyId},#{qualificationInput.reviewData},#{qualificationInput.status}," +
            "#{legalSubjectId})")
    Long uploadCheck(@Param("id") long id,
                     @Param("qualificationInput") QualificationInput qualificationInput,
                     @Param("legalSubjectId") long legalSubjectId);
}