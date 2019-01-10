package com.zwdbj.server.mobileapi.service.wxMiniProgram.discountCoupon.service;

import com.zwdbj.server.mobileapi.service.wxMiniProgram.discountCoupon.mapper.IDiscountCouponMapper;
import com.zwdbj.server.mobileapi.service.wxMiniProgram.discountCoupon.model.DiscountCouponModel;
import com.zwdbj.server.mobileapi.service.wxMiniProgram.discountCoupon.model.SearchDiscountCoupon;
import com.zwdbj.server.utility.model.ServiceStatusInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DiscountCouponServiceImpl implements DiscountCouponService{

    @Autowired
    private IDiscountCouponMapper iDiscountCouponMapper;

    @Override
    public ServiceStatusInfo<Long> userGetDiscountCoupon(long couponId) {
        return null;
    }

    @Override
    public ServiceStatusInfo<List<DiscountCouponModel>> selectUserDiscountCoupon(SearchDiscountCoupon searchDiscountCoupon) {
        return null;
    }

    @Override
    public ServiceStatusInfo<DiscountCouponModel> selectDiscountCoupon(long couponId) {
        return null;
    }
}
