package com.zwdbj.server.adminserver.service.shop.service.discountCoupon.mapper;

import com.zwdbj.server.adminserver.service.shop.service.discountCoupon.model.DiscountCouponInput;
import com.zwdbj.server.adminserver.service.shop.service.discountCoupon.model.DiscountCouponModel;
import com.zwdbj.server.adminserver.service.shop.service.discountCoupon.model.SearchDiscountCoupon;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface IDiscountCouponMapper {

    /**
     * 新增
     * @param discountCouponInput
     * @return
     */
    long createDiscountCoupon(@Param("input") DiscountCouponInput discountCouponInput);

    /**
     * 修改
     * @param discountCouponInput
     * @return
     */
    long updateDisountCoupon(@Param("input") DiscountCouponInput discountCouponInput);

    /**
     * 批量删除
     * @param ids
     * @param userId
     * @return
     */
    long deleteDisountCount(@Param("ids") long[] ids ,@Param("userId") long userId);

    /**
     * 分页查询
     * @param searchDiscountCoupon
     * @return
     */
    List<DiscountCouponModel> selectByPage(@Param("searchDiscountCoupon") SearchDiscountCoupon searchDiscountCoupon);

    /**
     * 减少数量
     * @param couponCount
     * @return
     */
    long reduceCouponCount(@Param("id") long id , @Param("userId") long userId , @Param("couponCount") int couponCount);
}
