/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gozenservice.gozen.controllers;

import com.gozenservice.gozen.models.JobSeeker;
import com.gozenservice.gozen.models.Permission;
import com.gozenservice.gozen.models.User;
import com.gozenservice.gozen.repository.UserRepository;
import com.gozenservice.gozen.request.CVRequest;
import com.gozenservice.gozen.request.cv.CVDetailRequest;
import com.gozenservice.gozen.response.Response;
import com.gozenservice.gozen.services.CVService;
import com.gozenservice.gozen.services.UserDetailsImpl;
import io.swagger.v3.oas.annotations.Operation;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author TCHINGANG Steve
 */
@RestController
@RequestMapping("/api/applicant/cv")
public class CVController {

    @Autowired
    private CVService cvService;

    @Autowired
    UserRepository userRepository;

    // Create a new CV for a user
    @Operation(summary = "Create a new CV", description = "Allows users to create a new CV")
    @PostMapping("/create")
    public ResponseEntity<?> createCV(@AuthenticationPrincipal UserDetailsImpl userDetail, @RequestBody CVRequest cvRequest) {
        // Récupérer l'utilisateur authentifié avec une gestion d'erreur plus sûre
        User user = userRepository.findById(userDetail.getId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Logique d'autorisation, pour s'assurer que l'utilisateur est un JobSeeker
        if (!(user instanceof JobSeeker)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new Response(HttpStatus.FORBIDDEN.value(), "Only JobSeekers can create a CV"));
        }

        // Appeler le service pour créer ou mettre à jour le CV
        try {
            cvService.createCV(cvRequest, user); // Passer l'utilisateur dans la méthode du service
            return ResponseEntity.status(HttpStatus.CREATED).body(new Response(HttpStatus.CREATED.value(), "CV created successfully"));
        } catch (Exception e) {
            // Gérer les exceptions et retourner une réponse appropriée
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), "An error occurred while creating the CV"));
        }
    }

    // Update a specific CV
    @Operation(summary = "Update a specific CV", description = "Allows users to specify and update a particular CV")
    @PutMapping("/{cvId}")
    public ResponseEntity<?> updateCV(@PathVariable Long cvId, @RequestBody CVRequest cvRequest) {
        cvService.updateCV(cvId, cvRequest);
        return ResponseEntity.ok(new Response(HttpStatus.OK.value(), "CV updated successfully"));
    }

//    // Generate downloadable CV file
//    @Operation(summary = "Generate downloadable CV file", description = "Generates a CV in PDF format using a selected template")
//    @GetMapping("/{cvId}/download")
//    public ResponseEntity<Resource> downloadCV(@PathVariable Long cvId, @RequestParam String templateType) throws IOException {
//        Resource pdf = cvService.generateCVFile(cvId, templateType);
//        return ResponseEntity.ok()
//                .contentType(MediaType.APPLICATION_PDF)
//                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=cv_" + cvId + ".pdf")
//                .body(pdf);
//    }
    
    /**
     * Génère le CV d'un utilisateur en PDF
     * @param userId ID de l'utilisateur
     * @return PDF en tant que ressource
     */
    @GetMapping("/{cvId}/generate-pdf")
    public ResponseEntity<Resource> generatePdf(@PathVariable Long cvId) {
        try {
            Resource pdfResource = cvService.generateCVPdf(cvId);
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_PDF)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=CV_" + cvId + ".pdf")
                    .body(pdfResource);
        } catch (RuntimeException ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (IOException ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    public void logUserPermissions(User user) {
        Set<Permission> permissions = user.getRoles().getPermissions();
        System.out.println("Permissions for user " + user.getName() + ":");
        permissions.forEach(permission -> System.out.println(permission.getName()));
    }

    // Récupérer le CV de l'utilisateur connecté
    @Operation(summary = "Retrieve the CV of the logged-in user", description = "Allows the logged-in JobSeeker to retrieve their CV")
    @GetMapping("/me")
    public ResponseEntity<?> getLoggedInUserCV(@AuthenticationPrincipal UserDetailsImpl userDetail) {
        // Récupérer l'utilisateur authentifié
        User user = userRepository.findById(userDetail.getId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Vérifier si l'utilisateur est un JobSeeker
        if (!(user instanceof JobSeeker)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new Response(HttpStatus.FORBIDDEN.value(), "Only JobSeekers can access their CV"));
        }

        // Récupérer le CV via le service
        try {
            return ResponseEntity.ok(cvService.getCVByUser(user.getId()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response(HttpStatus.NOT_FOUND.value(), "No CV found for the logged-in user"));
        }
    }
    
    // Récupérer le CV de l'utilisateur connecté
    @Operation(summary = "Retrieve all CV", description = "Get All CV")
    @GetMapping
    public ResponseEntity<?> getAllCV() {
        // Récupérer les CV via le service
        try {
            
            List<User> users = new ArrayList<>();
            for (User user : userRepository.findAll()) {
                if(user instanceof JobSeeker) {
                    user.setPassword("");
                    users.add(user);
                }
            }
            return ResponseEntity.ok(users);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error while retrieving cv"));
        }
    }
    
    // Update a specific CV
    @Operation(summary = "Update a CV Details", description = "Allows users to specify and update a particular CV")
    @PutMapping("/update/details/{cvId}")
    public ResponseEntity<?> updateCVDetail(@PathVariable Long cvId, @RequestBody CVDetailRequest cvRequest) {
        User user = userRepository.findById(cvRequest.getUserId()).orElseThrow(() -> new RuntimeException("User not found"));
        
        // Vérifier si l'utilisateur est un JobSeeker
        if (!(user instanceof JobSeeker)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new Response(HttpStatus.FORBIDDEN.value(), "Only JobSeekers can update cv detail"));
        }
        JobSeeker jobSeeker = (JobSeeker) user;
        jobSeeker.setName(cvRequest.getName());
        jobSeeker.setSurname(cvRequest.getSurname());
        jobSeeker.setPhone(cvRequest.getPhone());
        jobSeeker.setBirthdate(cvRequest.getBirthdate());
        
        userRepository.save(jobSeeker);
        
        cvService.updateCVDetail(cvId, cvRequest);
        return ResponseEntity.ok(new Response(HttpStatus.OK.value(), "CV updated successfully"));
    }

}
