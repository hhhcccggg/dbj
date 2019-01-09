package com.zwdbj.server.mobileapi.controller.shop;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zwdbj.server.mobileapi.service.shop.comments.model.ShopCommentsExtraDatas;
import com.zwdbj.server.mobileapi.service.shop.comments.model.UserComments;
import com.zwdbj.server.mobileapi.service.shop.comments.service.ShopCommentService;
import com.zwdbj.server.utility.model.ResponseData;
import com.zwdbj.server.utility.model.ResponsePageInfoData;
import com.zwdbj.server.utility.model.ServiceStatusInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(description = "店铺评论")
@RequestMapping(value = "/api/shopComments/dbj")
@RestController
public class ShopCommentController {
    @Autowired
    private ShopCommentService shopCommentServiceImpl;

    @RequestMapping(value = "/commentsList/{storeId}", method = RequestMethod.GET)
    @ApiOperation(value = "评论列表")
    public ResponsePageInfoData<List<ShopCommentsExtraDatas>> commentsList(@RequestParam(value = "pageNo", defaultValue = "1", required = true) int pageNo,
                                                                           @RequestParam(value = "rows", required = true, defaultValue = "10") int rows,
                                                                           @PathVariable("storeId") long storeId) {
        Page<ShopCommentsExtraDatas> page = PageHelper.startPage(pageNo, rows);
        ServiceStatusInfo<List<ShopCommentsExtraDatas>> statusInfo = shopCommentServiceImpl.commentList(storeId);
        return new ResponsePageInfoData<>(0, "", statusInfo.getData(), page.getTotal());

    }

    @RequestMapping(value = "/userComment/{storeId}", method = RequestMethod.GET)
    @ApiOperation(value = "用户评价")
    public ResponseData<UserComments> userComments(@PathVariable("storeId") long storeId) {
        ServiceStatusInfo<UserComments> statusInfo = this.shopCommentServiceImpl.userComments(storeId);
        if (statusInfo.isSuccess()) {
            return new ResponseData<>(0, "", statusInfo.getData());
        }
        return new ResponseData<>(1, statusInfo.getMsg(), null);


    }
}
