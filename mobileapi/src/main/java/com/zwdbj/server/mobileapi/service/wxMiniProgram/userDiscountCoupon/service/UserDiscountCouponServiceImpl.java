package com.zwdbj.server.mobileapi.service.wxMiniProgram.userDiscountCoupon.service;

import com.ecwid.consul.v1.ConsulClient;
import com.zwdbj.server.mobileapi.service.wxMiniProgram.userDiscountCoupon.common.UserDiscountCouponState;
import com.zwdbj.server.mobileapi.service.wxMiniProgram.userDiscountCoupon.mapper.UserDiscountCouponMapper;
import com.zwdbj.server.mobileapi.service.wxMiniProgram.userDiscountCoupon.model.SearchUserDiscountCoupon;
import com.zwdbj.server.mobileapi.service.wxMiniProgram.userDiscountCoupon.model.UserDiscountCouponModel;
import com.zwdbj.server.mobileapi.service.wxMiniProgram.userDiscountCoupon.model.UserDiscountCouponOut;
import com.zwdbj.server.pay.settlement.protocol.Coupon;
import com.zwdbj.server.utility.common.UniqueIDCreater;
import com.zwdbj.server.utility.common.shiro.JWTUtil;
import com.zwdbj.server.utility.consulLock.unit.Lock;
import com.zwdbj.server.utility.model.ServiceStatusInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserDiscountCouponServiceImpl implements UserDiscountCouponService{

    @Autowired
    private UserDiscountCouponMapper userDiscountCouponMapper;

    @Override
    public ServiceStatusInfo<Long> selectUserIdPossessCouponCount(long userId, long couponId) {
        try{
            long result = userDiscountCouponMapper.selectUserIdPossessCouponCount(userId,couponId);
            return new ServiceStatusInfo<>(0,"",result);
        }catch(Exception e){
            return new ServiceStatusInfo<>(1,"查询失败",null);
        }
    }

    @Override
    public ServiceStatusInfo<List<UserDiscountCouponOut>> selectUserDiscountCoupon(SearchUserDiscountCoupon searchUserDiscountCoupon) {
        try {
            long userId = JWTUtil.getCurrentId();
            if(userId == 0){
                return new ServiceStatusInfo<>(1,"用户未登录",null);
            }
            searchUserDiscountCoupon.setUserId(userId);
            List<UserDiscountCouponOut> list = userDiscountCouponMapper.selectUserDiscountCoupon(searchUserDiscountCoupon);
            return new ServiceStatusInfo<>(0,"",list);
        } catch (Exception e) {
            return new ServiceStatusInfo<>(1,"查询失败"+e.getMessage(),null);
        }
    }

    @Override
    public ServiceStatusInfo<Long> batchCreateUserDiscountCoupon(long userId, long couponId, int couponCount) {
        try{
            return batchCreateUserDiscountCouponException(userId,couponId,couponCount);
        }catch(Exception e){
            return new ServiceStatusInfo<>(1,"优惠券新增失败"+e.getMessage(),null);
        }
    }

    /**
     * 批量发券抛异常版
     * @param userId
     * @param couponId
     * @param couponCount
     * @return
     * @throws Exception
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public ServiceStatusInfo<Long> batchCreateUserDiscountCouponException(long userId , long couponId,int couponCount) throws Exception {
        UserDiscountCouponModel[] userDiscountCouponModels = new UserDiscountCouponModel[couponCount];
        for (int i = 0; i <couponCount ; i++) {
            userDiscountCouponModels[i]=new UserDiscountCouponModel(UniqueIDCreater.generateID(),couponId,userId, UserDiscountCouponState.UNUSED);
        }
        long result = userDiscountCouponMapper.batchCreateUserDiscountCoupon(userDiscountCouponModels);
        if(result != couponCount)
            throw new RuntimeException("优惠券新增失败");
        return new ServiceStatusInfo<>(0,"",null);
    }

    @Override
    public ServiceStatusInfo<List<Coupon>> getVaildCoupon(long storeId, long legalSubjectId, long price) {
        try {
            long userId = JWTUtil.getCurrentId();
            if(userId == 0){
                return new ServiceStatusInfo<>(1,"用户未登录",null);
            }
            List<Coupon> list = userDiscountCouponMapper.getVaildCoupon(storeId,legalSubjectId,price,userId);
            return new ServiceStatusInfo<>(0,"",list);
        } catch (Exception e) {
            return new ServiceStatusInfo<>(1,"查询失败"+e.getMessage(),null);
        }
    }

    @Override
    public ServiceStatusInfo<Coupon> getVaildCouponById(long storeId, long legalSubjectId, long price, long id) {
        try {
            long userId = JWTUtil.getCurrentId();
            if(userId == 0){
                return new ServiceStatusInfo<>(1,"用户未登录",null);
            }
            Coupon coupon = userDiscountCouponMapper.getVaildCouponById(storeId,legalSubjectId,price,userId,id);
            return new ServiceStatusInfo<>(0,"",coupon);
        } catch (Exception e) {
            return new ServiceStatusInfo<>(1,"查询失败"+e.getMessage(),null);
        }
    }

    public ServiceStatusInfo<Long> updateUserDiscountCouponState(long userId,long id){
        Lock lock = null;
        try {
            ConsulClient consulClient = new ConsulClient("localhost", 8500);	// 创建与Consul的连接
            lock = new Lock(consulClient, "modelapi","couponTout-lockKey:"+userId+id);
            if(lock.lock(true)){
                //锁住优惠券
                long count = userDiscountCouponMapper.getUserCouponCount(userId,id);
                if(count <= 0){
                    return new ServiceStatusInfo<>(1,"没有可用优惠券",null);
                }
                long retult = userDiscountCouponMapper.useUserCouponCount(userId, id);
                if(retult == 0){
                    return new ServiceStatusInfo<>(1,"优惠券影响行数为"+retult,null);
                }
                return new ServiceStatusInfo<>(0,"",retult);
            }
            return new ServiceStatusInfo<>(1,"没有可用优惠券",null);
        }catch (Exception e){
            return new ServiceStatusInfo<>(1,"修改失败"+e.getMessage(),null);
        }
    }
}
