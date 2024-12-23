/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.gozenservice.gozen.controllers.cv;

/**
 *
 * @author TCHINGANG Steve
 */

import com.gozenservice.gozen.request.cv.ReferenceRequest;
import com.gozenservice.gozen.response.cv.ReferenceResponse;
import com.gozenservice.gozen.services.cv.ReferenceService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/applicant/cv/reference")
public class ReferenceController {

    @Autowired
    private ReferenceService referenceService;

    @PostMapping("/{cvId}")
    public ReferenceResponse addReference(@PathVariable Long cvId, @RequestBody ReferenceRequest request) {
        return referenceService.addReferenceToCV(cvId, request);
    }
    
    @PutMapping("/{referenceId}")
    public ReferenceResponse updateReference(@PathVariable Long referenceId, @RequestBody ReferenceRequest request) {
        return referenceService.updateReference(referenceId, request);
    }

    @GetMapping("/{cvId}")
    public List<ReferenceResponse> getReferencesByCV(@PathVariable Long cvId) {
        return referenceService.getReferencesByCV(cvId);
    }

    @DeleteMapping("/{referenceId}")
    public void deleteReference(@PathVariable Long referenceId) {
        referenceService.deleteReference(referenceId);
    }
}

