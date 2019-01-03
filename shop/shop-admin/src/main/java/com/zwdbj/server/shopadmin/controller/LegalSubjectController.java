package com.zwdbj.server.shopadmin.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zwdbj.server.shop_admin_service.service.legalSubject.model.LegalSubjectModel;
import com.zwdbj.server.shop_admin_service.service.legalSubject.model.LegalSubjectReviewModel;
import com.zwdbj.server.shop_admin_service.service.legalSubject.model.LegalSubjectSearchInput;
import com.zwdbj.server.shop_admin_service.service.legalSubject.model.LegalSubjectVerityInput;
import com.zwdbj.server.shop_admin_service.service.legalSubject.service.ILegalSubjectService;
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
    @RequestMapping(value = "/Verify/{id}", method = RequestMethod.POST)
    @ApiOperation(value = "根据id审核未通过审核的商户")
    public ResponseData<Integer> verityUnReviewed(@PathVariable long id,
                                                  @RequestBody LegalSubjectVerityInput input){

        ServiceStatusInfo<Integer> statusInfo = this.legalSubjectServiceImpl.verityUnReviewed(id, input);
        if (statusInfo.isSuccess()) {
            return new ResponseData<>(ResponseDataCode.STATUS_NORMAL, "", statusInfo.getData());
        }
        return new ResponseData<>(ResponseDataCode.STATUS_ERROR, statusInfo.getMsg(), null);
    }
    @RequestMapping(value = "/getReviews/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "根据id得到商户的审核信息")
    public ResponsePageInfoData<List<LegalSubjectReviewModel>> getReviewsByLegalSubjectId(@PathVariable long id,
                                                                                          @RequestParam(value = "pageNo", required = true, defaultValue = "1") int pageNo,
                                                                                          @RequestParam(value = "rows", required = true, defaultValue = "30") int rows){

        Page<LegalSubjectReviewModel> pageInfo = PageHelper.startPage(pageNo, rows);
        List<LegalSubjectReviewModel> legalSubjectReviewModels = this.legalSubjectServiceImpl.getReviewsByLegalSubjectId(id);
        return new ResponsePageInfoData<>(ResponseDataCode.STATUS_NORMAL, "", legalSubjectReviewModels, pageInfo.getTotal());
    }







}
