package com.zwdbj.server.mobileapi.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zwdbj.server.mobileapi.model.ResponseData;
import com.zwdbj.server.mobileapi.model.ResponseDataCode;
import com.zwdbj.server.mobileapi.service.music.model.*;
import com.zwdbj.server.mobileapi.model.ResponsePageInfoData;
import com.zwdbj.server.mobileapi.service.music.service.MusicService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
}
