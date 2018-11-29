package com.zwdbj.server.mobileapi.controller;

import com.zwdbj.server.utility.model.ResponseData;
import com.zwdbj.server.utility.model.ResponseDataCode;
import com.zwdbj.server.mobileapi.service.category.model.*;
import com.zwdbj.server.mobileapi.service.category.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api(description = "分类")
@RequestMapping("/api/category")
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    @ApiOperation("分类列表")
    @RequestMapping(value = "/search",method = RequestMethod.POST)
    public ResponseData<List<CategoryDto>> search(@RequestBody CategorySearchInput input) {
        List<CategoryDto> dtos = this.categoryService.search(input);
        return new ResponseData<>(ResponseDataCode.STATUS_NORMAL,"",dtos);
    }

}
