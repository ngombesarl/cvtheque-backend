/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gozenservice.gozen.config;

import com.gozenservice.gozen.models.Permission;
import com.gozenservice.gozen.models.Role;
import com.gozenservice.gozen.repository.PermissionRepository;
import com.gozenservice.gozen.repository.RoleRepository;
import java.util.HashSet;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 *
 * @author TCHINGANG Steve
 */
@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void run(String... args) throws Exception {

        // Créer les permissions
        Permission createCv = new Permission();
        createCv.setName("CREATE_CV");

        Permission viewCv = new Permission();
        viewCv.setName("VIEW_CV");

        Permission deleteCv = new Permission();
        deleteCv.setName("DELETE_CV");
        if (permissionRepository.findByName("CREATE_CV").isEmpty()) {
            permissionRepository.saveAll(List.of(createCv));
        }
        if (permissionRepository.findByName("VIEW_CV").isEmpty()) {
            permissionRepository.saveAll(List.of(viewCv));
        }
        if (permissionRepository.findByName("DELETE_CV").isEmpty()) {
            permissionRepository.saveAll(List.of(deleteCv));
        }

        //permissionRepository.saveAll(List.of(createCv, viewCv, deleteCv));
        if (roleRepository.findByName("APPLICANT").isEmpty()) {
            
            // Créer le rôle APPLICANT_ROLE avec des permissions par défaut
            Role applicantRole = new Role("APPLICANT");
            applicantRole.setPermissions(new HashSet<>(List.of(createCv, viewCv)));
            roleRepository.save(applicantRole);
        }
        if (roleRepository.findByName("ADMIN").isEmpty()) {
            // Créer le rôle ADMIN_ROLE avec des permissions par défaut
            Role adminRole = new Role("ADMIN");
            adminRole.setPermissions(new HashSet<>(List.of(createCv, viewCv)));
            roleRepository.save(adminRole);
        }
        if (roleRepository.findByName("EMPLOYEER").isEmpty()) {
            // Créer le rôle EMPLOYEER_ROLE avec des permissions par défaut
            Role employeerRole = new Role("EMPLOYEER");
            employeerRole.setPermissions(new HashSet<>(List.of(createCv, viewCv)));
            roleRepository.save(employeerRole);
        }

    }

    @Autowired
    private PermissionRepository permissionRepository;
}
