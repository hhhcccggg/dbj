package com.zwdbj.server.mobileapi.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
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
}
