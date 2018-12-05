package com.zwdbj.server.adminserver.identity;

import java.util.ArrayList;
import java.util.List;

public class RoleIdentity {

    public static final String ADMIN_ROLE = "admin";
    public static final String MARKET_ROLE = "market";
    public static final String FINANCE_ROLE = "finance";
    public static final String NORMAL_ROLE = "normal";
    public static final String DATA_REPORT_ROLE = "datareport";

    List<String> roles;
    public List<String> getRoles() {
        return roles;
    }
    public RoleIdentity() {
        roles = new ArrayList<>();
        roles.add(RoleIdentity.ADMIN_ROLE);
        roles.add(RoleIdentity.FINANCE_ROLE);
        roles.add(RoleIdentity.NORMAL_ROLE);
        roles.add(RoleIdentity.MARKET_ROLE);
        roles.add(RoleIdentity.DATA_REPORT_ROLE);
    }


}
