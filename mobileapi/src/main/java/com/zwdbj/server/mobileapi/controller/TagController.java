package com.zwdbj.server.mobileapi.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zwdbj.server.utility.model.ResponseData;
import com.zwdbj.server.utility.model.ResponseDataCode;
import com.zwdbj.server.utility.model.ResponsePageInfoData;
import com.zwdbj.server.mobileapi.service.tag.model.*;
import com.zwdbj.server.mobileapi.service.tag.service.TagService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api(description = "标签")
@RequestMapping("/api/tag")
public class TagController {
    @Autowired
    TagService tagService;

    @RequestMapping(value = "/search",method = RequestMethod.POST)
    @ApiOperation(value = "获取标签列表")
    public ResponsePageInfoData<List<TagDto>> search(@RequestBody TagSearchInput input,
                                               @RequestParam(value = "pageNo",defaultValue = "1",required = true) int pageNo,
                                               @RequestParam(value = "rows",defaultValue = "13",required = true) int rows) {
        Page<TagDto> pageInfo = PageHelper.startPage(pageNo,rows);
        List<TagDto> dtos = tagService.search(input);
        return new ResponsePageInfoData<>(ResponseDataCode.STATUS_NORMAL,"",dtos,pageInfo.getTotal());
    }

    @RequestMapping(value = "/hotList",method = RequestMethod.GET)
    @ApiOperation(value = "获取热门标签")
    public ResponsePageInfoData<List<TagDto>> hotTags(@RequestParam(value = "pageNo",defaultValue = "1",required = true) int pageNo,
                                                      @RequestParam(value = "rows",defaultValue = "6",required = true) int rows ){

        Page<TagDto> pageInfo = PageHelper.startPage(pageNo,rows);
        List<TagDto> dtos = tagService.hotTags();
        if (dtos==null)return new ResponsePageInfoData<>(ResponseDataCode.STATUS_NORMAL,"没有数据",null,0);
        return new ResponsePageInfoData<>(ResponseDataCode.STATUS_NORMAL,"",dtos,pageInfo.getTotal());
    }

    @RequestMapping(value = "/listAll",method = RequestMethod.GET)
    @ApiOperation(value = "查询所有主题标签")
    public  ResponsePageInfoData<List<TagDto>> listAll(@RequestParam(value = "pageNo",defaultValue = "1",required = true) int pageNo,
                                                       @RequestParam(value = "rows",defaultValue = "13",required = true) int rows){
        Page<TagDto> pageInfo = PageHelper.startPage(pageNo,rows);
        List<TagDto> dtos = tagService.listAll();
        return new ResponsePageInfoData<>(ResponseDataCode.STATUS_NORMAL,"",dtos,pageInfo.getTotal());
    }
    @RequestMapping(value = "/detail/{id}",method = RequestMethod.GET)
    @ApiOperation(value = "主题详情页")
    public ResponseData<TagDetailDto> tagDetail(@PathVariable long id){
        TagDetailDto tagDetailDto = this.tagService.tagDetail(id);
        return new ResponseData<>(ResponseDataCode.STATUS_NORMAL,"",tagDetailDto);
    }


}
