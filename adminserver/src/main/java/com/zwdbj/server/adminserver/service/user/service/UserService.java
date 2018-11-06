package com.zwdbj.server.adminserver.service.user.service;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.zwdbj.server.adminserver.easemob.api.EaseMobUser;
import com.zwdbj.server.adminserver.model.EntityKeyModel;
import com.zwdbj.server.adminserver.model.ResourceOpenInput;
import com.zwdbj.server.adminserver.model.user.UserToken;
import com.zwdbj.server.adminserver.config.AppConfigConstant;
import com.zwdbj.server.adminserver.service.ServiceStatusInfo;
import com.zwdbj.server.adminserver.service.homepage.model.AdFindIncreasedDto;
import com.zwdbj.server.adminserver.service.homepage.model.AdFindIncreasedInput;
import com.zwdbj.server.adminserver.service.user.model.*;
import com.zwdbj.server.adminserver.service.userBind.service.UserBindService;
import com.zwdbj.server.adminserver.service.youzan.service.YouZanService;
import com.zwdbj.server.adminserver.shiro.JWTUtil;
import com.zwdbj.server.utility.common.SHAEncrypt;
import com.zwdbj.server.utility.common.UniqueIDCreater;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.zwdbj.server.adminserver.service.user.mapper.IUserMapper;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {


    @Autowired
    private IUserMapper userMapper;
    @Autowired
    YouZanService youZanService;
    @Autowired
    UserBindService userBindService;
    @Autowired
    EaseMobUser easeMobUser;

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

    public void newVestUser(String phone,String avatarUrl,String nickName){
        Long id = UniqueIDCreater.generateID();
        String userName = UniqueIDCreater.generateUserName();
        this.userMapper.newVestUser(phone,id,userName,avatarUrl,nickName);
    }

    public List<Long> getVestUserIds1(){
        return this.userMapper.getVestUserIds1();
    }
    public List<Long> getVestUserIds2(){
        return this.userMapper.getVestUserIds2();
    }


}


