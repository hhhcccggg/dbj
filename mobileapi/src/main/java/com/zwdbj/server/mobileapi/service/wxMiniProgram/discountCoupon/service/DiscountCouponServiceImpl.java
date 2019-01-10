package com.zwdbj.server.mobileapi.service.wxMiniProgram.discountCoupon.service;

import com.zwdbj.server.mobileapi.service.wxMiniProgram.discountCoupon.mapper.IDiscountCouponMapper;
import com.zwdbj.server.mobileapi.service.wxMiniProgram.discountCoupon.model.DiscountCouponModel;
import com.zwdbj.server.mobileapi.service.wxMiniProgram.discountCoupon.model.SearchDiscountCoupon;
import com.zwdbj.server.mobileapi.service.wxMiniProgram.userDiscountCoupon.common.UserDiscountCouponState;
import com.zwdbj.server.mobileapi.service.wxMiniProgram.userDiscountCoupon.model.UserDiscountCouponModel;
import com.zwdbj.server.mobileapi.service.wxMiniProgram.userDiscountCoupon.service.UserDiscountCouponService;
import com.zwdbj.server.utility.common.UniqueIDCreater;
import com.zwdbj.server.utility.common.shiro.JWTUtil;
import com.zwdbj.server.utility.model.ServiceStatusInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DiscountCouponServiceImpl implements DiscountCouponService{

    @Autowired
    private IDiscountCouponMapper iDiscountCouponMapper;

    @Autowired
    private UserDiscountCouponService userDiscountCouponServiceImpl;

    @Override
    public ServiceStatusInfo<Long> userGetDiscountCoupon(long couponId) {
        try{
            long userId = JWTUtil.getCurrentId();
            DiscountCouponModel discountCouponModel = iDiscountCouponMapper.selectByCoupon(couponId);
            if(discountCouponModel == null){
                return new ServiceStatusInfo(1,"优惠券不存在",null);
            }
            ServiceStatusInfo<Long> serviceStatusInfo = userDiscountCouponServiceImpl.selectUserIdPossessCouponCount(userId,couponId);
            if(discountCouponModel.getLimitGetPerPerson() != 0 && serviceStatusInfo.isSuccess() && serviceStatusInfo.getData()+1 > discountCouponModel.getLimitGetPerPerson()){
                return new ServiceStatusInfo(1,"超出领取限制",null);
            }
            UserDiscountCouponModel userDiscountCouponModel = new UserDiscountCouponModel(UniqueIDCreater.generateID(),couponId,userId, UserDiscountCouponState.UNUSED);
            return userDiscountCouponServiceImpl.createUserDiscountCoupon(userDiscountCouponModel);
        }catch (Exception e){
            return new ServiceStatusInfo<>(1,"领取失败"+e.getMessage(),null);
        }
    }

    @Override
    public ServiceStatusInfo<List<DiscountCouponModel>> selectUserDiscountCoupon(SearchDiscountCoupon searchDiscountCoupon) {
        try{
            long userId = JWTUtil.getCurrentId();
            searchDiscountCoupon.setUserId(userId);
            List<DiscountCouponModel> list = iDiscountCouponMapper.selectUserDiscountCoupon(searchDiscountCoupon);
            return new ServiceStatusInfo<>(0,"",list);
        }catch(Exception e){
            return new ServiceStatusInfo<>(1,"查询失败"+e.getMessage(),null);
        }
    }

    @Override
    public ServiceStatusInfo<DiscountCouponModel> selectDiscountCoupon(long couponId) {
        try{
            DiscountCouponModel discountCouponModel = iDiscountCouponMapper.selectByCoupon(couponId);
            return new ServiceStatusInfo<>(0,"",discountCouponModel);
        }catch (Exception e){
            return new ServiceStatusInfo<>(1,"优惠券不存在",null);
        }
    }
}
