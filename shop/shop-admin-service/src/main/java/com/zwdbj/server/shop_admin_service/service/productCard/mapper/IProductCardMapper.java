package com.zwdbj.server.shop_admin_service.service.productCard.mapper;

import com.zwdbj.server.shop_admin_service.service.productCard.model.ProductCard;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface IProductCardMapper {

    /**
     * 新增
     * @param id
     * @param productCard
     * @return
     */
    @Insert("INSERT INTO `shop_productcards` (" +
            "`id`," +
            "`festivalCanUse`," +
            "`useInfo`," +
            "`validType`," +
            "`specHoursValid`," +
            "`validDays`," +
            "`validStartTime`," +
            "`validEndTime`," +
            "`productId`," +
            "`productSKUId`)" +
            "VALUES(" +
            "#{id}," +
            "#{productCard.festivalCanUse}," +
            "#{productCard.useInfo}," +
            "#{productCard.validType}," +
            "#{productCard.specHoursValid}," +
            "#{productCard.validDays}," +
            "#{productCard.validStartTime}," +
            "#{productCard.validEndTime}," +
            "#{productCard.productId}," +
            "#{productCard.productSKUId})")
    Long createProductCard(@Param("id")long id, @Param("productCard")ProductCard productCard);

    /**
     * 修改
     * @param productCard
     * @return
     */
    Long updateProductCard(ProductCard productCard);
}
