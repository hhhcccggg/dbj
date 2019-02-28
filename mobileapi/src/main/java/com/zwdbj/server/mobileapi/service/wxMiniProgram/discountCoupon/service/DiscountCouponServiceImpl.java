package com.zwdbj.server.mobileapi.service.wxMiniProgram.discountCoupon.service;

import com.ecwid.consul.v1.ConsulClient;
import com.zwdbj.server.mobileapi.service.wxMiniProgram.discountCoupon.mapper.IDiscountCouponMapper;
import com.zwdbj.server.mobileapi.service.wxMiniProgram.discountCoupon.model.DiscountCouponModel;
import com.zwdbj.server.mobileapi.service.wxMiniProgram.userDiscountCoupon.service.UserDiscountCouponService;
import com.zwdbj.server.utility.common.shiro.JWTUtil;
import com.zwdbj.server.utility.consulLock.unit.Lock;
import com.zwdbj.server.basemodel.model.ServiceStatusInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class DiscountCouponServiceImpl implements DiscountCouponService{

    @Autowired
    private IDiscountCouponMapper iDiscountCouponMapper;

    @Autowired
    private UserDiscountCouponService userDiscountCouponServiceImpl;

    @Override
    public ServiceStatusInfo<Long> userGetDiscountCoupon(long couponId) {
        long userId = JWTUtil.getCurrentId();
        if(userId == 0){
            return new ServiceStatusInfo<>(1,"用户未登录",null);
        }
        Lock lock = null;
        try{
            DiscountCouponModel discountCouponModel = iDiscountCouponMapper.selectByCoupon(couponId);
            if(discountCouponModel == null){
                return new ServiceStatusInfo(1,"优惠券不存在",null);
            }
            ServiceStatusInfo<Long> serviceStatusInfo = userDiscountCouponServiceImpl.selectUserIdPossessCouponCount(userId,couponId);
            if(discountCouponModel.getLimitGetPerPerson() != 0 && serviceStatusInfo.isSuccess() && serviceStatusInfo.getData()+1 > discountCouponModel.getLimitGetPerPerson()){
                return new ServiceStatusInfo(1,"超出领取限制",null);
            }
            //锁住优惠券
            ConsulClient consulClient = new ConsulClient("localhost", 8500);	// 创建与Consul的连接
            lock = new Lock(consulClient, "adminapi","couponTout-lockKey:"+couponId);
            if(lock.lock(true)){
                return issueDiscountCouponException(couponId,userId,1);
            }
            return new ServiceStatusInfo<>(1,"领取失败",null);
        }catch (Exception e){
            return new ServiceStatusInfo<>(1,"领取失败"+e.getMessage(),null);
        }finally {
            if(lock != null)lock.unlock();
        }
    }

    @Override
    public ServiceStatusInfo<DiscountCouponModel> selectDiscountCoupon(long couponId) {
    try{
    DiscountCouponModel discountCouponModel = iDiscountCouponMapper.selectByCoupon(couponId);
    return new ServiceStatusInfo<>(0,"",discountCouponModel);
    }catch (Exception e){
    return new ServiceStatusInfo<>(1,"优惠券不存在"+e.getMessage(),null);
    }
    }

    /**
     * TODO 未回滚
     * 发券
     * @param id
     * @param userId
     * @param couponCount
     * @return
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    public ServiceStatusInfo<Long> issueDiscountCouponException(long id,long userId, int couponCount) throws Exception {
        //减数量
        long result  = iDiscountCouponMapper.reduceCouponCount(id,couponCount);
        if(result==0){
            return new ServiceStatusInfo(1,"扣除数量失败,影响行数"+result,null);
        }
        //发布优惠券
        ServiceStatusInfo<Long> serviceStatusInfo = userDiscountCouponServiceImpl.batchCreateUserDiscountCoupon(userId,id,couponCount);
        if( !serviceStatusInfo.isSuccess() )
            throw new RuntimeException("发放优惠券失败");
        return new ServiceStatusInfo(0,"",result);
    }
}
