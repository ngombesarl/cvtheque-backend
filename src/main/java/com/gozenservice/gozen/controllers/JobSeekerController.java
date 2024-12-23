/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.gozenservice.gozen.controllers;

import com.gozenservice.gozen.models.JobSeeker;
import com.gozenservice.gozen.services.JobSeekerService;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author TCHINGANG Steve
 */
@RestController
@RequestMapping("/api/all")
public class JobSeekerController {

    private final JobSeekerService jobSeekerService;

    public JobSeekerController(JobSeekerService jobSeekerService) {
        this.jobSeekerService = jobSeekerService;
    }

    @GetMapping("/filter")
    public ResponseEntity<List<JobSeeker>> filterJobSeekers(
            @RequestParam(required = false) List<String> skills,
            @RequestParam(required = false) List<String> jobTitles,
            @RequestParam(required = false) List<String> languages) {

        List<JobSeeker> filteredJobSeekers = jobSeekerService.filterJobSeekers(skills, jobTitles, languages);
        return ResponseEntity.ok(filteredJobSeekers);
    }
}
