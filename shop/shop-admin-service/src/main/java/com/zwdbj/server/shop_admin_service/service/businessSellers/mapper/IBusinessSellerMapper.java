package com.zwdbj.server.shop_admin_service.service.businessSellers.mapper;

import com.zwdbj.server.shop_admin_service.service.businessSellers.model.BusinessSellerAddInput;
import com.zwdbj.server.shop_admin_service.service.businessSellers.model.BusinessSellerDto;
import com.zwdbj.server.shop_admin_service.service.businessSellers.model.BusinessSellerModel;
import com.zwdbj.server.shop_admin_service.service.businessSellers.model.BusinessSellerModifyInput;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface IBusinessSellerMapper {
    @Select("select id, createTime,name,subName,logoUrl,marketName," +
            "status,isReviewed,isStopService from shop_businessSellers where isDeleted=0 order by createTime desc")
    List<BusinessSellerDto> findAllBusinessSellers();

    @Select("select * from shop_businessSellers where id=#{id} and isDeleted=0")
    BusinessSellerModel getBusinessSellerById(@Param("id") long id);

    @Update("update shop_businessSellers set sellerNumber=#{input.sellerNumber},name=#{input.name},subName=#{input.subName}," +
            "marketName=#{input.marketName},shareDesc=#{input.shareDesc},description=#{input.description}," +
            "logoUrl=#{input.logoUrl},isReviewed=#{input.isReviewed},isStopService=#{input.isStopService}," +
            "mainConverImage=#{input.mainConverImage},coverImages=#{input.coverImages},contactName=#{input.contactName}," +
            "contactPhone=#{input.contactPhone},qq=#{input.qq} where id=#{id}")
    int modifyBusinessSellers(@Param("id") long id, @Param("input") BusinessSellerModifyInput input);

    @Insert("insert into shop_businessSellers(id,name,address,type,categoryId,contactName,contactPhone,cityId) " +
            "values(#{id},#{input.name},#{input.address},#{input.type},#{input.categoryId},#{input.contactName},#{input.contactPhone},#{input.cityId})")
    int addBusinessSellers(@Param("id") long id, @Param("input") BusinessSellerAddInput input);

    @Delete("update shop_businessSellers set status=1 where id=#{id}")
    int closeBusinessSellers(@Param("id") long id);

    @Update("update shop_businessSellers set isDeleted=true,deleteTime=now(),isStopService=1 where id=#{id}")
    int deleteBusinessSellers(@Param("id") long id);
}
