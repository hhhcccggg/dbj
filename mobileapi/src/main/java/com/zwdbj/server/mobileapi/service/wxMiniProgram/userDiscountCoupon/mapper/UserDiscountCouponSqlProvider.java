package com.zwdbj.server.mobileapi.service.wxMiniProgram.userDiscountCoupon.mapper;


import com.zwdbj.server.mobileapi.service.wxMiniProgram.userDiscountCoupon.model.SearchUserDiscountCoupon;

import java.util.Map;

public class UserDiscountCouponSqlProvider {

    /**
     * 查询用户优惠券
     * @param map
     * @return
     */
    public String selectUserDiscountCoupon(Map map){
        SearchUserDiscountCoupon searchUserDiscountCoupon = (SearchUserDiscountCoupon)map.get("searchUserDiscountCoupon");
        StringBuffer stringBuffer = new StringBuffer("SELECT " +
                "udp.id,udp.state,dp.`name`, " +
                "dp.couponCount,dp.discountType,dp.discountValue, " +
                "dp.limitMoney,dp.limitGetPerPerson, " +
                "dp.useInfo,dp.onlySupportOriginProduct, " +
                "dp.validStartTime,dp.validEndTime, " +
                "dp.storeId,dp.legalSubjectId " +
                "FROM shop_userdiscountcoupons AS udp ,shop_discountcoupons AS dp WHERE udp.couponId=dp.id and  udp.isDeleted=0 AND dp.isDeleted=0 ");
        if(searchUserDiscountCoupon.getDiscountType()!= null){
            stringBuffer.append(" and dp.discountType = #{searchUserDiscountCoupon.discountType}");
        }
        if(searchUserDiscountCoupon.getState() == 0){
            stringBuffer.append(" and udp.state = 'UNUSED'");
        }
        if(searchUserDiscountCoupon.getState() == 1){
            stringBuffer.append(" and udp.state = 'USED'");
        }
        if(searchUserDiscountCoupon.getState() == 2){
            stringBuffer.append(" and udp.state = 'UNUSED' and (dp.validEndTime>now() or dp.validEndTime is null)");
        }
        stringBuffer.append(" and udp.userId = #{searchUserDiscountCoupon.userId}");
        return stringBuffer.toString();
    }
}
