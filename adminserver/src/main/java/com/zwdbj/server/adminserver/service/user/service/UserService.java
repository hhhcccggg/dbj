package com.zwdbj.server.adminserver.service.user.service;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.zwdbj.server.adminserver.easemob.api.EaseMobUser;
import com.zwdbj.server.adminserver.middleware.mq.MQWorkSender;
import com.zwdbj.server.adminserver.middleware.mq.QueueWorkInfoModel;
import com.zwdbj.server.adminserver.model.EntityKeyModel;
import com.zwdbj.server.adminserver.model.ResourceOpenInput;
import com.zwdbj.server.adminserver.model.user.UserToken;
import com.zwdbj.server.adminserver.config.AppConfigConstant;
import com.zwdbj.server.adminserver.service.ServiceStatusInfo;
import com.zwdbj.server.adminserver.service.homepage.model.AdFindIncreasedDto;
import com.zwdbj.server.adminserver.service.homepage.model.AdFindIncreasedInput;
import com.zwdbj.server.adminserver.service.messageCenter.model.MessageInput;
import com.zwdbj.server.adminserver.service.messageCenter.service.MessageCenterService;
import com.zwdbj.server.adminserver.service.qiniu.service.QiniuService;
import com.zwdbj.server.adminserver.service.user.model.*;
import com.zwdbj.server.adminserver.service.userBind.service.UserBindService;
import com.zwdbj.server.adminserver.service.youzan.service.YouZanService;
import com.zwdbj.server.adminserver.shiro.JWTUtil;
import com.zwdbj.server.adminserver.utility.SHAEncrypt;
import com.zwdbj.server.adminserver.utility.UniqueIDCreater;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.zwdbj.server.adminserver.service.user.mapper.IUserMapper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

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

    private Logger logger = LoggerFactory.getLogger(UserService.class);


    @Transactional
    @CacheEvict(value = "userauthinfo",key = "#userId",allEntries = true)
    public ServiceStatusInfo<Object> setRolesForUser(long userId, List<EntityKeyModel<String>> roles) {
        //TODO 检查角色是否在系统中
        if (roles.size()<=0) return new ServiceStatusInfo<>(1,"请至少指定一个角色",null);
        this.userMapper.deleteRole(userId);
        for(EntityKeyModel<String> name:roles) {
            this.setRole(userId,name.getId());
        }
        return new ServiceStatusInfo<>(0,"",null);
    }
    // 角色
    public List<String> roles(long userId) {
        List<String> roles = this.userMapper.roles(userId);
        return roles;
    }

    public List<String> permissions(long userId) {
        List<String> permissions = this.userMapper.permissions(userId);
        return permissions;
    }

    public List<String> permissions(String roleName) {
        return this.userMapper.permissionsByRole(roleName);
    }
    @CacheEvict(value = "userauthinfo",key = "#userId",allEntries = true)
    public long setRole(long userId,String roleName) {
        return this.userMapper.setRole(userId,roleName,UniqueIDCreater.generateID());
    }
    @CacheEvict(value = "userauthinfo",allEntries = true)
    public long setPermission(String roleName,String permissionName) {
        return this.userMapper.setPermission(roleName,permissionName,UniqueIDCreater.generateID());
    }

    public long addRole(String name,String des) {
        return this.userMapper.addRole(name,des);
    }

    public long addPermission(String name,String desc) {
        return this.userMapper.addPermission(name, desc);
    }




    // 用户

    @CacheEvict(value = "userauthinfo",key = "#id",allEntries = true)
    public void updateField(String fields,long id) {
        this.userMapper.updateField(fields,id);
    }

    public List<UserDetailInfoDto> search(UserSearchForAdInput input) {
        List<UserDetailInfoDto> userModelList = this.userMapper.findUsersAd(input);
        return userModelList;
    }

    public  List<UserDetailInfoDto> marketListAd(AdMarketUserInput input){
        List<UserDetailInfoDto> userInfoDtoList = this.userMapper.marketListAd(input);
        return userInfoDtoList;
    }

    @Transactional
    public ServiceStatusInfo<Long> newMarketAd(AdNewMarketInput input){
        //TODO 检查username和phone是否在系统存在
        Long id = UniqueIDCreater.generateID();
        Long userId  = UniqueIDCreater.generateID();
        Long result = 0L;
        try {
            result  = this.userMapper.newMarketAd(userId,input);
            Long roleResult = this.userMapper.insertUserRole(id,userId);
            return new ServiceStatusInfo<>(0,"",result);
        }catch (Exception e){
            return new ServiceStatusInfo<>(1,"创建失败"+e.getMessage(),result);
        }
    }
    @CacheEvict(value = "userauthinfo",key = "#input.id",allEntries = true)
    public ServiceStatusInfo<Object> lock(ResourceOpenInput<Long> input){
        long result = this.userMapper.lock(input);
        return new ServiceStatusInfo<>(0,"",null);
    }
    @CacheEvict(value = "userauthinfo",key = "#input.id",allEntries = true)
    public ServiceStatusInfo<Object> review(ResourceOpenInput<Long> input) {
        long result = this.userMapper.review(input);
        return new ServiceStatusInfo<>(0,"",result);
    }

    @Transactional
    public ServiceStatusInfo<Long> createUser(CreateUserInput input) {
        //TODO 检查角色是否在系统存在
        //TODO 先判断用户&电话是否存在
        long id = UniqueIDCreater.generateID();
        try {
            String password = SHAEncrypt.encryptSHA("123456");
            long result = this.userMapper.create(input, id, password);
            for (String roleName : input.getRoleName()) {
                this.userMapper.setRole(id, roleName, UniqueIDCreater.generateID());
            }
            return new ServiceStatusInfo<>(0, "", id);
        }
        catch (Exception ex) {
            return new ServiceStatusInfo<>(1, "创建失败："+ex.getMessage(), id);
        }
    }

    public ServiceStatusInfo<Object> logout(long userId) {
        // TODO 实现账号退出逻辑
        return new ServiceStatusInfo<>(0,"注销成功",null);
    }

    public List<AllHeartsForUserVideosMessageDto> getAllHeartsForMyVideos(long userId) {
        List<AllHeartsForUserVideosMessageDto> dtos = this.userMapper.getAllHeartsForMyVideos(userId);
        return dtos;
    }

    /**
     * 查询被举报的用户
     */
    public List<UserDetailInfoDto> searchComplainUserListAd(AdUserComplainInput input){
        List<UserDetailInfoDto> infoDtos = this.userMapper.searchComplainUserListAd(input);
        return  infoDtos;
    }

    /**
     * 查询举报的详细信息
     */
    public  List<AdUserComplainInfoDto> userComplainInfoListAd(Long id){
        List<AdUserComplainInfoDto> infoDtos = this.userMapper.userComplainInfoListAd(id);
        return infoDtos;
    }
    /**
     * 处理举报用户
     */
    @CacheEvict(value = "userauthinfo",key = "#id",allEntries = true)
    public ServiceStatusInfo<Long> doUComplainInfoAd(Long id,AdDoUserComplainInput input){
        Long result = 0L;
        try {
            result = this.userMapper.doUComplainInfoAd(id,input);
            return new ServiceStatusInfo<>(0, "", result);
        }catch (Exception e){
            return new ServiceStatusInfo<>(1, "创建失败："+e.getMessage(), result);
        }
    }

    /**
     * 账号管理-账号列表
     */
    public List<AdUserDetailInfoDto> manageUserListAd(AdManageUserInput input){
        List<AdUserDetailInfoDto> infoDtos = this.userMapper.manageUserListAd(input);
        return infoDtos;
    }

    /**
     * 账号管理-账号新增
     */
    @Transactional
    public ServiceStatusInfo<Long> addManageUserAd(AdNewManageUserInput input){
        //TODO 检查username和phone是否在系统存在
        Long isExist = this.userMapper.phoneIsExist(input.getPhone());
        if (isExist==1){
            return new ServiceStatusInfo<>(1,"创建失败:手机号已注册",isExist);
        }
        Long isExistName = this.userMapper.userNameIsExist(input.getUserName());
        if (isExistName==1){
            return new ServiceStatusInfo<>(1,"创建失败:用户名已存在",isExistName);
        }
        Long id = UniqueIDCreater.generateID();
        Long userId  = UniqueIDCreater.generateID();

        Long result = 0L;
        try {
            String password =  SHAEncrypt.encryptSHA("123456");
            result  = this.userMapper.addManageUserAd(userId,input,password);
            Long roleResult = this.userMapper.insertManageUserRole(id,userId,input.getRoleName());
            return new ServiceStatusInfo<>(0,"",result);
        }catch (Exception e){
            return new ServiceStatusInfo<>(1,"创建失败"+e.getMessage(),result);
        }
    }

    /**
     * 修改用户密码
     * @param id
     * @param input
     * @return
     */
    public ServiceStatusInfo<Long> modifyPwdAd(Long id,AdModifyManagerPasswordInput input){

        Long result = 0L;
        try {
            input.setOldPassword(SHAEncrypt.encryptSHA(input.getOldPassword()));
            input.setmNewPassword( SHAEncrypt.encryptSHA(input.getmNewPassword()));
            result  = this.userMapper.modifyPwdAd(id,input);
            return new ServiceStatusInfo<>(0,"",result);
        }catch (Exception e){
            return new ServiceStatusInfo<>(1,"修改密码失败"+e.getMessage(),result);
        }
    }

    /**
     * followerUserId 是否关注了userId
     * @param userId
     * @param followerUserId
     * @return
     */
    public boolean isFollower(long userId,long followerUserId) {
        int result = this.userMapper.checkFollowed(userId, followerUserId);
        return result>0;
    }

    @Transactional
    public ServiceStatusInfo<Object> follow(FollowInput input) {
        long userId = JWTUtil.getCurrentId();
        if (userId == 0) return new ServiceStatusInfo<>(1,"请重新登录",null);
        boolean isFollowed = isFollower(input.getUserId(),userId);
        if (input.isFollow()) {
            if (!isFollowed) {
                long id = UniqueIDCreater.generateID();
                long result = this.userMapper.follow(id,input.getUserId(),userId);
                if (result>0 ) {
                    if (input.getLivingId()!=null&&input.getLivingId()>0) {
                        this.userMapper.addFanCount(input.getLivingId());
                    }
                    //记录内容，发布通知
                    MessageInput msgInput = new MessageInput();
                    msgInput.setCreatorUserId(userId);
                    msgInput.setMessageType(2);
                    this.messageCenterService.push(msgInput,input.getUserId());
                    return new ServiceStatusInfo<>(0,"关注成功",null);
                } else {
                    return new ServiceStatusInfo<>(1,"关注失败",null);
                }
            }
        } else {
            if (isFollowed) {
                long result = this.userMapper.unFollow(input.getUserId(),userId);
                if (result>0) {
                    return new ServiceStatusInfo<>(0,"取消关注成功",null);
                } else {
                    return new ServiceStatusInfo<>(1,"取消关注失败",null);
                }
            }
        }
        return new ServiceStatusInfo<>(0,"操作成功",null);
    }

    /**
     * 用户的粉丝
     * @param userId
     * @return
     */
    public List<FollowerUserInfoDto> myFollowers(long userId) {
        List<FollowerUserInfoDto> dtos = this.userMapper.myFollowers(userId);
        return dtos;
    }

    /**
     * 用的关注，关注了那些用户
     * @param userId
     * @return
     */
    public List<FollowerUserInfoDto> myFollowed(long userId) {
        List<FollowerUserInfoDto> dtos = this.userMapper.myFollowed(userId);
        return dtos;
    }

    public UserDetailInfoDto getUserDetail(long userId) {
        //TODO 增加缓存数据
        UserDetailInfoDto userDetailInfoDto = this.userMapper.getUserDetail(userId);
        if (userDetailInfoDto==null) return null;
        userDetailInfoDto.getShopInfoDto().setLotteryTicketCount(this.youZanService.lotteryTicketCount(userId).getData());
        userDetailInfoDto.getShopInfoDto().setCartUrl("https://h5.youzan.com/v2/trade/cart?kdt_id="+AppConfigConstant.YOUZAN_BIND_SHOP_ID);
        userDetailInfoDto.getShopInfoDto().setLotteryUrl("https://h5.youzan.com/v2/coupons");
        userDetailInfoDto.getShopInfoDto().setOrderUrl("https://h5.youzan.com/v2/orders/all");
        //购物车
        ServiceStatusInfo<Integer> cartStatusInfo = this.youZanService.cartNum(userId);
        if (cartStatusInfo.isSuccess()) {
            userDetailInfoDto.getShopInfoDto().setCartNum(cartStatusInfo.getData());
        }
        //判断环信账号是否已经生成
        //TODO 优化,数据库写入可以放在消息队列处理
        long currentUserId = JWTUtil.getCurrentId();
        if (currentUserId>0 && currentUserId == userId) {
            if (userDetailInfoDto.getHxUserName()==null || userDetailInfoDto.getHxUserName().length()==0
                    || userDetailInfoDto.getHxPwd()==null || userDetailInfoDto.getHxPwd().length()==0) {
                String userName = String.valueOf(userDetailInfoDto.getId());
                String password = UUID.randomUUID().toString();
                boolean createSuccessful = this.easeMobUser.register(userName,password);
                if (createSuccessful) {
                    userDetailInfoDto.setHxUserName(userName);
                    userDetailInfoDto.setHxPwd(password);
                    this.userMapper.updateField("hxUserName='"+userName+"',hxPwd='"+password+"'",userId);
                }
            }
        }
        return userDetailInfoDto;
    }
    @CacheEvict(value = "userauthinfo",key = "#userId",allEntries = true)
    public ServiceStatusInfo<Object> updateUserInfo(long userId, UpdateUserInfoInput input) {
        input.setAvatarKey(this.qiniuService.url(input.getAvatarKey()));
        long result = this.userMapper.updateInfo(userId,input);
        return new ServiceStatusInfo<>(0,"",null);
    }

    public UserFollowInfoDto followStatusSearch(UserFollowInfoSearchInput input) {
        UserFollowInfoDto dto = this.userMapper.followStatusSearch(input);
        return dto;
    }

    /**
     * 通过手机号查找用户
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

    protected UserAuthInfoModel getUserAuthInfo(long userId) {
        UserAuthInfoModel userAuthInfo = null;
        UserModel userModel = this.findUserById(userId);
        if (userModel == null) {
            return null;
        }
        logger.info("从数据库加载{"+userId+"}数据");
        userAuthInfo = new UserAuthInfoModel();
        userAuthInfo.setRoles(this.roles(userId));
        userAuthInfo.setPermissions(this.permissions(userId));
        userAuthInfo.setId(userModel.getId());
        userAuthInfo.setUsername(userModel.getUsername());
        userAuthInfo.setAvatarUrl(userModel.getAvatarUrl());
        userAuthInfo.setEmail(userModel.getEmail());
        userAuthInfo.setPhone(userModel.getPhone());
        userAuthInfo.setNickName(userModel.getNickName());
        userAuthInfo.setSex(userModel.getSex());
        userAuthInfo.setReviewed(userModel.isReviewed());
        userAuthInfo.setLivingOpen(userModel.isLivingOpen());
        userAuthInfo.setLiving(userModel.isLiving());
        userAuthInfo.setLivingId(userModel.getLivingId());
        userAuthInfo.setLocked(userModel.isLocked());
        userAuthInfo.setEmailVerification(userModel.isEmailVerification());
        userAuthInfo.setPhoneVerification(userModel.isPhoneVerification());
        return userAuthInfo;
    }

    protected ServiceStatusInfo<UserAuthInfoModel> userAuthInfo(long userId) {
        UserAuthInfoModel userAuthInfo = null;
        try {
            userAuthInfo = this.getUserAuthInfo(userId);
            if (userAuthInfo == null) {
                return new ServiceStatusInfo<>(404,"用户不存在",null);
            }
        } catch (Exception ex) {
            return new ServiceStatusInfo<>(500,"用户不存在",null);
        }
        return new ServiceStatusInfo<>(0,"",userAuthInfo);
    }

    @Cacheable(value = "userauthinfo",key = "#userId")
    public ServiceStatusInfo<UserAuthInfoModel> checkUserAuth(long userId) {
        ServiceStatusInfo<UserAuthInfoModel> serviceStatusInfo = this.userAuthInfo(userId);
        if (!serviceStatusInfo.isSuccess()) {
            return new ServiceStatusInfo<>(serviceStatusInfo.getCode(),serviceStatusInfo.getMsg(),null);
        }
        UserAuthInfoModel userAuthInfo = serviceStatusInfo.getData();
        if (userAuthInfo == null) {
            return new ServiceStatusInfo<>(404,"用户不存在",null);
        }
        if(userAuthInfo.isLocked()) {
            return new ServiceStatusInfo<>(401,"用户已被锁定",null);
        }
        return new ServiceStatusInfo<>(0,"",userAuthInfo);
    }

    public long regAdminUser(String password) {
        try {
            long id = UniqueIDCreater.generateID();
            String encodePwd = SHAEncrypt.encryptSHA(password);
            return this.userMapper.regAdmin(encodePwd, id);
        } catch (Exception ex) {
            return 0;
        }
    }

    public ServiceStatusInfo<UserLoginInfoDto> loginByUserPwd(String username,String password) {
        boolean isLogined = false;
        UserModel userModel = null;
        try {
            String encodePassword = SHAEncrypt.encryptSHA(password);
            userModel = this.userMapper.findUserByUserPwd(username,encodePassword);
            isLogined = userModel != null;
        } catch (Exception ex) {
            isLogined = false;
        }
        if (isLogined && userModel!=null) {
            UserLoginInfoDto infoDto = new UserLoginInfoDto();
            String token = JWTUtil.sign(String.valueOf(userModel.getId()));
            UserToken userToken = new UserToken(token,JWTUtil.EXPIRE_TIME);
            infoDto.setUserToken(userToken);
            infoDto.setEmail(userModel.getEmail());
            infoDto.setAvatarUrl(userModel.getAvatarUrl());
            infoDto.setId(userModel.getId());
            infoDto.setPhone(userModel.getPhone());
            infoDto.setUsername(userModel.getUsername());
            return new ServiceStatusInfo<>(0,"登录成功",infoDto);
        } else {
            return new ServiceStatusInfo<>(1,"用户名或密码错误!",null);
        }
    }

    public long addHeart(long id,int num) {
        return this.userMapper.addHeart(id,num);
    }

    @Transactional
    public UserModel regUserByOpenId(BindThirdPartyAccountInput input) {
        String userName = UniqueIDCreater.generateUserName();
        UserModel userModel = this.userMapper.findUserByOpenId(input.getOpenUserId(),input.getThirdType());
        if (userModel == null) {
            //注册
            long id = UniqueIDCreater.generateID();
            this.userMapper.regByOpenId(id,userName,input);
            if (this.userBindService.get(id,input.getThirdType())==null) {
                this.userBindService.add(input,id);
            } else {
                this.userBindService.update2(input,id,input.getThirdType());
            }
            return new UserModel(id,userName,input.getAvaterUrl(),"","");
        } else {
            //TODO 刷新用户头像
            //刷新绑定信息
            if (this.userBindService.get(userModel.getId(),input.getThirdType())==null) {
                this.userBindService.add(input,userModel.getId());
            } else {
                this.userBindService.update2(input,userModel.getId(),input.getThirdType());
            }
            return userModel;
        }
    }


    /**
     * 用户绑定第三方账号
     * @param input
     * @param userId
     * @return
     */
    public ServiceStatusInfo<Long> UserBindThird(BindThirdPartyAccountInput input,long userId) {
        ServiceStatusInfo<Long> serviceStatusInfo = null;
        if (this.userBindService.get(userId,input.getThirdType())==null) {
            serviceStatusInfo = this.userBindService.add(input,userId);
        } else {
            serviceStatusInfo = this.userBindService.update2(input,userId,input.getThirdType());
        }
        return serviceStatusInfo;
    }

    /**
     * 移除用户某个三方绑定
     * @param userId
     * @param type
     * @return
     */
    public ServiceStatusInfo<Long> RemoveUserBindThird(long userId,int type) {
        ServiceStatusInfo<Long> serviceStatusInfo = this.userBindService.delete(userId,type);
        return serviceStatusInfo;
    }

    /**
     * 通过手机号注册用户
     * @param phone
     * @return
     */
    @Transactional
    public UserModel regUserByPhone(String phone) {
        long userId =  UniqueIDCreater.generateID();
        try {
            String userName = UniqueIDCreater.generateUserName();
            userMapper.regByPhone(phone,userId,userName);
            UserModel userModel = new UserModel(userId,userName,null,null,phone);
            return userModel;
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     * 手机号验证码登录
     * @param phone 手机号
     * @param code 手机验证码
     * @return
     */
    public ServiceStatusInfo<UserModel> loginByPhone(String phone, String code) {

        ServiceStatusInfo<Object> statusInfo = checkPhoneCode(phone, code);
        if (!statusInfo.isSuccess()) {
            return new ServiceStatusInfo<>(1,statusInfo.getMsg(),null);
        }
        UserModel userModel = findUserByPhone(phone);
        boolean isNeedReg = userModel == null;
        if (isNeedReg) {
            UserModel regUserModel = regUserByPhone(phone);
            if (regUserModel == null) return new ServiceStatusInfo<>(1,"登录失败",null);
            userModel = regUserModel;
        }
        return new ServiceStatusInfo<UserModel>(userModel);
    }

    /**
     * 绑定手机号码
     * @param userId
     * @param phone
     * @param code
     * @return
     */
    @CacheEvict(value = "userauthinfo",key = "#userId",allEntries = true)
    public ServiceStatusInfo<Object> bindPhone(long userId,String phone,String code) {
        ServiceStatusInfo<Object> statusInfo = checkPhoneCode(phone, code);
        if (!statusInfo.isSuccess()) {
            return new ServiceStatusInfo<>(1,statusInfo.getMsg(),null);
        }
        this.userMapper.updateField("IsPhoneVerification=true,phone='"+phone+"'",userId);
        return new ServiceStatusInfo<>(0,"绑定成功",null);
    }

    /**
     * 校验手机和验证码合法性
     * @param phone
     * @param code
     * @return
     */
    public ServiceStatusInfo<Object> checkPhoneCode(String phone,String code) {
        // 验证手机验证码是否正确
        String cacheKey = AppConfigConstant.getRedisPhoneCodeKey(phone);
        boolean hasPhoneCode = stringRedisTemplate.hasKey(cacheKey);
        if (!hasPhoneCode) {
            return new ServiceStatusInfo<>(1,"请输入正确的手机号和验证码",null);
        }
        String cachePhoneCode = this.stringRedisTemplate.opsForValue().get(cacheKey);
        if(!code.equals(cachePhoneCode)) {
            return new ServiceStatusInfo<>(1,"请输入正确的验证码",null);
        }
        //移除验证码
        stringRedisTemplate.delete(cacheKey);
        return new ServiceStatusInfo<>(0,"验证成功",null);
    }

    /**
     * @param phone
     * @return 发送给手机验证码并返回验证码
     */
    public ServiceStatusInfo<String> sendPhoneCode (String phone) {
        //TODO 是否要判断手机是否在我们的系统中
        //判断是否可以发送验证码
        ValueOperations<String,SmsSendCfg> operations = redisTemplate.opsForValue();
        SmsSendCfg cfg = null;
        long currentTimeStamp = System.currentTimeMillis() / 1000;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String todayStr = simpleDateFormat.format(new Date().getTime());

        if (redisTemplate.hasKey(AppConfigConstant.getRedisPhoneCodeCfgKey(phone))) {
            try {
                cfg = operations.get(AppConfigConstant.getRedisPhoneCodeCfgKey(phone));
                if (currentTimeStamp - cfg.getLastSendSmsTimeStamp() < AppConfigConstant.APP_SMS_SEND_INTERVAL) {
                    return new ServiceStatusInfo<String>(1,"发送验证码太频繁，请稍后再试.","");
                }
                String[] cfgDayArr = cfg.getDaySendCount().split(":");
                int currentSendCount = Integer.parseInt(cfgDayArr[1]);
                if (todayStr.equals(cfgDayArr[0])) {
                    if (currentSendCount > AppConfigConstant.APP_SMS_SEND_MAX_COUNT_DAY) {
                        return new ServiceStatusInfo<String>(1,"超过当日最大验证码发送次数","");
                    }
                }
                cfg.setLastSendSmsTimeStamp(currentTimeStamp);
                cfg.setDaySendCount(todayStr+":"+(currentSendCount+1));
            }
            catch (Exception ex) {
                //TODO 增加日志
            }
        }



        //生成验证码
        String code = UniqueIDCreater.generatePhoneCode();
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
                return new ServiceStatusInfo<>(1,"发送验证码失败，请稍后重试.","");
            }
        }
        //刷新缓存
        if (cfg == null) {
            cfg = new SmsSendCfg(currentTimeStamp,todayStr+":1");
        }
        operations.set(AppConfigConstant.getRedisPhoneCodeCfgKey(phone),cfg);
        // 存到缓存
        stringRedisTemplate.opsForValue().set(AppConfigConstant.getRedisPhoneCodeKey(phone),code,AppConfigConstant.APP_SMS_CODE_EXPIRE_TIME,TimeUnit.SECONDS);
        if(AppConfigConstant.APP_SMS_SEND_OPEN) {
            return new ServiceStatusInfo<>(0,"发送成功","");
        } else {
            return new ServiceStatusInfo<>(code);
        }
    }
    public boolean sendSms(String phone,String code) {
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
        request.setTemplateParam("{\"code\":\""+code+"\"}");
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

    public AdFindIncreasedDto findIncreasedUserAd(AdFindIncreasedInput input){
        AdFindIncreasedDto dto = this.userMapper.findIncreasedUserAd(input);
        return dto;
    }

    public Long everyIncreasedUsers(){
        return this.userMapper.everyIncreasedUsers();
    }

    //审核相关
    public void updateReview(Long id,String reviewResultType){
        if ("block".equals(reviewResultType) || "review".equals(reviewResultType)){
            this.userMapper.reviewUserAvatar(id);
        }else {
            logger.info("用户头像图片审核正常,不需要处理");
        }
    }

    //定时任务
    public List<UserIdAndFollowersDto> findMyFansCount(){
        List<UserIdAndFollowersDto> dtos = this.userMapper.findMyFansCount();
        return dtos;
    }

    public List<UserIdAndFocusesDto> findMyFocusesCount(){
        List<UserIdAndFocusesDto> dtos = this.userMapper.findMyFocusesCount();
        return dtos;
    }

}


