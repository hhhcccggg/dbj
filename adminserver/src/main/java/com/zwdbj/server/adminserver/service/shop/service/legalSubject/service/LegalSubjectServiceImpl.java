package com.zwdbj.server.adminserver.service.shop.service.legalSubject.service;

import com.zwdbj.server.adminserver.QueueUtil;
import com.zwdbj.server.adminserver.service.shop.service.legalSubject.mapper.ILegalSubjectMapper;
import com.zwdbj.server.adminserver.service.shop.service.legalSubject.model.*;
import com.zwdbj.server.adminserver.service.shop.service.store.model.ReviewStoreInput;
import com.zwdbj.server.adminserver.service.shop.service.store.model.StoreSimpleInfo;
import com.zwdbj.server.adminserver.service.shop.service.store.service.StoreService;
import com.zwdbj.server.adminserver.service.shop.service.store.model.ReviewStoreInput;
import com.zwdbj.server.adminserver.service.userTenant.model.ModifyUserTenantInput;
import com.zwdbj.server.adminserver.service.userTenant.model.UserTenantInput;
import com.zwdbj.server.probuf.middleware.mq.QueueWorkInfoModel;
import com.zwdbj.server.utility.common.UniqueIDCreater;
import com.zwdbj.server.utility.model.ServiceStatusInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LegalSubjectServiceImpl implements ILegalSubjectService {
    @Autowired
    ILegalSubjectMapper legalSubjectMapper;
    @Autowired
    private StoreService storeServiceImpl;
    protected Logger logger = LoggerFactory.getLogger(LegalSubjectServiceImpl.class);


    @Override
    public int addLegalSubject(long id,UserTenantInput input){
        //创建商家
        int result = this.legalSubjectMapper.addLegalSubject(id,input);
        //创建店铺
        this.addShopStore(id,input);
        return result;
    }
    @Override
    public int modifyBasicLegalSubject(long id, ModifyUserTenantInput input){
        LegalSubjectModel model = this.getLegalSubjectById(id);
        if (model==null)return 0;
        //修改商家基本信息
        int result = this.legalSubjectMapper.modifyBasicLegalSubject(id,input);
        //修改店铺
        this.modifyShopStore(id,input);

        return result;
    }
    @Override
    public int delLegalSubject(long id){
        LegalSubjectModel model = this.getLegalSubjectById(id);
        if (model==null)return 0;
        //删除商家
        int result = this.legalSubjectMapper.delLegalSubject(id);
        //删除店铺
        this.delShopStore(id);
        return result;
    }
    public LegalSubjectModel getLegalSubjectById(long id){
        return this.legalSubjectMapper.getLegalSubjectById(id);
    }

    public void addShopStore(long legalSubjectId,UserTenantInput input){
        long id = UniqueIDCreater.generateID();
        this.legalSubjectMapper.addShopStore(id,input,legalSubjectId);
        QueueUtil.sendQueue(id, QueueWorkInfoModel.QueueWorkModifyShopInfo.OperationEnum.CREATE);
    }
    public void modifyShopStore(long legalSubjectId,ModifyUserTenantInput input){
        this.legalSubjectMapper.modifyShopStore(input,legalSubjectId);
        StoreSimpleInfo info = this.storeServiceImpl.selectByLegalSubjectId(legalSubjectId).getData();
        QueueUtil.sendQueue(info.getId(), QueueWorkInfoModel.QueueWorkModifyShopInfo.OperationEnum.UPDATE);
    }
    //假删
    public void delShopStore(long legalSubjectId){
        this.legalSubjectMapper.delShopStore(legalSubjectId);
        StoreSimpleInfo info = this.storeServiceImpl.selectByLegalSubjectId(legalSubjectId).getData();
        QueueUtil.sendQueue(info.getId(), QueueWorkInfoModel.QueueWorkModifyShopInfo.OperationEnum.UPDATE);
    }


    @Override
    public List<LegalSubjectModel> getLegalSubjects(LegalSubjectSearchInput input) {
        List<LegalSubjectModel> legalSubjectModels = this.legalSubjectMapper.getLegalSubjects(input);
        return legalSubjectModels;
    }
    @Override
    public List<LegalSubjectModel> getUnReviewedLegalSubjects(LegalSubjectSearchInput input) {
        List<LegalSubjectModel> legalSubjectModels = this.legalSubjectMapper.getUnReviewedLegalSubjects(input);
        return legalSubjectModels;
    }

    @Override
    public ServiceStatusInfo<Integer> verityUnReviewedLegalSubject(long id, ReviewStoreInput input) {
        int result = 0;
        try {
            result = this.legalSubjectMapper.verityUnReviewedLegalSubject(id,input);
            if (result==0)new ServiceStatusInfo<>(1, "审核商家失败", result);
            return new ServiceStatusInfo<>(0, "审核完毕", result);
        }catch (Exception e){
            return new ServiceStatusInfo<>(1, "审核出现异常：" + e.getMessage(), result);
        }
    }


    @Override
    public List<LegalSubjectReviewModel> getReviewsByLegalSubjectId(long id) {
        List<LegalSubjectReviewModel> legalSubjectReviewModels = this.legalSubjectMapper.getReviewsByLegalSubjectId(id);
        return legalSubjectReviewModels;
    }

    @Override
    public ShopTenantModel getDetailTenant(long legalSubjectId) {
        ShopTenantModel model = this.legalSubjectMapper.getDetailTenant(legalSubjectId);
        return model;
    }

    @Override
    public ServiceStatusInfo<LegalSubjectReviewModel> getLegalSubjectReviewById(long id) {
        try {
            LegalSubjectReviewModel model = this.legalSubjectMapper.getLegalSubjectReviewById(id);
            if (model==null)return new ServiceStatusInfo<>(1,"没有查询到结果",null);
            return new ServiceStatusInfo<>(0,"",model);
        }catch (Exception e){
            return new ServiceStatusInfo<>(1,"查询资料失败,出现异常："+e.getMessage(),null);
        }

    }

    /**
     * 审核商家需要审核的资料
     * @param id
     * @param input
     * @return
     */
    @Override
    public ServiceStatusInfo<Integer> verityLegalSubjectReview(long id,LegalSubjectReviewVerityInput input) {
        try {
            int result = this.legalSubjectMapper.verityLegalSubjectReview(id,input);
            if (result==0)return new ServiceStatusInfo<>(1,"审核资料失败",0);
            return new ServiceStatusInfo<>(0,"审核资料成功",result);
        }catch (Exception e){
            return new ServiceStatusInfo<>(1,"审核资料失败,出现异常："+e.getMessage(),0);
        }

    }


    @Override
    public int updateStatusById(long id, int state) {
        int result = this.legalSubjectMapper.updateStatusById(id,state);
        return result;
    }
}
