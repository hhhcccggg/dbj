package com.zwdbj.server.adminserver.identity;


import com.zwdbj.server.adminserver.service.user.model.UserModel;
import com.zwdbj.server.adminserver.service.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserIdentityInit {
    @Autowired
    UserService userService;
    UserModel userModel;
    RoleIdentity roleIdentity = new RoleIdentity();
    PermissionIdentity permissionIdentity = new PermissionIdentity();

    public void init () {
        initAdmin();
    }

    private void initAdmin() {
        this.userService.regAdminUser("Scdbj0280!@#");
        userModel = this.userService.findUserByUserName("admin");
        initRole();
        initPermission(permissionIdentity.getPermissionTrees());
        initAdminRole();
    }

    private void initRole() {
        for (String name : roleIdentity.getRoles()) {
            try {
                this.userService.addRole(name, name);
            } catch (Exception ex) {

            }
        }
    }

    private void initPermission(List<PermissionTree> trees) {
        for (PermissionTree tree : trees) {
            try {
                this.userService.addPermission(tree.name, tree.description);
            } catch (Exception ex) {

            }
            if (tree.subPermissions!=null&&tree.subPermissions.size()>0) {
                initPermission(tree.subPermissions);
            }
        }
    }

    private void initAdminRole() {
        //分配角色
        List<String> roles = this.userService.roles(userModel.getId());
        if (!roles.contains(RoleIdentity.ADMIN_ROLE)) {
            this.userService.setRole(userModel.getId(),RoleIdentity.ADMIN_ROLE);
        }
        List<String> permissions = this.userService.permissions(RoleIdentity.ADMIN_ROLE);
        permissionInit(permissions,permissionIdentity.getPermissionTrees());
    }

    private void permissionInit(List<String> permissions,List<PermissionTree> trees) {
        for (PermissionTree p:trees) {
            if (!permissions.contains(p.getName())) {
                this.userService.setPermission(RoleIdentity.ADMIN_ROLE,p.getName());
            }
            if (p.subPermissions!=null&&p.subPermissions.size()>0) {
                permissionInit(permissions,p.subPermissions);
            }
        }
    }
}
