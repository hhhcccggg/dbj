package com.zwdbj.server.shop_admin_service.service.legalSubject.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "审核商家字段")
public class LegalSubjectVerityInput {
    @ApiModelProperty(value = "是否审核通过：true为通过，false为拒绝")
    private boolean reviewed;
    @ApiModelProperty("审核说明")
    private String rejectMsg;

    public boolean isReviewed() {
        return reviewed;
    }

    public void setReviewed(boolean reviewed) {
        this.reviewed = reviewed;
    }

    public String getRejectMsg() {
        return rejectMsg;
    }

    public void setRejectMsg(String rejectMsg) {
        this.rejectMsg = rejectMsg;
    }
}
