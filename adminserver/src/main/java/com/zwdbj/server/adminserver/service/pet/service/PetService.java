package com.zwdbj.server.adminserver.service.pet.service;

import com.zwdbj.server.adminserver.model.EntityKeyModel;
import com.zwdbj.server.adminserver.service.ServiceStatusInfo;
import com.zwdbj.server.adminserver.service.pet.mapper.IPetMapper;
import com.zwdbj.server.adminserver.service.pet.model.PetModelDto;
import com.zwdbj.server.adminserver.service.pet.model.UpdatePetModelInput;
import com.zwdbj.server.adminserver.service.qiniu.service.QiniuService;
import com.zwdbj.server.adminserver.utility.UniqueIDCreater;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PetService {
    @Autowired
    IPetMapper petMapper;
    @Autowired
    QiniuService qiniuService;
    protected Logger logger = LoggerFactory.getLogger(PetService.class);

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
        long rows = this.petMapper.delete(id);
        if (rows ==0) {
            return  new ServiceStatusInfo<>(1,"删除失败",null);
        }
        return new ServiceStatusInfo<>(0,"删除成功",rows);
    }

    public ServiceStatusInfo<Long> add(UpdatePetModelInput input,long userId) {
        if (!(input.getAvatar() == null || input.getAvatar().equals(""))) {
            input.setAvatar(this.qiniuService.url(input.getAvatar()));
        }
        input.setId(UniqueIDCreater.generateID());
        long rows = this.petMapper.add(input,userId);
        if (rows ==0) {
            return  new ServiceStatusInfo<>(1,"添加失败",null);
        } else {
            return new ServiceStatusInfo<>(0,"添加成功",rows);
        }
    }

    public ServiceStatusInfo<Long> update(UpdatePetModelInput input) {
        if (!(input.getAvatar() == null || input.getAvatar().equals(""))) {
            input.setAvatar(this.qiniuService.url(input.getAvatar()));
        }
        if (input.getId()<=0) {
            return new ServiceStatusInfo<>(1,"参数有误",null);
        }
        long rows = this.petMapper.update(input);
        if (rows ==0) {
            return  new ServiceStatusInfo<>(1,"保存失败",null);
        } else {
            return new ServiceStatusInfo<>(0,"保存成功",rows);
        }
    }

    //审核相关
    public void updateReview(Long id,String reviewResultType){
        if ("block".equals(reviewResultType) || "review".equals(reviewResultType)){
            this.petMapper.updatePetAvatar(id);
        }else {
            logger.info("宠物图片审核正常,不需要处理");
        }
    }

}
