package com.rb.web2.domain.enums;

import java.util.EnumSet;
import java.util.Set;

public enum Role {
    ADMIN(EnumSet.of(Permission.READ, Permission.WRITE, Permission.DELETE, Permission.UPDATE)),
    MANAGER(EnumSet.of(Permission.READ, Permission.WRITE, Permission.DELETE)),
    EMPLOYEE(EnumSet.of(Permission.READ, Permission.WRITE, Permission.DELETE)),
    USER(EnumSet.of(Permission.READ));

    private final Set<Permission> permissions;

    Role(Set<Permission> permissions) {
        this.permissions = permissions;
    }

    public Set<Permission> getPermissions() {
        return permissions;
    }
}
