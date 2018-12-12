package com.zwdbj.server.adminserver.service.homepage.service;

import com.zwdbj.server.adminserver.service.dailyIncreaseAnalysises.service.DailyIncreaseAnalysisesService;
import com.zwdbj.server.adminserver.service.homepage.model.AdFindHotTagsDto;
import com.zwdbj.server.adminserver.service.homepage.model.AdFindIncreasedDto;
import com.zwdbj.server.adminserver.service.homepage.model.AdFindIncreasedInput;
import com.zwdbj.server.adminserver.service.homepage.model.AdUserOrVideoGrowthDto;
import com.zwdbj.server.adminserver.service.tag.service.TagService;
import com.zwdbj.server.adminserver.service.user.service.UserService;
import com.zwdbj.server.adminserver.service.video.service.VideoService;
import com.zwdbj.server.utility.common.shiro.JWTUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class HomepageService {

    @Autowired
    UserService userService;
    @Autowired
    VideoService videoService;
    @Autowired
    DailyIncreaseAnalysisesService dailyIncreaseAnalysisesService;
    @Autowired
    TagService tagService;
    @Autowired
    StringRedisTemplate stringRedisTemplate;
    private Logger logger = LoggerFactory.getLogger(HomepageService.class);
    public AdFindIncreasedDto findIncreasedAd(AdFindIncreasedInput input){
        long userId = JWTUtil.getCurrentId();
        List<String> roles = this.userService.getUserAuthInfo(userId).getRoles();
        boolean flag = false;
        for (String nickName:roles){
            if ("datareport".equals(nickName))flag=true;
        }
        AdFindIncreasedDto dto = this.userService.findIncreasedUserAd(input,flag);
        if (dto==null) return null;
        Long videoNum = this.videoService.findIncreasedVideoAd(input.getQuantumTime());
        Long verifingVideoNum = this.videoService.findIncreasedVideoingAd(input.getQuantumTime());
        long dau1 = this.userService.dau();
        if (flag){
            String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date())+"v";
            if (this.stringRedisTemplate.hasKey(date)){
                videoNum = Long.valueOf(this.stringRedisTemplate.opsForValue().get(date));
                logger.info("videoNum="+videoNum);
            }
            dau1 = this.dailyIncreaseAnalysisesService.dau();
        }
        long dau = new Double(Math.ceil(dau1/3.5)).longValue();
        long mau = new Double(Math.ceil(dau1*4.5/3.5)).longValue();
        dto.setDau(dau);
        dto.setMau(mau);
        dto.setVideoNum(videoNum);
        dto.setVerifingVideoNum(verifingVideoNum);
        return dto;
    }

    public List<AdUserOrVideoGrowthDto> userGrowthAd(AdFindIncreasedInput input){
        List<AdUserOrVideoGrowthDto> growthDtos = this.dailyIncreaseAnalysisesService.userGrowthAd(input);
        return growthDtos;
    }

    public List<AdUserOrVideoGrowthDto> videoGrowthAd(AdFindIncreasedInput input){
        List<AdUserOrVideoGrowthDto> growthDtos = this.dailyIncreaseAnalysisesService.videoGrowthAd(input);
        return growthDtos;
    }

    public List<AdFindHotTagsDto> findHotTags(){
        return this.tagService.findHotTags();
    }
}
