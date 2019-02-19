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
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class TagService {
    @Autowired
    ITagMapper tagMapper;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
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

    public ServiceStatusInfo<Object> addTodayTag(long id,String date){
        String mon = date.substring(0,7)+"monthTags";
        this.redisTemplate.opsForHash().put(mon, date, String.valueOf(id));
        return new ServiceStatusInfo<>(0,"",true);

    }

    public AdVideoTagDto getTagDetailById(long tagId){
        return this.tagMapper.getTagDetailById(tagId);
    }


    public ServiceStatusInfo<List<TodayTagsDto>> getTagsByYearAndMonth(String yearAndMonth){
        if (!yearAndMonth.contains("-"))return new ServiceStatusInfo<>(1,"时间格式不对",null);
        try {
            List<TodayTagsDto> tagsDtoList = new ArrayList<>();
            Map result = this.redisTemplate.opsForHash().entries(yearAndMonth);
            Iterator entries = result.entrySet().iterator();
            while (entries.hasNext()) {
                TodayTagsDto dto = new TodayTagsDto();
                Map.Entry entry = (Map.Entry) entries.next();
                String date = (String)entry.getKey();
                String tagId = (String)entry.getValue();
                System.out.println("date = " + date + ", tagId = " + tagId);
                dto.setDate(date);
                if (!tagId.equals("")){
                    long id = Long.valueOf(tagId);
                    AdVideoTagDto tagDto = this.getTagDetailById(id);
                    dto.setDesc(tagDto.getDesc());
                    dto.setName(tagDto.getName());
                    dto.setResNumber((long)(tagDto.getResNumber()*0.9));
                    dto.setResVideoNumber(tagDto.getResNumber());
                }
                tagsDtoList.add(dto);

            }
            return new ServiceStatusInfo<>(0,"",tagsDtoList);
        }catch (Exception e){
            return new ServiceStatusInfo<>(1,e.getMessage(),null);
        }


    }
}
