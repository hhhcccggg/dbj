package com.zwdbj.server.adminserver.config;

/**
 * 首页存入redis的key
 */
public enum MainKeyType {

    /**
     * 分类key
     */
    MAINCATEGORY("MAINCATEGORY"),

    /**
     * 首页兑换商城
     */
    MAINPRODUCT("MAINPRODUCT");
    
    String name;

    MainKeyType(String name){
        this.name=name;
    }
}
