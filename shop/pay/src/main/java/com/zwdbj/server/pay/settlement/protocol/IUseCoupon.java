package com.zwdbj.server.pay.settlement.protocol;


public interface IUseCoupon {
    /**
     * 获取参与抵扣的优惠券
     * @return
     */
    Coupon coupons();
}
