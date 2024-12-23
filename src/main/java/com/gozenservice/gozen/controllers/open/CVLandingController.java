/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gozenservice.gozen.controllers.open;

import com.gozenservice.gozen.models.JobSeeker;
import com.gozenservice.gozen.models.User;
import com.gozenservice.gozen.repository.UserRepository;
import com.gozenservice.gozen.response.Response;
import io.swagger.v3.oas.annotations.Operation;
import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author TCHINGANG Steve
 */
@RestController
@RequestMapping("/api/public/cv")
public class CVLandingController {

    @Value("${upload.dir}")
    private String uploadDir;

    @Autowired
    private UserRepository userRepository;

    @Operation(summary = "Retrieve all CV", description = "Get All CV")
    @GetMapping
    public ResponseEntity<?> getCV() {
        // Récupérer les CV via le service
        try {

            List<Map<String, Object>> users = new ArrayList<>();

            for (User user : userRepository.findAll()) {
                if (user instanceof JobSeeker) {
                    Map<String, Object> json = new HashMap<>();
                    File file = new File(uploadDir + "/" + user.getProfilePhotoUrl());
                    if (file.exists() && file.isFile()) {
                        byte[] fileContent = Files.readAllBytes(file.toPath());
                        json.put("name", user.getName());
                        json.put("surname", user.getSurname());
                        json.put("profilePicture", Base64.getEncoder().encodeToString(fileContent));
                        json.put("jobTitle", ((JobSeeker) user).getCv().getJobTitle());
                        json.put("userId", user.getId());
                        json.put("cvId", ((JobSeeker) user).getCv().getId());
                        users.add(json);
                    }
                }
            }
            return ResponseEntity.ok(users);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error while retrieving cv"));
        }
    }
}
