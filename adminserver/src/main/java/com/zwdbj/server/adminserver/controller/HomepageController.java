package com.zwdbj.server.adminserver.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zwdbj.server.adminserver.identity.RoleIdentity;
import com.zwdbj.server.adminserver.service.homepage.model.AdFindHotTagsDto;
import com.zwdbj.server.adminserver.service.homepage.model.AdFindIncreasedDto;
import com.zwdbj.server.adminserver.service.homepage.model.AdFindIncreasedInput;
import com.zwdbj.server.adminserver.service.homepage.model.AdUserOrVideoGrowthDto;
import com.zwdbj.server.adminserver.service.homepage.service.HomepageService;
import com.zwdbj.server.utility.model.ResponseData;
import com.zwdbj.server.utility.model.ResponseDataCode;
import com.zwdbj.server.utility.model.ResponsePageInfoData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/api/homepage")
@Api(description = "后台首页相关", value = "homepage")
public class HomepageController {

    @Autowired
    private HomepageService homepageService;

    @RequiresAuthentication
    @RequestMapping(value = "/dbj/findNewNum", method = RequestMethod.POST)
    @ApiOperation("新增加的用户和短视频数量")
    @RequiresRoles(value = {RoleIdentity.ADMIN_ROLE, RoleIdentity.MARKET_ROLE, RoleIdentity.DATA_REPORT_ROLE}, logical = Logical.OR)
    public ResponseData<AdFindIncreasedDto> findIncreasedAd(@RequestBody AdFindIncreasedInput input) {
        AdFindIncreasedDto dto = this.homepageService.findIncreasedAd(input);
        return new ResponseData<>(ResponseDataCode.STATUS_NORMAL, "", dto);
    }

    @RequiresAuthentication
    @RequestMapping(value = "/dbj/userGrowth", method = RequestMethod.POST)
    @ApiOperation("用户增长趋势")
    @RequiresRoles(value = {RoleIdentity.ADMIN_ROLE, RoleIdentity.MARKET_ROLE, RoleIdentity.DATA_REPORT_ROLE}, logical = Logical.OR)
    public ResponsePageInfoData<List<AdUserOrVideoGrowthDto>> userGrowthAd(@RequestBody AdFindIncreasedInput input,
                                                                           @RequestParam(value = "pageNo", required = true, defaultValue = "1") int pageNo,
                                                                           @RequestParam(value = "rows", required = true, defaultValue = "31") int rows) {
        Page<AdUserOrVideoGrowthDto> pageInfo = PageHelper.startPage(pageNo, rows);
        List<AdUserOrVideoGrowthDto> dtos = this.homepageService.userGrowthAd(input);
        System.out.println(dtos);
        return new ResponsePageInfoData<>(ResponseDataCode.STATUS_NORMAL, "", dtos, pageInfo.getTotal());
    }

    @RequestMapping(value = "/dbj/userGrowthExcel", method = RequestMethod.GET)
    @RequiresAuthentication
    @RequiresRoles(value = {RoleIdentity.ADMIN_ROLE, RoleIdentity.MARKET_ROLE}, logical = Logical.OR)
    @ApiOperation(value = "导出用户增长趋势excel表")
    public void userGrowthExcel(@RequestParam("quantumTime") int quantumTime, HttpServletResponse response) {
        AdFindIncreasedInput adFindIncreasedInput = new AdFindIncreasedInput();
        adFindIncreasedInput.setQuantumTime(quantumTime);
        adFindIncreasedInput.setTof(false);
        homepageService.userGrowthAdExcel(adFindIncreasedInput, response);


    }

    @RequiresAuthentication
    @RequestMapping(value = "/dbj/videoGrowth", method = RequestMethod.POST)
    @ApiOperation("短视频增长趋势")
    @RequiresRoles(value = {RoleIdentity.ADMIN_ROLE, RoleIdentity.MARKET_ROLE, RoleIdentity.DATA_REPORT_ROLE}, logical = Logical.OR)
    public ResponsePageInfoData<List<AdUserOrVideoGrowthDto>> videoGrowthAd(@RequestBody AdFindIncreasedInput input,
                                                                            @RequestParam(value = "pageNo", required = true, defaultValue = "1") int pageNo,
                                                                            @RequestParam(value = "rows", required = true, defaultValue = "31") int rows) {
        Page<AdUserOrVideoGrowthDto> pageInfo = PageHelper.startPage(pageNo, rows);
        List<AdUserOrVideoGrowthDto> dtos = this.homepageService.videoGrowthAd(input);
        return new ResponsePageInfoData<>(ResponseDataCode.STATUS_NORMAL, "", dtos, pageInfo.getTotal());
    }

    @RequestMapping(value = "/dbj/videoGrowthExcel", method = RequestMethod.GET)
    @RequiresAuthentication
    @RequiresRoles(value = {RoleIdentity.ADMIN_ROLE, RoleIdentity.MARKET_ROLE}, logical = Logical.OR)
    @ApiOperation(value = "导出短视频增长趋势excel表")
    public void videoGrowthExcel(@RequestParam("quantumTime") int quantumTime, HttpServletResponse response) {
        AdFindIncreasedInput adFindIncreasedInput = new AdFindIncreasedInput();
        adFindIncreasedInput.setQuantumTime(quantumTime);
        adFindIncreasedInput.setTof(false);
        homepageService.videoGrowthAdExcel(adFindIncreasedInput, response);

    }

    @RequiresAuthentication
    @RequestMapping(value = "/dbj/hotTags", method = RequestMethod.GET)
    @ApiOperation("热门标签")
    @RequiresRoles(value = {RoleIdentity.ADMIN_ROLE, RoleIdentity.MARKET_ROLE, RoleIdentity.DATA_REPORT_ROLE}, logical = Logical.OR)
    public ResponsePageInfoData<List<AdFindHotTagsDto>> findHotTagsAd(@RequestParam(value = "pageNo", required = true, defaultValue = "1") int pageNo,
                                                                      @RequestParam(value = "rows", required = true, defaultValue = "20") int rows) {
        Page<AdFindHotTagsDto> pageInfo = PageHelper.startPage(pageNo, rows);
        List<AdFindHotTagsDto> dtos = this.homepageService.findHotTags();
        return new ResponsePageInfoData<>(ResponseDataCode.STATUS_NORMAL, "", dtos, pageInfo.getTotal());
    }


}
