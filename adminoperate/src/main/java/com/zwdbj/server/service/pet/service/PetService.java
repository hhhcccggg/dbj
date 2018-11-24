package com.zwdbj.server.service.pet.service;

import com.zwdbj.server.service.pet.mapper.IPetMapper;
import com.zwdbj.server.utility.common.UniqueIDCreater;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class PetService {
    @Autowired
    IPetMapper petMapper;
    public void newPet(String avatar,long userId,String nickName,long categoryId ){
        long id  = UniqueIDCreater.generateID();
        this.petMapper.newPet(id,avatar,userId,nickName,categoryId);
    }

}
