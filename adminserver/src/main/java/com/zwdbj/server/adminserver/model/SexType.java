package com.zwdbj.server.adminserver.model;

import java.util.HashMap;
import java.util.Map;

public enum  SexType {
    SexUnKnown("未知",0),
    SexGG("男",1),
    SexMM("女",2),
    SexSec("保密",3);

    int code;
    String name;
    SexType(String name,int code) {
        this.code = code;
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }

    public static Map<Integer,String> all() {
        Map<Integer,String> map = new HashMap<>();
        map.put(0,"未知");
        map.put(1,"男");
        map.put(2,"女");
        map.put(3,"保密");
        return map;
    }
}
