package com.zwdbj.server.adminserver.service.shop.service.productCard.mapper;

import com.zwdbj.server.adminserver.service.shop.service.productCard.model.ProductCard;
import org.apache.ibatis.annotations.*;

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
    Long createProductCard(@Param("id") long id, @Param("productCard") ProductCard productCard);

    /**
     * 根据productId修改
     * @param productCard
     * @return
     */
    @Update("UPDATE `shop_productcards`" +
            "SET " +
            " `festivalCanUse` = #{productCard.festivalCanUse}," +
            " `useInfo` = #{productCard.useInfo}," +
            " `validType` = #{productCard.validType}," +
            " `specHoursValid` = #{productCard.specHoursValid}," +
            " `validDays` = #{productCard.validDays}," +
            " `validStartTime` = #{productCard.validStartTime}," +
            " `validEndTime` = #{productCard.validEndTime}" +
            "WHERE" +
            "(`productId` = #{productCard.productId})")
    Long updateByProductIdByProductCard(@Param("productCard") ProductCard productCard);


    /**
     * 根据productId查询
     * @param productId
     * @return
     */
    @Select("select * from shop_productcards where productId=#{productId}")
    ProductCard selectByProductId(@Param("productId") long productId);
}
