package com.zwdbj.server.mobileapi.service.pet.service;

import com.zwdbj.server.mobileapi.shiro.JWTUtil;
import com.zwdbj.server.probuf.middleware.mq.QueueWorkInfoModel;
import com.zwdbj.server.mobileapi.model.EntityKeyModel;
import com.zwdbj.server.mobileapi.service.ServiceStatusInfo;
import com.zwdbj.server.mobileapi.service.pet.mapper.IPetMapper;
import com.zwdbj.server.mobileapi.service.pet.model.PetModelDto;
import com.zwdbj.server.mobileapi.service.pet.model.UpdatePetModelInput;
import com.zwdbj.server.mobileapi.service.qiniu.service.QiniuService;
import com.zwdbj.server.mobileapi.service.review.service.ReviewService;
import com.zwdbj.server.utility.common.UniqueIDCreater;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PetService {
    @Autowired
    protected IPetMapper petMapper;
    @Autowired
    protected QiniuService qiniuService;
    protected Logger logger = LoggerFactory.getLogger(PetService.class);
    @Autowired
    protected ReviewService reviewService;

    public List<PetModelDto> list(long userId) {
        List<PetModelDto> pets = this.petMapper.list(userId);
        // TODO 解析宠物的分类
        return pets;
    }

    public PetModelDto get(long id) {
        // TODO 解析宠物的分类
        return this.petMapper.get(id);
    }

    public List<PetModelDto> findMore(List<EntityKeyModel<Long>> ids) {
        List<PetModelDto> pets = this.petMapper.findMore(ids);
        // TODO 解析宠物的分类
        return pets;
    }

    public ServiceStatusInfo<Long> delete(long id) {
        long userId = JWTUtil.getCurrentId();
        if (userId<=0) return new ServiceStatusInfo<>(1,"请重新登录",null);
        long rows = this.petMapper.delete(id,userId);
        if (rows ==0) {
            return  new ServiceStatusInfo<>(1,"删除失败",null);
        }
        return new ServiceStatusInfo<>(0,"删除成功",rows);
    }

    public ServiceStatusInfo<Long> add(UpdatePetModelInput input,long userId) {
        String imageKey = input.getAvatar();
        if (!(imageKey == null || imageKey.equals(""))) {
            input.setAvatar(this.qiniuService.url(imageKey));
        }
        input.setId(UniqueIDCreater.generateID());
        long rows = this.petMapper.add(input,userId);
        if (rows ==0) {
            return  new ServiceStatusInfo<>(1,"添加失败",null);
        } else {
            this.reviewAvatar(imageKey,1,input.getId());
            return new ServiceStatusInfo<>(0,"添加成功",rows);
        }
    }

    public ServiceStatusInfo<Long> update(UpdatePetModelInput input) {
        String imageKey = input.getAvatar();
        if (!(imageKey == null || imageKey.equals(""))) {
            input.setAvatar(this.qiniuService.url(imageKey));
        }
        if (input.getId()<=0) {
            return new ServiceStatusInfo<>(1,"参数有误",null);
        }
        long rows = this.petMapper.update(input);
        if (rows ==0) {
            return  new ServiceStatusInfo<>(1,"保存失败",null);
        } else {
            this.reviewAvatar(imageKey,1,input.getId());
            return new ServiceStatusInfo<>(0,"保存成功",rows);
        }
    }

    /**
     * 将图片增加到审核队列
     * @param imageKey
     */
    private void reviewAvatar(String imageKey,int dataType,long dateId) {
        if (imageKey == null || imageKey.length()==0 || imageKey.startsWith("http://")) return;
        QueueWorkInfoModel.QueueWorkQiniuWaitReviewResData resData =
                QueueWorkInfoModel.QueueWorkQiniuWaitReviewResData.newBuilder()
                        .setResType(0)
                        .setResContent(imageKey)
                        .setDataId(dateId)
                        .setDataType(dataType)
                        .build();
        this.reviewService.reviewQiniuRes(resData);
    }

}
