package com.zwdbj.server.adminserver.service.shop.service.products.mapper;

import com.zwdbj.server.adminserver.service.shop.service.products.model.*;
import org.apache.ibatis.annotations.*;

import java.util.List;


@Mapper
public interface IProductsMapper {
    @Insert("INSERT INTO `shop_products` (" +
            "`id`," +
            "`productType`," +
            "`productDetailType`," +
            "`name`," +
            "`storeId`," +
            "`inventory`," +
            "`publish`," +
            "`specifyPublishTime`," +
            "`detailDescription`," +
            "`limitPerPerson`," +
            "`brandId`,`imageUrls`,"+
            "`categoryId`,`supportCoin`,`ruleDescription`"+
            ")VALUES(" +
            "#{id}," +
            "#{products.productType}," +
            "#{products.productDetailType}," +
            "#{products.name}," +
            "#{products.storeId}," +
            "#{products.inventory}," +
            "#{products.publish}," +
            "#{products.specifyPublishTime}," +
            "#{products.detailDescription}," +
            "#{products.limitPerPerson},#{products.brandId},#{products.imageUrls},#{products.categoryId},#{products.supportCoin},#{products.ruleDescription}" +
            ");")
    Long createProducts(@Param("id") Long id, @Param("products") CreateProducts products);

    @Update("update  shop_products set isDeleted=1,deleteTime=now() where id=#{id} and storeId=#{storeId}")
    Long deleteProduct(@Param("id") Long id,@Param("storeId")long storeId);

    @Update("update shop_products set " +
            "name=#{products.name},storeId=#{products.storeId}," +
            "inventory=#{products.inventory},publish=#{products.isPublish},imageUrls=#{products.imageUrls}," +
            "brandId=#{products.brandId},supportCoin=#{products.supportCoin},categoryId=#{products.categoryId},"+
            "specifyPublishTime=#{products.specifyPublishTime},detailDescription=#{products.detailDescription},limitPerPerson=#{products.limitPerPerson},ruleDescription=#{products.ruleDescription}" +
            " where id=#{products.id} and storeId=#{products.storeId} and isDeleted=0")
    Long update(@Param("products") UpdateProducts products);

    @Select("select * from shop_products where isDeleted=0 order by createTime")
    List<Products> selectAll();

    @SelectProvider(type = ProductsSqlProvider.class, method = "search")
    List<Products> search(@Param("searchProducts") SearchProducts searchProducts);

    @SelectProvider(type = ProductsSqlProvider.class, method = "searchCondition")
    List<Products> searchCondition(@Param("searchProducts") SearchProducts searchProducts, @Param("type") int type);

    @Select("select a.id,a.name,b.originalPrice,b.inventory b.sales,a.commentCount,b.createTime " +
            "from shop_products as a,shop_productSKUs as b where a.storeId=#{storeId} and " +
            "b.productId=a.id and a.publish=1 and b.inventory>0")
    List<ProductsDto> onSales(@Param("storeId") long storeId);

    @Select("select a.id,a.name,b.originalPrice,b.inventory b.sales,a.commentCount,b.createTime " +
            "from shop_products as a,shop_productSKUs as b where a.storeId=#{storeId} and " +
            "b.productId=a.id and a.publish=1 and b.inventory=0")
    List<ProductsDto> sellOut(@Param("storeId") long storeId);

    @Select("select a.id,a.name,b.originalPrice,b.inventory b.sales,a.commentCount,b.createTime " +
            "from shop_products as a,shop_productSKUs as b where a.storeId=#{storeId} and " +
            "b.productId=a.id and a.publish=0")
    List<ProductsDto> notOnSales(@Param("storeId") long storeId);


    @UpdateProvider(type = ProductsSqlProvider.class , method = "updatePublish")
    Long updatePublishs(Long[] id, boolean publish,long storeId);

    @Select("select * from shop_products where id=#{id} and isDeleted=0")
    Products selectById(@Param("id") long id);

    @DeleteProvider(type = ProductsSqlProvider.class , method = "deleteByProducts")
    Long deleteByProducts(@Param("id") Long[] id,@Param("storeId")long storeId);
}


