package com.zwdbj.server.shop_admin_service.http;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.util.List;


public class HttpUtils {

    public static String post(String url, String jsonStr) {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        CloseableHttpResponse response = null;
        try {
            HttpPost httpPost = new HttpPost(url);
            httpPost.addHeader("Content-Type", "application/json");
            httpPost.setEntity(new StringEntity(jsonStr));
            response = httpClient.execute(httpPost);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                //获取响应的实体对象
                HttpEntity entity = response.getEntity();
                String entityStr = EntityUtils.toString(entity, "UTF-8");
                System.out.println(entityStr);
                return entityStr;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (null != response) {
                try {
                    response.close();
                    httpClient.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public static String getMethod(String url, List parameter) {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        CloseableHttpResponse response = null;
        try {
            URIBuilder uriBuilder = new URIBuilder(url);
            //添加参数

            uriBuilder.addParameter("param", StringUtils.join(parameter, ","));
            HttpGet httpGet = new HttpGet(uriBuilder.build());

            httpGet.addHeader("Content-Type", "application/x-www-form-urlencoded");
            //执行请求
            response = httpClient.execute(httpGet);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                //获取响应的实体对象
                HttpEntity entity = response.getEntity();
                String entityStr = EntityUtils.toString(entity, "UTF-8");
                System.out.println(entityStr);
                return entityStr;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (null != response) {
                try {
                    response.close();
                    httpClient.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return null;
    }

}