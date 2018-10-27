package com.zwdbj.server.mobileapi.controller;

import com.zwdbj.server.mobileapi.model.ResponseData;
import com.zwdbj.server.mobileapi.model.ResponseDataCode;
import com.zwdbj.server.mobileapi.utility.UnauthorizedException;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.apache.shiro.ShiroException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionController {

    private Logger logger = LoggerFactory.getLogger(ExceptionController.class);

    // 捕捉shiro的异常
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(ShiroException.class)
    public ResponseData handle401(ShiroException e) {
        logger.warn(e.getStackTrace().toString());
        logger.warn(e.getMessage());
        return new ResponseData<String>(ResponseDataCode.STATUS_UNAUTH, "请授权以后访问", null);
    }

    // 捕捉UnauthorizedException
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseData handle401() {
        return new ResponseData<String>(ResponseDataCode.STATUS_UNAUTH, "请授权以后访问", null);
    }

    // 捕捉其他所有异常
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseData globalException(HttpServletRequest request, Throwable ex) {
        logger.warn(ex.getMessage());
        logger.warn(ex.getStackTrace().toString());
        return new ResponseData<String>(500, "服务器内部异常", null);
    }

    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.valueOf(statusCode);
    }
}
