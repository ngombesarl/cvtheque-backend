/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.gozenservice.gozen.controllers.cv;

/**
 *
 * @author TCHINGANG Steve
 */

import com.gozenservice.gozen.request.cv.InterestRequest;
import com.gozenservice.gozen.response.cv.InterestResponse;
import com.gozenservice.gozen.services.cv.InterestService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/applicant/cv/interests")
public class InterestController {

    @Autowired
    private InterestService interestService;

    // Ajouter un intérêt à un CV spécifique
    @PostMapping("/{cvId}")
    public InterestResponse addInterest(@PathVariable Long cvId, @RequestBody InterestRequest request) {
        return interestService.addInterestToCV(cvId, request);
    }
    
    // Mettre à jour un interest
    @PutMapping("/{interestId}")
    public InterestResponse updateInterest(@PathVariable Long interestId, @RequestBody InterestRequest request) {
        return interestService.updateInterest(interestId, request);
    }

    // Récupérer tous les intérêts associés à un CV spécifique
    @GetMapping("/{cvId}")
    public List<InterestResponse> getInterestsByCV(@PathVariable Long cvId) {
        return interestService.getInterestsByCV(cvId);
    }

    // Supprimer un intérêt spécifique par son ID
    @DeleteMapping("/{interestId}")
    public void deleteInterest(@PathVariable Long interestId) {
        interestService.deleteInterest(interestId);
    }
}

