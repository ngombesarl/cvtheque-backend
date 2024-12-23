/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.gozenservice.gozen.controllers.offer;

import com.gozenservice.gozen.models.SavedJobOffer;
import com.gozenservice.gozen.response.Response;
import com.gozenservice.gozen.services.UserDetailsImpl;
import com.gozenservice.gozen.services.offer.SavedJobOfferService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author TCHINGANG Steve
 */
@RestController
@RequestMapping("/api/applicant/job/saved-job-offers")
@RequiredArgsConstructor
@Tag(name = "Saved Job Offers", description = "API for managing saved job offers.")
public class SavedJobOfferController {

    private final SavedJobOfferService savedJobOfferService;

    @Operation(
        summary = "Save a job offer",
        description = "Allows a job seeker to save a job offer.",
        tags = { "Job Seeker" }
    )
    @PostMapping("/{jobOfferId}/save")
    public ResponseEntity<?> saveJobOffer(
            @PathVariable Long jobOfferId,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        savedJobOfferService.saveJobOffer(jobOfferId, userDetails.getUser());
        return ResponseEntity.status(HttpStatus.CREATED).body(new Response(HttpStatus.CREATED.value(), "Job offer saved successfully."));
    }

    @Operation(
        summary = "Delete a saved job offer",
        description = "Allows a job seeker to remove a saved job offer.",
        tags = { "Job Seeker" }
    )
    @DeleteMapping("/{jobOfferId}/delete")
    public ResponseEntity<?> deleteSavedJobOffer(
            @PathVariable Long jobOfferId,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        savedJobOfferService.deleteSavedJobOffer(jobOfferId, userDetails.getUser());
        return ResponseEntity.ok(new Response(HttpStatus.OK.value(), "Saved job offer deleted successfully."));
    }

    @Operation(
        summary = "Get saved job offers",
        description = "Retrieve all job offers saved by the authenticated job seeker.",
        tags = { "Job Seeker" }
    )
    @GetMapping
    public ResponseEntity<List<SavedJobOffer>> getSavedJobOffers(
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        List<SavedJobOffer> savedJobOffers = savedJobOfferService.getSavedJobOffers(userDetails.getUser());
        return ResponseEntity.ok(savedJobOffers);
    }
}

