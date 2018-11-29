package com.zwdbj.server.mobileapi.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zwdbj.server.mobileapi.model.EntityKeyModel;
import com.zwdbj.server.utility.model.ResponseData;
import com.zwdbj.server.utility.model.ResponseDataCode;
import com.zwdbj.server.utility.model.ResponsePageInfoData;
import com.zwdbj.server.mobileapi.service.ServiceStatusInfo;
import com.zwdbj.server.mobileapi.service.living.model.*;
import com.zwdbj.server.mobileapi.service.living.service.LivingService;
import com.zwdbj.server.mobileapi.service.youzan.model.YZItemDto;
import com.zwdbj.server.mobileapi.service.youzan.model.YZSearchItemInput;
import com.zwdbj.server.mobileapi.service.youzan.service.YouZanService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/living")
@Api(description = "直播相关")
public class LivingController {
    @Autowired
    LivingService livingService;
    @Autowired
    private YouZanService youZanService;

    @RequiresAuthentication
    @RequestMapping(value = "/create",method = RequestMethod.POST)
    @ApiOperation(value = "创建直播")
    public ResponseData<LivingInfoDto> create(@RequestBody CreateLivingInput input) {
        ServiceStatusInfo<LivingInfoDto> statusInfo = this.livingService.create(input);
        if (statusInfo.isSuccess()) {
            return new ResponseData<>(ResponseDataCode.STATUS_NORMAL,statusInfo.getMsg(),statusInfo.getData());
        } else
        {
            return new ResponseData<>(ResponseDataCode.STATUS_ERROR,statusInfo.getMsg(),null);
        }
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    @ApiOperation(value = "获取直播详情")
    public ResponseData<LivingDetailInfoDto> get(@PathVariable long id) {
        LivingDetailInfoDto livingInfoDto = this.livingService.get(id);
        if(livingInfoDto == null) {
            return new ResponseData<>(ResponseDataCode.STATUS_NOT_FOUND,"",null);
        }
        return new ResponseData<>(ResponseDataCode.STATUS_NORMAL,"",livingInfoDto);
    }

    @RequestMapping(value = "/user/{userId}",method = RequestMethod.GET)
    @ApiOperation(value = "获取用户上次的直播详情信息，比如意外关闭直播界面，可以调用此接口，重新恢复推流")
    public ResponseData<LivingDetailInfoDto> getByUser(@PathVariable long userId) {
        LivingDetailInfoDto livingInfoDto = this.livingService.getByUserId(userId);
        if(livingInfoDto == null) {
            return new ResponseData<>(ResponseDataCode.STATUS_NOT_FOUND,"",null);
        }
        return new ResponseData<>(ResponseDataCode.STATUS_NORMAL,"",livingInfoDto);
    }

    @RequiresAuthentication
    @RequestMapping(value = "/stop/{id}",method = RequestMethod.GET)
    @ApiOperation(value = "停止直播")
    public ResponseData<Object> stopLiving(@PathVariable long id) {
        ServiceStatusInfo<Object> statusInfo = this.livingService.stopLive(id);
        if (statusInfo.isSuccess()) {
            return new ResponseData<>(ResponseDataCode.STATUS_NORMAL,"",null);
        }
        return new ResponseData<>(ResponseDataCode.STATUS_ERROR,statusInfo.getMsg(),null);
    }

    @RequestMapping(value = "/livingList",method = RequestMethod.GET)
    @ApiOperation(value = "获取当前正在直播的用户")
    public ResponsePageInfoData<List<LivingInfoDto>> livingList(@RequestParam(value = "pageNo",defaultValue = "1",required = true) int pageNo,
                                                                @RequestParam(value = "rows",defaultValue = "30",required = true) int rows) {
        Page<LivingInfoDto> pageInfo = PageHelper.startPage(pageNo, rows);
        List<LivingInfoDto> dtos = this.livingService.livingList();
        return new ResponsePageInfoData<>(ResponseDataCode.STATUS_NORMAL,"",dtos,pageInfo.getTotal());
    }

    @RequiresAuthentication
    @RequestMapping(value = "/livingListByFollowed/{userId}",method = RequestMethod.GET)
    @ApiOperation(value = "我关注的用户的正在直播列表")
    public ResponsePageInfoData<List<LivingInfoDto>> livingListByFollowed(@RequestParam(value = "pageNo",defaultValue = "1",required = true) int pageNo,
                                                                          @RequestParam(value = "rows",defaultValue = "30",required = true) int rows,
                                                                          @PathVariable long userId) {
        Page<LivingInfoDto> pageInfo = PageHelper.startPage(pageNo, rows);
        List<LivingInfoDto> dtos = this.livingService.myFollowedLiving(userId);
        return new ResponsePageInfoData<>(ResponseDataCode.STATUS_NORMAL,"",dtos,pageInfo.getTotal());
    }

    @RequestMapping(value = "/shared/{id}",method = RequestMethod.GET)
    @ApiOperation(value = "分享成功回调")
    public ResponseData<Object> shared(@PathVariable long id) {
        this.livingService.updateShareCount(id);
        return new ResponseData<>(ResponseDataCode.STATUS_NORMAL,"",null);
    }

    @RequiresAuthentication
    @RequestMapping(value = "/saveGoods/{livingId}",method = RequestMethod.POST)
    @ApiOperation("为直播关联商品.多个商品ID用,号隔开")
    public ResponseData<Object> saveGoodsAd(@PathVariable long livingId,@RequestBody String goods) {
        ServiceStatusInfo<Object> statusInfo = this.livingService.saveGoodsAd(livingId,goods);
        if (statusInfo.isSuccess()) {
            return new ResponseData<>(ResponseDataCode.STATUS_NORMAL,"",null);
        }
        return new ResponseData<>(ResponseDataCode.STATUS_ERROR,statusInfo.getMsg(),null);
    }
    @RequestMapping(value = "/goods/{livingId}",method = RequestMethod.GET)
    @ApiOperation(value = "获取关联的商品列表")
    public ResponsePageInfoData<List<YZItemDto>> goods(@PathVariable long livingId,
                                                       @RequestParam(value = "pageNo",required = true,defaultValue = "1") int pageNo,
                                                       @RequestParam(value = "rows",required = true,defaultValue = "13") int rows) {
        ServiceStatusInfo<EntityKeyModel<String>> serviceStatusInfo = this.livingService.getGoodsAd(livingId);
        if (!serviceStatusInfo.isSuccess())  {
            return new ResponsePageInfoData<>(ResponseDataCode.STATUS_ERROR,"没有商品信息",null,0);
        }
        String goods = serviceStatusInfo.getData().getId();
        YZSearchItemInput input = new YZSearchItemInput();
        input.setIds(goods);
        input.setPageNo(pageNo);
        input.setRows(rows);
        ResponsePageInfoData<List<YZItemDto>> dtos = this.youZanService.searchGoods(input);
        return dtos;
    }


}
