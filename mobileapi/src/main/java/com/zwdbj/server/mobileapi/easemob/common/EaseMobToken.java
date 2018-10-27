package com.zwdbj.server.mobileapi.easemob.common;

import java.io.Serializable;

public class EaseMobToken implements Serializable {
    private String access_token;
    /**
     * 过期时间，单位秒
     */
    private long expires_in;
    private String application;

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public long getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(long expires_in) {
        this.expires_in = expires_in;
    }

    public String getApplication() {
        return application;
    }

    public void setApplication(String application) {
        this.application = application;
    }
}
