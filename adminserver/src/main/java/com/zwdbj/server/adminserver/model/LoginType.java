package com.zwdbj.server.adminserver.model;

import java.util.HashMap;
import java.util.Map;

public enum LoginType {
    PHONE("手机号",1),
    ACCOUNT("账号",2),
    WECHAT("微信",4),
    QQ("QQ",8),
    WEIBO("微博",16);
    int code;
    String name;
    LoginType(String name, int code) {
        this.code = code;
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }

    public static Map<Integer,String> all() {
        Map<Integer,String> map = new HashMap<>();
        map.put(1,"手机号");
        map.put(2,"账号");
        map.put(4,"微信");
        map.put(8,"QQ");
        map.put(16,"微博");
        return map;
    }
}
