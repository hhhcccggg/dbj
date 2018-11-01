package com.zwdbj.server.adminserver.service.pet.service;

import com.zwdbj.server.adminserver.service.pet.mapper.IPetMapper;
import com.zwdbj.server.adminserver.service.qiniu.service.QiniuService;
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

}
