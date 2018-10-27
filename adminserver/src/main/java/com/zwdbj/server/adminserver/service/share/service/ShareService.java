package com.zwdbj.server.adminserver.service.share.service;

import com.zwdbj.server.adminserver.model.EntityKeyModel;
import com.zwdbj.server.adminserver.service.pet.model.PetModelDto;
import com.zwdbj.server.adminserver.service.pet.service.PetService;
import com.zwdbj.server.adminserver.service.share.model.ShareDto;
import com.zwdbj.server.adminserver.service.video.service.VideoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ShareService {

    @Autowired
    private VideoService videoService;
    @Autowired
    private PetService petService;
    private Logger logger = LoggerFactory.getLogger(ShareService.class);
    public ShareDto doShare(Long id){
        try {
            String linkPet = this.videoService.findLinkPets(id);
            if (linkPet==null) return null;
            String[] pets = linkPet.split(",");
            List<EntityKeyModel<Long>> petIds = new ArrayList<>();
            for (String pet:pets) {
                EntityKeyModel<Long> petId= new EntityKeyModel<>();
                petId.setId(Long.parseLong(pet));
                petIds.add(petId);
            }
            List<PetModelDto> petModelDtos = petService.findMore(petIds);
            ShareDto sharedto = this.videoService.doShare(id);
            sharedto.setPetModelDtos(petModelDtos);
            return sharedto;
        }catch (RuntimeException e){
            logger.error(e.getMessage());
            throw new RuntimeException();
        }


    }
}
