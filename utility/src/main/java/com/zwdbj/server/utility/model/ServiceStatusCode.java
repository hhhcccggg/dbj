package com.zwdbj.server.utility.model;

import io.swagger.annotations.ApiModelProperty;

public class ServiceStatusCode {
    public static final int STATUS_NORMAL = 0;
    public static final int STATUS_UNAUTH = 401;
    public static final int STATUS_NOT_FOUND = 404;
    public static final int STATUS_ERROR = 500;
}
