package com.zwdbj.server.mobileapi.service.pet.service;

import com.zwdbj.server.mobileapi.model.HeartInput;
import com.zwdbj.server.mobileapi.service.heart.model.HeartModel;
import com.zwdbj.server.mobileapi.service.heart.service.HeartService;
import com.zwdbj.server.mobileapi.service.messageCenter.model.MessageInput;
import com.zwdbj.server.mobileapi.service.messageCenter.service.MessageCenterService;
import com.zwdbj.server.mobileapi.service.pet.model.PetHeartDto;
import com.zwdbj.server.mobileapi.service.user.model.UserModel;
import com.zwdbj.server.mobileapi.service.user.service.UserService;
import com.zwdbj.server.mobileapi.service.userInvitation.service.UserInvitationService;
import com.zwdbj.server.mobileapi.service.userAssets.model.UserCoinDetailAddInput;
import com.zwdbj.server.mobileapi.service.userAssets.service.UserAssetServiceImpl;
import com.zwdbj.server.mobileapi.service.video.model.VideoDetailInfoDto;
import com.zwdbj.server.mobileapi.service.video.model.VideoHeartStatusDto;
import com.zwdbj.server.utility.common.shiro.JWTUtil;
import com.zwdbj.server.probuf.middleware.mq.QueueWorkInfoModel;
import com.zwdbj.server.mobileapi.model.EntityKeyModel;
import com.zwdbj.server.utility.model.ResponseCoin;
import com.zwdbj.server.utility.model.ServiceStatusInfo;
import com.zwdbj.server.mobileapi.service.pet.mapper.IPetMapper;
import com.zwdbj.server.mobileapi.service.pet.model.PetModelDto;
import com.zwdbj.server.mobileapi.service.pet.model.UpdatePetModelInput;
import com.zwdbj.server.mobileapi.service.qiniu.service.QiniuService;
import com.zwdbj.server.mobileapi.service.review.service.ReviewService;
import com.zwdbj.server.utility.common.UniqueIDCreater;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class PetService {
    @Autowired
    protected IPetMapper petMapper;
    @Autowired
    protected QiniuService qiniuService;
    protected Logger logger = LoggerFactory.getLogger(PetService.class);
    @Autowired
    protected ReviewService reviewService;
    @Autowired
    protected UserService userService;
    @Autowired
    protected UserInvitationService userInvitationServiceImpl;
    @Autowired
    private UserAssetServiceImpl userAssetServiceImpl;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private HeartService heartService;
    @Autowired
    protected MessageCenterService messageCenterService;

    public List<PetModelDto> list(long userId) {
        List<PetModelDto> pets = this.petMapper.list(userId);
        // TODO 解析宠物的分类
        return pets;
    }

    public PetModelDto get(long id) {
        // TODO 解析宠物的分类
        return this.petMapper.get(id);
    }

    public List<PetModelDto> findMore(List<EntityKeyModel<Long>> ids) {
        List<PetModelDto> pets = this.petMapper.findMore(ids);
        // TODO 解析宠物的分类
        return pets;
    }

    public ServiceStatusInfo<Long> delete(long id) {
        long userId = JWTUtil.getCurrentId();
        if (userId<=0) return new ServiceStatusInfo<>(1,"请重新登录",null);
        long rows = this.petMapper.delete(id,userId);
        if (rows ==0) {
            return  new ServiceStatusInfo<>(1,"删除失败",null);
        }
        return new ServiceStatusInfo<>(0,"删除成功",rows);
    }

    @Transactional
    public ServiceStatusInfo<Long> add(UpdatePetModelInput input,long userId) {
        String regEx = "^(?!_)(?!.*?_$)[a-zA-Z0-9_\\u4e00-\\u9fa5]{1,20}$";
        Pattern r = Pattern.compile(regEx);
        Matcher m1 = r.matcher(input.getNickName());
        boolean rs1 = m1.matches();
        if (!rs1 ) return new ServiceStatusInfo<>(1, "你输入的宠物名称格式不对", null,null);
        String imageKey = input.getAvatar();
        if (!(imageKey == null || imageKey.equals(""))) {
            input.setAvatar(this.qiniuService.url(imageKey));
        }
        input.setId(UniqueIDCreater.generateID());
        boolean isFirst = this.isFirstAddPet(userId);
        ResponseCoin coin=null;
        if (isFirst){
            this.userAssetServiceImpl.userIsExist(userId);
            UserCoinDetailAddInput userCoinDetailAddInput = new UserCoinDetailAddInput();
            userCoinDetailAddInput.setStatus("SUCCESS");
            userCoinDetailAddInput.setNum(10);
            userCoinDetailAddInput.setTitle("首次添加宠物信息获得小饼干"+10+"个");
            userCoinDetailAddInput.setType("TASK");
            this.userAssetServiceImpl.userPlayCoinTask(userCoinDetailAddInput,userId,"TASK",10,"FIRSTADDPET","DONE");
            coin = new ResponseCoin();
            coin.setCoins(10);
            coin.setMessage("首次添加宠物信息获得小饼干"+10+"个");

        }
        long rows = this.petMapper.add(input,userId);
        if (rows ==0) {
            return  new ServiceStatusInfo<>(1,"添加失败",null,null);
        } else {
            this.reviewAvatar(imageKey,1,input.getId());
            firstAddPet(userId);
            return new ServiceStatusInfo<>(0,"添加成功",rows,coin);
        }
    }

    public ServiceStatusInfo<Long> update(UpdatePetModelInput input) {
        String regEx = "^(?!_)(?!.*?_$)[a-zA-Z0-9_\\u4e00-\\u9fa5]{1,20}$";
        Pattern r = Pattern.compile(regEx);
        Matcher m1 = r.matcher(input.getNickName());
        boolean rs1 = m1.matches();
        if (!rs1 ) return new ServiceStatusInfo<>(1, "你输入的宠物名称格式不对", null,null);
        String imageKey = input.getAvatar();
        if (!(imageKey == null || imageKey.equals(""))) {
            input.setAvatar(this.qiniuService.url(imageKey));
        }
        if (input.getId()<=0) {
            return new ServiceStatusInfo<>(1,"参数有误",null,null);
        }
        long rows = this.petMapper.update(input);
        if (rows ==0) {
            return  new ServiceStatusInfo<>(1,"保存失败",null,null);
        } else {
            this.reviewAvatar(imageKey,1,input.getId());
            return new ServiceStatusInfo<>(0,"保存成功",rows,null);
        }
    }

    /**
     * 将图片增加到审核队列
     * @param imageKey
     */
    private void reviewAvatar(String imageKey,int dataType,long dateId) {
        if (imageKey == null || imageKey.length()==0 || imageKey.startsWith("http://")) return;
        QueueWorkInfoModel.QueueWorkQiniuWaitReviewResData resData =
                QueueWorkInfoModel.QueueWorkQiniuWaitReviewResData.newBuilder()
                        .setResType(0)
                        .setResContent(imageKey)
                        .setDataId(dateId)
                        .setDataType(dataType)
                        .build();
        this.reviewService.reviewQiniuRes(resData);
    }

    /**
     * 查看是不是首次添加宠物,如果是看看是否有推荐人
     */
    public void firstAddPet(long userId){
        long count = petMapper.firstAddPet(userId);
        if(count > 1)return ;
        UserModel userModel = userService.findUserById(userId);
        if(userModel==null || userModel.getRecommendUserId()==0)return;
        userInvitationServiceImpl.createUserInvitation(userModel.getRecommendUserId());
    }
    /**
     * 是否为第一次添加宠物
     */
    public boolean isFirstAddPet(long userId){
        long result = this.petMapper.firstAddPet(userId);
        return result==0;
    }

    public long findUserIdByPetId(long petId){
        return this.petMapper.findUserIdByPetId(petId);
    }


    @Transactional
    public ServiceStatusInfo<PetHeartDto> heart(HeartInput input){
        long userId = JWTUtil.getCurrentId();
        if (userId <= 0) return new ServiceStatusInfo<>(1, "请重新登录", null,null);
        PetHeartDto petHeartDto = new PetHeartDto();
        petHeartDto.setPetId(input.getId());
        HeartModel heartModel = this.heartService.findHeart(userId, input.getId());
        Long pUserId = this.findUserIdByPetId(input.getId());
        if (heartModel != null && input.isHeart()) {
            return new ServiceStatusInfo<>(1, "已经点赞过", null,null);
        }
        if (heartModel != null && !input.isHeart()) {
            this.heartService.unHeart(userId, input.getId());
            this.userService.addHeart(pUserId, -1);
            petHeartDto.setHeart(false);
            return new ServiceStatusInfo<>(0, "取消成功", petHeartDto,null);
        }

        if (input.isHeart()) {
            long id = UniqueIDCreater.generateID();
            ServiceStatusInfo<Long> isFirst = this.heartService.heart(id, userId, input.getId(), 2);
            this.userService.addHeart(pUserId, 1);
            petHeartDto.setHeart(true);
            if (isFirst.getCoins()!=null)
                return new ServiceStatusInfo<>(0, "点赞成功", petHeartDto,isFirst.getCoins());
            return new ServiceStatusInfo<>(0, "点赞成功", petHeartDto);
        } else {
            return new ServiceStatusInfo<>(1, "取消失败", null,null);
        }
    }

    public ServiceStatusInfo<Long> getPetHeartCount(long petId) {
        try {
            Long count;
            if (stringRedisTemplate.hasKey(petId+"heartCount:")){
                count = Long.valueOf(this.stringRedisTemplate.opsForValue().get(petId+"heartCount:"));
            }else {
                count = this.heartService.getHeartCountByResourceOwnerId(petId,2);
                this.stringRedisTemplate.opsForValue().set(petId+"heartCount:",String.valueOf(count),3, TimeUnit.MINUTES);
            }

            return new ServiceStatusInfo<>(0, "", count);
        } catch (Exception e) {
            return new ServiceStatusInfo<>(1, "查询失败" + e.getMessage(), null);
        }
    }

}
