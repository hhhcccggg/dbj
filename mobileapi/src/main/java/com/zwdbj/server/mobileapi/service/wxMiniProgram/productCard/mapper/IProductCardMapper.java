package com.zwdbj.server.mobileapi.service.wxMiniProgram.productCard.mapper;

import com.zwdbj.server.mobileapi.service.wxMiniProgram.productCard.model.ProductCard;
import org.apache.ibatis.annotations.*;

@Mapper
public interface IProductCardMapper {




    /**
     * 根据productId查询
     * @param productId
     * @return
     */
    @Select("select * from shop_productCards where productId=#{productId} and isDeleted=0")
    ProductCard selectByProductId(@Param("productId") long productId);
}
