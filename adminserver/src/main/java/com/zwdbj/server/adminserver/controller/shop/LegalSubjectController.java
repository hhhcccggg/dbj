package com.zwdbj.server.adminserver.controller.shop;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zwdbj.server.adminserver.service.shop.service.legalSubject.model.*;
import com.zwdbj.server.adminserver.service.shop.service.legalSubject.service.ILegalSubjectService;
import com.zwdbj.server.utility.model.ResponseData;
import com.zwdbj.server.utility.model.ResponseDataCode;
import com.zwdbj.server.utility.model.ResponsePageInfoData;
import com.zwdbj.server.utility.model.ServiceStatusInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shop/legalSubject")
@Api(description = "商户相关")
public class LegalSubjectController {
    @Autowired
    ILegalSubjectService legalSubjectServiceImpl;

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    @ApiOperation(value = "查询商户")
    public ResponsePageInfoData<List<LegalSubjectModel>> getLegalSubjects(@RequestBody LegalSubjectSearchInput input,
                                                                          @RequestParam(value = "pageNo", required = true, defaultValue = "1") int pageNo,
                                                                          @RequestParam(value = "rows", required = true, defaultValue = "30") int rows){
        Page<LegalSubjectModel> pageInfo = PageHelper.startPage(pageNo, rows);
        List<LegalSubjectModel> legalSubjectModels = this.legalSubjectServiceImpl.getLegalSubjects(input);
        return new ResponsePageInfoData<>(ResponseDataCode.STATUS_NORMAL, "", legalSubjectModels, pageInfo.getTotal());
    }
    @RequestMapping(value = "/search/unReviewed", method = RequestMethod.POST)
    @ApiOperation(value = "查询未通过审核的商户")
    public ResponsePageInfoData<List<LegalSubjectModel>> getUnReviewedLegalSubjects(@RequestBody LegalSubjectSearchInput input,
                                                                          @RequestParam(value = "pageNo", required = true, defaultValue = "1") int pageNo,
                                                                          @RequestParam(value = "rows", required = true, defaultValue = "30") int rows){
        Page<LegalSubjectModel> pageInfo = PageHelper.startPage(pageNo, rows);
        List<LegalSubjectModel> legalSubjectModels = this.legalSubjectServiceImpl.getUnReviewedLegalSubjects(input);
        return new ResponsePageInfoData<>(ResponseDataCode.STATUS_NORMAL, "", legalSubjectModels, pageInfo.getTotal());
    }









}
