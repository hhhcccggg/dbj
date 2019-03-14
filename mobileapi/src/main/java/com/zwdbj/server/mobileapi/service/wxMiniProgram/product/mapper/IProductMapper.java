package com.zwdbj.server.mobileapi.service.wxMiniProgram.product.mapper;

import com.zwdbj.server.mobileapi.service.wxMiniProgram.product.model.*;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

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
            "where (publish=1 or (publish=0 and specifyPublishTime!=0 and  specifyPublishTime < REPLACE(unix_timestamp(current_timestamp(3)),'.','')))" +
            " and isDeleted=0 and storeId=#{storeId} and id=#{id}")
    ProductlShow selectByIdByStoreId(@Param("id") long id, @Param("storeId") long storeId);

    /**
     * 根据storeId查询数据
     *
     * @param storeId
     * @return
     */
    @Select("SELECT  * from shop_products " +
            "where (publish=1 or (publish=0 and specifyPublishTime!=0 and  specifyPublishTime < REPLACE(unix_timestamp(current_timestamp(3)),'.','')))" +
            " and isDeleted=0 and storeId=#{storeId}")
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
            "where (publish=1 or (publish=0 and specifyPublishTime!=0 and  specifyPublishTime < REPLACE(unix_timestamp(current_timestamp(3)),'.','')))" +
            " and isDeleted=0  and id=#{id}")
    ProductOut selectById(long id);

    /**
     * 根据id查询数据(未删除数据)
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
            "where isDeleted=0  and id=#{id}")
    ProductOut selectByIdNoDelete(long id);

    @Update("update shop_productSKUs set inventory=inventory-#{num},sales=sales+#{num} where id=#{id}")
    int updateProductSkuNum(@Param("id") long productSkuId, @Param("num") int num);

    @Update("update shop_products set inventory=inventory-#{num},sales=sales+#{num} where id=#{id}")
    int updateProductNum(@Param("id") long productId, @Param("num") int num);

    @Select("select inventory from shop_productSKUs where id=#{id}")
    int getProductSkuInventory(@Param("id") long productSkuId);

    @Select("select inventory from shop_products where id=#{id}")
    long getProductInventory(@Param("id") long productId);

    @Select("select id,`productType`,`productDetailType`,`name`,`imageUrls`,storeId from shop_products  where (publish=1 or (publish=0 and specifyPublishTime!=0 and  specifyPublishTime < REPLACE(unix_timestamp(current_timestamp(3)),'.','')))" +
            " and productDetailType = 'DELIVERY' and isDeleted=0  order by sales desc limit 3")
    List<ProductMainDto> mainSelectProduct();

    @Select("select p.id as productId,pk.id as skuId,p.storeId,p.productType,p.productDetailType,p.name,p.limitPerPerson,pk.originalPrice,pk.promotionPrice, "+
            "pk.inventory,pk.sales from shop_products as p,shop_productSKUs as pk"+
            " where productType=1 and (productDetailType='CARD' or productDetailType='CASHCOUPON') and p.storeId=#{storeId}"+
    " and pk.productId=p.id and (publish=1 or (publish=0 and specifyPublishTime!=0 and  specifyPublishTime < REPLACE(unix_timestamp(current_timestamp(3)),'.','')))")
    List<ProductInfo> selectProductByStoreId(@Param("storeId") Long storeId);

    @Select("select p.id ,pk.id as skuId,p.storeId,p.grade,pk.originalPrice as originalPrice,pk.promotionPrice as promotionPrice," +
            "pk.inventory as inventory,pk.sales as sales,pk.attrs as attrs,pk.weight as weight,p.createTime as createTime," +
            "p.brandId,p.categoryId,p.productType,p.attimageUrlsrs,p.videoUrl,p.numberId,p.detailDescription,p.ruleDescription," +
            "p.notes,p.productDetailType,p.name,p.subName,p.searchName,p.marketName,p.sellerPoint,p.limitPerPerson,p.supportCoin " +
            "from shop_products p right join shop_productSKUs pk on pk.productId=p.id where p.publish=true and p.productType=1")
    List<Map<String,String>> selectEs();

    @Select("SELECT * from shop_products where isDeleted=0  and id=#{id}")
    ShareProduct selectShareProduct(@Param("id") long id);
}
