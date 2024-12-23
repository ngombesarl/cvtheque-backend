/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.gozenservice.gozen.controllers;

/**
 *
 * @author TCHINGANG Steve
 */

import com.gozenservice.gozen.models.*;
import com.gozenservice.gozen.services.EmployerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/employeer")
public class EmployerController {

    private final EmployerService employerService;

    public EmployerController(EmployerService employerService) {
        this.employerService = employerService;
    }

    // Endpoint pour créer une offre d'emploi
    @PostMapping("/job-offers")
    public ResponseEntity<JobOffer> createJobOffer(@RequestBody JobOffer jobOffer, 
                                                   @AuthenticationPrincipal User employer) {
        JobOffer createdOffer = employerService.createJobOffer(jobOffer, employer);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdOffer);
    }

    // Endpoint pour qu'un chercheur d'emploi postule à une offre
    @PostMapping("/job-offers/{jobOfferId}/apply")
    public ResponseEntity<JobApplication> applyForJob(@PathVariable Long jobOfferId, 
                                                      @RequestBody JobApplication jobApplication, 
                                                      @AuthenticationPrincipal User jobSeeker) {
        JobApplication application = employerService.applyForJob(jobOfferId, jobApplication, jobSeeker);
        return ResponseEntity.status(HttpStatus.CREATED).body(application);
    }
}
