package com.zwdbj.server.mobileapi.service.share.service;

import com.zwdbj.server.mobileapi.model.EntityKeyModel;
import com.zwdbj.server.mobileapi.service.pet.model.PetModelDto;
import com.zwdbj.server.mobileapi.service.pet.service.PetService;
import com.zwdbj.server.mobileapi.service.share.model.ShareDto;
import com.zwdbj.server.mobileapi.service.video.service.VideoService;
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
            ShareDto sharedto = this.videoService.doShare(id);
            if (sharedto==null) return null;
            if (sharedto!=null && sharedto.getStatus()!=0) {
                logger.info("视频未审核通过");
                return null;
            }
            this.videoService.addShareCount(id);
            String linkPet = this.videoService.findLinkPets(id);
            if (linkPet==null) return sharedto;
            if (linkPet!=null) {
                String[] pets = linkPet.split(",");
                List<EntityKeyModel<Long>> petIds = new ArrayList<>();
                for (String pet : pets) {
                    EntityKeyModel<Long> petId = new EntityKeyModel<>();
                    petId.setId(Long.parseLong(pet));
                    petIds.add(petId);
                }
                List<PetModelDto> petModelDtos = petService.findMore(petIds);
                if (petModelDtos!=null)
                sharedto.setPetModelDtos(petModelDtos);
                return sharedto;
            }
            return null;
        }catch (RuntimeException e){
            logger.error(e.getMessage());
            throw new RuntimeException();
        }
    }
}
