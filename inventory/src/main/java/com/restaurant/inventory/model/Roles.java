package com.restaurant.inventory.model;

import java.util.List;

import jakarta.persistence.*;

@Entity
public class Roles {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int roleID;

    @Column(nullable = false)
    private String roleName;

    @ManyToMany
    @JoinTable(
        name = "Role_Permissions",
        joinColumns = @JoinColumn(name = "roleID"),
        inverseJoinColumns = @JoinColumn(name = "permissionID"))
   private List<Permission> permissions;

    public int getRoleID() {
        return roleID;
    }

    public void setRoleID(int roleID) {
        this.roleID = roleID;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public List<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }

}
