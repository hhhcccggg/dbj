package com.zwdbj.server.adminserver.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zwdbj.server.adminserver.identity.RoleIdentity;
import com.zwdbj.server.basemodel.model.ResponseData;
import com.zwdbj.server.basemodel.model.ResponseDataCode;
import com.zwdbj.server.adminserver.service.music.model.*;
import com.zwdbj.server.basemodel.model.ResponsePageInfoData;
import com.zwdbj.server.adminserver.service.music.service.MusicService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/music")
@Api(description = "音乐资源相关",value = "Music")
public class MusicController {
    @Autowired
    private MusicService musicService;
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    @ApiOperation(value = "获取音乐列表")
    public ResponsePageInfoData<List<MusicDto>> list(@RequestParam(value = "pageNo",required = true,defaultValue = "1") int pageNo,
                                               @RequestParam(value = "rows",required = true,defaultValue = "30") int rows) {
        Page<MusicModel> pageInfo = PageHelper.startPage(pageNo, rows);
        List<MusicModel> musics = musicService.list();
        List<MusicDto> musicDtos = new ModelMapper().map(musics,new TypeToken<List<MusicDto>>(){}.getType());
        return new ResponsePageInfoData<List<MusicDto>>(ResponseDataCode.STATUS_NORMAL,"",musicDtos,pageInfo.getTotal());
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    @ApiOperation(value = "获取音乐详情")
    public ResponseData<MusicDto> get(@PathVariable long id) {
        MusicDto musicDto = this.musicService.get(id);
        if (musicDto == null) {
            return new ResponseData<>(ResponseDataCode.STATUS_NOT_FOUND,"音乐资源未找到",null);
        } else  {
            return new ResponseData<>(ResponseDataCode.STATUS_NORMAL,"",musicDto);
        }
    }

    @RequiresAuthentication
    @RequestMapping(value = "/dbj/videoMusic",method = RequestMethod.POST)
    @ApiOperation("基础信息-视频音乐管理")
    @RequiresRoles(RoleIdentity.ADMIN_ROLE)
    public ResponsePageInfoData<List<AdMusicDto>> videoMusicListAd(@RequestBody AdMusicInput input,
                                                                   @RequestParam(value = "pageNo",required = true,defaultValue = "1") int pageNo,
                                                                   @RequestParam(value = "rows",required = true,defaultValue = "13") int rows){
        Page<AdMusicDto> pageInfo = PageHelper.startPage(pageNo,rows);
        List<AdMusicDto> musicDtos = this.musicService.videoMusicListAd(input);
        return new ResponsePageInfoData<>(ResponseDataCode.STATUS_NORMAL,"",musicDtos,pageInfo.getTotal());
    }

    @RequiresAuthentication
    @RequestMapping(value = "/dbj/videoMusic/add",method = RequestMethod.POST)
    @ApiOperation("基础信息-新建视频音乐")
    @RequiresRoles(RoleIdentity.ADMIN_ROLE)
    public ResponseData<Map<String,String>> addVideoMusicAd(@RequestBody AdNewMusicInput input){
        Long musicId = this.musicService.addVideoMusicAd(input);
        if (musicId <= 0){
            return new ResponseData<>(ResponseDataCode.STATUS_ERROR,"上传音乐失败",null);
        }
        Map<String,String> dataMap = new HashMap<>();
        dataMap.put("id",String.valueOf(musicId));
        return new ResponseData<>(ResponseDataCode.STATUS_NORMAL,"",dataMap);
    }
}
