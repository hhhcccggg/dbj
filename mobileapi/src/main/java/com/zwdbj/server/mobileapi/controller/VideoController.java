package com.zwdbj.server.mobileapi.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zwdbj.server.mobileapi.model.*;
import com.zwdbj.server.utility.model.ServiceStatusInfo;
import com.zwdbj.server.mobileapi.service.video.model.*;
import com.zwdbj.server.mobileapi.service.video.service.VideoService;
import com.zwdbj.server.mobileapi.service.youzan.model.YZItemDto;
import com.zwdbj.server.mobileapi.service.youzan.model.YZSearchItemInput;
import com.zwdbj.server.mobileapi.service.youzan.service.YouZanService;
import com.zwdbj.server.utility.common.shiro.JWTUtil;
import com.zwdbj.server.utility.model.ResponseData;
import com.zwdbj.server.utility.model.ResponseDataCode;
import com.zwdbj.server.utility.model.ResponsePageInfoData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
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
    @RequestMapping(value = "/listHot1",method = RequestMethod.GET)
    @ApiOperation(value = "获取短视频推荐列表(使用此接口)")
    public ResponsePageInfoData<List<VideoInfoDto>> listHot1(@RequestParam(value = "pageNo",required = true,defaultValue = "1") int pageNo) {
        Page<VideoInfoDto> pageInfo = PageHelper.startPage(pageNo, 30);
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

    @RequestMapping(value = "/listByTag",method = RequestMethod.GET)
    @ApiOperation(value = "根据标签获取短视频列表")
    public ResponsePageInfoData<List<VideoInfoDto>> listByTag(@RequestParam String tag,
                                                           @RequestParam(value = "pageNo",required = true,defaultValue = "1") int pageNo,
                                                           @RequestParam(value = "rows",required = true,defaultValue = "30") int rows){
        Page<VideoInfoDto> pageInfo = PageHelper.startPage(pageNo,rows);
        List<VideoInfoDto> videoInfoDtos = this.videoService.listByTag(tag);
        return new ResponsePageInfoData<>(ResponseDataCode.STATUS_NORMAL,"",videoInfoDtos,pageInfo.getTotal());
    }
    @RequestMapping(value = "/listByTag/{id}/{type}",method = RequestMethod.GET)
    @ApiOperation(value = "根据标签获取短视频列表(type的值为0或1,0为按点赞量排序，1为按时间排序)")
    public ResponsePageInfoData<List<VideoInfoDto>> listByTagId(@PathVariable long id,
                                                              @PathVariable int type,
                                                              @RequestParam(value = "pageNo",required = true,defaultValue = "1") int pageNo,
                                                              @RequestParam(value = "rows",required = true,defaultValue = "30") int rows){
        Page<VideoInfoDto> pageInfo = PageHelper.startPage(pageNo,rows);
        List<VideoInfoDto> videoInfoDtos = this.videoService.listByTagId(id,type);
        return new ResponsePageInfoData<>(ResponseDataCode.STATUS_NORMAL,"",videoInfoDtos,pageInfo.getTotal());
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
        if (videoInfoDto ==null) {
            return new ResponseData<>(ResponseDataCode.STATUS_NOT_FOUND,"视频已删除",null);
        }
        if (videoInfoDto.getStatus()!=0) {
            if (videoInfoDto.getUserId()==JWTUtil.getCurrentId()) {
                this.videoService.updatePlayCount(id);
                return new ResponseData<>(ResponseDataCode.STATUS_NORMAL,"视频未审核通过或者正在审核中...",videoInfoDto);
            }
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

    @RequestMapping(value = "/heart",method = RequestMethod.POST)
    @ApiOperation(value = "视频点赞")
    @RequiresAuthentication
    public ResponseData<Object> heart(@RequestBody HeartInput input) {
        ServiceStatusInfo<Object> statusInfo = this.videoService.heart(input);
        if (statusInfo.isSuccess()) {
            return new ResponseData<>(ResponseDataCode.STATUS_NORMAL,statusInfo.getMsg(),null);
        }
        return new ResponseData<>(ResponseDataCode.STATUS_ERROR,statusInfo.getMsg(),null);
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
        ServiceStatusInfo<EntityKeyModel<String>> serviceStatusInfo = this.videoService.getGoods(videoId);
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

    @RequestMapping(value = "/deleteVideo/{id}",method = RequestMethod.GET)
    @ApiOperation(value = "删除视频")
    public ResponseData<Long> deleteVideo(@PathVariable Long id){
        ServiceStatusInfo<Long> statusInfo = this.videoService.deleteVideo(id);
        if (statusInfo.isSuccess()) {
            return new ResponseData<>(ResponseDataCode.STATUS_NORMAL,statusInfo.getMsg(),null);
        }
        return new ResponseData<>(ResponseDataCode.STATUS_ERROR,statusInfo.getMsg(),null);
    }




}
