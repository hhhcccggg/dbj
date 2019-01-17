package com.zwdbj.server.mobileapi.controller.wxMini;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zwdbj.server.mobileapi.service.wxMiniProgram.task.model.TaskModel;
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
    @ApiOperation(value = "用户任务,type为任务的类型")
    public ResponsePageInfoData<List<TaskModel>> getUserTasks(@PathVariable String type,
                                                              @RequestParam(value = "pageNo", defaultValue = "1", required = true) int pageNo,
                                                              @RequestParam(value = "rows", defaultValue = "13", required = true) int rows){
        Page<TaskModel> pageInfo = PageHelper.startPage(pageNo, rows);
        List<TaskModel> taskModels = this.taskService.getUserTasks(JWTUtil.getCurrentId(),type);
        return new ResponsePageInfoData<>(ResponseDataCode.STATUS_NORMAL, "", taskModels, pageInfo.getTotal());
    }

    @RequiresAuthentication
    @RequestMapping(value = "/do/{id}/{type}", method = RequestMethod.GET)
    @ApiOperation(value = "做任务,id为任务的id(String类型),type为任务的类型,返回数据(101:跳转添加宠物页面,201;跳转邀请,301:跳转首页)")
    public ResponseData<Integer> doUserTask(@PathVariable String id,
                                            @PathVariable String type){
        ServiceStatusInfo<Integer> result = this.taskService.doUserTask(id,type);
        return new ResponseData<>(ResponseDataCode.STATUS_NORMAL, "",result.getData());
    }
}