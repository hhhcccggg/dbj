package com.zwdbj.server.adminserver.service.review.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.green.model.v20180509.TextScanRequest;
import com.aliyuncs.http.FormatType;
import com.aliyuncs.http.HttpResponse;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.zwdbj.server.adminserver.config.AppConfigConstant;
import com.zwdbj.server.adminserver.service.comment.service.CommentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TextScanSample {
    @Autowired
    CommentService commentService;
    private Logger logger = LoggerFactory.getLogger(TextScanSample.class);

    public  void textScan() throws Exception {
        //请替换成您自己的accessKeyId、accessKeySecret
        List<Map<String, Object>> tasks = this.commentService.findComments();
        if (tasks==null || tasks.size()==0) return;
        IClientProfile profile = DefaultProfile.getProfile("cn-shanghai", AppConfigConstant.ALIYUN_ACCESS_KEY, AppConfigConstant.ALIYUN_ACCESS_SECRECT);
        DefaultProfile.addEndpoint("cn-shanghai", "cn-shanghai", "Green", "green.cn-shanghai.aliyuncs.com");
        IAcsClient client = new DefaultAcsClient(profile);
        TextScanRequest textScanRequest = new TextScanRequest();
        textScanRequest.setAcceptFormat(FormatType.JSON); // 指定api返回格式
        textScanRequest.setHttpContentType(FormatType.JSON);
        textScanRequest.setMethod(com.aliyuncs.http.MethodType.POST); // 指定请求方法
        textScanRequest.setEncoding("UTF-8");
        textScanRequest.setRegionId("cn-shanghai");

        JSONObject data = new JSONObject();
        /**
         * 文本垃圾检测：antispam
         * 关键词检测：keyword
         **/
        data.put("scenes", Arrays.asList("antispam"));
        data.put("tasks", tasks);
        textScanRequest.setHttpContent(data.toJSONString().getBytes("UTF-8"), "UTF-8", FormatType.JSON);
        // 请务必设置超时时间
        textScanRequest.setConnectTimeout(3000);
        textScanRequest.setReadTimeout(6000);
        try {
            HttpResponse httpResponse = client.doAction(textScanRequest);
            if(httpResponse.isSuccess()){
                JSONObject scrResponse = JSON.parseObject(new String(httpResponse.getHttpContent(), "UTF-8"));
                System.out.println(JSON.toJSONString(scrResponse, true));
                if (200 == scrResponse.getInteger("code")) {
                    JSONArray taskResults = scrResponse.getJSONArray("data");
                    for (Object taskResult : taskResults) {
                        if(200 == ((JSONObject)taskResult).getInteger("code")){
                            String dataId = ((JSONObject) taskResult).getString("dataId");
                            JSONObject sceneResult = ((JSONObject)taskResult).getJSONArray("results").getJSONObject(0);
                            String scene = sceneResult.getString("scene");
                            String suggestion = sceneResult.getString("suggestion");
                            //根据scene和suggetion做相关处理
                            //do something
                            this.commentService.updateComment(Long.parseLong(dataId),suggestion);
                        }else{
                            logger.info("task process fail:" + ((JSONObject)taskResult).getInteger("code"));
                        }
                    }
                } else {
                    logger.info("detect not success. code:" + scrResponse.getInteger("code"));
                }
            }else{
                logger.info("response not success. status:" + httpResponse.getStatus());
            }
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
