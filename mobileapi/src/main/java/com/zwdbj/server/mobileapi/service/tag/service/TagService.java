package com.zwdbj.server.mobileapi.service.tag.service;


import com.zwdbj.server.mobileapi.service.tag.mapper.ITagMapper;
import com.zwdbj.server.mobileapi.service.tag.model.*;
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

    public List<TagDto> search(TagSearchInput input) {
        List<TagDto> dtos = this.tagMapper.search(input);
        return dtos;
    }
    public void everyTagCount(String[] tagNames){
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
    }

    /**
     * 热门主题标签
     */
    public List<TagDto> hotTags(){
        List<TagDto> dtos = this.tagMapper.hotTags();
        return dtos;
    }

    /**
     * 查询所有的主题
     */
    public List<TagDto> listAll(){
        List<TagDto> dtos = this.tagMapper.listAll();
        return dtos;
    }
    /**
     * 主题的详情
     */
    public TagDetailDto tagDetail(String name){
        TagDetailDto tagDetailDto = this.tagMapper.tagDetail(name);
        return tagDetailDto;
    }


}
