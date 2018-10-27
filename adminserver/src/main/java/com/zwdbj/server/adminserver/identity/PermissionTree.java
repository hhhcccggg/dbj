package com.zwdbj.server.adminserver.identity;

import java.util.ArrayList;
import java.util.List;

public class PermissionTree {
    String name;
    String description;
    List<PermissionTree> subPermissions;

    public PermissionTree(){
        subPermissions = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<PermissionTree> getSubPermissions() {
        return subPermissions;
    }

    public void setSubPermissions(List<PermissionTree> subPermissions) {
        this.subPermissions = subPermissions;
    }
}
