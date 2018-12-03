package com.zwdbj.shop_common_service.logistics.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zwdbj.server.utility.model.ServiceStatusInfo;
import com.zwdbj.shop_common_service.logistics.model.Logistics;
import com.zwdbj.shop_common_service.logistics.model.LogisticsDetail;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisServer;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
@Transactional
public class LogisticsService implements ILogisticsService {

    @Autowired
    RedisTemplate redisTemplate;

    @Override
    public ServiceStatusInfo<Logistics> selectLogistics(String no, String logisticsName) {

        ValueOperations<String,Logistics> operations = redisTemplate.opsForValue();

        Logistics getRedis=(Logistics)operations.get(no+logisticsName);//缓存中取出物流
        if (getRedis != null){
            return new ServiceStatusInfo<>(0,"查询成功",getRedis);
        }else {
                JSONObject jsonObject = selectLogisticsJSON(no,logisticsName);
                if (jsonObject.getString("status").equals("0")){
                    String result=jsonObject.getString("result");
                    JSONObject jsonObject1 = JSONObject.parseObject(result);

                    Logistics logistics = new Logistics();
                    List<LogisticsDetail> lists = new ArrayList<>();

                    logistics.setNumber(jsonObject1.getString("number"));
                    logistics.setType(jsonObject1.getString("type"));
                    JSONArray jsonArray = jsonObject1.getJSONArray("list");
                    for (Object object :jsonArray) {
                        JSONObject jsonObject3 = JSONObject.parseObject(String.valueOf(object));
                        LogisticsDetail detail = new LogisticsDetail();
                        detail.setTime(jsonObject3.getString("time"));
                        detail.setStatus(jsonObject3.getString("status"));
                        lists.add(detail);
                    }
                    logistics.setList(lists);
                    logistics.setDeliverystatus(jsonObject1.getString("deliverystatus"));
                    logistics.setIssign(jsonObject1.getString("issign"));
                    logistics.setExpName(jsonObject1.getString("expName"));
                    logistics.setExpSite(jsonObject1.getString("expSite"));
                    logistics.setExpPhone(jsonObject.getString("expPhone"));

                    operations.set(no+logisticsName,logistics);//快递对象存入redis中

                    redisTemplate.expire(no+logisticsName,2, TimeUnit.HOURS);//设置过期时间2小时
                    return new ServiceStatusInfo<>(0,"查询成功",logistics);

                }else {
                    return new ServiceStatusInfo<>(0,"查询失败",null);
                }
        }
    }

    public JSONObject selectLogisticsJSON(String no,String type){
        String logistics = logistics(no,type);

        return JSONObject.parseObject(logistics);
    }

    public String logistics(String no,String type){
        String host = "https://wuliu.market.alicloudapi.com";
        String path = "/kdi";
        String method = "GET";
        String appcode = "4f3dedb5bbcb42c2afff129de4585c47";  // !!!替换填写自己的AppCode 在买家中心查看
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "APPCODE " + appcode); //格式为:Authorization:APPCODE 83359fd73fe11248385f570e3c139xxx
        Map<String, String> querys = new HashMap<>();
        querys.put("no", no);
        querys.put("type", type);
        String logistics = null;
        try {
            //状态码: 200 正常；400 URL无效；401 appCode错误； 403 次数用完； 500 API网管错误
            logistics = doGet(host, path, method, headers, querys);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(logistics);
        return logistics;
    }

    public static String doGet(String host, String path, String method,
                               Map<String, String> headers,
                               Map<String, String> querys)
            throws Exception {
        String url =buildUrl(host, path, querys);
        OkHttpClient client = new OkHttpClient();
        Request request1 = null;
        for (Map.Entry<String, String> e : headers.entrySet()) {
            request1= new Request.Builder()
                    .url(url)
                    .header(e.getKey(), e.getValue())
                    .build();

        }
        if (request1!=null){
            Response response = client.newCall(request1).execute();
            if (response.body()!=null){
                return response.body().string();
            }else {
                return "";
            }
        }else {
            return "";
        }
    }

    private static String buildUrl(String host, String path, Map<String, String> querys) throws UnsupportedEncodingException {
        StringBuilder sbUrl = new StringBuilder();
        sbUrl.append(host);
        if (!StringUtils.isBlank(path)) {
            sbUrl.append(path);
        }
        if (null != querys) {
            StringBuilder sbQuery = new StringBuilder();
            for (Map.Entry<String, String> query : querys.entrySet()) {
                if (0 < sbQuery.length()) {
                    sbQuery.append("&");
                }
                if (StringUtils.isBlank(query.getKey()) && !StringUtils.isBlank(query.getValue())) {
                    sbQuery.append(query.getValue());
                }
                if (!StringUtils.isBlank(query.getKey())) {
                    sbQuery.append(query.getKey());
                    if (!StringUtils.isBlank(query.getValue())) {
                        sbQuery.append("=");
                        sbQuery.append(URLEncoder.encode(query.getValue(), "utf-8"));
                    }
                }
            }
            if (0 < sbQuery.length()) {
                sbUrl.append("?").append(sbQuery);
            }
        }
        return sbUrl.toString();
    }
}
