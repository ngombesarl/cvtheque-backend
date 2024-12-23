/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.gozenservice.gozen.controllers.cv;

/**
 *
 * @author TCHINGANG Steve
 */

import com.gozenservice.gozen.request.cv.ExperienceRequest;
import com.gozenservice.gozen.response.cv.ExperienceResponse;
import com.gozenservice.gozen.services.cv.ExperienceService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/applicant/cv/experiences")
public class ExperienceController {

    @Autowired
    private ExperienceService experienceService;

    @PostMapping("/{cvId}/add")
    public ExperienceResponse addExperience(@PathVariable Long cvId, @RequestBody ExperienceRequest request) {
        return experienceService.addExperienceToCV(cvId, request);
    }
    
    @PutMapping("/{experienceId}")
    public ExperienceResponse updateExperience(@PathVariable Long experienceId, @RequestBody ExperienceRequest request) {
        return experienceService.updateExperience(experienceId, request);
    }

    @GetMapping("/{cvId}")
    public List<ExperienceResponse> getExperiencesByCV(@PathVariable Long cvId) {
        return experienceService.getExperiencesByCV(cvId);
    }

    @DeleteMapping("/{experienceId}")
    public void deleteExperience(@PathVariable Long experienceId) {
        experienceService.deleteExperience(experienceId);
    }
}

