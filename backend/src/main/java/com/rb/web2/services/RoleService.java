package com.rb.web2.services;

import com.rb.web2.domain.permission.Permission;
import com.rb.web2.domain.roles.Role;
import com.rb.web2.repositories.PermissionRepository;
import com.rb.web2.repositories.RoleRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {
    
    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;

    public RoleService(RoleRepository roleRepository, PermissionRepository permissionRepository) {
        this.roleRepository = roleRepository;
        this.permissionRepository = permissionRepository;
    }

    public Role createRole(String name) {
        Role role = new Role();
        role.setName(name.toUpperCase());
        return roleRepository.save(role);
    }

    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    public Role findRoleByName(String name) {
        return this.roleRepository.findByName(name).orElseThrow(() -> new IllegalArgumentException("Role doesnt exist"));
    }


    @Transactional
    public Role addPermissionsToRole(Long roleId, List<String> permissionNames) {
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new IllegalArgumentException("Role not found"));

        for (String permissionName : permissionNames) {
            var permission = permissionRepository.findByName(permissionName)
                    .orElseGet(() -> {
                        var newPermission = new Permission(permissionName);
                        return permissionRepository.save(newPermission);
                    });
            role.getPermissions().add(permission);
        }

        return roleRepository.save(role);
    }


}