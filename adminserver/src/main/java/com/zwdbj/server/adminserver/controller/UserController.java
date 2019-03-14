package com.zwdbj.server.adminserver.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zwdbj.server.adminserver.identity.RoleIdentity;
import com.zwdbj.server.common.sms.ISendSmsService;
import com.zwdbj.server.config.settings.AppSettingConfigs;
import com.zwdbj.server.tokencenter.TokenCenterManager;
import com.zwdbj.server.basemodel.model.*;
import com.zwdbj.server.adminserver.model.*;
import com.zwdbj.server.adminserver.service.user.model.*;
import com.zwdbj.server.basemodel.model.ServiceStatusInfo;
import com.zwdbj.server.utility.common.shiro.JWTUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.zwdbj.server.adminserver.service.user.service.UserService;

import java.util.*;

@RestController
@RequestMapping("/api/user")
@Api(description = "用户相关", value = "User")
public class UserController {
    private UserService userService;
    @Autowired
    private TokenCenterManager tokenCenterManager;
    @Autowired
    private ISendSmsService sendSmsService;
    @Autowired
    private AppSettingConfigs appSettingConfigs;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }


    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ApiOperation(value = "登录")
    public ResponseData<UserLoginInfoDto> login(@RequestBody UserPwdLoginInput input) {
        ServiceStatusInfo<UserLoginInfoDto> statusInfo = this.userService.loginByUserPwd(input.getUsername(), input.getPassword());
        if (statusInfo.isSuccess()) {
            return new ResponseData<>(ResponseDataCode.STATUS_NORMAL, statusInfo.getMsg(), statusInfo.getData());
        }
        return new ResponseData<>(ResponseDataCode.STATUS_UNAUTH, statusInfo.getMsg(), null);
    }
    @RequestMapping(value = "/shop/login", method = RequestMethod.POST)
    @ApiOperation(value = "店铺登录")
    public ResponseData<UserLoginInfoDto> shopLogin(@RequestBody ShopUserLoginInput input) {
        ServiceStatusInfo<UserLoginInfoDto> statusInfo = this.userService.loginByPhonePwd(input.getPhone(), input.getPassword());
        if (statusInfo.isSuccess()) {
            return new ResponseData<>(ResponseDataCode.STATUS_NORMAL, statusInfo.getMsg(), statusInfo.getData());
        }
        return new ResponseData<>(ResponseDataCode.STATUS_UNAUTH, statusInfo.getMsg(), null);
    }


    @RequiresAuthentication
    @RequestMapping(value = "/detail/{userId}", method = RequestMethod.GET)
    @ApiOperation(value = "获取用户详情")
    @RequiresRoles(value = {RoleIdentity.ADMIN_ROLE, RoleIdentity.MARKET_ROLE, RoleIdentity.DATA_REPORT_ROLE}, logical = Logical.OR)
    public ResponseData<UserDetailInfoDto> userDetail(@PathVariable long userId) {
        UserDetailInfoDto userDetailInfoDto = this.userService.getUserDetail(userId);
        if (userDetailInfoDto == null) {
            return new ResponseData<>(ResponseDataCode.STATUS_NOT_FOUND, "用户不存在", null);
        } else {
            return new ResponseData<>(ResponseDataCode.STATUS_NORMAL, "", userDetailInfoDto);
        }
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


    //admin

    @RequiresAuthentication
    @RequestMapping(value = "/dbj/search", method = RequestMethod.POST)
    @ApiOperation(value = "搜索用户")
    @RequiresRoles(value = {RoleIdentity.ADMIN_ROLE, RoleIdentity.MARKET_ROLE, RoleIdentity.DATA_REPORT_ROLE}, logical = Logical.OR)
    public ResponsePageInfoData<List<UserDetailInfoDto>> users(@RequestBody UserSearchForAdInput input,
                                                               @RequestParam(value = "pageNo", required = true, defaultValue = "1") int pageNo,
                                                               @RequestParam(value = "rows", required = true, defaultValue = "30") int rows) {
        long userId = JWTUtil.getCurrentId();
        List<String> roles = new ArrayList<>(Arrays.asList(this.tokenCenterManager.fetchUser(String.valueOf(userId)).getData().getRoles()));
        boolean flag = false;
        for (String role : roles) {
            if ("datareport".equals(role)) {
                flag = true;
            }
        }
        Page<UserDetailInfoDto> pageInfo = PageHelper.startPage(pageNo, rows);
        List<UserDetailInfoDto> userModelList = this.userService.search(input, flag);
        if (flag) {
            if (pageNo < 10) {
                userModelList = this.userService.searchTopFake((pageNo - 1) * rows, rows);
            }
            return new ResponsePageInfoData<>(ResponseDataCode.STATUS_NORMAL,
                    "", userModelList, pageInfo.getTotal());
        } else {
            return new ResponsePageInfoData<>(ResponseDataCode.STATUS_NORMAL,
                    "", userModelList, pageInfo.getTotal());
        }


    }

    @RequiresAuthentication
    @RequestMapping(value = "/dbj/search/market", method = RequestMethod.POST)
    @ApiOperation(value = "搜索运营用户")
    @RequiresRoles(value = {RoleIdentity.ADMIN_ROLE, RoleIdentity.MARKET_ROLE}, logical = Logical.OR)
    public ResponsePageInfoData<List<UserDetailInfoDto>> marketListAd(@RequestBody AdMarketUserInput input,
                                                                      @RequestParam(value = "pageNo", required = true, defaultValue = "1") int pageNo,
                                                                      @RequestParam(value = "rows", required = true, defaultValue = "30") int rows) {
        Page<UserDetailInfoDto> pageInfo = PageHelper.startPage(pageNo, rows);
        List<UserDetailInfoDto> userModelList = this.userService.marketListAd(input);
        return new ResponsePageInfoData<>(ResponseDataCode.STATUS_NORMAL,
                "", userModelList, pageInfo.getTotal());
    }

    @RequiresAuthentication
    @RequestMapping(value = "/dbj/market/new", method = RequestMethod.POST)
    @ApiOperation(value = "新建运营用户")
    @RequiresRoles(value = RoleIdentity.ADMIN_ROLE)
    public ResponseData<Long> newMarketAd(@RequestBody AdNewMarketInput input) {
        ServiceStatusInfo<Long> statusInfo = this.userService.newMarketAd(input);
        if (statusInfo.isSuccess()) {
            return new ResponseData<>(ResponseDataCode.STATUS_NORMAL, "", statusInfo.getData());
        }
        return new ResponseData<>(ResponseDataCode.STATUS_ERROR, statusInfo.getMsg(), null);
    }

    @RequiresAuthentication
    @ApiOperation(value = "锁定用户")
    @RequestMapping(value = "/dbj/lock", method = RequestMethod.POST)
    @RequiresRoles(value = {RoleIdentity.ADMIN_ROLE})
    public ResponseData<Object> lock(@RequestBody ResourceOpenInput<Long> input) {
        ServiceStatusInfo<Object> statusInfo = this.userService.lock(input);
        if (statusInfo.isSuccess()) {
            return new ResponseData<>(ResponseDataCode.STATUS_NORMAL, "", null);
        }
        return new ResponseData<>(ResponseDataCode.STATUS_ERROR, "操作失败", null);
    }

    @RequiresAuthentication
    @ApiOperation(value = "认证，可以进行直播")
    @RequestMapping(value = "/dbj/review", method = RequestMethod.POST)
    @RequiresRoles({RoleIdentity.ADMIN_ROLE})
    public ResponseData<Object> review(@RequestBody ResourceOpenInput<Long> input) {
        ServiceStatusInfo<Object> statusInfo = this.userService.review(input);
        if (statusInfo.isSuccess()) {
            return new ResponseData<>(ResponseDataCode.STATUS_NORMAL, "", null);
        }
        return new ResponseData<>(ResponseDataCode.STATUS_ERROR, "", null);
    }

    @RequiresAuthentication
    @RequestMapping(value = "/dbj/create", method = RequestMethod.POST)
    @ApiOperation(value = "创建管理用户")
    @RequiresRoles({RoleIdentity.ADMIN_ROLE})
    public ResponseData<Long> create(@RequestBody CreateUserInput input) {
        ServiceStatusInfo<Long> statusInfo = this.userService.createUser(input);
        if (statusInfo.isSuccess()) {
            return new ResponseData<>(ResponseDataCode.STATUS_NORMAL, "", statusInfo.getData());
        }
        return new ResponseData<>(ResponseDataCode.STATUS_ERROR, statusInfo.getMsg(), null);
    }
    @RequestMapping(value = "/sendPhoneCode", method = RequestMethod.GET)
    @ApiOperation(value = "获取手机验证码")
    public ResponseData<Map<String, String>> sendPhoneCode(@RequestParam("phone") String phone) {
        ServiceStatusInfo<Object> info = this.sendSmsService.sendCode(phone);
        if (info.isSuccess()) {
            if (this.appSettingConfigs.getSmsSendConfigs().isSendOpen()) {
                return new ResponseData<>(ResponseDataCode.STATUS_NORMAL, "发送验证码成功!", null);
            } else {
                Map<String, String> map = new HashMap<>();
                map.put("code", (String) info.getData());
                return new ResponseData<>(ResponseDataCode.STATUS_NORMAL, "发送验证码成功!{" + info.getData() + "}", map);
            }
        }
        return new ResponseData<>(ResponseDataCode.STATUS_ERROR, info.getMsg(), null);
    }

    @RequiresAuthentication
    @RequiresRoles({RoleIdentity.ADMIN_ROLE})
    @RequestMapping(value = "/dbj/setRoles/{userId}", method = RequestMethod.POST)
    @ApiOperation(value = "设置用户的角色")
    public ResponseData<Object> setRolesForUser(@PathVariable long userId, @RequestBody List<EntityKeyModel<String>> roles) {
        if (JWTUtil.getCurrentId() == userId) return new ResponseData<>(ResponseDataCode.STATUS_UNAUTH,
                "不能为管理员设置", null);
        ServiceStatusInfo<Object> statusInfo = this.userService.setRolesForUser(userId, roles);
        if (statusInfo.isSuccess()) {
            return new ResponseData<>(ResponseDataCode.STATUS_NORMAL, "", null);
        }
        return new ResponseData<>(ResponseDataCode.STATUS_ERROR, "", null);
    }

    //查询举报用户
    @RequiresAuthentication
    @RequestMapping(value = "/dbj/search/complainUser", method = RequestMethod.POST)
    @ApiOperation(value = "查询被举报的用户")
    @RequiresRoles(value = {RoleIdentity.ADMIN_ROLE, RoleIdentity.MARKET_ROLE}, logical = Logical.OR)
    public ResponsePageInfoData<List<UserDetailInfoDto>> searchComplainUserListAd(@RequestBody AdUserComplainInput input,
                                                                                  @RequestParam(value = "pageNo", required = true, defaultValue = "1") int pageNo,
                                                                                  @RequestParam(value = "rows", required = true, defaultValue = "13") int rows) {
        Page<UserDetailInfoDto> pageInfo = PageHelper.startPage(pageNo, rows);
        List<UserDetailInfoDto> dtos = this.userService.searchComplainUserListAd(input);
        return new ResponsePageInfoData<>(ResponseDataCode.STATUS_NORMAL, "", dtos, pageInfo.getTotal());
    }

    @RequiresAuthentication
    @RequestMapping(value = "/dbj/{id}/doUComplainInfo", method = RequestMethod.GET)
    @ApiOperation(value = "查询举报的详细信息")
    @RequiresRoles(value = {RoleIdentity.ADMIN_ROLE, RoleIdentity.MARKET_ROLE}, logical = Logical.OR)
    public ResponsePageInfoData<List<AdUserComplainInfoDto>> userComplainInfoListAd(@PathVariable Long id,
                                                                                    @RequestParam(value = "pageNo", required = true, defaultValue = "1") int pageNo,
                                                                                    @RequestParam(value = "rows", required = true, defaultValue = "13") int rows) {
        Page<UserDetailInfoDto> pageInfo = PageHelper.startPage(pageNo, rows);
        List<AdUserComplainInfoDto> dtos = this.userService.userComplainInfoListAd(id);
        return new ResponsePageInfoData<>(ResponseDataCode.STATUS_NORMAL, "", dtos, pageInfo.getTotal());
    }

    @RequiresAuthentication
    @RequestMapping(value = "/dbj/{id}/doUComplainInfo", method = RequestMethod.POST)
    @ApiOperation("被举报用户处理方式")
    @RequiresRoles(RoleIdentity.ADMIN_ROLE)
    public ResponseData<Long> doUComplainInfoAd(@PathVariable("id") Long toResId,
                                                @RequestBody AdDoUserComplainInput input) {
        ServiceStatusInfo<Long> statusInfo = this.userService.doUComplainInfoAd(toResId, input);
        if (statusInfo.isSuccess()) {
            return new ResponseData<>(ResponseDataCode.STATUS_NORMAL, "", statusInfo.getData());
        }
        return new ResponseData<>(ResponseDataCode.STATUS_ERROR, statusInfo.getMsg(), null);
    }

    @RequiresAuthentication
    @RequestMapping(value = "/dbj/manageUser/list", method = RequestMethod.POST)
    @ApiOperation("账号管理-账号列表")
    @RequiresRoles(value = {RoleIdentity.ADMIN_ROLE, RoleIdentity.MARKET_ROLE, RoleIdentity.FINANCE_ROLE}, logical = Logical.OR)
    public ResponsePageInfoData<List<AdUserDetailInfoDto>> manageUserListAd(@RequestBody AdManageUserInput input,
                                                                            @RequestParam(value = "pageNo", required = true, defaultValue = "1") int pageNo,
                                                                            @RequestParam(value = "rows", required = true, defaultValue = "13") int rows) {

        Page<AdUserDetailInfoDto> pageInfo = PageHelper.startPage(pageNo, rows);
        List<AdUserDetailInfoDto> dtos = this.userService.manageUserListAd(input);
        return new ResponsePageInfoData<>(ResponseDataCode.STATUS_NORMAL, "", dtos, pageInfo.getTotal());
    }

    @RequiresAuthentication
    @RequestMapping(value = "/dbj/manageUser/add", method = RequestMethod.POST)
    @ApiOperation("账号管理-账号新增")
    @RequiresRoles(value = RoleIdentity.ADMIN_ROLE)
    public ResponseData<Long> addManageUserAd(@RequestBody AdNewManageUserInput input) {
        ServiceStatusInfo<Long> statusInfo = this.userService.addManageUserAd(input);
        if (statusInfo.isSuccess()) {
            return new ResponseData<>(ResponseDataCode.STATUS_NORMAL, "", statusInfo.getData());
        }
        return new ResponseData<>(ResponseDataCode.STATUS_ERROR, statusInfo.getMsg(), null);
    }

    @RequiresAuthentication
    @RequestMapping(value = "/dbj/manageUser/modifyPwd/{id}", method = RequestMethod.POST)
    @ApiOperation("账号管理-账号密码修改")
    @RequiresRoles(value = {RoleIdentity.ADMIN_ROLE, RoleIdentity.MARKET_ROLE, RoleIdentity.FINANCE_ROLE}, logical = Logical.OR)
    public ResponseData<Long> modifyPwdAd(@PathVariable Long id,
                                          @RequestBody AdModifyManagerPasswordInput input) {
        ServiceStatusInfo<Long> statusInfo = this.userService.modifyPwdAd(id, input);
        if (statusInfo.isSuccess()) {
            return new ResponseData<>(ResponseDataCode.STATUS_NORMAL, "", statusInfo.getData());
        }
        return new ResponseData<>(ResponseDataCode.STATUS_ERROR, statusInfo.getMsg(), null);
    }
    @RequiresAuthentication
    @RequestMapping(value = "/dbj/get/marketUser", method = RequestMethod.GET)
    @ApiOperation(value = "得到一个马甲用户的id")
    @RequiresRoles(value = {RoleIdentity.ADMIN_ROLE, RoleIdentity.MARKET_ROLE}, logical = Logical.OR)
    public ResponseData<Long> getManualUser() {
        ServiceStatusInfo<Long> statusInfo = this.userService.getManualUser();
        if (statusInfo.isSuccess()) {
            return new ResponseData<>(ResponseDataCode.STATUS_NORMAL, "", statusInfo.getData());
        }
        return new ResponseData<>(ResponseDataCode.STATUS_ERROR, statusInfo.getMsg(), null);
    }


}
