package com.zwdbj.server.shop_admin_service.service.products.mapper;

import com.zwdbj.server.shop_admin_service.service.products.model.Products;
import com.zwdbj.server.shop_admin_service.service.products.model.ProductsDto;
import com.zwdbj.server.shop_admin_service.service.products.model.SearchProducts;
import org.apache.ibatis.annotations.*;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;


@Mapper
public interface IProductsMapper {
    @Insert("insert into shop_products(id," +
            "productType,numberId,name,subName,searchName,marketName," +
            "sellerPoint,categoryId,categoryLevel," +
            "brandId,shareDesc,storeId,commentCount,grade,sales,inventory,priceUp," +
            "priceDown,imageUrls,videoUrl,productGroupId,joinMemberDiscount," +
            "needDelivery,universalDeliveryPrice,deliverytemplateId,publish," +
            "specifyPublishTime,detailDescription,weight,notes) values(" +
            "#{id}," +
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
            "#{products.storeId}," +
            "#{products.commentCount}," +
            "#{products.grade}," +
            "#{products.sales}," +
            "#{products.inventory}," +
            "#{products.priceUp}," +
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
            "#{products.detailDescription}," +
            "#{products.weight}," +
            "#{products.notes})")
    Long createProducts(@Param("id") Long id, @Param("products") Products products);

    @Update("update  shop_products set isDeleted=1,deleteTime=now() where id=#{id}")
    Long deleteProduct(@Param("id") Long id);

    @Update("update shop_products set " +
            "productType=#{products.productType}," +
            "numberId=#{products.numberId},name=#{products.name},subName=" +
            "#{products.subName},searchName=#{products.searchName}," +
            "marketName=#{products.marketName},sellerPoint=#{products.sellerPoint}," +
            "categoryId=#{products.categoryId},categoryLevel=#{products.categoryLevel}," +
            "brandId=#{products.brandId},shareDesc=#{products.shareDesc},sellerId=#{products.sellerId}," +
            "commentCount=#{products.commentCount},grade=#{products.grade},sales=#{products.sales}," +
            "inventory=#{products.inventory},priceUp=#{products.priceUp}," +
            "priceDown=#{products.priceDown},imageUrls=#{products.priceDown},videoUrl=#{products.videoUrl}," +
            "productGroupId=#{products.productGroupId},isJoinMemberDiscount=#{products.isJoinMemberDiscount}," +
            "isNeedDelivery=#{products.isNeedDelivery},universalDeliveryPrice=#{products.universalDeliveryPrice}," +
            "deliverytemplateId=#{products.deliverytemplateId},isPublish=#{products.isPublish}," +
            "specifyPublishTime=#{products.specifyPublishTime},detailDescription=#{products.detailDescription}," +
            "weight=#{products.weight},notes=#{products.notes}" +
            "where id=#{products.id}")
    Long update(@Param("products") Products products);

    @Select("select * from shop_products where isDeleted=0 order by createTime")
    List<Products> selectAll();

    @SelectProvider(type = ProductsSqlProvider.class, method = "search")
    List<Products> search(@Param("searchProducts") SearchProducts searchProducts);

    @Select("select a.id,a.name,b.originalPrice,b.inventory b.sales,a.commentCount,b.createTime " +
            "from shop_products as a,shop_productSKUs as b where a.storeId=#{storeId} and " +
            "b.productId=a.id and a.publish=1 and b.inventory>0")
    List<ProductsDto> onSales(@PathVariable("storeId") long storeId);

    @Select("select a.id,a.name,b.originalPrice,b.inventory b.sales,a.commentCount,b.createTime " +
            "from shop_products as a,shop_productSKUs as b where a.storeId=#{storeId} and " +
            "b.productId=a.id and a.publish=1 and b.inventory=0")
    List<ProductsDto> sellOut(@PathVariable("storeId") long storeId);

    @Select("select a.id,a.name,b.originalPrice,b.inventory b.sales,a.commentCount,b.createTime " +
            "from shop_products as a,shop_productSKUs as b where a.storeId=#{storeId} and " +
            "b.productId=a.id and a.publish=0")
    List<ProductsDto> notOnSales(@PathVariable("storeId") long storeId);
}


