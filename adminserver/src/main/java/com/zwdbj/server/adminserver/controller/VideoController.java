package com.zwdbj.server.adminserver.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zwdbj.server.adminserver.identity.RoleIdentity;
import com.zwdbj.server.adminserver.model.*;
import com.zwdbj.server.adminserver.service.ServiceStatusInfo;
import com.zwdbj.server.adminserver.service.video.model.*;
import com.zwdbj.server.adminserver.service.video.service.VideoService;
import com.zwdbj.server.adminserver.service.youzan.model.YZItemDto;
import com.zwdbj.server.adminserver.service.youzan.model.YZSearchItemInput;
import com.zwdbj.server.adminserver.service.youzan.service.YouZanService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/video")
@Api(description = "短视频相关",value = "Video")
public class VideoController {
    @Autowired
    private VideoService videoService;
    @Autowired
    private YouZanService youZanService;

    @RequestMapping(value = "/publish",method = RequestMethod.POST)
    @ApiOperation(value = "发布短视频")
    @RequiresAuthentication
    public ResponseData<Map<String,String>> publishVideo(@RequestBody VideoPublishInput input) {
        long videoId = videoService.publicVideo(input);
        if (videoId<=0) {
            return new ResponseData<>(ResponseDataCode.STATUS_ERROR,"发布视频失败",null);
        }
        Map<String,String> dataMap = new HashMap<>();
        dataMap.put("id",String.valueOf(videoId));
        return new ResponseData<>(ResponseDataCode.STATUS_NORMAL,"",dataMap);
    }
    @RequestMapping(value = "/listHot",method = RequestMethod.GET)
    @ApiOperation(value = "获取短视频推荐列表")
    public ResponsePageInfoData<List<VideoInfoDto>> listHot(@RequestParam(value = "pageNo",required = true,defaultValue = "1") int pageNo,
                                                      @RequestParam(value = "rows",required = true,defaultValue = "30") int rows) {
        Page<VideoInfoDto> pageInfo = PageHelper.startPage(pageNo, rows);
        List<VideoInfoDto> videos = videoService.listHot(pageInfo);
        return new ResponsePageInfoData<>(ResponseDataCode.STATUS_NORMAL,"",videos,pageInfo.getTotal());
    }
    @RequestMapping(value = "/listLatest",method = RequestMethod.GET)
    @ApiOperation(value = "获取短视频最新列表")
    public ResponsePageInfoData<List<VideoInfoDto>> listLatest(@RequestParam(value = "pageNo",required = true,defaultValue = "1") int pageNo,
                                                      @RequestParam(value = "rows",required = true,defaultValue = "30") int rows) {
        Page<VideoInfoDto> pageInfo = PageHelper.startPage(pageNo, rows);
        List<VideoInfoDto> videos = videoService.listLatest(pageInfo);
        return new ResponsePageInfoData<>(ResponseDataCode.STATUS_NORMAL,"",videos,pageInfo.getTotal());
    }

    @RequestMapping(value = "/next",method = RequestMethod.GET)
    @ApiOperation(value = "获取下一波短视频列表，可以用于短视频观看界面，下拉观看下一个短视频场景. id为当面正在播放的视频ID")
    public ResponseData<List<VideoInfoDto>> next(@RequestParam(value = "id") long id) {
        List<VideoInfoDto> videos = videoService.next(id);
        return new ResponseData<>(ResponseDataCode.STATUS_NORMAL,"",videos);
    }

    @RequestMapping(value = "/nearby/{distance}/{longitude}/{latitude}",method = RequestMethod.GET)
    @ApiOperation(value = "获取附近的视频")
    public ResponsePageInfoData<List<VideoInfoDto>> nearBy(@PathVariable(required = true) float distance,
                                                   @PathVariable(required = true) double longitude,
                                                   @PathVariable(required = true) double latitude,
                                                   @RequestParam(value = "pageNo",required = true,defaultValue = "1") int pageNo,
                                                   @RequestParam(value = "rows",required = true,defaultValue = "30") int rows) {
        Page<VideoInfoDto> pageInfo = PageHelper.startPage(pageNo,rows);
        List<VideoInfoDto> videoInfoDtos = this.videoService.nearby(longitude,latitude,100000);
        return new ResponsePageInfoData<>(ResponseDataCode.STATUS_NORMAL,"",videoInfoDtos,pageInfo.getTotal());
    }


    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    @ApiOperation(value = "获取视频详情")
    public ResponseData<VideoDetailInfoDto> video(@PathVariable long id) {
        VideoDetailInfoDto videoInfoDto = this.videoService.video(id);
        if (videoInfoDto!=null&&videoInfoDto.getStatus()!=0) {
            return new ResponseData<>(ResponseDataCode.STATUS_NOT_FOUND,"视频未审核通过或者正在审核中...",null);
        }
        this.videoService.updatePlayCount(id);
        return new ResponseData<>(ResponseDataCode.STATUS_NORMAL,"",videoInfoDto);
    }
    @RequestMapping(value = "/shared/{id}",method = RequestMethod.GET)
    @ApiOperation(value = "视频分享成功回调")
    public ResponseData<Object> shared(@PathVariable long id) {
        this.videoService.updateShareCount(id);
        return new ResponseData<>(ResponseDataCode.STATUS_NORMAL,"",null);
    }


    @RequiresAuthentication
    @RequestMapping(value = "/listByUserFollowed/{userId}",method = RequestMethod.GET)
    @ApiOperation(value = "获取我关注的用户的视频列表")
    public ResponsePageInfoData<List<VideoInfoDto>> listByUserFollowed(@RequestParam(value = "pageNo",required = true,defaultValue = "1") int pageNo,
                                                                       @RequestParam(value = "rows",required = true,defaultValue = "30") int rows,
                                                                       @PathVariable long userId) {
        Page<VideoInfoDto> pageInfo = PageHelper.startPage(pageNo, rows);
        List<VideoInfoDto> dtos = this.videoService.listByUserFollowed(userId);
        return new ResponsePageInfoData<>(ResponseDataCode.STATUS_NORMAL,"",dtos,pageInfo.getTotal());
    }
    @RequestMapping(value = "/videosByUser/{userId}",method = RequestMethod.GET)
    @ApiOperation(value = "获取用户发布的视频列表")
    public ResponsePageInfoData<List<VideoInfoDto>> videosByUser(@PathVariable long userId,
                                                                 @RequestParam(value = "pageNo",required = true,defaultValue = "1") int pageNo,
                                                                 @RequestParam(value = "rows",required = true,defaultValue = "30") int rows) {
        Page<VideoInfoDto> pageInfo = PageHelper.startPage(pageNo,rows);
        List<VideoInfoDto> dtos = this.videoService.videosByUser(userId);
        return new ResponsePageInfoData<>(ResponseDataCode.STATUS_NORMAL,"",dtos,pageInfo.getTotal());
    }
    @RequestMapping(value = "/videosByUserHeart/{userId}",method = RequestMethod.GET)
    @ApiOperation(value = "获取用户点赞过的视频列表")
    public ResponsePageInfoData<List<VideoInfoDto>> videosByUserHeart(@PathVariable long userId,
                                                                      @RequestParam(value = "pageNo",required = true,defaultValue = "1") int pageNo,
                                                                      @RequestParam(value = "rows",required = true,defaultValue = "30") int rows) {
        Page<VideoInfoDto> pageInfo = PageHelper.startPage(pageNo,rows);
        List<VideoInfoDto> dtos = this.videoService.videosByHearted(userId);
        return new ResponsePageInfoData<>(ResponseDataCode.STATUS_NORMAL,"",dtos,pageInfo.getTotal());
    }

    @RequestMapping(value = "/goods/{videoId}",method = RequestMethod.GET)
    @ApiOperation(value = "获取视频关联的商品列表")
    public ResponsePageInfoData<List<YZItemDto>> goods(@PathVariable long videoId,
                                                       @RequestParam(value = "pageNo",required = true,defaultValue = "1") int pageNo,
                                                       @RequestParam(value = "rows",required = true,defaultValue = "13") int rows) {
        ServiceStatusInfo<EntityKeyModel<String>> serviceStatusInfo = this.videoService.getGoodsAd(videoId);
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


    //admin
    @RequiresAuthentication
    @RequestMapping(value = "/dbj/saveGoods/{videoId}",method = RequestMethod.POST)
    @ApiOperation("为短视频关联商品.多个商品ID用,号隔开")
    @RequiresRoles(value = {RoleIdentity.ADMIN_ROLE,RoleIdentity.MARKET_ROLE},logical = Logical.OR)
    public ResponseData<Object> saveGoodsAd(@PathVariable long videoId,@RequestBody String goods) {
        ServiceStatusInfo<Object> statusInfo = this.videoService.saveGoodsAd(videoId,goods);
        if (statusInfo.isSuccess()) {
            return new ResponseData<>(ResponseDataCode.STATUS_NORMAL,"",null);
        }
        return new ResponseData<>(ResponseDataCode.STATUS_ERROR,"保存失败",null);
    }


    @RequiresAuthentication
    @RequestMapping(value = "/dbj/search",method = RequestMethod.POST)
    @ApiOperation("搜索短视频")
    @RequiresRoles(value = {RoleIdentity.ADMIN_ROLE,RoleIdentity.MARKET_ROLE},logical = Logical.OR)
    public ResponsePageInfoData<List<VideoInfoDto>> searchAd(@RequestBody SearchVideoAdInput input,
                                                                 @RequestParam(value = "pageNo",required = true,defaultValue = "1") int pageNo,
                                                                 @RequestParam(value = "rows",required = true,defaultValue = "13") int rows) {
        Page<VideoInfoDto> pageInfo = PageHelper.startPage(pageNo,rows);
        List<VideoInfoDto> videoModelDtos = this.videoService.searchAd(input);
        return new ResponsePageInfoData<>(ResponseDataCode.STATUS_NORMAL,"",videoModelDtos,pageInfo.getTotal());
    }
    @RequiresAuthentication
    @RequestMapping(value = "/dbj/{id}/vComplains",method = RequestMethod.GET)
    @ApiOperation("视屏详情-举报信息")
    @RequiresRoles(value = {RoleIdentity.ADMIN_ROLE,RoleIdentity.MARKET_ROLE},logical = Logical.OR)
    public ResponsePageInfoData<List<AdVideoComplainInfoDto>> complainInfoAd(@PathVariable("id") Long toResId,
                                                                     @RequestParam(value = "pageNo",required = true,defaultValue = "1") int pageNo,
                                                                     @RequestParam(value = "rows",required = true,defaultValue = "13") int rows){
        Page<AdVideoComplainInfoDto> pageInfo = PageHelper.startPage(pageNo,rows);
        List<AdVideoComplainInfoDto> videoComplainInfos= this.videoService.complainInfoAd(toResId);
        return new ResponsePageInfoData<>(ResponseDataCode.STATUS_NORMAL,"",videoComplainInfos,pageInfo.getTotal());
    }

    @RequiresAuthentication
    @RequestMapping(value = "/dbj/{id}/doVComplainInfo",method = RequestMethod.POST)
    @ApiOperation("被举报视频处理方式")
    @RequiresRoles(value = {RoleIdentity.ADMIN_ROLE,RoleIdentity.MARKET_ROLE},logical = Logical.OR)
    public ResponseData<Long> doComplainInfoAd(@PathVariable("id")Long toResId,
                                   @RequestBody AdVideoDoComplainInput input){
        ServiceStatusInfo<Long> statusInfo =  this.videoService.doComplainInfoAd(toResId,input);
        if (statusInfo.isSuccess()) {
            return new ResponseData<>(ResponseDataCode.STATUS_NORMAL,"",statusInfo.getData());
        }
        return new ResponseData<>(ResponseDataCode.STATUS_ERROR,statusInfo.getMsg(),null);
    }

    /**
     * 缺少审核人员和审核日期字段
     * @param input
     * @param pageNo
     * @param rows
     * @return
     */
    @RequiresAuthentication
    @RequestMapping(value = "/dbj/vVerify",method = RequestMethod.POST)
    @ApiOperation("查询审核视频")
    @RequiresRoles(value = {RoleIdentity.ADMIN_ROLE,RoleIdentity.MARKET_ROLE},logical = Logical.OR)
    public  ResponsePageInfoData<List<AdVideoVerityInfoDto>> verityListAd(@RequestBody AdVideoVerityInfoInput input,
                                                                          @RequestParam(value = "pageNo",required = true,defaultValue = "1") int pageNo,
                                                                          @RequestParam(value = "rows",required = true,defaultValue = "13") int rows){
        Page<AdVideoVerityInfoDto> pageInfo = PageHelper.startPage(pageNo,rows);
        List<AdVideoVerityInfoDto> verityVideos = this.videoService.verityListAd(input);
        return new ResponsePageInfoData<>(ResponseDataCode.STATUS_NORMAL,"",verityVideos,pageInfo.getTotal());
    }

    @RequiresAuthentication
    @RequestMapping(value = "/dbj/vVerify/{id}",method = RequestMethod.POST)
    @ApiOperation("审核视频")
    @RequiresRoles(value = {RoleIdentity.ADMIN_ROLE,RoleIdentity.MARKET_ROLE},logical = Logical.OR)
    public ResponseData<Long> verityAd(@PathVariable("id")Long id,
                           @RequestBody AdVideoVerityInput input){
        ServiceStatusInfo<Long> statusInfo = this.videoService.verityAd(id,input);
        if (statusInfo.isSuccess()) {
            return new ResponseData<>(ResponseDataCode.STATUS_NORMAL,"",statusInfo.getData());
        }
        return new ResponseData<>(ResponseDataCode.STATUS_ERROR,statusInfo.getMsg(),null);
    }

    @RequiresAuthentication
    @RequestMapping(value = "/dbj/vComplains",method = RequestMethod.POST)
    @ApiOperation("查询搜索的被举报视频")
    @RequiresRoles(value = {RoleIdentity.ADMIN_ROLE,RoleIdentity.MARKET_ROLE},logical = Logical.OR)
    public ResponsePageInfoData<List<VideoInfoDto>> complainVideosAd(@RequestBody AdVideoSearchComplainInput input,
                                                                     @RequestParam(value = "pageNo",required = true,defaultValue = "1") int pageNo,
                                                                     @RequestParam(value = "rows",required = true,defaultValue = "13") int rows){
        Page<VideoInfoDto> pageInfo = PageHelper.startPage(pageNo,rows);
        List<VideoInfoDto> videoInfoDtos = this.videoService.complainVideosAd(input);
        return new ResponsePageInfoData<>(ResponseDataCode.STATUS_NORMAL,"",videoInfoDtos,pageInfo.getTotal());
    }

    @RequiresAuthentication
    @RequestMapping(value = "/dbj/recVideos",method = RequestMethod.GET)
    @ApiOperation("保存每天推荐的视频")
    @RequiresRoles(value = {RoleIdentity.ADMIN_ROLE,RoleIdentity.MARKET_ROLE},logical = Logical.OR)
    public ResponseData<Object> recVideosEveryday(String videoIds){
        this.videoService.recVideosEveryday(videoIds);
        return new ResponseData<>(ResponseDataCode.STATUS_NORMAL,"",null);

    }
    @RequiresAuthentication
    @RequestMapping(value = "/dbj/weightVideos",method = RequestMethod.POST)
    @ApiOperation("保存视频推荐的权重")
    @RequiresRoles(value = {RoleIdentity.ADMIN_ROLE,RoleIdentity.MARKET_ROLE},logical = Logical.OR)
    public ResponseData<Object> weightVideos(AdVideoWeightInput input){
        this.videoService.weightVideos(input);
        return new ResponseData<>(ResponseDataCode.STATUS_NORMAL,"",null);

    }



}
