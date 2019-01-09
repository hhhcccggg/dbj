package com.zwdbj.server.adminserver.service.shop.service.discountCoupon.mapper;

import com.zwdbj.server.adminserver.service.shop.service.discountCoupon.model.DiscountCouponInput;
import com.zwdbj.server.adminserver.service.shop.service.discountCoupon.model.DiscountCouponModel;
import com.zwdbj.server.adminserver.service.shop.service.discountCoupon.model.SearchDiscountCoupon;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface IDiscountCouponMapper {

    /**
     * 新增
     * @param discountCouponInput
     * @return
     */
    @Insert("INSERT INTO `shop_discountcoupons` (" +
            "`id`,`name`,`couponCount`,`discountType`,`discountValue`,`limitMoney`," +
            "`limitGetPerPerson`,`useInfo`,`onlySupportOriginProduct`,`validStartTime`," +
            "`validEndTime`,`storeId`,`legalSubjectId`" +
            ")VALUES(" +
            "#{input.id},#{input.name},#{input.couponCount},#{input.discountType},#{input.discountValue},#{input.limitMoney}," +
            "#{input.limitGetPerPerson},#{input.useInfo},#{input.onlySupportOriginProduct},#{input.validStartTime}," +
            "#{input.validEndTime},#{input.storeId},#{input.legalSubjectId})")
    long createDiscountCoupon(@Param("input") DiscountCouponInput discountCouponInput);

    /**
     * 修改
     * @param discountCouponInput
     * @return
     */
    @Update("UPDATE `shop_discountcoupons` SET " +
            " `name` = #{input.name}," +
            " `couponCount` = #{input.couponCount}," +
            " `discountType` = #{input.discountType}," +
            " `discountValue` = #{input.discountValue}," +
            " `limitMoney` = #{input.limitMoney}," +
            " `limitGetPerPerson` = #{input.limitGetPerPerson}," +
            " `useInfo` = #{input.useInfo}," +
            " `onlySupportOriginProduct` = #{input.onlySupportOriginProduct}," +
            " `validStartTime` = #{input.validStartTime}," +
            " `validEndTime` = #{input.validEndTime}" +
            "WHERE `id` = #{input.id} and `storeId` = #{input.storeId} and `legalSubjectId` = #{input.legalSubjectId}")
    long updateDisountCoupon(@Param("input") DiscountCouponInput discountCouponInput);

    /**
     * 批量删除
     * @param ids
     * @param storeId
     * @param legalSubjectId
     * @return
     */
    @UpdateProvider(type =DiscountCouponSqlProvider.class ,method = "deleteDisountCount")
    long deleteDisountCount(@Param("ids") long[] ids ,@Param("storeId") long storeId,@Param("legalSubjectId") long legalSubjectId);

    /**
     * 分页查询
     * @param searchDiscountCoupon
     * @return
     */
    @SelectProvider(type =DiscountCouponSqlProvider.class ,method = "selectByPage")
    List<DiscountCouponModel> selectByPage(@Param("searchDiscountCoupon") SearchDiscountCoupon searchDiscountCoupon);

    /**
     * 根据ID查询
     * @param id
     * @param storeId
     * @param legalSubjectId
     * @return
     */
    @Select("select * from `shop_discountcoupons` where id=#{id} and storeId=#{storeId} and legalSubjectId=#{legalSubjectId}")
    DiscountCouponModel selectById(@Param("id") long id ,@Param("storeId") long storeId,@Param("legalSubjectId") long legalSubjectId);

    /**
     * 减少数量
     * @param couponCount
     * @return
     */
    @Update("UPDATE `shop_discountcoupons` SET  `couponCount` = `couponCount` - #{couponCount} where id = #{id} " +
            "and storeId=#{storeId} and legalSubjectId=#{legalSubjectId}  and `couponCount`-#{couponCount}>=0")
    long reduceCouponCount(@Param("id") long id ,@Param("storeId") long storeId,@Param("legalSubjectId") long legalSubjectId , @Param("couponCount") int couponCount);
}
