package com.zwdbj.server.adminserver.service.complain.service;

import com.zwdbj.server.adminserver.service.ServiceStatusInfo;
import com.zwdbj.server.adminserver.service.complain.mapper.IComplainMapper;
import com.zwdbj.server.adminserver.service.complain.model.*;
import com.zwdbj.server.adminserver.service.living.service.LivingService;
import com.zwdbj.server.adminserver.service.qiniu.service.QiniuService;
import com.zwdbj.server.adminserver.service.user.service.UserService;
import com.zwdbj.server.adminserver.service.video.service.VideoService;
import com.zwdbj.server.adminserver.shiro.JWTUtil;
import com.zwdbj.server.adminserver.utility.UniqueIDCreater;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ComplainService {
    @Autowired
    protected IComplainMapper complainMapper;
    @Autowired
    QiniuService qiniuService;
    @Autowired
    VideoService videoService;
    @Autowired
    LivingService livingService;
    @Autowired
    UserService userService;

    public List<ComplainReasonListInfoDto> list(ComplainReasonListInput input) {
        return this.complainMapper.list(input);
    }

    @Transactional
    public ServiceStatusInfo<Object> pubComplain(PubComplainInput input) {
        PubComplainInfo pubComplainInfo = new ModelMapper().map(input,PubComplainInfo.class);
        pubComplainInfo.setId(UniqueIDCreater.generateID());
        pubComplainInfo.setSnapshotKey(this.qiniuService.url(pubComplainInfo.getSnapshotKey()));
        long userId = JWTUtil.getCurrentId();
        if (userId>0) {
            pubComplainInfo.setFromUserId(userId);
            pubComplainInfo.setFromTypeId(0);
        } else {
            pubComplainInfo.setFromUserId(0);
            pubComplainInfo.setFromTypeId(1);
        }
        //0：用户1：视频2：直播
        if (pubComplainInfo.getToResTypeId()==0) {
            if (pubComplainInfo.getFromUserId()>0) {
                this.userService.updateField("complainCount=complainCount+1", pubComplainInfo.getToResId());
            }
        } else if (pubComplainInfo.getToResTypeId()==1) {
            this.videoService.updateField("complainCount=complainCount+1",pubComplainInfo.getToResId());
        } else if(pubComplainInfo.getToResTypeId()==2) {
            this.livingService.updateField("complainCount=complainCount+1",pubComplainInfo.getToResId());
        }
        //TODO 需要防止重复举报??
        this.complainMapper.pubComplain(pubComplainInfo);
        return new ServiceStatusInfo<>(0,"举报成功",null);
    }

    //admin
    public List<AdComplainListDto> basicCompalinAd(AdFindComplainInput input,int resTypeId){
        List<AdComplainListDto> complainListDtos = this.complainMapper.basicCompalinAd(input,resTypeId);
        return complainListDtos;
    }

    public ServiceStatusInfo<Long> addComplainAd(AdNewComplainInput input,int type){
        //TODO 检查title是否在系统存在
        Long id = UniqueIDCreater.generateID();
        Long result = 0L;
        try {
            result = this.complainMapper.addComplainAd(input,id,type);
            return new ServiceStatusInfo<>(0,"",result);
        }catch (Exception e){
            return new ServiceStatusInfo<>(1,"创建失败"+e.getMessage(),result);
        }
    }
    //定时任务
    public List<UserComplainDto>  findUserComplainCount(){
        List<UserComplainDto> userComplainDtos = this.complainMapper.findUserComplainCount();
        return userComplainDtos;
    }
}
