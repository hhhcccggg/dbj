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
import com.zwdbj.server.adminserver.service.shop.service.offlineStoreStaffs.mapper.OfflineStoreStaffsMapper;
import com.zwdbj.server.tokencenter.model.AuthUser;
import com.zwdbj.server.tokencenter.model.UserToken;
import com.zwdbj.server.adminserver.config.AppConfigConstant;
import com.zwdbj.server.tokencenter.IAuthUserManager;
import com.zwdbj.server.tokencenter.TokenCenterManager;
import com.zwdbj.server.basemodel.model.ServiceStatusInfo;
import com.zwdbj.server.adminserver.service.homepage.model.AdFindIncreasedDto;
import com.zwdbj.server.adminserver.service.homepage.model.AdFindIncreasedInput;
import com.zwdbj.server.adminserver.service.user.model.*;
import com.zwdbj.server.adminserver.service.userBind.service.UserBindService;
import com.zwdbj.server.adminserver.service.youzan.service.YouZanService;
import com.zwdbj.server.utility.common.shiro.JWTUtil;
import com.zwdbj.server.utility.common.SHAEncrypt;
import com.zwdbj.server.utility.common.UniqueIDCreater;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.zwdbj.server.adminserver.service.user.mapper.IUserMapper;

import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    @Autowired
    TokenCenterManager tokenCenterManager;
    @Autowired
    IAuthUserManager iAuthUserManagerImpl;
    @Autowired
    OfflineStoreStaffsMapper offlineStoreStaffsMapper;

    private Logger logger = LoggerFactory.getLogger(UserService.class);


    @Transactional
    public ServiceStatusInfo<Object> setRolesForUser(long userId, List<EntityKeyModel<String>> roles) {
        //TODO 检查角色是否在系统中
        if (roles.size() <= 0) return new ServiceStatusInfo<>(1, "请至少指定一个角色", null);
        this.userMapper.deleteRole(userId);
        for (EntityKeyModel<String> name : roles) {
            this.setRole(userId, name.getId());
        }
        this.tokenCenterManager.refreshUserInfo(String.valueOf(userId), iAuthUserManagerImpl);
        return new ServiceStatusInfo<>(0, "", null);
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

    public long setRole(long userId, String roleName) {
        long result = this.userMapper.setRole(userId, roleName, UniqueIDCreater.generateID());
        if (result>0) {
            this.tokenCenterManager.refreshUserInfo(String.valueOf(userId), iAuthUserManagerImpl);
        }
        return result;
    }

    public long setPermission(String roleName, String permissionName) {
        return this.userMapper.setPermission(roleName, permissionName, UniqueIDCreater.generateID());
    }

    public long addRole(String name, String des) {
        return this.userMapper.addRole(name, des);
    }

    public long addPermission(String name, String desc) {
        return this.userMapper.addPermission(name, desc);
    }


    // 用户
    public void updateField(String fields, long id) {
        long result = this.userMapper.updateField(fields, id);
        if (result>0) {
            this.tokenCenterManager.refreshUserInfo(String.valueOf(id), iAuthUserManagerImpl);
        }
    }

    public List<UserDetailInfoDto> search(UserSearchForAdInput input, boolean flag) {
        List<UserDetailInfoDto> userModelList = this.userMapper.findUsersAd(input, flag);
        return userModelList;
    }

    public List<UserDetailInfoDto> searchTopFake(int s, int e) {
        return this.userMapper.findUsersFakeAd(s, e);
    }

    public List<UserDetailInfoDto> marketListAd(AdMarketUserInput input) {
        List<UserDetailInfoDto> userInfoDtoList = this.userMapper.marketListAd(input);
        return userInfoDtoList;
    }

    /**
     *
     * @param fullName
     * @param phone
     * @param tenantId
     * @param businessType 1:店主,2:普通员工
     * @return
     */
    public int greateUserByTenant (String fullName,String phone,long tenantId,int businessType,String notes){
        try {
            long result = this.userMapper.phoneIsExist(phone);
            long rId =  UniqueIDCreater.generateID();
            long id;
            int a=0;
            if (result==0){
                id = UniqueIDCreater.generateID();
                String username = UniqueIDCreater.generateUserName();
                String password = SHAEncrypt.encryptSHA(phone.substring(8)+"123456");
                a = this.userMapper.greateUserByTenant(id,username,password,fullName,phone,tenantId,notes,"BUSINESS");
            }else {
                UserModel u = this.userMapper.findUserByPhone(phone);
                a = this.userMapper.updateUserTanById(u.getId(),tenantId,notes,"BUSINESS");
                this.userMapper.deleteBusinessRole(u.getId(),tenantId);
                id=u.getId();
            }
            if (businessType==1){
                this.userMapper.insertUserRole(rId, id,"business_admin",tenantId);
            }else if (businessType==2){
                this.userMapper.insertUserRole(rId, id,"business_user",tenantId);
            }

            return a;

        }catch (Exception e){
            throw new RuntimeException("异常");
        }


    }
    public int modifyUserByTenantId(long tenantId){
        long userId = this.userMapper.findUserByTenId(tenantId,"business_admin");
        this.userMapper.deleteBusinessRole(userId,tenantId);
        int a = this.userMapper.updateUserTanById(userId,tenantId,"","NORMAL");
        return a;
    }
    public int delUserByTenantId(long tenantId){
        long userId = this.userMapper.findUserByTenId(tenantId,"business_admin");
        this.userMapper.deleteAllBusinessRole(tenantId);
        int a = this.userMapper.updateUserTanById(userId,tenantId,"","NORMAL");
        return a;
    }


    @Transactional
    public ServiceStatusInfo<Long> newMarketAd(AdNewMarketInput input) {
        //TODO 检查username和phone是否在系统存在
        Long id = UniqueIDCreater.generateID();
        Long userId = UniqueIDCreater.generateID();
        Long result = 0L;
        try {
            result = this.userMapper.newMarketAd(userId, input);
            Long roleResult = this.userMapper.insertUserRole(id, userId,"market",0L);
            return new ServiceStatusInfo<>(0, "", result);
        } catch (Exception e) {
            return new ServiceStatusInfo<>(1, "创建失败" + e.getMessage(), result);
        }
    }

    public ServiceStatusInfo<Object> lock(ResourceOpenInput<Long> input) {
        long result = this.userMapper.lock(input);
        if (result>0) {
            this.tokenCenterManager.refreshUserInfo(String.valueOf(input.getId()), iAuthUserManagerImpl);
        }
        return new ServiceStatusInfo<>(0, "", null);
    }

    public ServiceStatusInfo<Object> review(ResourceOpenInput<Long> input) {
        long result = this.userMapper.review(input);
        if (result>0) {
            this.tokenCenterManager.refreshUserInfo(String.valueOf(input.getId()), iAuthUserManagerImpl);
        }
        return new ServiceStatusInfo<>(0, "", result);
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
        } catch (Exception ex) {
            return new ServiceStatusInfo<>(1, "创建失败：" + ex.getMessage(), id);
        }
    }

    public ServiceStatusInfo<Object> logout(long userId) {
        this.tokenCenterManager.logout(String.valueOf(userId));
        return new ServiceStatusInfo<>(0, "注销成功", null);
    }


    /**
     * 查询被举报的用户
     */
    public List<UserDetailInfoDto> searchComplainUserListAd(AdUserComplainInput input) {
        List<UserDetailInfoDto> infoDtos = this.userMapper.searchComplainUserListAd(input);
        return infoDtos;
    }

    /**
     * 查询举报的详细信息
     */
    public List<AdUserComplainInfoDto> userComplainInfoListAd(Long id) {
        List<AdUserComplainInfoDto> infoDtos = this.userMapper.userComplainInfoListAd(id);
        return infoDtos;
    }

    /**
     * 处理举报用户
     */
    public ServiceStatusInfo<Long> doUComplainInfoAd(Long id, AdDoUserComplainInput input) {
        Long result = 0L;
        try {
            result = this.userMapper.doUComplainInfoAd(id, input);
            if (input.getIsLocked() == 1) {//冻结用户
                this.tokenCenterManager.refreshUserInfo(String.valueOf(id), iAuthUserManagerImpl);
            }
            return new ServiceStatusInfo<>(0, "", result);
        } catch (Exception e) {
            return new ServiceStatusInfo<>(1, "创建失败：" + e.getMessage(), result);
        }
    }

    /**
     * 账号管理-账号列表
     */
    public List<AdUserDetailInfoDto> manageUserListAd(AdManageUserInput input) {
        List<AdUserDetailInfoDto> infoDtos = this.userMapper.manageUserListAd(input);
        return infoDtos;
    }

    /**
     * 账号管理-账号新增
     */
    @Transactional
    public ServiceStatusInfo<Long> addManageUserAd(AdNewManageUserInput input) {
        //TODO 检查username和phone是否在系统存在
        Long isExist = this.userMapper.phoneIsExist(input.getPhone());
        if (isExist == 1) {
            return new ServiceStatusInfo<>(1, "创建失败:手机号已注册", isExist);
        }
        Long isExistName = this.userMapper.userNameIsExist(input.getUserName());
        if (isExistName == 1) {
            return new ServiceStatusInfo<>(1, "创建失败:用户名已存在", isExistName);
        }
        Long id = UniqueIDCreater.generateID();
        Long userId = UniqueIDCreater.generateID();

        Long result = 0L;
        try {
            String password = SHAEncrypt.encryptSHA("123456");
            result = this.userMapper.addManageUserAd(userId, input, password);
            Long roleResult = this.userMapper.insertManageUserRole(id, userId, input.getRoleName());
            return new ServiceStatusInfo<>(0, "", result);
        } catch (Exception e) {
            return new ServiceStatusInfo<>(1, "创建失败" + e.getMessage(), result);
        }
    }



    public ServiceStatusInfo<Long> newlyPwdAd(AdNewlyPwdInput input){
        Long result = 0L;
        try {
            if (!input.getmNewPassword().equals(input.getNewPassword()))return new ServiceStatusInfo<>(1, "新密码和确认密码不一致", null);
            String regEx = "^(?![a-zA-z]+$)(?!\\d+$)(?![_]+$)[a-zA-Z\\d_]{8,12}$";
            Pattern r = Pattern.compile(regEx);
            Matcher m1 = r.matcher(input.getmNewPassword());
            Matcher m2 = r.matcher(input.getNewPassword());
            boolean rs1 = m1.matches();
            boolean rs2 = m2.matches();
            if (!rs1  || !rs2 ) return new ServiceStatusInfo<>(1, "密码为8到12位字母、数字或“_”的组合", null);
            input.setmNewPassword(SHAEncrypt.encryptSHA(input.getNewPassword()));
            result = this.userMapper.newlyPwdAd(input);
            if (result==0)new ServiceStatusInfo<>(1, "找回失败", result);
            long id = this.userMapper.findUserIdByPhone(input.getPhone());
            this.tokenCenterManager.refreshUserInfo(String.valueOf(id), iAuthUserManagerImpl);
            return new ServiceStatusInfo<>(0, "", result);
        }catch (Exception e){
            return new ServiceStatusInfo<>(1, "找回密码失败" + e.getMessage(), result);
        }
    }

    /**
     * 修改用户密码
     *
     * @param id
     * @param input
     * @return
     */
    public ServiceStatusInfo<Long> modifyPwdAd(Long id, AdModifyManagerPasswordInput input) {

        Long result = 0L;
        try {
            if (!input.getmNewPassword().equals(input.getNewPassword()))return new ServiceStatusInfo<>(1, "新密码和确认密码不一致", result);
            String regEx = "^(?![a-zA-z]+$)(?!\\d+$)(?![_]+$)[a-zA-Z\\d_]{8,12}$";
            Pattern r = Pattern.compile(regEx);
            Matcher m1 = r.matcher(input.getmNewPassword());
            Matcher m2 = r.matcher(input.getNewPassword());
            Matcher m3 = r.matcher(input.getOldPassword());
            boolean rs1 = m1.matches();
            boolean rs2 = m2.matches();
            boolean rs3 = m3.matches();
            if (!rs1  || !rs2 || !rs3) return new ServiceStatusInfo<>(1, "密码为8到12位字母、数字或“_”的组合", null);
            input.setOldPassword(SHAEncrypt.encryptSHA(input.getOldPassword()));
            input.setmNewPassword(SHAEncrypt.encryptSHA(input.getmNewPassword()));
            result = this.userMapper.modifyPwdAd(id, input);
            if (result==0)new ServiceStatusInfo<>(1, "修改失败", result);
            this.tokenCenterManager.refreshUserInfo(String.valueOf(id), iAuthUserManagerImpl);
            return new ServiceStatusInfo<>(0, "", result);
        } catch (Exception e) {
            return new ServiceStatusInfo<>(1, "修改密码失败" + e.getMessage(), result);
        }
    }

    public boolean phoneIsExit(String phone){
        return this.userMapper.phoneIsExist(phone)>0;
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

    /**
     * 根据id得到用户昵称
     * @param userId
     * @return
     */
    public String getNickNameById(long userId){
        return this.userMapper.getNickNameById(userId);
    }


    public UserDetailInfoDto getUserDetail(long userId) {
        //TODO 增加缓存数据
        UserDetailInfoDto userDetailInfoDto = this.userMapper.getUserDetail(userId);
        if (userDetailInfoDto == null) return null;
        userDetailInfoDto.getShopInfoDto().setLotteryTicketCount(this.youZanService.lotteryTicketCount(userId).getData());
        userDetailInfoDto.getShopInfoDto().setCartUrl("https://h5.youzan.com/v2/trade/cart?kdt_id=" + AppConfigConstant.YOUZAN_BIND_SHOP_ID);
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

    public long regAdminUser(String password) {
        try {
            long id = UniqueIDCreater.generateID();
            String encodePwd = SHAEncrypt.encryptSHA(password);
            return this.userMapper.regAdmin(encodePwd, id);
        } catch (Exception ex) {
            return 0;
        }
    }
    public ServiceStatusInfo<UserLoginInfoDto> loginByPhonePwd(String phone,String password){
        boolean isLogined = false;
        UserModel userModel = null;
        try {
            String encodePassword = SHAEncrypt.encryptSHA(password);
            userModel = this.userMapper.findUserByPhonePwd(phone, encodePassword);
            isLogined = userModel != null;
        } catch (Exception ex) {
            isLogined = false;
        }
        if (isLogined && userModel != null) {
            UserLoginInfoDto infoDto = new UserLoginInfoDto();
            String token = JWTUtil.sign(String.valueOf(userModel.getId()));
            UserToken userToken = new UserToken(token, JWTUtil.EXPIRE_TIME);
            infoDto.setUserToken(userToken);
            infoDto.setEmail(userModel.getEmail());
            infoDto.setAvatarUrl(userModel.getAvatarUrl());
            infoDto.setId(userModel.getId());
            infoDto.setPhone(userModel.getPhone());
            infoDto.setUsername(userModel.getUsername());
            infoDto.setRoleName(userModel.getRoleName());
            this.tokenCenterManager.fetchToken(String.valueOf(userModel.getId()), iAuthUserManagerImpl);
            return new ServiceStatusInfo<>(0, "登录成功", infoDto);
        } else {
            return new ServiceStatusInfo<>(1, "用户名或密码错误!", null);
        }
    }

    public ServiceStatusInfo<UserLoginInfoDto> loginByUserPwd(String username, String password) {
        boolean isLogined = false;
        UserModel userModel = null;
        try {
            String encodePassword = SHAEncrypt.encryptSHA(password);
            userModel = this.userMapper.findUserByUserPwd(username, encodePassword);
            isLogined = userModel != null;
        } catch (Exception ex) {
            isLogined = false;
        }
        if (isLogined && userModel != null) {
            UserLoginInfoDto infoDto = new UserLoginInfoDto();
            String token = JWTUtil.sign(String.valueOf(userModel.getId()));
            UserToken userToken = new UserToken(token, JWTUtil.EXPIRE_TIME);
            infoDto.setUserToken(userToken);
            infoDto.setEmail(userModel.getEmail());
            infoDto.setAvatarUrl(userModel.getAvatarUrl());
            infoDto.setId(userModel.getId());
            infoDto.setPhone(userModel.getPhone());
            infoDto.setUsername(userModel.getUsername());
            infoDto.setRoleName(userModel.getRoleName());
            this.tokenCenterManager.fetchToken(String.valueOf(userModel.getId()), iAuthUserManagerImpl);
            return new ServiceStatusInfo<>(0, "登录成功", infoDto);
        } else {
            return new ServiceStatusInfo<>(1, "用户名或密码错误!", null);
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

    public AdFindIncreasedDto findIncreasedUserAd(AdFindIncreasedInput input, boolean flag) {
        AdFindIncreasedDto dto = this.userMapper.findIncreasedUserAd(input, flag);
        return dto;
    }

    public long dau() {
        return this.userMapper.dau();
    }

    public Long everyIncreasedUsers() {
        return this.userMapper.everyIncreasedUsers();
    }

    //审核相关
    public void updateReview(Long id, String reviewResultType) {
        if ("block".equals(reviewResultType) || "review".equals(reviewResultType)) {
            this.userMapper.reviewUserAvatar(id);
        } else {
            logger.info("用户头像图片审核正常,不需要处理");
        }
    }

    //定时任务
    public List<UserIdAndFollowersDto> findMyFansCount() {
        List<UserIdAndFollowersDto> dtos = this.userMapper.findMyFansCount();
        return dtos;
    }

    public List<UserIdAndFocusesDto> findMyFocusesCount() {
        List<UserIdAndFocusesDto> dtos = this.userMapper.findMyFocusesCount();
        return dtos;
    }

    public void newVestUser(String phone, String avatarUrl, String nickName) {
        Long id = UniqueIDCreater.generateID();
        String userName = UniqueIDCreater.generateUserName();
        this.userMapper.newVestUser(phone, id, userName, avatarUrl, nickName);
    }

    public List<Long> getVestUserIds1() {
        return this.userMapper.getVestUserIds1();
    }

    public List<Long> getVestUserIds2() {
        return this.userMapper.getVestUserIds2();
    }

    /**
     * 商家的员工查询
     * @param userShopSearchInput
     * @return
     */
    public ServiceStatusInfo<List<UserShopSelectInput>> selectStaff(UserShopSearchInput userShopSearchInput){
        try{
            AuthUser authUser = tokenCenterManager.fetchUser(JWTUtil.getCurrentId()+"").getData();
            if(authUser == null){
                return new ServiceStatusInfo(1,"查询失败:用户不存在",null);
            }
            return new ServiceStatusInfo(0,"",this.userMapper.selectStaff(userShopSearchInput,authUser.getLegalSubjectId(),authUser.getTenantId()));
        }catch(Exception e){
            e.printStackTrace();
            return new ServiceStatusInfo(1,"查询失败"+e.getMessage(),null);
        }
    }

    /**
     * 创建该商家下的员工
     * @param createUserInput
     * @return
     */
    public ServiceStatusInfo<Long> createUserShop(CreateUserInput createUserInput){
        Long isExist = this.userMapper.phoneIsExist(createUserInput.getPhone());
        AuthUser authUser = tokenCenterManager.fetchUser(JWTUtil.getCurrentId()+"").getData();
        if(authUser == null){
            return new ServiceStatusInfo(1,"查询失败:用户不存在",null);
        }
        if (isExist == 1) {
            long result = this.userMapper.updateUserByPhone(createUserInput,authUser.getTenantId());
            if (result==0)return new ServiceStatusInfo<>(1, "创建失败:手机号已注册", result);
            return new ServiceStatusInfo<>(0, "手机号已注册,请以手机号登录", result);

        }
        createUserInput.setUserName(UniqueIDCreater.generateUserName());
        long id = UniqueIDCreater.generateID();
        try{
            String password = SHAEncrypt.encryptSHA("123456");
            long result = this.userMapper.createUserShop(createUserInput, id, password,authUser.getTenantId());
            //成功发短信
            return new ServiceStatusInfo<>(0, "", result);
        }catch(Exception e){
            return new ServiceStatusInfo<>(1, "添加失败"+e.getMessage(), 0L);
        }
    }

    /**
     * 批量删除员工假删
     * @param id
     * @return
     */
    public ServiceStatusInfo<Long> deleteByIds(long[] id ){
        try{
            AuthUser authUser = tokenCenterManager.fetchUser(JWTUtil.getCurrentId()+"").getData();
            if(authUser == null){
                return new ServiceStatusInfo(1,"查询失败:用户不存在",null);
            }
            long result = this.userMapper.deleteByIds(id,authUser.getTenantId());
            return new ServiceStatusInfo<>(0, "", result);
        }catch(Exception e){
            return new ServiceStatusInfo<>(1, "批量删除失败"+e.getMessage(), 0L);
        }
    }

    /**
     * 批量设置为代言人
     * @param id
     * @return
     */
    public ServiceStatusInfo<Long> setRepresent(long[] id ){
        try{
            AuthUser authUser = tokenCenterManager.fetchUser(JWTUtil.getCurrentId()+"").getData();
            if(authUser == null){
                return new ServiceStatusInfo(1,"查询失败:用户不存在",null);
            }
            long result = this.offlineStoreStaffsMapper.setRepresent(id,authUser.getLegalSubjectId());
            return new ServiceStatusInfo<>(0, "", result);
        }catch(Exception e){
            return new ServiceStatusInfo<>(1, "批量设置失败"+e.getMessage(), 0L);
        }
    }

    /**
     * 取消代言人
     * @param id
     * @return
     */
    public ServiceStatusInfo<Long> cancelRepresent(long[] id ){
        try{
            AuthUser authUser = tokenCenterManager.fetchUser(JWTUtil.getCurrentId()+"").getData();
            if(authUser == null){
                return new ServiceStatusInfo(1,"查询失败:用户不存在",null);
            }
            long result = this.offlineStoreStaffsMapper.cancelRepresent(id,authUser.getLegalSubjectId());
            return new ServiceStatusInfo<>(0, "", result);
        }catch(Exception e){
            return new ServiceStatusInfo<>(1, "批量取消失败"+e.getMessage(), 0L);
        }
    }
    public ServiceStatusInfo<Long> getManualUser(){
        List<Long> manualUserIds = this.userMapper.getManualUser();
        Random random = new Random();
        int a = random.nextInt(manualUserIds.size());
        Long userId = manualUserIds.get(a);
        return new ServiceStatusInfo<>(0,"",userId);
    }

}


