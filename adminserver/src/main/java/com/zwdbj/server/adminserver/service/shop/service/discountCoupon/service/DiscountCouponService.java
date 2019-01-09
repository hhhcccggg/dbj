package com.zwdbj.server.adminserver.service.shop.service.discountCoupon.service;

import com.zwdbj.server.adminserver.service.shop.service.discountCoupon.model.DiscountCouponInput;
import com.zwdbj.server.adminserver.service.shop.service.discountCoupon.model.DiscountCouponModel;
import com.zwdbj.server.adminserver.service.shop.service.discountCoupon.model.SearchDiscountCoupon;
import com.zwdbj.server.utility.model.ServiceStatusInfo;

import java.util.List;

public interface DiscountCouponService {

    /**
     * 新增
     * @param discountCouponInput
     * @return
     */
    ServiceStatusInfo<Long> createDiscountCoupon(DiscountCouponInput discountCouponInput);

    /**
     * 修改
     * @param discountCouponInput
     * @return
     */
    ServiceStatusInfo<Long> updateDiscountCoupon(DiscountCouponInput discountCouponInput);

    /**
     * 删除
     * @param id
     * @return
     */
    ServiceStatusInfo<Long> removeDiscountCoupon(long id);

    /**
     * 根据ID查询
     * @param id
     * @return
     */
    ServiceStatusInfo<DiscountCouponModel> findById(long id);

    /**
     * 列表查询
     * @param searchDiscountCoupon
     * @return
     */
    ServiceStatusInfo<List<DiscountCouponModel>> findByPage(SearchDiscountCoupon searchDiscountCoupon);

    /**
     * 指定发放优惠券
     * @param id
     * @param userId
     * @return
     */
    ServiceStatusInfo<Long> issueDiscountCoupon(long id,long userId);
}
