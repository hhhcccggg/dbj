package com.zwdbj.server.adminserver.service.shop.service.storeReview.service;

import com.zwdbj.server.adminserver.service.shop.service.store.model.ReviewStoreInput;
import com.zwdbj.server.adminserver.service.shop.service.storeReview.mapper.IStoreReviewMapper;
import com.zwdbj.server.adminserver.service.shop.service.storeReview.model.BusinessSellerReviewModel;
import com.zwdbj.server.adminserver.service.shop.service.storeReview.model.StoreReviewAddInput;
import com.zwdbj.server.utility.common.UniqueIDCreater;
import com.zwdbj.server.utility.model.ServiceStatusInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional
public class StoreReviewServiceImpl implements StoreReviewService {
    @Resource
    IStoreReviewMapper storeReviewMapper;

    @Override
    public List<BusinessSellerReviewModel> findAllStoreReviews() {
        List<BusinessSellerReviewModel> businessSellerReviewModels = this.storeReviewMapper.findAllStoreReviews();
        return businessSellerReviewModels;
    }

    @Override
    public ServiceStatusInfo<Integer> modifyStoreReview(long id, StoreReviewAddInput input) {
        try {
            int result = this.storeReviewMapper.modifyStoreReview(id,input);
            if (result==0)return new ServiceStatusInfo<>(1,"修改认证信息失败",result);
            return new ServiceStatusInfo<>(0,"修改认证信息成功",result);
        }catch (Exception e){
            return new ServiceStatusInfo<>(1,"修改认证信息失败"+e.getMessage(),null);
        }
    }

    @Override
    public ServiceStatusInfo<Integer> addStoreReview(StoreReviewAddInput input) {
        try {
            long id = UniqueIDCreater.generateID();
            int result = this.storeReviewMapper.addStoreReview(id,input);
            if (result==0)return new ServiceStatusInfo<>(1,"添加认证信息失败",result);
            return new ServiceStatusInfo<>(0,"添加认证信息成功",result);
        }catch (Exception e){
            return  new ServiceStatusInfo<>(1,"添加认证信息失败"+e.getMessage(),null);
        }
    }

    @Override
    public ServiceStatusInfo<Integer> deleteStoreReview(long businessSellerId) {
        try {
            int result = this.storeReviewMapper.deleteStoreReview(businessSellerId);
            if (result==0)return new ServiceStatusInfo<>(1,"删除认证信息失败",result);
            return new ServiceStatusInfo<>(0,"删除认证信息成功",result);
        }catch (Exception e){
            return  new ServiceStatusInfo<>(1,"删除认证信息失败"+e.getMessage(),null);
        }
    }

    @Override
    public ServiceStatusInfo<Integer> reviewStore(long legalSubjectId, ReviewStoreInput input) {
        try {
            int status = 1;
            if (input.isReviewOrNot()){
                status=0;
            }else {
                status=2;
            }
            int a = this.storeReviewMapper.reviewStore(legalSubjectId,input,status);
            return new ServiceStatusInfo<>(0,"商铺资料审核成功",a);
        }catch (Exception e){
            return  new ServiceStatusInfo<>(1,"商铺资料审核失败"+e.getMessage(),null);
        }
    }

    @Override
    public ServiceStatusInfo<BusinessSellerReviewModel> getStoreReviewById(long businessSellerId) {
        try {
            BusinessSellerReviewModel businessSellerReviewModel = this.storeReviewMapper.getStoreReviewById(businessSellerId);
            return new ServiceStatusInfo<>(0,"获取商铺成功",businessSellerReviewModel);
        }catch (Exception e){
            return  new ServiceStatusInfo<>(1,"获取商铺失败"+e.getMessage(),null);
        }
    }

    @Override
    public ServiceStatusInfo<Integer> notRealDeleteStoreReview(long id) {
        try {
            int result = this.storeReviewMapper.notRealDeleteStoreReview(id);
            if (result==0)return new ServiceStatusInfo<>(1,"删除认证信息失败",result);
            return new ServiceStatusInfo<>(0,"删除认证信息成功",result);
        }catch (Exception e){
            return  new ServiceStatusInfo<>(1,"删除认证信息失败"+e.getMessage(),null);
        }
    }
}
