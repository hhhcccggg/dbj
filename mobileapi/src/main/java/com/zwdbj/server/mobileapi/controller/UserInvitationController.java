package com.zwdbj.server.mobileapi.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zwdbj.server.mobileapi.service.userInvitation.model.SearchUserInvitation;
import com.zwdbj.server.mobileapi.service.userInvitation.model.UserInvitationModel;
import com.zwdbj.server.mobileapi.service.userInvitation.service.UserInvitationService;
import com.zwdbj.server.basemodel.model.ResponseDataCode;
import com.zwdbj.server.basemodel.model.ResponsePageInfoData;
import com.zwdbj.server.basemodel.model.ServiceStatusInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(description = "邀请好友")
@RestController
@RequestMapping(value = "/api/userInvitation")
public class UserInvitationController {

    @Autowired
    private UserInvitationService userInvitationServiceImpl;

    @ApiOperation(value = "查询邀请好友列表")
    @GetMapping("/searchUserInvitation")
    public ResponsePageInfoData<List<UserInvitationModel>> searchUserInvitation(@RequestParam(value = "pageNo", required = true, defaultValue = "1") int pageNo,
                                                                                @RequestParam(value = "rows", required = true, defaultValue = "10") int rows,
                                                                                SearchUserInvitation searchUserInvitation){
        PageHelper.startPage(pageNo,rows);
        ServiceStatusInfo<List<UserInvitationModel>> serviceStatusInfo = userInvitationServiceImpl.searchUserInvitation(searchUserInvitation);
        if(!serviceStatusInfo.isSuccess())
            return new ResponsePageInfoData<>(ResponseDataCode.STATUS_ERROR,serviceStatusInfo.getMsg(),null,0L);
        PageInfo<UserInvitationModel> pageInfo = new PageInfo<>(serviceStatusInfo.getData());
        return new ResponsePageInfoData<>(ResponseDataCode.STATUS_NORMAL,serviceStatusInfo.getMsg(),pageInfo.getList(),pageInfo.getTotal());
    }
}
