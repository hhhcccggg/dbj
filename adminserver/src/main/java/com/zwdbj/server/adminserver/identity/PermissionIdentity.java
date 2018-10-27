package com.zwdbj.server.adminserver.identity;

import java.util.ArrayList;
import java.util.List;

public class PermissionIdentity {

    List<PermissionTree> permissionTrees;
    public List<PermissionTree> getPermissionTrees() {
        return permissionTrees;
    }
    public PermissionIdentity(){
        permissionTrees = new ArrayList<>();
        PermissionTree tree = new PermissionTree();
        tree.setName("create");
        tree.setDescription("desc");
        permissionTrees.add(tree);
    }

}
