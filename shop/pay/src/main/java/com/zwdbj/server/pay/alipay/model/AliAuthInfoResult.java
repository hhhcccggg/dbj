package com.zwdbj.server.pay.alipay.model;

import java.io.Serializable;

public class AliAuthInfoResult implements Serializable {
    private String authString;

    public String getAuthString() {
        return authString;
    }

    public void setAuthString(String authString) {
        this.authString = authString;
    }
}
