package com.zwdbj.server.mobileapi.service.wxMiniProgram.userDiscountCoupon.service;

import com.zwdbj.server.mobileapi.service.wxMiniProgram.userDiscountCoupon.mapper.UserDiscountCouponMapper;
import com.zwdbj.server.mobileapi.service.wxMiniProgram.userDiscountCoupon.model.UserDiscountCouponModel;
import com.zwdbj.server.utility.common.UniqueIDCreater;
import com.zwdbj.server.utility.model.ServiceStatusInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDiscountCouponServiceImpl implements UserDiscountCouponService{

    @Autowired
    private UserDiscountCouponMapper userDiscountCouponMapper;

    @Override
    public ServiceStatusInfo<Long> createUserDiscountCoupon(UserDiscountCouponModel userDiscountCouponModel) {
        try{
            userDiscountCouponModel.setId(UniqueIDCreater.generateID());
            long reslut = userDiscountCouponMapper.createUserDiscountCoupon(userDiscountCouponModel);
            if(reslut == 0)
                return new ServiceStatusInfo<>(1,"新增失败,影响行数"+reslut,null);
            return new ServiceStatusInfo<>(0,"",reslut);
        }catch(Exception e){
            return new ServiceStatusInfo<>(1,"新增失败"+e.getMessage(),null);
        }
    }

    @Override
    public ServiceStatusInfo<Long> selectUserIdPossessCouponCount(long userId, long couponId) {
        try{
            long result = userDiscountCouponMapper.selectUserIdPossessCouponCount(userId,couponId);
            return new ServiceStatusInfo<>(0,"",result);
        }catch(Exception e){
            return new ServiceStatusInfo<>(1,"查询失败",null);
        }
    }
}
