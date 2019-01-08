package com.zwdbj.server.mobileapi.service.wxMiniProgram.product.mapper;

import com.zwdbj.server.mobileapi.service.wxMiniProgram.product.model.ProductInput;
import com.zwdbj.server.mobileapi.service.wxMiniProgram.product.model.ProductOut;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

@Mapper
public interface IProductMapper {

    /**
     * 查询小程序的兑换商城 目前默认storeId=1
     * @return
     */
    @SelectProvider(type = ProductSqlProvider.class,method = "seleteList")
    List<ProductOut> selectWXXCXShopProduct(@Param("productInput") ProductInput productInput);

    /**
     * 根据id查询数据
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
            "where publish=1 and isDeleted=0 and storeId=1 and id=#{id}")
    ProductOut selectWXXCXById(long id);
}
