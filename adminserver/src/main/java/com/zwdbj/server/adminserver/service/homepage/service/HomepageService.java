package com.zwdbj.server.adminserver.service.homepage.service;

import com.zwdbj.server.adminserver.service.dailyIncreaseAnalysises.service.DailyIncreaseAnalysisesService;
import com.zwdbj.server.adminserver.service.homepage.model.AdFindHotTagsDto;
import com.zwdbj.server.adminserver.service.homepage.model.AdFindIncreasedDto;
import com.zwdbj.server.adminserver.service.homepage.model.AdFindIncreasedInput;
import com.zwdbj.server.adminserver.service.homepage.model.AdUserOrVideoGrowthDto;
import com.zwdbj.server.adminserver.service.tag.service.TagService;
import com.zwdbj.server.adminserver.service.user.service.UserService;
import com.zwdbj.server.adminserver.service.video.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public AdFindIncreasedDto findIncreasedAd(AdFindIncreasedInput input){
        AdFindIncreasedDto dto = this.userService.findIncreasedUserAd(input);
        if (dto==null) return null;
        Long videoNum = this.videoService.findIncreasedVideoAd(input.getQuantumTime());
        Long verifingVideoNum = this.videoService.findIncreasedVideoingAd(input.getQuantumTime());
        long dau1 = this.userService.dau();
        long dau = new Double(Math.ceil(dau1/3.5)).longValue();
        Long mau = new Double(Math.ceil(dau1*4.5/3.5)).longValue();
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
