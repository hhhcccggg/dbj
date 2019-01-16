package com.zwdbj.server.mobileapi.service.user.service;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.zwdbj.server.mobileapi.easemob.api.EaseMobUser;
import com.zwdbj.server.mobileapi.middleware.mq.MQWorkSender;
import com.zwdbj.server.mobileapi.service.userAssets.model.UserCoinDetailAddInput;
import com.zwdbj.server.mobileapi.service.userAssets.service.UserAssetServiceImpl;
import com.zwdbj.server.probuf.middleware.mq.QueueWorkInfoModel;
import com.zwdbj.server.tokencenter.IAuthUserManager;
import com.zwdbj.server.tokencenter.TokenCenterManager;
import com.zwdbj.server.tokencenter.model.UserToken;
import com.zwdbj.server.mobileapi.config.AppConfigConstant;
import com.zwdbj.server.utility.model.ServiceStatusInfo;
import com.zwdbj.server.mobileapi.service.messageCenter.model.MessageInput;
import com.zwdbj.server.mobileapi.service.messageCenter.service.MessageCenterService;
import com.zwdbj.server.mobileapi.service.pet.model.PetModelDto;
import com.zwdbj.server.mobileapi.service.pet.service.PetService;
import com.zwdbj.server.mobileapi.service.qiniu.service.QiniuService;
import com.zwdbj.server.mobileapi.service.review.service.ReviewService;
import com.zwdbj.server.mobileapi.service.user.model.*;
import com.zwdbj.server.mobileapi.service.userBind.model.UserThirdAccountBindDto;
import com.zwdbj.server.mobileapi.service.userBind.service.UserBindService;
import com.zwdbj.server.mobileapi.service.youzan.service.YouZanService;
import com.zwdbj.server.utility.common.shiro.JWTUtil;
import com.zwdbj.server.utility.common.SHAEncrypt;
import com.zwdbj.server.utility.common.UniqueIDCreater;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.zwdbj.server.mobileapi.service.user.mapper.IUserMapper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserService {

    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private IUserMapper userMapper;
    @Autowired
    private QiniuService qiniuService;
    @Autowired
    YouZanService youZanService;
    @Autowired
    UserBindService userBindService;
    @Autowired
    EaseMobUser easeMobUser;
    @Autowired
    private MessageCenterService messageCenterService;
    @Autowired
    private PetService petService;
    @Autowired
    private ReviewService reviewService;
    @Autowired
    private UserAssetServiceImpl userAssetServiceImpl;
    @Autowired
    private TokenCenterManager tokenCenterManager;
    @Autowired
    private IAuthUserManager iAuthUserManagerImpl;
    private Logger logger = LoggerFactory.getLogger(UserService.class);


    // 用户
    public void updateField(String fields, long id) {
        this.userMapper.updateField(fields, id);
        this.tokenCenterManager.refreshUserInfo(String.valueOf(id), iAuthUserManagerImpl);
    }


    public ServiceStatusInfo<Object> logout(long userId) {
        this.tokenCenterManager.logout(String.valueOf(userId));
        return new ServiceStatusInfo<>(0, "注销成功", null);
    }

    public List<AllHeartsForUserVideosMessageDto> getAllHeartsForMyVideos(long userId) {
        List<AllHeartsForUserVideosMessageDto> dtos = this.userMapper.getAllHeartsForMyVideos(userId);
        return dtos;
    }

    /**
     * followerUserId 是否关注了userId
     *
     * @param userId
     * @param followerUserId
     * @return
     */
    public boolean isFollower(long userId, long followerUserId) {
        int result = this.userMapper.checkFollowed(userId, followerUserId);
        return result > 0;
    }

    @Transactional
    public ServiceStatusInfo<Object> follow(FollowInput input) {
        long userId = JWTUtil.getCurrentId();
        if (userId == 0) return new ServiceStatusInfo<>(1, "请重新登录", null);
        if (input.getUserId() == userId) return new ServiceStatusInfo<>(1, "不能关注自己", null);
        boolean isFollowed = isFollower(input.getUserId(), userId);
        if (input.isFollow()) {
            if (!isFollowed) {
                long id = UniqueIDCreater.generateID();
                long result = this.userMapper.follow(id, input.getUserId(), userId);
                if (result > 0) {
                    if (input.getLivingId() != null && input.getLivingId() > 0) {
                        this.userMapper.addFanCount(input.getLivingId());
                    }
                    //记录内容，发布通知
                    MessageInput msgInput = new MessageInput();
                    msgInput.setCreatorUserId(userId);
                    msgInput.setMessageType(2);
                    this.messageCenterService.push(msgInput, input.getUserId());
                    return new ServiceStatusInfo<>(0, "关注成功", null);
                } else {
                    return new ServiceStatusInfo<>(1, "关注失败", null);
                }
            }
        } else {
            if (isFollowed) {
                long result = this.userMapper.unFollow(input.getUserId(), userId);
                if (result > 0) {
                    return new ServiceStatusInfo<>(0, "取消关注成功", null);
                } else {
                    return new ServiceStatusInfo<>(1, "取消关注失败", null);
                }
            }
        }
        return new ServiceStatusInfo<>(0, "操作成功", null);
    }

    /**
     * 用户的粉丝
     *
     * @param userId
     * @return
     */
    public List<FollowerUserInfoDto> myFollowers(long userId) {
        List<FollowerUserInfoDto> dtos = this.userMapper.myFollowers(userId);
        if (dtos == null) return null;
        //TODO 优化
        for (FollowerUserInfoDto dto : dtos) {
            List<PetModelDto> petModelDtos = this.petService.list(dto.getId());
            if (petModelDtos != null) {
                dto.setPets(petModelDtos);
            }
        }
        return dtos;
    }

    /**
     * 用的关注，关注了那些用户
     *
     * @param userId
     * @return
     */
    public List<FollowerUserInfoDto> myFollowed(long userId) {
        List<FollowerUserInfoDto> dtos = this.userMapper.myFollowed(userId);
        //TODO 优化
        for (FollowerUserInfoDto dto : dtos) {
            if (dto.getId() == userId) {
                dtos.remove(dto);
            }
            List<PetModelDto> petModelDtos = this.petService.list(dto.getId());
            if (petModelDtos != null) {
                dto.setPets(petModelDtos);
            }
        }
        return dtos;
    }

    public String getUserName(long id) {
        String name = this.userMapper.getUserNickName(id);
        return name;
    }

    public UserDetailInfoDto getUserDetailByToken() {
        long userId = JWTUtil.getCurrentId();
        if (userId == 0) return null;
        return this.getUserDetail(userId);
    }

    public String getPhone(long userId) {
        UserModel userModel = this.userMapper.findUserById(userId);
        if (userModel!=null) {
            return userModel.getPhone();
        }
        return null;
    }

    public UserDetailInfoDto getUserDetail(long userId) {
        //TODO 增加缓存数据
        UserDetailInfoDto userDetailInfoDto = this.userMapper.getUserDetail(userId);
        if (userDetailInfoDto == null) return null;
        userDetailInfoDto.getShopInfoDto().setLotteryTicketCount(this.youZanService.lotteryTicketCount(userId).getData());
        userDetailInfoDto.getShopInfoDto().setCartUrl("https://h5.youzan.com/wsctrade/cart?kdt_id="+AppConfigConstant.YOUZAN_BIND_SHOP_ID);
        userDetailInfoDto.getShopInfoDto().setLotteryUrl("https://h5.youzan.com/wscump/coupon/list?kdtId="+AppConfigConstant.YOUZAN_BIND_SHOP_ID);
        userDetailInfoDto.getShopInfoDto().setOrderUrl("https://h5.youzan.com/wsctrade/order/list?kdt_id="+AppConfigConstant.YOUZAN_BIND_SHOP_ID);
        //购物车
        ServiceStatusInfo<Integer> cartStatusInfo = this.youZanService.cartNum(userId);
        if (cartStatusInfo.isSuccess()) {
            userDetailInfoDto.getShopInfoDto().setCartNum(cartStatusInfo.getData());
        }
        long coins;
        if (this.stringRedisTemplate.hasKey("USERASSET_" + userId)) {
            coins = Long.valueOf(this.stringRedisTemplate.opsForValue().get("USERASSET_" + userId));
        } else {
            coins = this.userAssetServiceImpl.getCoinsByUserId(userId).getData();
        }
        userDetailInfoDto.setCoins(coins);
        //判断环信账号是否已经生成
        //TODO 优化,数据库写入可以放在消息队列处理
        long currentUserId = JWTUtil.getCurrentId();
        if (currentUserId > 0 && currentUserId == userId) {
            if (userDetailInfoDto.getHxUserName() == null || userDetailInfoDto.getHxUserName().length() == 0
                    || userDetailInfoDto.getHxPwd() == null || userDetailInfoDto.getHxPwd().length() == 0) {
                String userName = String.valueOf(userDetailInfoDto.getId());
                String password = UUID.randomUUID().toString();
                boolean createSuccessful = this.easeMobUser.register(userName, password);
                if (createSuccessful) {
                    userDetailInfoDto.setHxUserName(userName);
                    userDetailInfoDto.setHxPwd(password);
                    this.userMapper.updateField("hxUserName='" + userName + "',hxPwd='" + password + "'", userId);
                } else {
                    logger.warn("用户{" + userId + "}生成环信账号失败");
                }
            }
        }

        if (this.stringRedisTemplate.hasKey(AppConfigConstant.getRedisUserFanKey(userId)) && this.stringRedisTemplate.hasKey(AppConfigConstant.getRedisUserFocusesKey(userId))) {
            Long totalFans = Long.parseLong(this.stringRedisTemplate.opsForValue().get(AppConfigConstant.getRedisUserFanKey(userId)));
            Long totalMyFocuses = Long.parseLong(this.stringRedisTemplate.opsForValue().get(AppConfigConstant.getRedisUserFocusesKey(userId)));
            userDetailInfoDto.setTotalFans(totalFans);
            userDetailInfoDto.setTotalMyFocuses(totalMyFocuses);
            logger.info("我是缓存中的粉丝和关注总量----用户:" + userDetailInfoDto.getNickName() + ",totalFans:" + totalFans + ",totalMyFocuses:" + totalMyFocuses + "," + new SimpleDateFormat("HH:mm:ss").format(new Date()));
        } else {
            this.findMyFansCount(userId);
        }

        return userDetailInfoDto;
    }

    public void findMyFansCount(Long userId) {
        Long totalFans = this.userMapper.findMyFansCount(userId);
        Long totalMyFocuses = this.userMapper.findMyFocusesCount(userId);
        String field = "totalFans=" + totalFans + ",totalMyFocuses=" + totalMyFocuses;
        this.updateField(field, userId);
        // 存到缓存
        stringRedisTemplate.opsForValue().set(AppConfigConstant.getRedisUserFanKey(userId), String.valueOf(totalFans), 120, TimeUnit.SECONDS);
        stringRedisTemplate.opsForValue().set(AppConfigConstant.getRedisUserFocusesKey(userId), String.valueOf(totalMyFocuses), 120, TimeUnit.SECONDS);
        logger.info("我是用户" + userId + "详情中的粉丝和关注总量加入缓存" + new SimpleDateFormat("HH:mm:ss").format(new Date()));
    }

    public ServiceStatusInfo<Object> updateUserInfo(long userId, UpdateUserInfoInput input) {
        try {
            String imageKey = input.getAvatarKey();
            input.setAvatarKey(this.qiniuService.url(imageKey));
            long result = this.userMapper.updateInfo(userId, input);
            if (imageKey.startsWith("http://") || imageKey.length() == 0) {
                return new ServiceStatusInfo<>(0, "", null);
            }
            QueueWorkInfoModel.QueueWorkQiniuWaitReviewResData resData =
                    QueueWorkInfoModel.QueueWorkQiniuWaitReviewResData.newBuilder()
                            .setResContent(imageKey)
                            .setResType(0)
                            .setDataId(userId)
                            .setDataType(0)
                            .build();
            this.reviewService.reviewQiniuRes(resData);
            this.tokenCenterManager.refreshUserInfo(String.valueOf(userId), iAuthUserManagerImpl);
            return new ServiceStatusInfo<>(0, "", null);
        } catch (Exception e) {
            return new ServiceStatusInfo<>(500, e.getMessage(), null);
        }

    }

    public ServiceStatusInfo<Object> userNameIsExist(String userName) {
        try {
            String regEx = "^[a-zA-Z][a-zA-Z0-9_]{2,11}$";
            Pattern r = Pattern.compile(regEx);
            Matcher m = r.matcher(userName);
            boolean rs = m.matches();
            if (rs == false) return new ServiceStatusInfo<>(1, "用户Id格式不正确", null);
            long userId = JWTUtil.getCurrentId();
            int result = this.userMapper.userNameIsExist(userName);
            UserModel userModel = this.userMapper.findUserByUserName(userName);
            if (result > 0 && userId != userModel.getId()) {
                return new ServiceStatusInfo<>(1, "此用户id已经存在", null);
            } else {
                return new ServiceStatusInfo<>(0, "", null);
            }
        } catch (Exception e) {
            return new ServiceStatusInfo<>(500, e.getMessage(), null);
        }
    }

    public UserFollowInfoDto followStatusSearch(UserFollowInfoSearchInput input) {
        UserFollowInfoDto dto = this.userMapper.followStatusSearch(input);
        return dto;
    }

    /**
     * 通过手机号查找用户
     *
     * @param 手机号
     * @return 返回用户
     */
    public UserModel findUserByPhone(String phone) {
        UserModel userModel = userMapper.findUserByPhone(phone);
        return userModel;
    }

    public UserModel findUserByUserName(String username) {
        return this.userMapper.findUserByUserName(username);
    }

    public UserModel findUserById(long userId) {
        return userMapper.findUserById(userId);
    }

    public ServiceStatusInfo<UserLoginInfoDto> loginByPwd(String phone, String password) {
        boolean isLogined = false;
        UserModel userModel = null;
        try {
            String regEx = "^0?(13|14|15|18|17|19)[0-9]{9}$";
            Pattern r = Pattern.compile(regEx);
            Matcher m1 = r.matcher(phone);
            boolean rs1 = m1.matches();
            if (rs1 == false ) return new ServiceStatusInfo<>(1, "密码为8到12位字母、数字或“_”的组合", null);
            String encodePassword = SHAEncrypt.encryptSHA(password);
            userModel = this.userMapper.findUserByPwd(phone, encodePassword);
            isLogined = userModel != null;
        } catch (Exception ex) {
            isLogined = false;
        }
        if (isLogined && userModel != null) {
            UserLoginInfoDto infoDto = new UserLoginInfoDto();
            UserToken userToken = this.tokenCenterManager.
                    fetchToken(String.valueOf(userModel.getId()),this.iAuthUserManagerImpl)
                    .getData();
            infoDto.setUserToken(userToken);
            infoDto.setEmail(userModel.getEmail());
            infoDto.setAvatarUrl(userModel.getAvatarUrl());
            infoDto.setId(userModel.getId());
            infoDto.setPhone(userModel.getPhone());
            infoDto.setUsername(userModel.getUsername());
            return new ServiceStatusInfo<>(0, "登录成功", infoDto);
        } else {
            return new ServiceStatusInfo<>(1, "用户名或密码错误!", null);
        }
    }

    public long addHeart(long id, int num) {
        return this.userMapper.addHeart(id, num);
    }

    @Transactional
    public UserModel regUserByOpenId(BindThirdPartyAccountInput input) {
        String userName = UniqueIDCreater.generateUserName();
        UserThirdAccountBindDto userThirdAccountBindDto = this.userBindService.findUserByOpenId(input.getOpenUserId(), input.getThirdType());
        //UserModel userModel = this.userMapper.findUserByOpenId(input.getOpenUserId(),input.getThirdType());
        if (userThirdAccountBindDto == null) {
            //注册
            long id = UniqueIDCreater.generateID();
            this.userMapper.regByOpenId(id, userName, input);
            this.userBindService.add(input, id);
            //首次注册添加金币
            this.userAssetServiceImpl.userIsExist(id);
            UserCoinDetailAddInput userCoinDetailAddInput = new UserCoinDetailAddInput();
            userCoinDetailAddInput.setStatus("SUCCESS");
            userCoinDetailAddInput.setNum(10);
            userCoinDetailAddInput.setTitle("首次完成注册信息获得小饼干"+10+"个");
            userCoinDetailAddInput.setType("TASK");
            this.userAssetServiceImpl.userPlayCoinTask(userCoinDetailAddInput,id,"TASK",10);
            return new UserModel(id, userName, input.getAvaterUrl(), "", "");
        } else {
            //TODO 刷新用户头像
            //刷新绑定信息
            this.userBindService.update2(input, userThirdAccountBindDto.getUserId(), userThirdAccountBindDto.getAccountType());
            String field = "loginType=" + input.getThirdType() + ",thirdOpenId='" + input.getOpenUserId() + "'";
            this.userMapper.updateField(field, userThirdAccountBindDto.getUserId());
            UserModel userModel = this.userMapper.findUserById(userThirdAccountBindDto.getUserId());
            return userModel;
        }
    }


    /**
     * 用户绑定第三方账号
     *
     * @param input
     * @param userId
     * @return
     */
    public ServiceStatusInfo<Long> UserBindThird(BindThirdPartyAccountInput input, long userId) {
        int count = this.userBindService.thirdIsExist(input.getOpenUserId(), input.getThirdType());
        ServiceStatusInfo<Long> serviceStatusInfo = null;
        if (count == 0) {
            if (this.userBindService.get(userId, input.getThirdType()) == null) {
                serviceStatusInfo = this.userBindService.add(input, userId);
            } else {
                serviceStatusInfo = this.userBindService.update2(input, userId, input.getThirdType());
            }
            return serviceStatusInfo;
        } else {
            return new ServiceStatusInfo<>(1, "此账号已经被其他账号绑定", null);
        }


    }

    /**
     * 移除用户某个三方绑定
     *
     * @param userId
     * @param type
     * @return
     */
    public ServiceStatusInfo<Long> RemoveUserBindThird(long userId, int type) {
        ServiceStatusInfo<Long> serviceStatusInfo = this.userBindService.delete(userId, type);
        this.userMapper.updateThirdInfo(userId);
        return serviceStatusInfo;
    }

    /**
     * 通过手机号注册用户
     *
     * @param phone
     * @return
     */
    @Transactional
    public UserModel regUserByPhone(String phone) {
        long userId = UniqueIDCreater.generateID();
        try {
            String userName = UniqueIDCreater.generateUserName();
            userMapper.regByPhone(phone, userId, userName);
            UserModel userModel = new UserModel(userId, userName, null, null, phone);
            this.userAssetServiceImpl.userIsExist(userId);
            UserCoinDetailAddInput userCoinDetailAddInput = new UserCoinDetailAddInput();
            userCoinDetailAddInput.setStatus("SUCCESS");
            userCoinDetailAddInput.setNum(10);
            userCoinDetailAddInput.setTitle("首次完成注册信息获得小饼干"+10+"个");
            userCoinDetailAddInput.setType("TASK");
            this.userAssetServiceImpl.userPlayCoinTask(userCoinDetailAddInput,userId,"TASK",10);
            return userModel;
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     * 手机号验证码登录
     *
     * @param phone 手机号
     * @param code  手机验证码
     * @return
     */
    public ServiceStatusInfo<UserModel> loginByPhone(String phone, String code) {

        ServiceStatusInfo<Object> statusInfo = checkPhoneCode(phone, code);
        if (!statusInfo.isSuccess()) {
            return new ServiceStatusInfo<>(1, statusInfo.getMsg(), null);
        }
        UserModel userModel = findUserByPhone(phone);
        boolean isNeedReg = userModel == null;
        if (isNeedReg) {
            UserModel regUserModel = regUserByPhone(phone);
            if (regUserModel == null) return new ServiceStatusInfo<>(1, "登录失败", null);
            userModel = regUserModel;
        }
        return new ServiceStatusInfo<UserModel>(0, "", userModel);
    }

    /**
     * 绑定手机号码
     *
     * @param userId
     * @param phone
     * @param code
     * @return
     */
    @CacheEvict(value = "userauthinfo", key = "#userId", allEntries = true)
    public ServiceStatusInfo<Object> bindPhone(long userId, String phone, String code) {
        ServiceStatusInfo<Object> statusInfo = checkPhoneCode(phone, code);
        if (!statusInfo.isSuccess()) {
            return new ServiceStatusInfo<>(1, statusInfo.getMsg(), null);
        }
        //判断手机号是否已经被别人绑定
        UserModel userModel = this.findUserByPhone(phone);
        if (userModel != null) {
            return new ServiceStatusInfo<>(1, "手机号已经被其他账号绑定", null);
        }
        this.userMapper.updateField("IsPhoneVerification=true,phone='" + phone + "'", userId);
        this.tokenCenterManager.refreshUserInfo(String.valueOf(userId), iAuthUserManagerImpl);
        //初始化金币账户
        this.userAssetServiceImpl.userIsExist(userId);
        return new ServiceStatusInfo<>(0, "绑定成功", null);
    }

    /**
     * 校验手机和验证码合法性
     *
     * @param phone
     * @param code
     * @return
     */
    public ServiceStatusInfo<Object> checkPhoneCode(String phone, String code) {
        try {
            int result = this.phoneIsTrue(phone);
            if (result == 1) {
                if (code.equals((phone.substring(7) + "18"))) {
                    return new ServiceStatusInfo<>(0, "验证成功", null);
                } else {
                    return new ServiceStatusInfo<>(1, "请输入正确的验证码", null);
                }

            } else {
                //TODO 审核以后删除
                if (phone.equals("18161279360") && code.equals("1234")) return new ServiceStatusInfo<>(0, "验证成功", null);
                // 验证手机验证码是否正确
                String cacheKey = AppConfigConstant.getRedisPhoneCodeKey(phone);
                boolean hasPhoneCode = stringRedisTemplate.hasKey(cacheKey);
                if (!hasPhoneCode) {
                    return new ServiceStatusInfo<>(1, "请输入正确的手机号和验证码", null);
                }
                String cachePhoneCode = this.stringRedisTemplate.opsForValue().get(cacheKey);
                if (!code.equals(cachePhoneCode)) {
                    return new ServiceStatusInfo<>(1, "请输入正确的验证码", null);
                }
                //移除验证码
                stringRedisTemplate.delete(cacheKey);
                return new ServiceStatusInfo<>(0, "验证成功", null);
            }
        }catch (Exception e){
            return new ServiceStatusInfo<>(1, "请输入正确的手机号和验证码"+e.getMessage(), null);
        }


    }

    /**
     * 判断phone是否为人造
     */
    public int phoneIsTrue(String phone) {
        int result = this.userMapper.phoneIsTrue(phone);
        return result;
    }

    /**
     * @param phone
     * @return 发送给手机验证码并返回验证码
     */
    public ServiceStatusInfo<String> sendPhoneCode(String phone) {
        //TODO 是否要判断手机是否在我们的系统中
        //判断是否可以发送验证码
        ValueOperations<String, SmsSendCfg> operations = redisTemplate.opsForValue();
        SmsSendCfg cfg = null;
        long currentTimeStamp = System.currentTimeMillis() / 1000;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String todayStr = simpleDateFormat.format(new Date().getTime());

        if (redisTemplate.hasKey(AppConfigConstant.getRedisPhoneCodeCfgKey(phone))) {
            try {
                cfg = operations.get(AppConfigConstant.getRedisPhoneCodeCfgKey(phone));
                if (currentTimeStamp - cfg.getLastSendSmsTimeStamp() < AppConfigConstant.APP_SMS_SEND_INTERVAL) {
                    return new ServiceStatusInfo<String>(1, "发送验证码太频繁，请稍后再试.", "");
                }
                String[] cfgDayArr = cfg.getDaySendCount().split(":");
                int currentSendCount = Integer.parseInt(cfgDayArr[1]);
                if (todayStr.equals(cfgDayArr[0])) {
                    if (currentSendCount > AppConfigConstant.APP_SMS_SEND_MAX_COUNT_DAY) {
                        return new ServiceStatusInfo<String>(1, "超过当日最大验证码发送次数", "");
                    }
                    cfg.setLastSendSmsTimeStamp(currentTimeStamp);
                    cfg.setDaySendCount(todayStr + ":" + (currentSendCount + 1));
                } else {
                    // 不是当天
                    cfg.setLastSendSmsTimeStamp(currentTimeStamp);
                    cfg.setDaySendCount(todayStr + ":" + 1);
                }
            } catch (Exception ex) {
                logger.warn(ex.getMessage());
                logger.warn(ex.getStackTrace().toString());
                //重新处理缓存
                cfg = new SmsSendCfg(currentTimeStamp, todayStr + ":1");
                operations.set(AppConfigConstant.getRedisPhoneCodeCfgKey(phone), cfg);
                return new ServiceStatusInfo<String>(1, "发送验证码失败，请稍后再试.", "");
            }
        }


        //生成验证码
        String code = UniqueIDCreater.generatePhoneCode();
        int result = this.phoneIsTrue(phone);
        if (result == 1) {
            code = phone.substring(7) + "18";
        }
        if (phone.equals("18161279360")) {
            code = "1234";
        }
        if (AppConfigConstant.APP_SMS_SEND_OPEN) {
            try {
                // 发送验证码加入消息队列
                QueueWorkInfoModel.QueueWorkPhoneCode phoneCode = QueueWorkInfoModel.QueueWorkPhoneCode.newBuilder()
                        .setPhone(phone)
                        .setCode(code)
                        .build();
                QueueWorkInfoModel.QueueWorkInfo queueWorkInfo = QueueWorkInfoModel.QueueWorkInfo.newBuilder()
                        .setWorkType(QueueWorkInfoModel.QueueWorkInfo.WorkTypeEnum.SEND_PHONE_CODE)
                        .setPhoneCode(phoneCode)
                        .build();
                MQWorkSender.shareSender().send(queueWorkInfo);
            } catch (Exception ex) {
                logger.error(ex.getMessage());
                return new ServiceStatusInfo<>(1, "发送验证码失败，请稍后重试.", "");
            }
        }
        //刷新缓存
        if (cfg == null) {
            cfg = new SmsSendCfg(currentTimeStamp, todayStr + ":1");
        }
        operations.set(AppConfigConstant.getRedisPhoneCodeCfgKey(phone), cfg);
        // 存到缓存
        stringRedisTemplate.opsForValue().set(AppConfigConstant.getRedisPhoneCodeKey(phone), code, AppConfigConstant.APP_SMS_CODE_EXPIRE_TIME, TimeUnit.SECONDS);
        if (AppConfigConstant.APP_SMS_SEND_OPEN) {
            return new ServiceStatusInfo<>(0, "发送成功", "");
        } else {
            return new ServiceStatusInfo<>(0, "", code);
        }
    }

    public boolean sendSms(String phone, String code) {
        //超时时间
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");

        //初始化acsClient,暂不支持region化
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", AppConfigConstant.ALIYUN_ACCESS_KEY, AppConfigConstant.ALIYUN_ACCESS_SECRECT);
        try {
            DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", "Dysmsapi", "dysmsapi.aliyuncs.com");
        } catch (Exception e) {
            return false;
        }
        IAcsClient acsClient = new DefaultAcsClient(profile);

        //组装请求对象-具体描述见控制台-文档部分内容
        SendSmsRequest request = new SendSmsRequest();
        //必填:待发送手机号
        request.setPhoneNumbers(phone);
        //必填:短信签名-可在短信控制台中找到
        request.setSignName("爪子APP");
        //必填:短信模板-可在短信控制台中找到
        request.setTemplateCode("SMS_140020345");
        request.setTemplateParam("{\"code\":\"" + code + "\"}");
        try {
            SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
            if (sendSmsResponse.getCode().equals("OK")) {
                return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    public ServiceStatusInfo<Integer> phoneIsRegOrNot(PhoneCodeInput input){
        try {
            ServiceStatusInfo<Object> statusInfo = this.checkPhoneCode(input.getPhone(),input.getCode());
            if (statusInfo.isSuccess()){
                int result = this.userMapper.phoneIsRegOrNot(input.getPhone());
                if (result!=0){
                    result = this.userMapper.phoneIsHavePWD(input.getPhone());
                    if (result==0){
                        return  new ServiceStatusInfo<>(0,"",202);
                    }else {
                        return  new ServiceStatusInfo<>(0,"",201);
                    }
                }else {
                    return  new ServiceStatusInfo<>(0,"",100);
                }
            }else {
                new ServiceStatusInfo<>(1, "验证失败，请输入正确的手机号和验证码", null);
            }

        }catch (Exception e){
            return  new ServiceStatusInfo<>(1,"出现异常："+e.getMessage(),null);
        }
        return new ServiceStatusInfo<>(1, "验证失败，请输入正确的手机号和验证码", null);
    }

    public ServiceStatusInfo<Integer> regUser(RegUserInput input){
        try {
            String regEx = "^(?![a-zA-z]+$)(?!\\d+$)(?![_]+$)[a-zA-Z\\d_]{8,12}$";
            Pattern r = Pattern.compile(regEx);
            Matcher m1 = r.matcher(input.getPassword());
            Matcher m2 = r.matcher(input.getPasswordTwo());
            boolean rs1 = m1.matches();
            boolean rs2 = m2.matches();
            if (rs1 == false || rs2==false) return new ServiceStatusInfo<>(1, "密码为8到12位字母、数字或“_”的组合", null);
            int result=0;
            String password = SHAEncrypt.encryptSHA(input.getPassword());
            if (input.getPassword().equals(input.getPasswordTwo())){
                if (input.getType()==100){
                    long id = UniqueIDCreater.generateID();
                    String userName = UniqueIDCreater.generateUserName();
                    result = this.userMapper.regUser(id,userName,input.getPhone(),password,input.getRecommendUserId());
                    if (result==0)return new ServiceStatusInfo<>(1,"注册失败",0);
                    //首次注册添加金币
                    this.userAssetServiceImpl.userIsExist(id);
                    UserCoinDetailAddInput userCoinDetailAddInput = new UserCoinDetailAddInput();
                    userCoinDetailAddInput.setStatus("SUCCESS");
                    userCoinDetailAddInput.setNum(10);
                    userCoinDetailAddInput.setTitle("首次完成注册信息获得小饼干"+10+"个");
                    userCoinDetailAddInput.setType("TASK");
                    this.userAssetServiceImpl.userPlayCoinTask(userCoinDetailAddInput,id,"TASK",10);
                    return new ServiceStatusInfo<>(0,"注册成功",result);
                }else if (input.getType()==201){
                    UserModel userModel = this.findUserByPhone(input.getPhone());
                    if (userModel==null)return new ServiceStatusInfo<>(1,"此手机号还没有注册，请注册账号",null);
                    result = this.userMapper.updatePasswordByUserId(password,userModel.getId());
                    if (result==0)return new ServiceStatusInfo<>(1,"完善密码失败",0);
                    return new ServiceStatusInfo<>(0,"完善密码成功",result);
                }else if (input.getType()==202){
                    return  new ServiceStatusInfo<>(1,"该手机号码已经注册",null);
                }else {
                    return  new ServiceStatusInfo<>(1,"请重新填写验证码",null);
                }
            }else {
                return  new ServiceStatusInfo<>(1,"两次输入的密码不一致，请确认",null);
            }
        }catch (Exception e){
            return  new ServiceStatusInfo<>(1,"出现异常："+e.getMessage(),null);
        }

    }

    public  ServiceStatusInfo<Integer> getMyNewPWD(NewMyPasswordInput input){

        try {
            String regEx = "^(?![a-zA-z]+$)(?!\\d+$)(?![_]+$)[a-zA-Z\\d_]{8,12}$";
            Pattern r = Pattern.compile(regEx);
            Matcher m1 = r.matcher(input.getPassword());
            Matcher m2 = r.matcher(input.getPasswordTwo());
            boolean rs1 = m1.matches();
            boolean rs2 = m2.matches();
            if (rs1 == false || rs2==false) return new ServiceStatusInfo<>(1, "密码为8到12位字母、数字或“_”的组合", null);
            int result=0;
            String password = SHAEncrypt.encryptSHA(input.getPassword());
            if (input.getPassword().equals(input.getPasswordTwo())){
                UserModel userModel = this.findUserByPhone(input.getPhone());
                if (userModel==null)return new ServiceStatusInfo<>(1,"此手机号还没有注册，请注册账号",null);
                result = this.userMapper.updatePasswordByUserId(password,userModel.getId());
                if (result==0)return new ServiceStatusInfo<>(1,"找回密码失败",0);
                return new ServiceStatusInfo<>(0,"找回密码成功",result);
            }else {
                return  new ServiceStatusInfo<>(1,"两次输入的密码不一致，请确认",null);
            }
        }catch (Exception e){
            return  new ServiceStatusInfo<>(1,"出现异常："+e.getMessage(),null);
        }
    }


}


