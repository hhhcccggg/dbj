package com.zwdbj.server.es.common;

import java.util.HashMap;
import java.util.Map;

/**
 * ES类型,可自定义
 */
public class ESType {

    /**
     * 长数据类型
     */
    public static Map<String, Object> getLongt() {
        Map<String,Object> longt = new HashMap<>();
        longt.put("type","long");
        return longt;
    }
    /**
     * 布尔类型
     */
    public static Map<String, Object> getBooleant() {
        Map<String,Object> booleant = new HashMap<>();
        booleant.put("type","boolean");
        return booleant;
    }
    /**
     * 长文本类型,无分词
     */
    public static Map<String, Object> getText() {
        Map<String,Object> text = new HashMap<>();
        text.put("type","text");
        return text;
    }
    /**
     * 时间类型
     */
    public static Map<String, Object> getDate() {
        Map<String,Object> date = new HashMap<>();
        date.put("type","date");
        date.put("format","yyy-MM-dd HH:mm:ss||yyyy-MM-dd||epoch_millis");
        return date;
    }

    /**
     * 坐标类型,纬度,经度
     */
    public static Map<String, Object> getGeo_point() {
        Map<String,Object> geo_point = new HashMap<>();
        geo_point.put("type","geo_point");
        return geo_point;
    }

    /**
     * 长文本类型,包含分词
     */
    public static Map<String, Object> getIk_max_word() {
        Map<String,Object> ik_max_word = new HashMap<>();
        ik_max_word.put("type","text");
        ik_max_word.put("analyzer","ik_max_word");
        return ik_max_word;
    }
}
