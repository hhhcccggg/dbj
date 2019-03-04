package com.zwdbj.server.adminserver.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zwdbj.server.adminserver.identity.RoleIdentity;
import com.zwdbj.server.adminserver.model.EntityKeyModel;
import com.zwdbj.server.adminserver.service.user.service.UserService;
import com.zwdbj.server.adminserver.service.video.model.*;
import com.zwdbj.server.adminserver.service.video.service.VideoService;
import com.zwdbj.server.adminserver.service.youzan.model.YZItemDto;
import com.zwdbj.server.adminserver.service.youzan.model.YZSearchItemInput;
import com.zwdbj.server.adminserver.service.youzan.service.YouZanService;
import com.zwdbj.server.probuf.middleware.mq.QueueWorkInfoModel;
import com.zwdbj.server.tokencenter.TokenCenterManager;
import com.zwdbj.server.utility.common.shiro.JWTUtil;
import com.zwdbj.server.basemodel.model.ResponseData;
import com.zwdbj.server.basemodel.model.ResponseDataCode;
import com.zwdbj.server.basemodel.model.ResponsePageInfoData;
import com.zwdbj.server.basemodel.model.ServiceStatusInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/video")
@Api(description = "短视频相关", value = "Video")
public class VideoController {
    @Autowired
    private VideoService videoService;
    @Autowired
    private YouZanService youZanService;
    @Autowired
    StringRedisTemplate stringRedisTemplate;
    @Autowired
    UserService userService;
    @Autowired
    private TokenCenterManager tokenCenterManager;
    private Logger logger = LoggerFactory.getLogger(VideoController.class);

    @RequestMapping(value = "/publish", method = RequestMethod.POST)
    @ApiOperation(value = "发布短视频")
    @RequiresAuthentication
    public ResponseData<Map<String, String>> publishVideo(@RequestBody VideoPublishInput input) {
        long videoId = videoService.publicVideo(input);
        if (videoId <= 0) {
            return new ResponseData<>(ResponseDataCode.STATUS_ERROR, "发布视频失败", null);
        }
        Map<String, String> dataMap = new HashMap<>();
        dataMap.put("id", String.valueOf(videoId));
        return new ResponseData<>(ResponseDataCode.STATUS_NORMAL, "", dataMap);
    }


    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "获取视频详情")
    public ResponseData<VideoDetailInfoDto> video(@PathVariable long id) {
        VideoDetailInfoDto videoInfoDto = this.videoService.video(id);
        this.videoService.updatePlayCount(id);
        return new ResponseData<>(ResponseDataCode.STATUS_NORMAL, "", videoInfoDto);
    }
    @RequestMapping(value = "/findVideos/ids", method = RequestMethod.GET)
    @ApiOperation(value = "获取视频指定id的视频")
    public ResponsePageInfoData<List<VideoInfoDto>> getHomeVideos(@RequestParam String videoIds) {
        ServiceStatusInfo<List<VideoInfoDto>> videoInfoDtos = this.videoService.getHomeVideos(videoIds);
        return new ResponsePageInfoData<>(ResponseDataCode.STATUS_NORMAL, "", videoInfoDtos.getData(),videoInfoDtos.getData().size());
    }

    @RequestMapping(value = "/videoTipDetails/{videoId}", method = RequestMethod.GET)
    @ApiModelProperty(value = "获取视频打赏金币详情")
    public ResponsePageInfoData<List<VideoTipDetails>> videoTipDetails(@PathVariable("videoId") Long videoId,
                                                                       @RequestParam(value = "pageNo", required = true, defaultValue = "1") int pageNo,
                                                                       @RequestParam(value = "rows", required = true, defaultValue = "13") int rows) {
        ServiceStatusInfo<List<VideoTipDetails>> statusInfo = this.videoService.getVideoTipDetails(videoId);
        PageHelper.startPage(pageNo, rows);
        List<VideoTipDetails> result = statusInfo.getData();
        PageInfo<VideoTipDetails> pageInfo = new PageInfo<>(result);
        return new ResponsePageInfoData<>(ResponseDataCode.STATUS_NORMAL, statusInfo.getMsg(), result, pageInfo.getTotal());
    }

    @RequestMapping(value = "/goods/{videoId}", method = RequestMethod.GET)
    @ApiOperation(value = "获取视频关联的商品列表")
    public ResponsePageInfoData<List<YZItemDto>> goods(@PathVariable long videoId,
                                                       @RequestParam(value = "pageNo", required = true, defaultValue = "1") int pageNo,
                                                       @RequestParam(value = "rows", required = true, defaultValue = "13") int rows) {
        ServiceStatusInfo<EntityKeyModel<String>> serviceStatusInfo = this.videoService.getGoodsAd(videoId);
        if (!serviceStatusInfo.isSuccess()) {
            return new ResponsePageInfoData<>(ResponseDataCode.STATUS_ERROR, "没有商品信息", null, 0);
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
    @RequestMapping(value = "/dbj/saveGoods/{videoId}", method = RequestMethod.POST)
    @ApiOperation("为短视频关联商品.多个商品ID用,号隔开")
    @RequiresRoles(value = {RoleIdentity.ADMIN_ROLE, RoleIdentity.MARKET_ROLE}, logical = Logical.OR)
    public ResponseData<Object> saveGoodsAd(@PathVariable long videoId, @RequestBody String goods) {
        ServiceStatusInfo<Object> statusInfo = this.videoService.saveGoodsAd(videoId, goods);
        if (statusInfo.isSuccess()) {
            return new ResponseData<>(ResponseDataCode.STATUS_NORMAL, "", null);
        }
        return new ResponseData<>(ResponseDataCode.STATUS_ERROR, "保存失败", null);
    }
    @RequiresAuthentication
    @RequestMapping(value = "/dbj/addTag/{videoId}", method = RequestMethod.POST)
    @ApiOperation("为短视频关联主题")
    @RequiresRoles(value = {RoleIdentity.ADMIN_ROLE, RoleIdentity.MARKET_ROLE}, logical = Logical.OR)
    public ResponseData<Object> addTagForVideo(@PathVariable long videoId, @RequestBody VideoAddTagInput input) {
        ServiceStatusInfo<Object> statusInfo = this.videoService.addTagForVideo(videoId, input);
        if (statusInfo.isSuccess()) {
            return new ResponseData<>(ResponseDataCode.STATUS_NORMAL, "", null);
        }
        return new ResponseData<>(ResponseDataCode.STATUS_ERROR, "保存失败", null);
    }


    @RequiresAuthentication
    @RequestMapping(value = "/dbj/search", method = RequestMethod.POST)
    @ApiOperation("搜索短视频")
    @RequiresRoles(value = {RoleIdentity.ADMIN_ROLE, RoleIdentity.MARKET_ROLE, RoleIdentity.DATA_REPORT_ROLE}, logical = Logical.OR)
    public ResponsePageInfoData<List<VideoInfoDto>> searchAd(@RequestBody SearchVideoAdInput input,
                                                             @RequestParam(value = "pageNo", required = true, defaultValue = "1") int pageNo,
                                                             @RequestParam(value = "rows", required = true, defaultValue = "13") int rows) {
        Long id = JWTUtil.getCurrentId();
        List<String> roles = new ArrayList<>(Arrays.asList(this.tokenCenterManager.fetchUser(String.valueOf(id)).getData().getRoles()));
        //
        int videoNum = this.videoService.findAllVideoNum(input);//真实视频数量
        List<VideoInfoDto> videoModelDtos;
        Page<VideoInfoDto> pageInfo;
        boolean flag = false;
        for (String role : roles) {
            if ("datareport".equals(role)) {
                flag = true;
            }
        }
        if (flag) {
            int allNotTrueNum = Integer.valueOf(this.stringRedisTemplate.opsForValue().get("OPERATE_ALL_VIDEO_NUM"));
            if (allNotTrueNum == 0) allNotTrueNum = 13309;


            if (videoNum == 0) {//搜索出的匹配视频数量为0
                pageInfo = PageHelper.startPage(pageNo, rows);
                videoModelDtos = this.videoService.searchAd(input);
                return new ResponsePageInfoData<>(ResponseDataCode.STATUS_NORMAL, "", videoModelDtos, pageInfo.getTotal());
            } else {
                int a = new Double(Math.ceil(videoNum * 1.0 / rows)).intValue();//搜索视频的总页数
                if (pageNo > a) {//当前页数大于总页数
                    if (pageNo % a == 0) {
                        pageNo = a;//显示最后一页的视频
                    } else {
                        pageNo = pageNo % a;
                    }
                }
                pageInfo = PageHelper.startPage(pageNo, rows);
                videoModelDtos = this.videoService.searchAd(input);
                logger.info("pageInfo.getTotal()=" + pageInfo.getTotal());
                if ((input.getKeywords() != null && input.getKeywords().length() != 0) || input.getStatus() == 2)
                    return new ResponsePageInfoData<>(ResponseDataCode.STATUS_NORMAL, "", videoModelDtos, videoNum);
                return new ResponsePageInfoData<>(ResponseDataCode.STATUS_NORMAL, "", videoModelDtos, videoNum + allNotTrueNum);
            }
        } else {
            pageInfo = PageHelper.startPage(pageNo, rows);
            videoModelDtos = this.videoService.searchAd(input);
            return new ResponsePageInfoData<>(ResponseDataCode.STATUS_NORMAL, "", videoModelDtos, videoNum);
        }


    }

    @RequiresAuthentication
    @RequestMapping(value = "/dbj/{id}/vComplains", method = RequestMethod.GET)
    @ApiOperation("视屏详情-举报信息")
    @RequiresRoles(value = {RoleIdentity.ADMIN_ROLE, RoleIdentity.MARKET_ROLE, RoleIdentity.DATA_REPORT_ROLE}, logical = Logical.OR)
    public ResponsePageInfoData<List<AdVideoComplainInfoDto>> complainInfoAd(@PathVariable("id") Long toResId,
                                                                             @RequestParam(value = "pageNo", required = true, defaultValue = "1") int pageNo,
                                                                             @RequestParam(value = "rows", required = true, defaultValue = "13") int rows) {
        Page<AdVideoComplainInfoDto> pageInfo = PageHelper.startPage(pageNo, rows);
        List<AdVideoComplainInfoDto> videoComplainInfos = this.videoService.complainInfoAd(toResId);
        return new ResponsePageInfoData<>(ResponseDataCode.STATUS_NORMAL, "", videoComplainInfos, pageInfo.getTotal());
    }

    @RequiresAuthentication
    @RequestMapping(value = "/dbj/{id}/doVComplainInfo", method = RequestMethod.POST)
    @ApiOperation("被举报视频处理方式")
    @RequiresRoles(value = {RoleIdentity.ADMIN_ROLE, RoleIdentity.MARKET_ROLE}, logical = Logical.OR)
    public ResponseData<Long> doComplainInfoAd(@PathVariable("id") Long toResId,
                                               @RequestBody AdVideoDoComplainInput input) {
        ServiceStatusInfo<Long> statusInfo = this.videoService.doComplainInfoAd(toResId, input);
        if (statusInfo.isSuccess()) {
            return new ResponseData<>(ResponseDataCode.STATUS_NORMAL, "", statusInfo.getData());
        }
        return new ResponseData<>(ResponseDataCode.STATUS_ERROR, statusInfo.getMsg(), null);
    }

    /**
     * 缺少审核人员和审核日期字段
     *
     * @param input
     * @param pageNo
     * @param rows
     * @return
     */
    @RequiresAuthentication
    @RequestMapping(value = "/dbj/vVerify", method = RequestMethod.POST)
    @ApiOperation("查询审核视频")
    @RequiresRoles(value = {RoleIdentity.ADMIN_ROLE, RoleIdentity.MARKET_ROLE}, logical = Logical.OR)
    public ResponsePageInfoData<List<AdVideoVerityInfoDto>> verityListAd(@RequestBody AdVideoVerityInfoInput input,
                                                                         @RequestParam(value = "pageNo", required = true, defaultValue = "1") int pageNo,
                                                                         @RequestParam(value = "rows", required = true, defaultValue = "13") int rows) {
        Page<AdVideoVerityInfoDto> pageInfo = PageHelper.startPage(pageNo, rows);
        List<AdVideoVerityInfoDto> verityVideos = this.videoService.verityListAd(input);
        return new ResponsePageInfoData<>(ResponseDataCode.STATUS_NORMAL, "", verityVideos, pageInfo.getTotal());
    }

    @RequiresAuthentication
    @RequestMapping(value = "/dbj/vVerify/{id}", method = RequestMethod.POST)
    @ApiOperation("审核视频")
    @RequiresRoles(value = {RoleIdentity.ADMIN_ROLE, RoleIdentity.MARKET_ROLE}, logical = Logical.OR)
    public ResponseData<Long> verityAd(@PathVariable("id") Long id,
                                       @RequestBody AdVideoVerityInput input) {
        ServiceStatusInfo<Long> statusInfo = this.videoService.verityAd(id, input);
        if (statusInfo.isSuccess()) {
            return new ResponseData<>(ResponseDataCode.STATUS_NORMAL, "", statusInfo.getData());
        }
        return new ResponseData<>(ResponseDataCode.STATUS_ERROR, statusInfo.getMsg(), null);
    }

    @RequiresAuthentication
    @RequestMapping(value = "/dbj/vComplains", method = RequestMethod.POST)
    @ApiOperation("查询搜索的被举报视频")
    @RequiresRoles(value = {RoleIdentity.ADMIN_ROLE, RoleIdentity.MARKET_ROLE}, logical = Logical.OR)
    public ResponsePageInfoData<List<VideoInfoDto>> complainVideosAd(@RequestBody AdVideoSearchComplainInput input,
                                                                     @RequestParam(value = "pageNo", required = true, defaultValue = "1") int pageNo,
                                                                     @RequestParam(value = "rows", required = true, defaultValue = "13") int rows) {
        Page<VideoInfoDto> pageInfo = PageHelper.startPage(pageNo, rows);
        List<VideoInfoDto> videoInfoDtos = this.videoService.complainVideosAd(input);
        return new ResponsePageInfoData<>(ResponseDataCode.STATUS_NORMAL, "", videoInfoDtos, pageInfo.getTotal());
    }

    @RequiresAuthentication
    @RequestMapping(value = "/dbj/recVideos", method = RequestMethod.GET)
    @ApiOperation("保存每天推荐的视频")
    @RequiresRoles(value = {RoleIdentity.ADMIN_ROLE, RoleIdentity.MARKET_ROLE}, logical = Logical.OR)
    public ResponseData<Object> recVideosEveryday(String videoIds) {
        this.videoService.recVideosEveryday(videoIds);
        return new ResponseData<>(ResponseDataCode.STATUS_NORMAL, "", null);

    }

    @RequiresAuthentication
    @RequestMapping(value = "/dbj/weightVideos", method = RequestMethod.POST)
    @ApiOperation("保存视频推荐的权重")
    @RequiresRoles(value = {RoleIdentity.ADMIN_ROLE, RoleIdentity.MARKET_ROLE}, logical = Logical.OR)
    public ResponseData<Object> weightVideos(AdVideoWeightInput input) {
        this.videoService.weightVideos(input);
        return new ResponseData<>(ResponseDataCode.STATUS_NORMAL, "", null);
    }

    @RequiresAuthentication
    @RequestMapping(value = "/dbj/getVideosWeight", method = RequestMethod.POST)
    @ApiOperation("查看视频推荐的权重")
    @RequiresRoles(value = {RoleIdentity.ADMIN_ROLE, RoleIdentity.MARKET_ROLE}, logical = Logical.OR)
    public ResponseData<AdVideoWeightInput> getVideosWeight() {
        ServiceStatusInfo<AdVideoWeightInput> statusInfo = this.videoService.getVideosWeight();
        if (statusInfo.isSuccess()) {
            return new ResponseData<>(ResponseDataCode.STATUS_NORMAL, "", statusInfo.getData());
        } else {
            return new ResponseData<>(ResponseDataCode.STATUS_ERROR, statusInfo.getMsg(), null);
        }
    }

    @PostMapping(value = "/dbj/getSuperStarViodes/{userId}")
    @ApiOperation(value = "获取代言人视频详情")
    public ResponsePageInfoData<List<SuperStarVideos>> getSuperStarViodes(@RequestParam(value = "pageNo", defaultValue = "1", required = true) int pageNo,
                                                                          @RequestParam(value = "rows", defaultValue = "10", required = true) int rows,
                                                                          @RequestParam(value = "rank", defaultValue = "createTime", required = true) String rank,
                                                                          @RequestParam(value = "sort", defaultValue = "desc", required = true) String sort,
                                                                          @PathVariable long userId) {
        PageHelper.startPage(pageNo, rows);
        List<SuperStarVideos> result = videoService.searchSuperStarVideos(userId, rank, sort).getData();
        PageInfo<SuperStarVideos> pageInfo = new PageInfo<>(result);
        return new ResponsePageInfoData<>(0, "", pageInfo.getList(), pageInfo.getTotal());


    }

    @GetMapping("updateVideoEs")
    @ApiOperation(value = "",hidden = true)
    public String updateVideoEs(long id,int type){
        QueueWorkInfoModel.QueueWorkVideoInfo.OperationEnum operationEnum = null;
        if(type == 1){
            operationEnum = QueueWorkInfoModel.QueueWorkVideoInfo.OperationEnum.CREATE;
        }else if(type == 2){
            operationEnum = QueueWorkInfoModel.QueueWorkVideoInfo.OperationEnum.UPDATE;
        }else if(type == 3){
            operationEnum = QueueWorkInfoModel.QueueWorkVideoInfo.OperationEnum.DELETE;
        }
        if(operationEnum != null)
            try{
                videoService.operationByIdES(id,operationEnum);
            }catch (Exception e){
               return e.toString();
            }
        return "11";
    }
}
