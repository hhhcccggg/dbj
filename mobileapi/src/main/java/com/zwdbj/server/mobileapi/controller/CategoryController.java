package com.zwdbj.server.mobileapi.controller;

import com.zwdbj.server.utility.model.ResponseData;
import com.zwdbj.server.utility.model.ResponseDataCode;
import com.zwdbj.server.mobileapi.service.category.model.*;
import com.zwdbj.server.mobileapi.service.category.service.CategoryService;
import com.zwdbj.server.utility.model.ServiceStatusInfo;
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
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public ResponseData<List<CategoryDto>> search(@RequestBody CategorySearchInput input) {
        List<CategoryDto> dtos = this.categoryService.search(input);
        return new ResponseData<>(ResponseDataCode.STATUS_NORMAL, "", dtos);
    }

    @ApiOperation("首页分类")
    @GetMapping("/mainCategory")
    public ResponseData<CategoryMainDto> mainCategory(){
        ServiceStatusInfo<CategoryMainDto> serviceStatusInfo = this.categoryService.mainSelect();
        if(serviceStatusInfo.isSuccess())
            return new ResponseData<>(ResponseDataCode.STATUS_NORMAL, "", serviceStatusInfo.getData());
        return new ResponseData<>(ResponseDataCode.STATUS_ERROR, serviceStatusInfo.getMsg(), null);
    }
    @ApiOperation("商家的服务分类")
    @GetMapping("/business/category")
    public ResponseData<List<CategoryRecommendDto>> categoryRecommends(){
        ServiceStatusInfo<List<CategoryRecommendDto>> serviceStatusInfo = this.categoryService.categoryRecommends();
        if(serviceStatusInfo.isSuccess())
            return new ResponseData<>(ResponseDataCode.STATUS_NORMAL, "", serviceStatusInfo.getData());
        return new ResponseData<>(ResponseDataCode.STATUS_ERROR, serviceStatusInfo.getMsg(), null);
    }



}
