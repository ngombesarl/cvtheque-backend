/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.gozenservice.gozen.services;

import com.gozenservice.gozen.models.Permission;
import com.gozenservice.gozen.repository.PermissionRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author TCHINGANG Steve
 */
@Service
public class PermissionService {
    @Autowired
    private PermissionRepository permissionRepository;

    public Permission savePermission(Permission permission) {
        return permissionRepository.save(permission);
    }

    public Optional<Permission> findPermissionByName(String name) {
        return permissionRepository.findByName(name);
    }

    public List<Permission> findAllPermissions() {
        return permissionRepository.findAll();
    }

    public void deletePermission(Long permissionId) {
        permissionRepository.deleteById(permissionId);
    }
}

