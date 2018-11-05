package com.zwdbj.server.adminserver.service.homepage.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "首页相关时间段")
public class AdFindIncreasedInput {
    @ApiModelProperty(value = "时间段,0:当前天,1:当前星期,2:当前月,3:当前季度,4:今年,5:前一天,6:前一星期,7:前月,8:前年默认为0")
    int quantumTime ;
    @ApiModelProperty(value = "是否包含马甲用户,true为包含,false为不包含")
    boolean tof;

    public boolean isTof() {
        return tof;
    }

    public void setTof(boolean tof) {
        this.tof = tof;
    }

    public int getQuantumTime() {
        return quantumTime;
    }

    public void setQuantumTime(int quantumTime) {
        this.quantumTime = quantumTime;
    }
}
