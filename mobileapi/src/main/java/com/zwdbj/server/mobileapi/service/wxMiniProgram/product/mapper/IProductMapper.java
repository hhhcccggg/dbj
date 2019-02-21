package com.zwdbj.server.mobileapi.service.wxMiniProgram.product.mapper;

import com.zwdbj.server.mobileapi.service.wxMiniProgram.product.model.*;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface IProductMapper {

    /**
     * 查询小程序的兑换商城
     *
     * @return
     */
    @SelectProvider(type = ProductSqlProvider.class, method = "seleteList")
    List<ProductOut> selectShopProduct(@Param("productInput") ProductInput productInput);

    /**
     * 根据id查询数据
     *
     * @param id
     * @return
     */
    @Select("SELECT  * from shop_products " +
            "where publish=1 and isDeleted=0 and storeId=#{storeId} and id=#{id}")
    ProductlShow selectByIdByStoreId(@Param("id") long id, @Param("storeId") long storeId);

    /**
     * 根据storeId查询数据
     *
     * @param storeId
     * @return
     */
    @Select("SELECT  * from shop_products " +
            "where publish=1 and isDeleted=0 and storeId=#{storeId}")
    List<ProductlShow> selectByStoreId(@Param("storeId") long storeId);

    /**
     * 根据id查询数据
     *
     * @param id
     * @return
     */
    @Select("SELECT " +
            "id," +
            "productType," +
            "productDetailType," +
            "`name`," +
            "categoryId," +
            "brandId," +
            "inventory," +
            "imageUrls," +
            "limitPerPerson " +
            "from shop_products " +
            "where publish=1 and isDeleted=0  and id=#{id}")
    ProductOut selectById(long id);

    @Update("update shop_productSKUs set inventory=inventory-#{num},sales=sales+#{num} where id=#{id}")
    int updateProductSkuNum(@Param("id") long productSkuId, @Param("num") int num);

    @Update("update shop_products set inventory=inventory-#{num},sales=sales+#{num} where id=#{id}")
    int updateProductNum(@Param("id") long productId, @Param("num") int num);

    @Select("select inventory from shop_productSKUs where id=#{id}")
    int getProductSkuInventory(@Param("id") long productSkuId);

    @Select("select inventory from shop_products where id=#{id}")
    long getProductInventory(@Param("id") long productId);

    @Select("select id,`productType`,`productDetailType`,`name`,`imageUrls`,storeId from shop_products  where publish=1 and isDeleted=0" +
            " order by sales desc limit 3")
    List<ProductMainDto> mainSelectProduct();

    @Select("select pk.id,p.storeId,p.productType,p.productDetailType,p.name,p.limitPerPerson,pk.originalPrice,pk.promotionPrice, "+
            "pk.inventory,pk.sales from shop_products as p,shop_productSKUs as pk"+
            " where productType=1 and productDetailType='CARD' or productDetailType='CASHCOUPON' and p.storeId=#{storeId}"+
    " and pk.productId=p.id")
    List<ProductInfo> selectProductByStoreId(@Param("storeId") Long storeId);
}
