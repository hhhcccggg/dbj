package com.zwdbj.server.adminserver.service.pet.service;

import com.zwdbj.server.adminserver.service.pet.mapper.IPetMapper;
import com.zwdbj.server.adminserver.service.pet.model.PetModelDto;
import com.zwdbj.server.common.qiniu.QiniuService;
import com.zwdbj.server.utility.common.shiro.JWTUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class PetService {
    @Autowired
    IPetMapper petMapper;
    @Autowired
    QiniuService qiniuService;
    protected Logger logger = LoggerFactory.getLogger(PetService.class);


    //审核相关
    public void updateReview(Long id,String reviewResultType){
        if ("block".equals(reviewResultType) || "review".equals(reviewResultType)){
            this.petMapper.updatePetAvatar(id);
        }else {
            logger.info("宠物图片审核正常,不需要处理");
        }
    }

    public PetModelDto get(long id) {
        // TODO 解析宠物的分类
        PetModelDto dto = this.petMapper.get(id);
        return dto;
    }

}
