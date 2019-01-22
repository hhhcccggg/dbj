package com.zwdbj.server.mobileapi.controller.wxMini;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zwdbj.server.mobileapi.service.wxMiniProgram.task.model.InviteesModel;
import com.zwdbj.server.mobileapi.service.wxMiniProgram.task.model.UserTaskModel;
import com.zwdbj.server.mobileapi.service.wxMiniProgram.task.service.TaskService;
import com.zwdbj.server.utility.common.shiro.JWTUtil;
import com.zwdbj.server.utility.model.ResponseData;
import com.zwdbj.server.utility.model.ResponseDataCode;
import com.zwdbj.server.utility.model.ResponsePageInfoData;
import com.zwdbj.server.utility.model.ServiceStatusInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/task/user")
@Api(description = "用户任务相关")
public class TaskController {
    @Autowired
    private TaskService taskService;
    @RequiresAuthentication
    @RequestMapping(value = "/select/{type}", method = RequestMethod.GET)
    @ApiOperation(value = "用户任务,type为任务的类型:  NEWUSER:新手任务,DAILY:日常任务,INVITATION:邀请任务")
    public ResponsePageInfoData<List<UserTaskModel>> getUserTasks(@PathVariable String type,
                                                                  @RequestParam(value = "pageNo", defaultValue = "1", required = true) int pageNo,
                                                                  @RequestParam(value = "rows", defaultValue = "13", required = true) int rows){
        Page<UserTaskModel> pageInfo = PageHelper.startPage(pageNo, rows);
        List<UserTaskModel> userTaskModels = this.taskService.getUserTasks(JWTUtil.getCurrentId(),type);
        return new ResponsePageInfoData<>(ResponseDataCode.STATUS_NORMAL, "", userTaskModels, pageInfo.getTotal());
    }

    @RequiresAuthentication
    @RequestMapping(value = "/do/{id}/{type}", method = RequestMethod.GET)
    @ApiOperation(value = "做任务,id为任务的id(String类型),type为任务的类型,返回数据(101:跳转添加宠物页面,201;跳转邀请,301:跳转首页)")
    public ResponseData<Integer> doUserTask(@PathVariable String id,
                                            @PathVariable String type){
        ServiceStatusInfo<Integer> result = this.taskService.doUserTask(id,type);
        return new ResponseData<>(ResponseDataCode.STATUS_NORMAL, "",result.getData());
    }
    @RequiresAuthentication
    @RequestMapping(value = "/get/invitees", method = RequestMethod.GET)
    @ApiOperation(value = "得到我所邀请的人")
    public ResponsePageInfoData<List<InviteesModel>> findMyInvitees(@RequestParam(value = "pageNo", defaultValue = "1", required = true) int pageNo,
                                                                   @RequestParam(value = "rows", defaultValue = "13", required = true)int rows){
        Page<InviteesModel> pageInfo = PageHelper.startPage(pageNo, rows);
        List<InviteesModel> InviteesModels = this.taskService.findMyInvitees(JWTUtil.getCurrentId());
        return new ResponsePageInfoData<>(ResponseDataCode.STATUS_NORMAL, "", InviteesModels, pageInfo.getTotal());
    }
}
