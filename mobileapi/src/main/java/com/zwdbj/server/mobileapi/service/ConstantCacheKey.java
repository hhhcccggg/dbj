package com.zwdbj.server.mobileapi.service;

public class ConstantCacheKey {
    public static final String user(long userId) {
        return "user_"+String.valueOf(userId);
    }
    public static final String userInfoKey = "user_infokey";
}
