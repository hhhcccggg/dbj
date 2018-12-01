package com.zwdbj.server.adminserver.service.tag.service;

import com.zwdbj.server.adminserver.model.ResourceOpenInput;
import com.zwdbj.server.utility.model.ServiceStatusInfo;
import com.zwdbj.server.adminserver.service.homepage.model.AdFindHotTagsDto;
import com.zwdbj.server.adminserver.service.tag.mapper.ITagMapper;
import com.zwdbj.server.adminserver.service.tag.model.*;
import com.zwdbj.server.utility.common.UniqueIDCreater;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TagService {
    @Autowired
    ITagMapper tagMapper;
    private Logger logger = LoggerFactory.getLogger(TagService.class);


    public List<AdVideoTagDto> getVideoTagAd(AdVideoTagInput input){
        List<AdVideoTagDto>  videoTagDtos = this.tagMapper.getVideoTagAd(input);
        return videoTagDtos;
    }

    public ServiceStatusInfo<Long> addVideoTagAd(AdNewVideoTagInput input){
        int tag = this.tagMapper.findTagByName(input.getName());
        if (tag<=0){
            Long id = UniqueIDCreater.generateID();
            Long result = 0L;
            try {
                String desc = input.getDesc();
                if (desc==null)desc="";
                result = this.tagMapper.addVideoTagAd(id,input.getName(),desc);
                return new ServiceStatusInfo<>(0,"",result);
            }catch (Exception e){
                return new ServiceStatusInfo<>(1,"创建失败"+e.getMessage(),result);
            }
        }else {
            return  new ServiceStatusInfo<>(1,"创建失败,标签已存在",null);
        }

    }
    
    public List<AdFindHotTagsDto> findHotTags(){
        return this.tagMapper.findHotTags();
    }

    public ServiceStatusInfo<Object> addHotTag(ResourceOpenInput<Long> input){
        int result = this.tagMapper.addHotTag(input);
        return new ServiceStatusInfo<>(0,"",result);

    }
}
