package com.zwdbj.server.adminserver.service.tag.service;

import com.zwdbj.server.adminserver.service.ServiceStatusInfo;
import com.zwdbj.server.adminserver.service.homepage.model.AdFindHotTagsDto;
import com.zwdbj.server.adminserver.service.tag.mapper.ITagMapper;
import com.zwdbj.server.adminserver.service.tag.model.*;
import com.zwdbj.server.adminserver.utility.UniqueIDCreater;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TagService {
    /*private static ThreadLocal<SqlSession> threadLocal =new ThreadLocal<SqlSession>();
    private static SqlSessionFactory sqlSessionFactory;*/
    @Autowired
    ITagMapper tagMapper;
    private Logger logger = LoggerFactory.getLogger(TagService.class);

    public List<TagDto> search(TagSearchInput input) {
        List<TagDto> dtos = this.tagMapper.search(input);
        return dtos;
    }

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
                result = this.tagMapper.addVideoTagAd(id,input.getName());
                return new ServiceStatusInfo<>(0,"",result);
            }catch (Exception e){
                return new ServiceStatusInfo<>(1,"创建失败"+e.getMessage(),result);
            }
        }else {
            return  new ServiceStatusInfo<>(1,"创建失败,标签已存在",null);
        }

    }

    public ServiceStatusInfo<Object> everyTagCount(String[] tagNames){
        for (String tagName:tagNames) {
            int tag = this.tagMapper.findTagByName(tagName);
            if (tag<=0){
                try {
                    Long id = UniqueIDCreater.generateID();
                    this.tagMapper.addVideoTag(id,tagName);
                    logger.info("标签增加------");
                }catch (RuntimeException e){
                    logger.info("标签增加错误------"+e.getMessage());
                    e.printStackTrace();
                }
            }else {
                try {
                    this.tagMapper.updateTagResNumber(tagName);
                    logger.info("标签数量更新成功------");
                }catch (Exception e){
                    logger.info("标签数量更新错误-------"+e.getMessage());
                    e.printStackTrace();
                }
            }

        }
        return new ServiceStatusInfo<>(0,"","");
    }

    public List<AdFindHotTagsDto> findHotTags(){
        return this.tagMapper.findHotTags();
    }
}
