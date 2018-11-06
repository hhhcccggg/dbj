package com.zwdbj.server.mobileapi.service.living.service;

import com.zwdbj.server.mobileapi.easemob.api.EaseMobChatRoom;
import com.zwdbj.server.mobileapi.model.EntityKeyModel;
import com.zwdbj.server.mobileapi.config.AppConfigConstant;
import com.zwdbj.server.mobileapi.service.ServiceStatusInfo;
import com.zwdbj.server.mobileapi.service.living.mapper.ILivingMapper;
import com.zwdbj.server.mobileapi.service.living.model.*;
import com.zwdbj.server.mobileapi.service.qiniu.service.QiniuService;
import com.zwdbj.server.mobileapi.service.resourceRefGoods.service.ResRefGoodsService;
import com.zwdbj.server.mobileapi.service.user.model.UserModel;
import com.zwdbj.server.mobileapi.service.user.service.UserService;
import com.zwdbj.server.mobileapi.shiro.JWTUtil;
import com.zwdbj.server.utility.common.UniqueIDCreater;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class LivingService {

    @Autowired
    ILivingMapper livingMapper;
    @Autowired
    QiniuService qiniuService;
    @Autowired
    UserService userService;
    @Autowired
    protected ResRefGoodsService resRefGoodsService;
    @Autowired
    protected EaseMobChatRoom easeMobChatRoom;
    protected Logger logger = LoggerFactory.getLogger(LivingService.class);

    @Transactional
    public ServiceStatusInfo<LivingInfoDto> create(CreateLivingInput input) {
        //TODO 性能优化
        long userId = JWTUtil.getCurrentId();
        long id = UniqueIDCreater.generateID();
        if (userId<=0) return new ServiceStatusInfo<>(1,"请重新登录",null);
        //判断用户是否有直播权限
        UserModel userModel = this.userService.findUserById(userId);
        boolean isPushing=false;
        if (userModel.isLiving()&&userModel.getLivingId()>0) {
            //判断当前是否正在推流中
            isPushing = qiniuService.getLiveStatus(String.valueOf(userModel.getLivingId()));
            if (isPushing) {
                return new ServiceStatusInfo<>(1, "当前正在直播，创建直播失败!", null);
            }
        }
        if (!(userModel.isLivingOpen()&&userModel.isReviewed())) {
            return new ServiceStatusInfo<>(1,"未开通直播权限，创建直播失败!",null);
        }
        CreateLivingModel model = new ModelMapper().map(input,CreateLivingModel.class);
        model.setId(id);
        model.setUserId(userId);
        model.setCoverUrl(qiniuService.url(model.getCoverUrl()));
        String streamName = String.valueOf(id);
        String pushlishUrl = this.qiniuService.getRTMPPublishUrl(streamName);
        logger.info("publish url ="+pushlishUrl);
        model.setRtmpPublishUrl(pushlishUrl);
        model.setRtmpPlayUrl(qiniuService.getRTMPPlayUrl(streamName));
        model.setHlsPlayUrl(qiniuService.getHLSPlayUrl(streamName));
        if (model.getRtmpPublishUrl()!=null) {
            logger.info(model.getRtmpPublishUrl());
        } else {
            logger.info("没有成功推流地址");
        }
        long result = this.livingMapper.add(model);
        this.userService.updateField("isLiving=true",userId);
        this.userService.updateField("livingId="+id,userId);
        if (result>0) {
            //更新之前的直播信息
            if(userModel.getLivingId()>0) {
                this.livingMapper.updateField("isLiving=false",userModel.getLivingId());
            }
            LivingInfoDto infoDto = get(id);
            if (infoDto == null) {
                return new ServiceStatusInfo<>(1,"创建直播失败!",null);
            } else {
                return new ServiceStatusInfo<>(0,"创建直播成功!",infoDto);
            }
        } else {
            return new ServiceStatusInfo<>(1,"创建直播失败!",null);
        }
    }

    public LivingDetailInfoDto get(long id) {
        LivingDetailInfoDto infoDto = this.livingMapper.get(id);
        if (infoDto==null) return null;
        setLivingInfo(infoDto);
        //TODO 增加真实的URL
        infoDto.setLinkProductUrl(AppConfigConstant.getShopListUrl(id,"live"));
        infoDto.setShareTitle(infoDto.getTitle());
        infoDto.setShareUrl(AppConfigConstant.getShareUrl(id,"live"));
        infoDto.setShareContent(infoDto.getTitle());
        if (infoDto.getUserId()!=JWTUtil.getCurrentId()) {
            infoDto.setPushUrl(null);
            infoDto.setAddProductUrl("");
        } else {
            infoDto.setAddProductUrl(AppConfigConstant.getLiveAddShopUrl(id));
        }
        //TODO 判断聊天室是否已经生成
        //TODO 优化性能
        if((infoDto.getChatRoomId()==null||infoDto.getChatRoomId().length()==0)&&infoDto.isLiving()) {
            String chatroomId = this.easeMobChatRoom.registerChatRoom(infoDto.getTitle(),String.valueOf(infoDto.getUserId()));
            if (chatroomId!=null) {
                infoDto.setChatRoomId(chatroomId);
                this.livingMapper.updateField("chatRoomId='"+chatroomId+"'",infoDto.getId());
            }
        }

        return infoDto;
    }

    public List<LivingInfoDto> livingList() {
        List<LivingInfoDto> livingInfoDtoList = this.livingMapper.living();
        for(LivingInfoDto dto:livingInfoDtoList){
            setLivingInfo(dto);
        }
        return livingInfoDtoList;
    }

    public List<LivingInfoDto> myFollowedLiving(long userId) {
        List<LivingInfoDto> dtos = this.livingMapper.myFollowedLiving(userId);
        for (LivingInfoDto dto : dtos) {
            setLivingInfo(dto);
        }
        return dtos;
    }

    @Transactional
    public ServiceStatusInfo<Object> stopLive(long liveId) {
        //TODO 下发通知到用户端
        LivingDetailInfoDto infoDto = get(liveId);
        long userId = JWTUtil.getCurrentId();
        String chatRoomId = this.livingMapper.findChatRoomIdById(liveId);
        if (infoDto!=null) {
            if (infoDto.getUserId()!=userId) {
                return new ServiceStatusInfo<>(1,"没有权限停止此直播",null);
            }
            easeMobChatRoom.deleteChatRoom(chatRoomId);
            String streamName = String.valueOf(liveId);
            qiniuService.disableStream(streamName,liveId);
            this.livingMapper.updateField("isLiving=false", liveId);
            this.userService.updateField("isLiving=false", userId);
            this.userService.updateField("livingId=0", userId);
        }
        return new ServiceStatusInfo<>(0,"",null);
    }
    public LivingDetailInfoDto getByUserId(long userId) {
        UserModel userModel = this.userService.findUserById(userId);
        if (userModel==null) return null;
        if (userModel.getLivingId()>0&&userModel.isLiving()) {
            return get(userModel.getLivingId());
        }
        return null;
    }

    public void  updateShareCount(long id) {
        this.livingMapper.updateField("shareCount=shareCount+1",id);
    }
    public void updateField(String fields,long id) {
        this.livingMapper.updateField(fields,id);
    }
    //短视频关联商品
    @Transactional
    public ServiceStatusInfo<Object> saveGoodsAd(long livingId, String goods) {
        //判断用户是主播本身
        //TODO 性能
        long userId = JWTUtil.getCurrentId();
        if (userId<=0) return new ServiceStatusInfo<>(1,"请重新登录",null);
        LivingDetailInfoDto livingDetailInfoDto = this.livingMapper.get(livingId);
        if (livingDetailInfoDto==null) return new ServiceStatusInfo<>(1,"直播间不存在",null);
        if (livingDetailInfoDto.getUserId()!=userId) {
            return new ServiceStatusInfo<>(1,"没有权限操作",null);
        }
        UserModel userModel = this.userService.findUserById(userId);
        if (!(userModel.isLivingOpen()&&userModel.isReviewed())) {
            return new ServiceStatusInfo<>(1,"未开通直播权限，关联商品失败!",null);
        }
        String chatRoomId = this.livingMapper.timedQueryChatRoomId(livingId);
        int linkProduct=0;
        if (goods!=null&&goods.length()>0) {
            linkProduct=goods.split(",").length;
        }
        this.livingMapper.updateField("linkProductCount="+linkProduct,livingId);
        this.easeMobChatRoom.setMessages(chatRoomId,linkProduct);
        String haveGoods = this.resRefGoodsService.getGoods(livingId);
        if (haveGoods==null) {
            this.resRefGoodsService.add(livingId,goods,1);
        } else {
            this.resRefGoodsService.update(livingId,goods);
        }
        return new ServiceStatusInfo<>(0,"",goods);
    }

    public ServiceStatusInfo<EntityKeyModel<String>> getGoodsAd(long livingId) {
        String goods = this.resRefGoodsService.getGoods(livingId);
        if (goods==null) return new ServiceStatusInfo<>(1,"未找到关联的商品",null);
        return new ServiceStatusInfo<>(0,"",new EntityKeyModel<>(goods));
    }

    private void setLivingInfo(LivingInfoDto dto) {
        if (dto == null) return;
        UserModel userModel = userService.findUserById(dto.getUserId());
        if (userModel != null) {
            dto.setUserNickName(userModel.getNickName());
            dto.setUserAvatarUrl(userModel.getAvatarUrl());
        }
        long curUserId = JWTUtil.getCurrentId();
        if (curUserId>0) {
            dto.setFollow(userService.isFollower(dto.getUserId(),curUserId));
        }
    }


}
