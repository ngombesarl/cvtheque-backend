package com.gozenservice.gozen.controllers.offer;

import com.gozenservice.gozen.models.JobProposal;
import com.gozenservice.gozen.models.ProposalStatus;
import com.gozenservice.gozen.response.Response;
import com.gozenservice.gozen.services.UserDetailsImpl;
import com.gozenservice.gozen.services.offer.JobProposalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

/**
 * Job Proposal Controller for managing job proposals.
 * 
 * @author TCHINGANG Steve
 */
@RestController
@RequestMapping("/api/all/job-proposals")
@RequiredArgsConstructor
@Tag(name = "Job Proposals", description = "API for managing job proposals.")
public class JobProposalController {

    private final JobProposalService jobProposalService;

    @Operation(
        summary = "Propose a job to job seekers",
        description = "Allows an employer to propose a job to multiple job seekers.",
        tags = { "Employer" }
    )
    @PostMapping("/propose/{jobOfferId}")
    public ResponseEntity<?> proposeJob(
            @PathVariable Long jobOfferId,
            @RequestBody List<Long> jobSeekerIds,
            @AuthenticationPrincipal UserDetailsImpl employerDetails
    ) {
        jobProposalService.proposeJob(jobOfferId, jobSeekerIds, employerDetails.getUser());
        return ResponseEntity.status(HttpStatus.CREATED).body(new Response(HttpStatus.CREATED.value(), "Job proposals created successfully."));
    }

    @Operation(
        summary = "Update proposal status",
        description = "Allows a job seeker to accept or reject a job proposal.",
        tags = { "Job Seeker" }
    )
    @PutMapping("/{proposalId}/status")
    public ResponseEntity<?> updateProposalStatus(
            @PathVariable Long proposalId,
            @RequestParam ProposalStatus status,
            @AuthenticationPrincipal UserDetailsImpl jobSeekerDetails
    ) {
        jobProposalService.updateProposalStatus(proposalId, status, jobSeekerDetails.getUser());
        return ResponseEntity.ok(new Response(HttpStatus.OK.value(), "Proposal status updated successfully."));
    }

    @Operation(
        summary = "Get proposals for a job seeker",
        description = "Retrieve all job proposals for the currently authenticated job seeker.",
        tags = { "Job Seeker" }
    )
    @GetMapping
    public ResponseEntity<List<JobProposal>> getProposalsForJobSeeker(
            @AuthenticationPrincipal UserDetailsImpl jobSeekerDetails
    ) {
        List<JobProposal> proposals = jobProposalService.getProposalsForJobSeeker(jobSeekerDetails.getUser());
        return ResponseEntity.ok(proposals);
    }

    @Operation(
        summary = "Get proposals for an employer",
        description = "Retrieve all job proposals sent by the currently authenticated employer.",
        tags = { "Employer" }
    )
    @GetMapping("/employer")
    public ResponseEntity<List<JobProposal>> getProposalsForEmployer(
            @AuthenticationPrincipal UserDetailsImpl employerDetails
    ) {
        List<JobProposal> proposals = jobProposalService.getProposalsForEmployer(employerDetails.getUser());
        return ResponseEntity.ok(proposals);
    }
    
    
    @GetMapping("/filter/status")
    public ResponseEntity<List<JobProposal>> filterByStatus(@RequestBody ProposalStatus status,
            @AuthenticationPrincipal UserDetailsImpl employerDetails
    ) {
        
        List<ProposalStatus> stat = new ArrayList<>();
        stat.add(ProposalStatus.ABORTED);
        stat.add(ProposalStatus.ACCEPTED);
        stat.add(ProposalStatus.PENDING);
        stat.add(ProposalStatus.REJECTED);
        
        if(stat.indexOf(status) == -1) {
            return ResponseEntity.notFound().build();
        }
        
        List<JobProposal> proposals = jobProposalService.filterByStatus(status);
        return ResponseEntity.ok(proposals);
    }
    
    

//    @Operation(
//        summary = "Remove a proposal",
//        description = "Allows an employer to remove a job proposal sent to a job seeker.",
//        tags = { "Employer" }
//    )
//    @PutMapping("/{proposalId}")
//    public ResponseEntity<?> removeProposal(
//            @PathVariable Long proposalId,
//            @AuthenticationPrincipal UserDetailsImpl employerDetails
//    ) {
//        jobProposalService.removeProposal(proposalId, employerDetails.getUser());
//        return ResponseEntity.ok(new Response(HttpStatus.OK.value(), "Proposal removed successfully."));
//    }
}
