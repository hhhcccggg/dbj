package com.zwdbj.server.adminserver.controller;

import com.zwdbj.server.adminserver.service.review.service.ReviewService;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.InputStreamReader;

@RestController
@RequestMapping("/review")
@Api(description = "审核相关")
public class ReviewController {
    @Autowired
    ReviewService reviewService;

    public  Logger logger = LoggerFactory.getLogger(ReviewController.class);

    @RequestMapping(value = "/qiniu")
    public void getResponse(HttpServletRequest request){

        try {
            String line = "";
            BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
            StringBuilder sb = new StringBuilder();
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            String response  = sb.toString();
            this.reviewService.getResponse(response);
            // 设置返回给七牛的数据
            //logger.info(response);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("审核------------error"+e.getMessage());
        }
    }

    @RequestMapping(value = "/living/callback",method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
    public void LivingReview(HttpServletRequest request, HttpServletResponse rsp){
        try {
            String lines = "";
            BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
            StringBuilder sb = new StringBuilder();
            while ((lines = br.readLine()) != null) {
                lines = new String(lines.getBytes(), "utf-8");
                sb.append(lines);
            }
            String response  = sb.toString();
            this.reviewService.getLivingResponse(response);
            logger.info("living鉴黄回调");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("living鉴黄回调------------error"+e.getMessage());
        }

    }

}
