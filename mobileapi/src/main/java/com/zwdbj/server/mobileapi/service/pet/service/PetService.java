package com.zwdbj.server.mobileapi.service.pet.service;

import com.zwdbj.server.mobileapi.service.user.model.UserModel;
import com.zwdbj.server.mobileapi.service.user.service.UserService;
import com.zwdbj.server.mobileapi.service.userInvitation.service.UserInvitationService;
import com.zwdbj.server.mobileapi.service.userAssets.model.UserCoinDetailAddInput;
import com.zwdbj.server.mobileapi.service.userAssets.service.UserAssetServiceImpl;
import com.zwdbj.server.utility.common.shiro.JWTUtil;
import com.zwdbj.server.probuf.middleware.mq.QueueWorkInfoModel;
import com.zwdbj.server.mobileapi.model.EntityKeyModel;
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
import org.springframework.stereotype.Service;

import java.util.List;
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

    public ServiceStatusInfo<Long> add(UpdatePetModelInput input,long userId) {
        String regEx = "^(?!_)(?!.*?_$)[a-zA-Z0-9_\\u4e00-\\u9fa5]{1,20}$";
        Pattern r = Pattern.compile(regEx);
        Matcher m1 = r.matcher(input.getNickName());
        boolean rs1 = m1.matches();
        if (rs1 == false ) return new ServiceStatusInfo<>(1, "你输入的宠物名称格式不对", null);
        String imageKey = input.getAvatar();
        if (!(imageKey == null || imageKey.equals(""))) {
            input.setAvatar(this.qiniuService.url(imageKey));
        }
        input.setId(UniqueIDCreater.generateID());
        long rows = this.petMapper.add(input,userId);
        if (rows ==0) {
            return  new ServiceStatusInfo<>(1,"添加失败",null);
        } else {
            List<PetModelDto> pets = this.list(userId);
            if (pets==null){
                this.userAssetServiceImpl.userIsExist(userId);
                UserCoinDetailAddInput userCoinDetailAddInput = new UserCoinDetailAddInput();
                userCoinDetailAddInput.setStatus("SUCCESS");
                userCoinDetailAddInput.setNum(10);
                userCoinDetailAddInput.setTitle("首次添加宠物信息获得小饼干"+10+"个");
                userCoinDetailAddInput.setType("OTHER");
                this.userAssetServiceImpl.addUserCoinDetail(userId,userCoinDetailAddInput);
                this.userAssetServiceImpl.updateUserCoinType(userId,"OTHER",10);
                this.userAssetServiceImpl.updateUserAsset(userId,10);
                // TODO 改变金币任务状态
            }
            this.reviewAvatar(imageKey,1,input.getId());
            firstAddPet(userId);
            return new ServiceStatusInfo<>(0,"添加成功",rows);
        }
    }

    public ServiceStatusInfo<Long> update(UpdatePetModelInput input) {
        String regEx = "^(?!_)(?!.*?_$)[a-zA-Z0-9_\\u4e00-\\u9fa5]{1,20}$";
        Pattern r = Pattern.compile(regEx);
        Matcher m1 = r.matcher(input.getNickName());
        boolean rs1 = m1.matches();
        if (rs1 == false ) return new ServiceStatusInfo<>(1, "你输入的宠物名称格式不对", null);
        String imageKey = input.getAvatar();
        if (!(imageKey == null || imageKey.equals(""))) {
            input.setAvatar(this.qiniuService.url(imageKey));
        }
        if (input.getId()<=0) {
            return new ServiceStatusInfo<>(1,"参数有误",null);
        }
        long rows = this.petMapper.update(input);
        if (rows ==0) {
            return  new ServiceStatusInfo<>(1,"保存失败",null);
        } else {
            this.reviewAvatar(imageKey,1,input.getId());
            return new ServiceStatusInfo<>(0,"保存成功",rows);
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
        if(userModel==null)return;
        userInvitationServiceImpl.createUserInvitation(userModel.getRecommendUserId());
    }

}
