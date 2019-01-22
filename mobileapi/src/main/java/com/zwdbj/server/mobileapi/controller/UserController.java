package com.zwdbj.server.mobileapi.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zwdbj.server.mobileapi.config.AppConfigConstant;
import com.zwdbj.server.mobileapi.service.setting.model.AppPushSettingModel;
import com.zwdbj.server.mobileapi.service.setting.service.SettingService;
import com.zwdbj.server.mobileapi.service.user.model.*;
import com.zwdbj.server.tokencenter.TokenCenterManager;
import com.zwdbj.server.tokencenter.model.UserToken;
import com.zwdbj.server.usercommonservice.authuser.service.AuthUserManagerImpl;
import com.zwdbj.server.utility.model.ServiceStatusInfo;
import com.zwdbj.server.mobileapi.service.userBind.model.UserThirdAccountBindDto;
import com.zwdbj.server.mobileapi.service.userBind.service.UserBindService;
import com.zwdbj.server.utility.common.shiro.JWTUtil;
import com.zwdbj.server.utility.model.ResponseData;
import com.zwdbj.server.utility.model.ResponseDataCode;
import com.zwdbj.server.utility.model.ResponsePageInfoData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.zwdbj.server.mobileapi.service.user.service.UserService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
@Api(description = "用户相关", value = "User")
public class UserController {
    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    private SettingService settingService;
    @Autowired
    private UserBindService userBindService;
    @Autowired
    private AuthUserManagerImpl iAuthUserManager;
    @Autowired
    private TokenCenterManager tokenCenterManager;

    @RequiresAuthentication
    @RequestMapping(value = "/pushSetting", method = RequestMethod.POST)
    @ApiOperation(value = "设置用户推送设置")
    public ResponseData<AppPushSettingModel> pushSetting(@RequestBody AppPushSettingModel settingModel) {
        AppPushSettingModel appPushSettingModel = this.settingService.set(settingModel, JWTUtil.getCurrentId());
        return new ResponseData<>(ResponseDataCode.STATUS_NORMAL, "", appPushSettingModel);
    }

    @RequiresAuthentication
    @RequestMapping(value = "/pushSetting", method = RequestMethod.GET)
    @ApiOperation(value = "获取用户推送设置")
    public ResponseData<AppPushSettingModel> pushSetting() {
        AppPushSettingModel appPushSettingModel = this.settingService.get(JWTUtil.getCurrentId());
        return new ResponseData<>(ResponseDataCode.STATUS_NORMAL, "", appPushSettingModel);
    }

    @RequiresAuthentication
    @RequestMapping(value = "bindPhone", method = RequestMethod.POST)
    @ApiOperation(value = "用户绑定手机号码")
    public ResponseData<Object> bindPhone(@RequestBody PhoneCodeInput input) {
        long userId = JWTUtil.getCurrentId();
        ServiceStatusInfo<Object> statusInfo = this.userService.bindPhone(userId, input.getPhone(), input.getCode());
        if (statusInfo.isSuccess()) {
            return new ResponseData<>(ResponseDataCode.STATUS_NORMAL, "绑定成功", null);
        }
        return new ResponseData<>(ResponseDataCode.STATUS_ERROR, statusInfo.getMsg(), null);
    }

    @RequiresAuthentication
    @RequestMapping(value = "/verifyPhone", method = RequestMethod.POST)
    @ApiOperation(value = "校验手机号和验证码")
    public ResponseData<Object> verifyPhone(@RequestBody PhoneCodeInput input) {
        ServiceStatusInfo<Object> statusInfo = this.userService.checkPhoneCode(input.getPhone(), input.getCode());
        if (statusInfo.isSuccess()) {
            return new ResponseData<>(ResponseDataCode.STATUS_NORMAL, "验证成功", null);
        }
        return new ResponseData<>(ResponseDataCode.STATUS_ERROR, "验证失败，请输入正确的手机号和验证码", null);
    }

    @RequestMapping(value = "/loginByThird", method = RequestMethod.POST)
    @ApiOperation(value = "第三方登录,recommendUserId为邀请人id,若无则可无此参数")
    public ResponseData<UserLoginInfoDto> loginByThird(@RequestBody BindThirdPartyAccountInput input,
                                                       @RequestParam(required = false,defaultValue = "0") Long recommendUserId) {
        UserModel userModel = userService.regUserByOpenId(input,recommendUserId);
        if (userModel==null)return new ResponseData<>(ResponseDataCode.STATUS_ERROR, "登录失败", null);
        UserToken userToken = this.tokenCenterManager.fetchToken(String.valueOf(userModel.getId()),this.iAuthUserManager).getData();
        UserLoginInfoDto userLoginInfo = new UserLoginInfoDto();
        userLoginInfo.setUserToken(userToken);
        userLoginInfo.setEmail(userModel.getEmail());
        userLoginInfo.setId(userModel.getId());
        userLoginInfo.setPhone(userModel.getPhone());
        userLoginInfo.setAvatarUrl(userModel.getAvatarUrl());
        userLoginInfo.setUsername(userModel.getUsername());
        return new ResponseData<>(ResponseDataCode.STATUS_NORMAL, "登录成功", userLoginInfo);
    }

    @RequiresAuthentication
    @RequestMapping(value = "/thirdBind", method = RequestMethod.POST)
    @ApiOperation(value = "绑定第三方账号")
    public ResponseData<Object> thirdBind(@RequestBody BindThirdPartyAccountInput input) {
        ServiceStatusInfo<Long> statusInfo = this.userService.UserBindThird(input, JWTUtil.getCurrentId());
        if (statusInfo.isSuccess()) {
            return new ResponseData<>(ResponseDataCode.STATUS_NORMAL, "", null);
        } else {
            return new ResponseData<>(ResponseDataCode.STATUS_ERROR, "绑定失败", null);
        }
    }

    @RequiresAuthentication
    @RequestMapping(value = "/removeThirdBind", method = RequestMethod.POST)
    @ApiOperation(value = "解绑某个平台")
    public ResponseData<Object> removeThirdBind(@RequestParam("type") int type) {
        ServiceStatusInfo<Long> statusInfo = this.userService.RemoveUserBindThird(JWTUtil.getCurrentId(), type);
        if (statusInfo.isSuccess()) {
            return new ResponseData<>(ResponseDataCode.STATUS_NORMAL, "", null);
        } else {
            return new ResponseData<>(ResponseDataCode.STATUS_ERROR, "解绑失败", null);
        }
    }

    @RequiresAuthentication
    @RequestMapping(value = "/thirdBindList", method = RequestMethod.GET)
    @ApiOperation(value = "获取当前用户第三方账号绑定情况")
    public ResponseData<List<UserThirdAccountBindDto>> thirdBindList() {
        List<UserThirdAccountBindDto> dtos = this.userBindService.list(JWTUtil.getCurrentId());
        return new ResponseData<>(ResponseDataCode.STATUS_NORMAL, "", dtos);
    }


    @RequestMapping(value = "/AuthByPhone", method = RequestMethod.POST)
    @ApiOperation(value = "通过手机号验证码登录授权,recommendUserId为邀请人id,若无则可无此参数")
    public ResponseData<UserLoginInfoDto> AuthByPhone(@RequestBody PhoneCodeInput input,
                                                      @RequestParam(required = false,defaultValue = "0") Long recommendUserId) {
        ServiceStatusInfo<UserModel> serviceStatusInfo = userService.loginByPhone(input.getPhone(), input.getCode(),recommendUserId);
        if (serviceStatusInfo.isSuccess()) {
            UserToken userToken = this.tokenCenterManager
                    .fetchToken(String.valueOf(serviceStatusInfo.getData().getId()),this.iAuthUserManager)
                    .getData();
            UserLoginInfoDto userLoginInfo = new UserLoginInfoDto();
            userLoginInfo.setUserToken(userToken);
            userLoginInfo.setEmail(serviceStatusInfo.getData().getEmail());
            userLoginInfo.setId(serviceStatusInfo.getData().getId());
            userLoginInfo.setPhone(serviceStatusInfo.getData().getPhone());
            userLoginInfo.setAvatarUrl(serviceStatusInfo.getData().getAvatarUrl());
            userLoginInfo.setUsername(serviceStatusInfo.getData().getUsername());
            return new ResponseData<>(ResponseDataCode.STATUS_NORMAL, "登录成功", userLoginInfo);
        } else {
            return new ResponseData<>(ResponseDataCode.STATUS_UNAUTH, serviceStatusInfo.getMsg(), null);
        }
    }

    @RequestMapping(value = "/sendPhoneCode", method = RequestMethod.GET)
    @ApiOperation(value = "获取手机验证码")
    public ResponseData<Map<String, String>> sendPhoneCode(@RequestParam("phone") String phone) {
        ServiceStatusInfo<String> info = userService.sendPhoneCode(phone);
        if (info.isSuccess()) {
            if (AppConfigConstant.APP_SMS_SEND_OPEN) {
                return new ResponseData<>(ResponseDataCode.STATUS_NORMAL, "发送验证码成功!", null);
            } else {
                Map<String, String> map = new HashMap<>();
                map.put("code", info.getData());
                return new ResponseData<>(ResponseDataCode.STATUS_NORMAL, "发送验证码成功!{" + info.getData() + "}", map);
            }
        }
        return new ResponseData<>(ResponseDataCode.STATUS_ERROR, info.getMsg(), null);
    }

    // 关注
    @RequiresAuthentication
    @RequestMapping(value = "/follow", method = RequestMethod.POST)
    @ApiOperation(value = "关注/取消关注用户,如果FollowInput的livingId不为0则代表从直播间关注")
    public ResponseData<Object> follow(@RequestBody FollowInput input) {
        ServiceStatusInfo<Object> statusInfo = this.userService.follow(input);
        if (statusInfo.isSuccess()) {
            return new ResponseData<>(ResponseDataCode.STATUS_NORMAL, statusInfo.getMsg(), null);
        } else {
            return new ResponseData<>(ResponseDataCode.STATUS_ERROR, statusInfo.getMsg(), null);
        }
    }

    @RequiresAuthentication
    @RequestMapping(value = "/myFollowers/{userId}", method = RequestMethod.GET)
    @ApiOperation(value = "我的粉丝/关注我的人")
    public ResponsePageInfoData<List<FollowerUserInfoDto>> myFollowers(@RequestParam("pageNo") int pageNo,
                                                                       @RequestParam("rows") int rows,
                                                                       @PathVariable long userId) {
        Page<FollowerUserInfoDto> pageInfo = PageHelper.startPage(pageNo, rows);
        List<FollowerUserInfoDto> dtos = this.userService.myFollowers(userId);
        return new ResponsePageInfoData<>(ResponseDataCode.STATUS_NORMAL, "", dtos, pageInfo.getTotal());
    }

    @RequiresAuthentication
    @RequestMapping(value = "/myFollowed/{userId}", method = RequestMethod.GET)
    @ApiOperation(value = "我关注的人")
    public ResponsePageInfoData<List<FollowerUserInfoDto>> myFollowed(@RequestParam("pageNo") int pageNo,
                                                                      @RequestParam("rows") int rows,
                                                                      @PathVariable long userId) {
        Page<FollowerUserInfoDto> pageInfo = PageHelper.startPage(pageNo, rows);
        List<FollowerUserInfoDto> dtos = this.userService.myFollowed(userId);
        return new ResponsePageInfoData<>(ResponseDataCode.STATUS_NORMAL, "", dtos, pageInfo.getTotal());
    }

    @RequiresAuthentication
    @RequestMapping(value = "/detail/{userId}", method = RequestMethod.GET)
    @ApiOperation(value = "获取用户详情")
    public ResponseData<UserDetailInfoDto> userDetail(@PathVariable long userId) {
        UserDetailInfoDto userDetailInfoDto = this.userService.getUserDetail(userId);
        if (userDetailInfoDto == null) {
            return new ResponseData<>(ResponseDataCode.STATUS_NOT_FOUND, "用户不存在", null);
        } else {
            return new ResponseData<>(ResponseDataCode.STATUS_NORMAL, "", userDetailInfoDto);
        }
    }

    @RequiresAuthentication
    @RequestMapping(value = "/current/userDetail", method = RequestMethod.GET)
    @ApiOperation(value = "获取当前登录用户信息")
    public ResponseData<UserDetailInfoDto> userDetailByToken() {
        UserDetailInfoDto userDetailInfoDto = this.userService.getUserDetailByToken();
        if (userDetailInfoDto == null) {
            return new ResponseData<>(ResponseDataCode.STATUS_NOT_FOUND, "用户不存在", null);
        } else {
            return new ResponseData<>(ResponseDataCode.STATUS_NORMAL, "", userDetailInfoDto);
        }
    }

    @RequiresAuthentication
    @RequestMapping(value = "/updateUserInfo", method = RequestMethod.POST)
    @ApiOperation(value = "修改我的个人信息")
    public ResponseData<Object> updateUserInfo(@RequestBody UpdateUserInfoInput input) {
        long userId = JWTUtil.getCurrentId();
        ServiceStatusInfo<Object> statusInfo = this.userService.updateUserInfo(userId, input);
        if (statusInfo.isSuccess()) {
            return new ResponseData<>(ResponseDataCode.STATUS_NORMAL, "修改资料成功", null);
        } else {
            return new ResponseData<>(ResponseDataCode.STATUS_ERROR, "修改资料失败", null);
        }
    }

    @RequiresAuthentication
    @RequestMapping(value = "/userNameIsExist/{username}", method = RequestMethod.GET)
    @ApiOperation(value = "判断username是否已经存在,username格式:字母开头，允许3-12字节，允许字母数字下划线")
    public ResponseData<Object> userNameIsExist(@PathVariable String username) {
        ServiceStatusInfo<Object> statusInfo = this.userService.userNameIsExist(username);
        if (statusInfo.isSuccess()) {
            return new ResponseData<>(ResponseDataCode.STATUS_NORMAL, "", null);
        } else {
            return new ResponseData<>(ResponseDataCode.STATUS_ERROR, statusInfo.getMsg(), null);
        }
    }

    @RequiresAuthentication
    @RequestMapping(value = "/followStatusSearch", method = RequestMethod.POST)
    @ApiOperation(value = "查询两个用户关注关系")
    public ResponseData<UserFollowInfoDto> followStatusSearch(@RequestBody UserFollowInfoSearchInput input) {
        UserFollowInfoDto dto = this.userService.followStatusSearch(input);
        if (dto == null) {
            return new ResponseData<>(ResponseDataCode.STATUS_NORMAL, "", new UserFollowInfoDto());
        } else {
            return new ResponseData<>(ResponseDataCode.STATUS_NORMAL, "", dto);
        }
    }

    @RequiresAuthentication
    @RequestMapping(value = "/myAllHearts", method = RequestMethod.GET)
    @ApiOperation(value = "获取我的所有点赞信息列表")
    public ResponsePageInfoData<List<AllHeartsForUserVideosMessageDto>> myAllHearts(@RequestParam(value = "pageNo", defaultValue = "1", required = true) int pageNo,
                                                                                    @RequestParam(value = "rows", defaultValue = "13", required = true) int rows) {
        Page<AllHeartsForUserVideosMessageDto> pageInfo = PageHelper.startPage(pageNo, rows);
        List<AllHeartsForUserVideosMessageDto> dtos = this.userService.getAllHeartsForMyVideos(JWTUtil.getCurrentId());
        return new ResponsePageInfoData<>(ResponseDataCode.STATUS_NORMAL, "", dtos, pageInfo.getTotal());
    }


    @RequiresAuthentication
    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    @ApiOperation(value = "退出")
    public ResponseData<Object> logout() {
        ServiceStatusInfo<Object> statusInfo = this.userService.logout(JWTUtil.getCurrentId());
        if (statusInfo.isSuccess()) {
            return new ResponseData<>(ResponseDataCode.STATUS_NORMAL, statusInfo.getMsg(), null);
        }
        return new ResponseData<>(ResponseDataCode.STATUS_ERROR, statusInfo.getMsg(), null);
    }

    @RequestMapping(value = "/phoneIsRegOrNot", method = RequestMethod.POST)
    @ApiOperation(value = "查验此手机号是否被注册(返回的结果 100:未被注册，201:已经注册过但是没有设置密码,202:已经注册过并设置密码)")
    public  ResponseData<Integer> phoneIsRegOrNot(@RequestBody PhoneCodeInput input){
        ServiceStatusInfo<Integer> statusInfo = this.userService.phoneIsRegOrNot(input);
        if (statusInfo.isSuccess()) {
            return new ResponseData<>(ResponseDataCode.STATUS_NORMAL, statusInfo.getMsg(), statusInfo.getData());
        }
        return new ResponseData<>(ResponseDataCode.STATUS_ERROR, statusInfo.getMsg(), null);
    }

    @RequestMapping(value = "/regUser", method = RequestMethod.POST)
    @ApiOperation(value = "账号注册并设置密码")
    public ResponseData<Integer> regUser(@RequestBody RegUserInput input){
        ServiceStatusInfo<Integer> statusInfo = this.userService.regUser(input);
        if (statusInfo.isSuccess()) {
            return new ResponseData<>(ResponseDataCode.STATUS_NORMAL, statusInfo.getMsg(), statusInfo.getData());
        }
        return new ResponseData<>(ResponseDataCode.STATUS_ERROR, statusInfo.getMsg(), null);

    }

    @RequestMapping(value = "/getMyNewPWD", method = RequestMethod.POST)
    @ApiOperation(value = "找回账号密码")
    public ResponseData<Integer> getMyNewPWD(@RequestBody NewMyPasswordInput input){
        ServiceStatusInfo<Integer> statusInfo = this.userService.getMyNewPWD(input);
        if (statusInfo.isSuccess()) {
            return new ResponseData<>(ResponseDataCode.STATUS_NORMAL, statusInfo.getMsg(), statusInfo.getData());
        }
        return new ResponseData<>(ResponseDataCode.STATUS_ERROR, statusInfo.getMsg(), null);

    }
    @RequestMapping(value = "/loginByPwd", method = RequestMethod.POST)
    @ApiOperation(value = "以账号密码方式登录")
    public ResponseData<UserLoginInfoDto> loginByUserPwd(@RequestBody UserPwdLoginInput input) {
        ServiceStatusInfo<UserLoginInfoDto> statusInfo = this.userService.loginByPwd(input.getPhone(), input.getPassword());
        if (statusInfo.isSuccess()) {
            return new ResponseData<>(ResponseDataCode.STATUS_NORMAL, statusInfo.getMsg(), statusInfo.getData());
        }
        return new ResponseData<>(ResponseDataCode.STATUS_UNAUTH, statusInfo.getMsg(), null);
    }

    @ApiOperation(value = "推荐人设置")
    @PostMapping("/setRecommendUserId")
    public ResponseData<Long> setRecommendUserId(long recommendUserId){
        ServiceStatusInfo<Long> statusInfo = this.userService.setRecommendUserId(recommendUserId);
        if (statusInfo.isSuccess()) {
            return new ResponseData<>(ResponseDataCode.STATUS_NORMAL, statusInfo.getMsg(), statusInfo.getData());
        }
        return new ResponseData<>(ResponseDataCode.STATUS_UNAUTH, statusInfo.getMsg(), null);
    }


}
