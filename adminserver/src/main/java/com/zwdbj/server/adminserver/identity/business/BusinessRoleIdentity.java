package com.zwdbj.server.adminserver.identity.business;

import java.util.ArrayList;
import java.util.List;

public class BusinessRoleIdentity {
    public static final String ADMIN_ROLE = "business_admin";
    public static final String USER_ROLE = "business_user";

    List<String> roles;
    public List<String> getRoles() {
        return roles;
    }
    public BusinessRoleIdentity() {
        roles = new ArrayList<>();
        roles.add(BusinessRoleIdentity.ADMIN_ROLE);
        roles.add(BusinessRoleIdentity.USER_ROLE);
    }
}
