package com.zwdbj.server.adminserver.service.complain.service;

import com.zwdbj.server.adminserver.service.ServiceStatusInfo;
import com.zwdbj.server.adminserver.service.complain.mapper.IComplainMapper;
import com.zwdbj.server.adminserver.service.complain.model.*;
import com.zwdbj.server.adminserver.service.living.service.LivingService;
import com.zwdbj.server.adminserver.service.qiniu.service.QiniuService;
import com.zwdbj.server.adminserver.service.user.service.UserService;
import com.zwdbj.server.adminserver.service.video.service.VideoService;
import com.zwdbj.server.utility.common.UniqueIDCreater;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
