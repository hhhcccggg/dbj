package com.zwdbj.server.shopservice.product.mapper;

import com.zwdbj.server.shopservice.product.model.Products;
import org.apache.ibatis.annotations.*;

import java.util.List;


@Mapper
public interface IProductsMapper {
    @Insert("insert into shop_products(id,createTime,isDeleted,deleteTime,isManualData," +
            "productType,numberId,name,subName,searchName,marketName," +
            "sellerPoint,categoryId,categoryLevel," +
            "brandId,shareDesc,sellerId,commentCount,grade,sales,inventory,priceUp," +
            "priceDown,imageUrls,videoUrl,productGroupId,isJoinMemberDiscount," +
            "isNeedDelivery,universalDeliveryPrice,deliverytemplateId,isPublish," +
            "specifyPublishTime,detailDescription,weight,notes) values(#{id}," +
            "#{id}," +
            "#{products.createTime}," +
            "#{products.isDeleted}," +
            "#{products.deleteTime}," +
            "#{products.isManualData}," +
            "#{products.productType}," +
            "#{products.numberId}," +
            "#{products.name}," +
            "#{products.subName}," +
            "#{products.searchName}," +
            "#{products.marketName}," +
            "#{products.sellerPoint}," +
            "#{products.categoryId}," +
            "#{products.categoryLevel}," +
            "#{products.brandId}," +
            "#{products.shareDesc}," +
            "#{products.sellerId}," +
            "#{products.commentCount}," +
            "#{products.grade}," +
            "#{products.sales}," +
            "#{products.inventory}," +
            "#{products.priceUp," +
            "#{products.priceDown}," +
            "#{products.imageUrls}," +
            "#{products.videoUrl}," +
            "#{products.productGroupId}," +
            "#{products.isJoinMemberDiscount}," +
            "#{products.isNeedDelivery}," +
            "#{products.universalDeliveryPrice}," +
            "#{products.deliverytemplateId}," +
            "#{products.isPublish}," +
            "#{products.specifyPublishTime}," +
            "#{products.etailDescription}," +
            "#{products.weight}," +
            "#{products.notes}")
    Long createProducts(@Param("id") Long id, @Param("procucts") Products products);

    @Delete("delete from shop_products where id=#{id}")
    Long deleteProduct(@Param("id") Long id);

    @Update("update shop_products set isDeleted=#{products.isDeleted},isManualData=" +
            "#{products.isManualData},productType=#{products.productType}," +
            "numberId=#{products.numberId},name=#{products.name},subName=" +
            "#{products.subName},searchName=#{products.searchName}," +
            "marketName=#{products.marketName},sellerPoint=#{products.sellerPoint}," +
            "categoryId=#{products.categoryId},categoryLevel=#{products.categoryLevel}," +
            "brandId=#{products.brandId},shareDesc=#{products.share},sellerId=#{products.sellerId}," +
            "commentCount=#{commentCount},grade=#{products.grade},sales=#{products.sales}," +
            "inventory=#{products.inventory},priceUp=#{products.priceUp}," +
            "priceDown=#{products.priceDown},imageUrls=#{products.priceDown},videoUrl=#{products.videoUrl}," +
            "productGroupId=#{products.productGroupId},isJoinMemberDiscount=#{products.isJoinMemberDiscount}," +
            "isNeedDelivery=#{products.isNeedDelivery},universalDeliveryPrice=#{products.universalDeliveryPrice}," +
            "deliverytemplateId=#{products.deliverytemplateId},isPublish=#{products.isPublish}" +
            "specifyPublishTime=#{products.specifyPublishTime},detailDescription=#{products.detailDescription}" +
            "weight=#{products.weight},notes=#{products.notes}" +
            "where id=#{products.id}")
    Long update(@Param("products") Products products);

    @Select("select * from shop_products order by id")
    List<Products> selectAll();
}


