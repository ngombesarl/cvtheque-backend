/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.gozenservice.gozen.controllers.cv;

/**
 *
 * @author TCHINGANG Steve
 */

import com.gozenservice.gozen.request.cv.EducationRequest;
import com.gozenservice.gozen.response.cv.EducationResponse;
import com.gozenservice.gozen.services.cv.EducationService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/applicant/cv/education")
public class EducationController {

    @Autowired
    private EducationService educationService;

    @PostMapping("/{cvId}")
    public EducationResponse addEducation(@PathVariable Long cvId, @RequestBody EducationRequest request) {
        return educationService.addEducationToCV(cvId, request);
    }
    
    @PutMapping("/{educationId}")
    public EducationResponse updateEducation(@PathVariable Long educationId, @RequestBody EducationRequest request) {
        return educationService.updateEducation(educationId, request);
    }

    @GetMapping("/{cvId}")
    public List<EducationResponse> getEducationByCV(@PathVariable Long cvId) {
        return educationService.getEducationByCV(cvId);
    }

    @DeleteMapping("/{educationId}")
    public void deleteEducation(@PathVariable Long educationId) {
        educationService.deleteEducation(educationId);
    }
}

