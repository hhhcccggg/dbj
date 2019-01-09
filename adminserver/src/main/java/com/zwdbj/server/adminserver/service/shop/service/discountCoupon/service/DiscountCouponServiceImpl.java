package com.zwdbj.server.adminserver.service.shop.service.discountCoupon.service;

import com.github.pagehelper.PageHelper;
import com.zwdbj.server.adminserver.service.shop.service.discountCoupon.mapper.IDiscountCouponMapper;
import com.zwdbj.server.adminserver.service.shop.service.discountCoupon.model.DiscountCouponInput;
import com.zwdbj.server.adminserver.service.shop.service.discountCoupon.model.DiscountCouponModel;
import com.zwdbj.server.adminserver.service.shop.service.discountCoupon.model.SearchDiscountCoupon;
import com.zwdbj.server.adminserver.service.shop.service.store.service.StoreService;
import com.zwdbj.server.tokencenter.TokenCenterManager;
import com.zwdbj.server.tokencenter.model.AuthUser;
import com.zwdbj.server.utility.common.UniqueIDCreater;
import com.zwdbj.server.utility.common.shiro.JWTUtil;
import com.zwdbj.server.utility.model.ServiceStatusInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DiscountCouponServiceImpl implements DiscountCouponService{

    @Autowired
    private IDiscountCouponMapper iDiscountCouponMapper;

    @Autowired
    private StoreService storeServiceImpl;

    @Autowired
    private TokenCenterManager tokenCenterManager;

    @Override
    public ServiceStatusInfo<Long> createDiscountCoupon(DiscountCouponInput discountCouponInput) {
        try{
            AuthUser authUser = tokenCenterManager.fetchUser(String.valueOf(JWTUtil.getCurrentId())).getData();
            if(authUser == null){
                return new ServiceStatusInfo(1,"用户不存在",null);
            }
            Long storeId = storeServiceImpl.selectByLegalSubjectId(authUser.getLegalSubjectId()).getData();
            if(storeId ==null || storeId<=0 ){
                return new ServiceStatusInfo(1,"店铺不存在",null);
            }
            discountCouponInput.setStoreId(storeId);
            discountCouponInput.setLegalSubjectId(authUser.getLegalSubjectId());
            discountCouponInput.setId(UniqueIDCreater.generateID());
            long result = iDiscountCouponMapper.createDiscountCoupon(discountCouponInput);
            if(result==0){
                return new ServiceStatusInfo(1,"新增失败,影响行数"+result,null);
            }
            return new ServiceStatusInfo(0,"",result);
        }catch(Exception e){
            e.printStackTrace();
            return new ServiceStatusInfo(1,"新增失败"+e.getMessage(),null);
        }
    }

    @Override
    public ServiceStatusInfo<Long> updateDiscountCoupon(DiscountCouponInput discountCouponInput) {
        try{
            AuthUser authUser = tokenCenterManager.fetchUser(String.valueOf(JWTUtil.getCurrentId())).getData();
            if(authUser == null){
                return new ServiceStatusInfo(1,"用户不存在",null);
            }
            Long storeId = storeServiceImpl.selectByLegalSubjectId(authUser.getLegalSubjectId()).getData();
            if(storeId ==null || storeId<=0 ){
                return new ServiceStatusInfo(1,"店铺不存在",null);
            }
            discountCouponInput.setStoreId(storeId);
            discountCouponInput.setLegalSubjectId(authUser.getLegalSubjectId());
            long result = iDiscountCouponMapper.updateDisountCoupon(discountCouponInput);
            if(result==0){
                return new ServiceStatusInfo(1,"修改失败,影响行数"+result,null);
            }
            return new ServiceStatusInfo(0,"",result);
        }catch(Exception e){
            return new ServiceStatusInfo(1,"修改失败"+e.getMessage(),null);
        }
    }

    @Override
    public ServiceStatusInfo<Long> removeDiscountCoupon(long[] ids) {
        try{
            AuthUser authUser = tokenCenterManager.fetchUser(String.valueOf(JWTUtil.getCurrentId())).getData();
            if(authUser == null){
                return new ServiceStatusInfo(1,"用户不存在",null);
            }
            Long storeId = storeServiceImpl.selectByLegalSubjectId(authUser.getLegalSubjectId()).getData();
            if(storeId ==null || storeId<=0 ){
                return new ServiceStatusInfo(1,"店铺不存在",null);
            }
            long result = iDiscountCouponMapper.deleteDisountCount(ids,storeId,authUser.getLegalSubjectId());
            if(result==0){
                return new ServiceStatusInfo(1,"删除失败,影响行数"+result,null);
            }
            return new ServiceStatusInfo(0,"",result);
        }catch(Exception e){
            return new ServiceStatusInfo(1,"删除失败"+e.getMessage(),null);
        }
    }

    @Override
    public ServiceStatusInfo<DiscountCouponModel> findById(long id) {
        try{
            AuthUser authUser = tokenCenterManager.fetchUser(String.valueOf(JWTUtil.getCurrentId())).getData();
            if(authUser == null){
                return new ServiceStatusInfo(1,"用户不存在",null);
            }
            Long storeId = storeServiceImpl.selectByLegalSubjectId(authUser.getLegalSubjectId()).getData();
            if(storeId ==null || storeId<=0 ){
                return new ServiceStatusInfo(1,"店铺不存在",null);
            }
            DiscountCouponModel discountCouponModel = iDiscountCouponMapper.selectById(id,storeId,authUser.getLegalSubjectId());
            return new ServiceStatusInfo(0,"",discountCouponModel);
        }catch(Exception e){
            return new ServiceStatusInfo(1,"查询失败"+e.getMessage(),null);
        }
    }

    @Override
    public ServiceStatusInfo<List<DiscountCouponModel>> findByPage(SearchDiscountCoupon searchDiscountCoupon,int pageNo,int rows) {
        try{
            AuthUser authUser = tokenCenterManager.fetchUser(String.valueOf(JWTUtil.getCurrentId())).getData();
            if(authUser == null){
                return new ServiceStatusInfo(1,"用户不存在",null);
            }
            Long storeId = storeServiceImpl.selectByLegalSubjectId(authUser.getLegalSubjectId()).getData();
            if(storeId ==null || storeId<=0 ){
                return new ServiceStatusInfo(1,"店铺不存在",null);
            }
            searchDiscountCoupon.setLegalSubjectId(authUser.getLegalSubjectId());
            searchDiscountCoupon.setStoreId(storeId);
            PageHelper.startPage(pageNo,rows);
            List<DiscountCouponModel> list = iDiscountCouponMapper.selectByPage(searchDiscountCoupon);
            return new ServiceStatusInfo(0,"",list);
        }catch(Exception e){
            return new ServiceStatusInfo(1,"查询失败"+e.getMessage(),null);
        }
    }

    @Override
    @Transactional
    public ServiceStatusInfo<Long> issueDiscountCoupon(long id,long userId, int couponCount) {
        try{
            AuthUser authUser = tokenCenterManager.fetchUser(String.valueOf(JWTUtil.getCurrentId())).getData();
            if(authUser == null){
                return new ServiceStatusInfo(1,"用户不存在",null);
            }
            Long storeId = storeServiceImpl.selectByLegalSubjectId(authUser.getLegalSubjectId()).getData();
            if(storeId ==null || storeId<=0 ){
                return new ServiceStatusInfo(1,"店铺不存在",null);
            }

            //减数量
            long result = iDiscountCouponMapper.reduceCouponCount(id,storeId,authUser.getLegalSubjectId(),couponCount);
            if(result==0){
                return new ServiceStatusInfo(1,"扣除数量失败,影响行数"+result,null);
            }
            //发布优惠券
            //TODO 未完待续
            return new ServiceStatusInfo(0,"",result);
        }catch(Exception e){
            return new ServiceStatusInfo(1,"查询失败"+e.getMessage(),null);
        }
    }
}
