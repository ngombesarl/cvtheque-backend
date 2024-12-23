/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gozenservice.gozen.controllers.offer;

/**
 *
 * @author TCHINGANG Steve
 */
import com.gozenservice.gozen.dto.GroupInterviewScheduleDTO;
import com.gozenservice.gozen.dto.InterviewGroupDTO;
import com.gozenservice.gozen.dto.InterviewScheduleDTO;
import com.gozenservice.gozen.dto.JobApplicationUpdateDTO;
import com.gozenservice.gozen.models.Employer;
import com.gozenservice.gozen.models.JobApplication;
import com.gozenservice.gozen.models.JobOffer;
import com.gozenservice.gozen.models.JobOfferStatus;
import com.gozenservice.gozen.models.JobSeeker;
import com.gozenservice.gozen.models.User;
import com.gozenservice.gozen.repository.UserRepository;
import com.gozenservice.gozen.repository.offer.JobApplicationRepository;
import com.gozenservice.gozen.request.offer.JobApplicationRequest;
import com.gozenservice.gozen.request.offer.JobOfferRequest;
import com.gozenservice.gozen.response.Response;
import com.gozenservice.gozen.services.UserDetailsImpl;
import com.gozenservice.gozen.services.offer.JobApplicationExportService;
import com.gozenservice.gozen.services.offer.JobOfferService;
import jakarta.persistence.EntityNotFoundException;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/all/job")
public class JobOfferController {

    @Autowired
    private JobOfferService jobOfferService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DocumentValidationService documentValidationService;
    
    @Autowired
    private JobApplicationRepository jobApplicationRepository;
    
    @Autowired
    private JobApplicationExportService exportService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createJobOffer(@AuthenticationPrincipal UserDetailsImpl userDetail, @RequestBody JobOfferRequest request) {
        // Récupérer l'utilisateur authentifié avec une gestion d'erreur plus sûre
        User user = userRepository.findById(userDetail.getId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Logique d'autorisation, pour s'assurer que l'utilisateur est un Employer
        if (!(user instanceof Employer)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new Response(HttpStatus.FORBIDDEN.value(), "Only Employer can create a Job"));
        }
        request.setEmployer((Employer) user);
        //request.setStatus(JobOfferStatus.ACTIVE);
        JobOffer jobOffer = jobOfferService.createJobOffer(request);
        return ResponseEntity.ok().body(new Response(HttpStatus.OK.value(), "Job created successfully"));
    }

    @PutMapping(value = "/{jobOfferId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateJobOffer(@AuthenticationPrincipal UserDetailsImpl userDetail, @PathVariable Long jobOfferId, @RequestBody JobOfferRequest request) {
        // Récupérer l'utilisateur authentifié avec une gestion d'erreur plus sûre
        User user = userRepository.findById(userDetail.getId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Logique d'autorisation, pour s'assurer que l'utilisateur est un Employer
        if (!(user instanceof Employer)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new Response(HttpStatus.FORBIDDEN.value(), "Only Employer can create a Job"));
        }
        request.setEmployer((Employer) user);
        JobOffer jobOffer = jobOfferService.updateJobOffer(jobOfferId, request);
        return ResponseEntity.ok().body(new Response(HttpStatus.OK.value(), "Job updated successfully"));
    }

    @GetMapping
    public ResponseEntity<List<JobOffer>> getAllJobOffers(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        User user = userRepository.findByEmail(userDetails.getUsername()).orElseThrow(() -> new RuntimeException("You are allowed to access this resource please loging first"));

        List<JobOffer> jobOffers = jobOfferService.getAllJobOffers();

        List<JobOffer> returnedJob = new ArrayList<>();
        if (user instanceof JobSeeker) {
            for (JobOffer offer : jobOffers) {
                if (offer.getStatus() != JobOfferStatus.INACTIVE) {
                    returnedJob.add(offer);
                }
            }
        }
        return ResponseEntity.ok(returnedJob);
    }

    /**
     * *
     *
     * @param userDetails recuperes l'utilisateru connecté pour verifier s'il
     * est employeur
     * @return
     */
    @GetMapping("/employer")
    public ResponseEntity<?> getAllJobOffersByEmployer(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        Optional<User> user = userRepository.findByEmail(userDetails.getUsername());
        if (!(user.get() instanceof Employer)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new Response(HttpStatus.FORBIDDEN.value(), "Only Employer can display their Job Offer"));
        }

        return ResponseEntity.ok(jobOfferService.getAllJobOffersByEmployerId(user.get().getId()));
    }

    @GetMapping("/{id}")
    public Map<String, Object> getJobOfferById(@PathVariable Long id) {
        return jobOfferService.getJobOfferById(id).get().toResponse();
//        return jobOfferService.getJobOfferById(id)
//                .map(ResponseEntity::ok)
//                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/apply/{offerId}")
    public ResponseEntity<JobApplication> applyToJob(
            @AuthenticationPrincipal UserDetailsImpl userDetail,
            @PathVariable Long offerId,
            @RequestParam(value = "cv", required = false) MultipartFile cv,
            @RequestParam(value = "coverLetter", required = false) MultipartFile coverLetter) {

        // Valider les fichiers
        documentValidationService.validateFile(cv, "CV");
        documentValidationService.validateFile(coverLetter, "Cover Letter");

        // Construire l'objet de demande
        JobApplicationRequest request = new JobApplicationRequest();
        request.setApplicantEmail(userDetail.getUsername());
        request.setJobOfferId(offerId);
//        request.setJobTitle(jobTitle);

        JobApplication application = jobOfferService.applyToJob(request, cv, coverLetter);
        return ResponseEntity.ok(application);
    }

    /**
     * *
     *
     * @param id id of application
     * @param type can be "cv" or "coverLetter"
     * @return
     */
    @GetMapping("/{id}/documents/{type}")
    public ResponseEntity<byte[]> downloadDocument(@PathVariable Long id, @PathVariable String type) {
        JobApplication application = jobOfferService.getApplicationById(id);
        byte[] document;
        String fileName;

        if ("cv".equalsIgnoreCase(type)) {
            document = application.getCv();
            fileName = application.getCvFileName();
        } else if ("coverLetter".equalsIgnoreCase(type)) {
            document = application.getCoverLetter();
            fileName = application.getCoverLetterFileName();
        } else {
            throw new RuntimeException("Invalid document type");
        }

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                .body(document);
    }

    @DeleteMapping("{offerId}")
    public ResponseEntity<?> deleteJobOffer(@PathVariable Long offerId) {
        try {
            jobOfferService.delteJobOffer(offerId);
            return ResponseEntity.ok().body(new Response(HttpStatus.OK.value(), "Job deleted successfully"));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/applications/all")
    public ResponseEntity<List<Map<String, Object>>> getAllApplications(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        List<Map<String, Object>> applications = jobOfferService.getAllApplications();
        return ResponseEntity.ok(applications);
    }

    /**
     * *
     *
     * @param jobOfferId
     * @return list of application by offer
     */
    @GetMapping("/applications/employer/{jobOfferId}")
    public ResponseEntity<List<Map<String, Object>>> getAllApplicationsByOffer(@PathVariable Long jobOfferId) {
        List<Map<String, Object>> applications = jobOfferService.getAllApplicationsByOffer(jobOfferId);
        return ResponseEntity.ok(applications);
    }

    /**
     * *
     *
     * @param jobOfferId
     * @return list of application for authenticated job seeker
     */
    @GetMapping("/applications")
    public ResponseEntity<List<Map<String, Object>>> getAllApplicationsByJobSeeker(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        User jobseeker = userRepository.findByUsername(userDetails.getUsername()).orElseThrow(() -> new RuntimeException("User not found please logging first"));

        if (!(jobseeker instanceof JobSeeker)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        List<Map<String, Object>> applications = jobOfferService.getAllApplicationsByJobSeeker(jobseeker.getId());
        return ResponseEntity.ok(applications);
    }

    // Permet de notifier tous les jobseeker avec le statut PRE_INTERVIEW n'ayant pas soumis de document
    @Scheduled(cron = "0 0 8 * * ?") // Notifier Tous les jours à 8h
    public void sendDocumentReminders() {
        jobOfferService.sendDocumentReminders();
    }

    // envoyer ses documents
    @PostMapping(value = "/submit-documents", consumes = MediaType.ALL_VALUE)
    public ResponseEntity<?> submitDocuments(
            @RequestParam Long applicationId, @RequestParam("file") MultipartFile file) {
        return jobOfferService.submitDocument(applicationId, file);
    }

//    // programmer les interview
//    @PostMapping("/schedule-interview")
//    public ResponseEntity<?> scheduleInterview(@RequestParam Long applicationId,
//            @RequestBody List<InterviewStepDTO> steps) {
//
//        return jobOfferService.scheduleInterview(applicationId, steps);
//    }
    @PostMapping("/schedule-interview/{applicationId}")
    public ResponseEntity<?> scheduleInterviewForCandidate(
            @PathVariable Long applicationId,
            @RequestBody InterviewScheduleDTO stepDTO) {
        return jobOfferService.scheduleInterviewForCandidate(applicationId, stepDTO);
    }

    @PostMapping("/schedule-interview-group")
    public ResponseEntity<?> scheduleInterviewForGroup(
            @RequestBody GroupInterviewScheduleDTO groupInterviewDTO) {
        return jobOfferService.scheduleInterviewForGroup(groupInterviewDTO);
    }

    @GetMapping("/interview-steps/{applicationId}")
    public ResponseEntity<?> getInterviewSteps(@PathVariable Long applicationId) {
        return jobOfferService.getInterviewSteps(applicationId);
    }

    @GetMapping("/group-interview-steps/{groupId}")
    public ResponseEntity<?> getGroupInterviewSteps(@PathVariable Long groupId) {
        return jobOfferService.getGroupInterviewSteps(groupId);
    }

    // add note
    @PostMapping("/update")
    public ResponseEntity<?> updateJobApplication(@RequestBody JobApplicationUpdateDTO dto) {
        JobApplication updatedApplication = jobOfferService.updateJobApplication(dto);
        return ResponseEntity.ok(updatedApplication);
    }

    // get job seeker with document submitted
    @GetMapping("/document-submitted/{jobOfferId}")
    public ResponseEntity<?> getJobSeekersWithDocumentSubmitted(@AuthenticationPrincipal UserDetailsImpl userDetail, @PathVariable Long jobOfferId) {

        Optional<JobOffer> offers = jobOfferService.getJobOfferById(jobOfferId);
        JobOffer offer;
        try{
        offer = offers.get();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response(HttpStatus.NOT_FOUND.value(), "Job offer not found"));
        }
        
        User user = userRepository.findByEmail(userDetail.getUsername()).orElseThrow(() -> new RuntimeException("user not found"));
        List<JobApplication> applications = jobOfferService.getApplicationsWithDocumentSubmitted(user, offer);

        List<Map<String, Object>> response = applications.stream().map(application -> {
            Map<String, Object> data = new HashMap<>();
            data.put("jobApplicationId", application.getId());
            data.put("jobSeekerName", application.getJobSeeker().getName());
            data.put("jobSeekerEmail", application.getJobSeeker().getEmail());
            data.put("documentPath", application.getDocumentPath());
            return data;
        }).collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    
    // download document for jobseeker when submitted
    @GetMapping("/download/{jobApplicationId}")
    public ResponseEntity<Resource> downloadSubmittedDocument(@PathVariable Long jobApplicationId) {
        JobApplication application = jobApplicationRepository.findById(jobApplicationId)
                .orElseThrow(() -> new EntityNotFoundException("JobApplication not found"));

        if (application.getDocumentPath() == null || application.getDocumentPath().isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        File file = new File(application.getDocumentPath());
        if (!file.exists()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        Resource resource = new FileSystemResource(file);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getName() + "\"")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }
    
    @GetMapping("/interviews/{jobOfferId}")
    public List<InterviewGroupDTO> getAllInterview(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long jobOfferId) {
        User user = userRepository.findByEmail(userDetails.getUsername()).orElseThrow(() -> new RuntimeException("User not found"));
        if(!(user instanceof Employer)) 
            return null;
        
        Optional<JobOffer> offer = jobOfferService.getJobOfferById(jobOfferId);
        
        return jobOfferService.getAllInterview(offer.get(), user);
    }

    @GetMapping("/export")
    public ResponseEntity<?> exportJobApplications(@RequestParam Long offerId,
                                                   @RequestParam(required = false) String filter) {
        // Récupérer les JobApplications en fonction du filtre
        List<JobApplication> jobApplications;
        
        if ("invited".equalsIgnoreCase(filter)) {
            jobApplications = jobApplicationRepository.findByJobOfferIdAndIsProposed(offerId, true);
        } else if ("self-applied".equalsIgnoreCase(filter)) {
            jobApplications = jobApplicationRepository.findByJobOfferIdAndIsProposed(offerId, false);
        } else {
            jobApplications = jobApplicationRepository.findByJobOfferId(offerId);
        }

        // Générer le CSV
        String csvContent = exportService.exportJobApplicationsToCSV(jobApplications);

        // Retourner le CSV avec les entêtes appropriés
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=job_applications.csv")
                .contentType(MediaType.parseMediaType("text/csv"))
                .body(csvContent);
    }

}
