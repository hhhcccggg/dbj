package com.zwdbj.server.shop_admin_service.service.legalSubject.service;

import com.zwdbj.server.probuf.middleware.mq.QueueWorkInfoModel;
import com.zwdbj.server.shop_admin_service.service.legalSubject.mapper.ILegalSubjectMapper;
import com.zwdbj.server.shop_admin_service.service.legalSubject.model.LegalSubjectModel;
import com.zwdbj.server.shop_admin_service.service.legalSubject.model.LegalSubjectReviewModel;
import com.zwdbj.server.shop_admin_service.service.legalSubject.model.LegalSubjectSearchInput;
import com.zwdbj.server.shop_admin_service.service.legalSubject.model.LegalSubjectVerityInput;
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
    protected Logger logger = LoggerFactory.getLogger(LegalSubjectServiceImpl.class);


    @Override
    public boolean handleLegalSubject(QueueWorkInfoModel.QueueWorkShopLegalSubjectData data) {
        try {
            if (data==null)return true;
            int type = data.getType();
            if (type==1){
                this.addLegalSubject(data);
                logger.info("++++++添加商家基本信息++++++");
            }else if (type==2){
                this.modifyBasicLegalSubject(data);
                logger.info("++++++修改商家基本信息++++++");
            }else if (type==3){
                this.delLegalSubject(data);
                logger.info("++++++删除商家基本信息++++++");
            }else {
                logger.info("++++++商家信息处理失败++++++");
                return false;
            }
        }catch (Exception e){
            logger.error("++++++商家信息处理出现异常++++++"+e.getMessage());
        }
        return true;
    }
    public void addLegalSubject(QueueWorkInfoModel.QueueWorkShopLegalSubjectData data){
        //创建商家
        this.legalSubjectMapper.addLegalSubject(data);
        //创建店铺
        // TODO 创建店铺
    }
    public void modifyBasicLegalSubject(QueueWorkInfoModel.QueueWorkShopLegalSubjectData data){
        LegalSubjectModel model = this.getLegalSubjectById(data.getLegalSubjectId());
        if (model==null)return;
        //修改商家基本信息
        this.legalSubjectMapper.modifyBasicLegalSubject(data);
        //修改店铺
        // TODO 修改店铺信息
    }
    public void delLegalSubject(QueueWorkInfoModel.QueueWorkShopLegalSubjectData data){
        LegalSubjectModel model = this.getLegalSubjectById(data.getLegalSubjectId());
        if (model==null)return;
        //删除商家
        this.legalSubjectMapper.delLegalSubject(data.getLegalSubjectId());
        //删除店铺
        // TODO 删除店铺信息
    }
    public LegalSubjectModel getLegalSubjectById(long id){
        return this.legalSubjectMapper.getLegalSubjectById(id);
    }

    public void addShopStore(QueueWorkInfoModel.QueueWorkShopLegalSubjectData data){
        long id = UniqueIDCreater.generateID();
        //this.legalSubjectMapper.addShopStore();
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
    public ServiceStatusInfo<Integer> verityUnReviewed(long id, LegalSubjectVerityInput input) {
        int result = 0;
        try {
            result = this.legalSubjectMapper.verityUnReviewed(id,input);
            // TODO 需不需要这里把店铺也审核了？
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
}
