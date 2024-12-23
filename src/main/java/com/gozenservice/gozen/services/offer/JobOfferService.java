/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gozenservice.gozen.services.offer;

/**
 *
 * @author TCHINGANG Steve
 */
import com.gozenservice.gozen.dto.GroupInterviewScheduleDTO;
import com.gozenservice.gozen.dto.InterviewGroupDTO;
import com.gozenservice.gozen.dto.InterviewScheduleDTO;
import com.gozenservice.gozen.dto.InterviewStepDTO;
import com.gozenservice.gozen.dto.JobApplicationUpdateDTO;
import com.gozenservice.gozen.dto.JobSeekerDTO;
import com.gozenservice.gozen.models.ApplicationStatus;
import com.gozenservice.gozen.models.InterviewGroup;
import com.gozenservice.gozen.models.InterviewStep;
import com.gozenservice.gozen.models.JobApplication;
import com.gozenservice.gozen.models.JobOffer;
import com.gozenservice.gozen.models.JobRequirement;
import com.gozenservice.gozen.models.JobSeeker;
import com.gozenservice.gozen.models.User;
import com.gozenservice.gozen.repository.UserRepository;
import com.gozenservice.gozen.repository.offer.InterviewGroupRepository;
import com.gozenservice.gozen.repository.offer.InterviewStepRepository;
import com.gozenservice.gozen.repository.offer.JobApplicationRepository;
import com.gozenservice.gozen.repository.offer.JobOfferRepository;
import com.gozenservice.gozen.request.offer.JobApplicationRequest;
import com.gozenservice.gozen.request.offer.JobOfferRequest;
import com.gozenservice.gozen.response.Response;
import com.gozenservice.gozen.services.EmailService;
import com.gozenservice.gozen.services.FileStorageService;
import jakarta.persistence.EntityNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class JobOfferService {

    @Autowired
    private JobOfferRepository jobOfferRepository;

    @Autowired
    private JobApplicationRepository jobApplicationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private InterviewStepRepository interviewStepRepository;

    @Autowired
    private InterviewGroupRepository interviewGroupRepository;

    public JobOffer createJobOffer(JobOfferRequest request) {
        JobOffer jobOffer = new JobOffer();
        jobOffer.setTitle(request.getTitle());
        jobOffer.setDescription(request.getDescription());
        jobOffer.setLocation(request.getLocation());
        jobOffer.setSalary(request.getSalary());
        jobOffer.setStatus(request.getStatus());
        jobOffer.setEmploymentType(request.getEmploymentType());
        jobOffer.setResponsibilities(request.getResponsibilities());
        jobOffer.setApplicationDeadline(request.getApplicationDeadline());
        jobOffer.setBenefits(request.getBenefits());
        jobOffer.setCompany(request.getCompany());
        jobOffer.setContactEmail(request.getContactEmail());
        jobOffer.setWorkLocation(request.getWorkLocation());
        JobRequirement requirements = request.getRequirements();
        requirements.setJobOffer(jobOffer);
//        List<JobRequirement> requirement = new ArrayList<>();
//        for (JobRequirement r : request.getRequirements()) {
//            r.setJobOffer(jobOffer);
//            requirement.add(r);
//        }
        jobOffer.setRequirements(requirements);
        jobOffer.setEmployer(request.getEmployer());
        return jobOfferRepository.save(jobOffer);
    }

    public JobOffer updateJobOffer(Long jobOfferId, JobOfferRequest request) {
        JobOffer jobOffer = jobOfferRepository.findById(jobOfferId).orElseThrow(() -> new RuntimeException("Offer not found"));
        jobOffer.setTitle(request.getTitle());
        jobOffer.setDescription(request.getDescription());
        jobOffer.setLocation(request.getLocation());
        jobOffer.setSalary(request.getSalary());
        jobOffer.setStatus(request.getStatus());
        jobOffer.setEmploymentType(request.getEmploymentType());
        jobOffer.setResponsibilities(request.getResponsibilities());
        jobOffer.setApplicationDeadline(request.getApplicationDeadline());
        jobOffer.setBenefits(request.getBenefits());
        jobOffer.setCompany(request.getCompany());
        jobOffer.setContactEmail(request.getContactEmail());
        jobOffer.setWorkLocation(request.getWorkLocation());
        JobRequirement requirements = request.getRequirements();
        requirements.setJobOffer(jobOffer);
//        List<JobRequirement> requirement = new ArrayList<>();
//        for (JobRequirement r : request.getRequirements()) {
//            r.setJobOffer(jobOffer);
//            requirement.add(r);
//        }
        jobOffer.setRequirements(requirements);
        jobOffer.setEmployer(request.getEmployer());
        return jobOfferRepository.save(jobOffer);
    }

    public List<JobOffer> getAllJobOffers() {
        return jobOfferRepository.findAll();
    }

    public List<JobOffer> getAllJobOffersByEmployerId(Long employerId) {
        return jobOfferRepository.findByEmployerId(employerId);
    }

    public Optional<JobOffer> getJobOfferById(Long id) {
        return jobOfferRepository.findById(id);
    }

    public JobApplication applyToJob(JobApplicationRequest request, MultipartFile cv, MultipartFile coverLetter) {
        JobApplication application = new JobApplication();
        JobOffer jobOffer = jobOfferRepository.findById(request.getJobOfferId())
                .orElseThrow(() -> new RuntimeException("Job offer not found"));
        //application.setAdditionalSkills(request.getAdditionalSkills());

        User jobSeeker = userRepository.findByEmail(request.getApplicantEmail()).get();

        application.setJobOffer(jobOffer);
        application.setStatus(ApplicationStatus.PENDING);
        application.setJobSeeker((JobSeeker) jobSeeker);
        application.setIsProposed(Boolean.FALSE);

        if (cv != null) {
            try {
                application.setCv(cv.getBytes());
                application.setCvFileName(cv.getOriginalFilename());
            } catch (IOException ex) {
                Logger.getLogger(JobOfferService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        if (coverLetter != null) {
            try {
                application.setCoverLetter(coverLetter.getBytes());
                application.setCoverLetterFileName(coverLetter.getOriginalFilename());
            } catch (IOException ex) {
                Logger.getLogger(JobOfferService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return jobApplicationRepository.save(application);
    }

    public JobApplication getApplicationById(Long id) {
        return jobApplicationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Application not found"));
    }

    public List<Map<String, Object>> getAllApplications() {
        List<JobApplication> applications = jobApplicationRepository.findAll();

        return applications.stream().map(application -> {
            Map<String, Object> appJson = new HashMap<>();
            appJson.put("id", application.getId());
            appJson.put("status", application.getStatus());
            appJson.put("jobOfferTitle", application.getJobOffer().getTitle());
            appJson.put("jobSeekerName", application.getJobSeeker().getName());
            appJson.put("applicationDate", application.getApplicationDate());
            appJson.put("userId", application.getJobSeeker().getId());
            appJson.put("jobId", application.getJobOffer().getId());

            // Ajouter le CV si disponible
            if (application.getCv() != null) {
                appJson.put("cvFileName", application.getCvFileName());
                appJson.put("cvContent", Base64.getEncoder().encodeToString(application.getCv()));
            }

            // Ajouter la lettre de motivation si disponible
            if (application.getCoverLetter() != null) {
                appJson.put("coverLetterFileName", application.getCoverLetterFileName());
                appJson.put("coverLetterContent", Base64.getEncoder().encodeToString(application.getCoverLetter()));
            }

            return appJson;
        }).toList();
    }

    public List<Map<String, Object>> getAllApplicationsByOffer(Long jobOfferId) {
        List<JobApplication> applications = jobApplicationRepository.findByJobOfferId(jobOfferId);

        return applications.stream().map(application -> {
            Map<String, Object> appJson = new HashMap<>();
            appJson.put("id", application.getId());
            appJson.put("status", application.getStatus());
            appJson.put("jobOfferTitle", application.getJobOffer().getTitle());
            appJson.put("jobSeekerName", application.getJobSeeker().getName());
            appJson.put("applicationDate", application.getApplicationDate());
            appJson.put("userId", application.getJobSeeker().getId());
            appJson.put("jobId", application.getJobOffer().getId());

            // Ajouter le CV si disponible
            if (application.getCv() != null) {
                appJson.put("cvFileName", application.getCvFileName());
                appJson.put("cvContent", Base64.getEncoder().encodeToString(application.getCv()));
            }

            // Ajouter la lettre de motivation si disponible
            if (application.getCoverLetter() != null) {
                appJson.put("coverLetterFileName", application.getCoverLetterFileName());
                appJson.put("coverLetterContent", Base64.getEncoder().encodeToString(application.getCoverLetter()));
            }

            return appJson;
        }).toList();
    }

    public List<Map<String, Object>> getAllApplicationsByJobSeeker(Long jobSeekerId) {
        List<JobApplication> applications = jobApplicationRepository.findByJobSeekerId(jobSeekerId);

        return applications.stream().map(application -> {
            Map<String, Object> appJson = new HashMap<>();
            appJson.put("id", application.getId());
            appJson.put("status", application.getStatus());
            appJson.put("jobOfferTitle", application.getJobOffer().getTitle());
            appJson.put("jobSeekerName", application.getJobSeeker().getName());
            appJson.put("applicationDate", application.getApplicationDate());
            appJson.put("userId", application.getJobSeeker().getId());
            appJson.put("jobId", application.getJobOffer().getId());

            // Ajouter le CV si disponible
            if (application.getCv() != null) {
                appJson.put("cvFileName", application.getCvFileName());
                appJson.put("cvContent", Base64.getEncoder().encodeToString(application.getCv()));
            }

            // Ajouter la lettre de motivation si disponible
            if (application.getCoverLetter() != null) {
                appJson.put("coverLetterFileName", application.getCoverLetterFileName());
                appJson.put("coverLetterContent", Base64.getEncoder().encodeToString(application.getCoverLetter()));
            }

            return appJson;
        }).toList();
    }

//    public JobApplication applyToJob(JobApplicationRequest request) {
//        JobOffer jobOffer = jobOfferRepository.findById(request.getJobOfferId())
//                .orElseThrow(() -> new RuntimeException("Job offer not found"));
//
//        JobApplication application = new JobApplication();
//        application.setAdditionalSkills(request.getAdditionalSkills());
//        application.setJobOffer(jobOffer);
//        application.setStatus("PENDING");
//
//        return jobApplicationRepository.save(application);
//    }
    public void delteJobOffer(Long id) {
        JobOffer offer = jobOfferRepository.findById(id).orElseThrow(() -> new RuntimeException("Job Offer not found"));
        jobOfferRepository.deleteById(id);
    }

    // Submit document
    public ResponseEntity<?> submitDocument(Long applicationId,
            MultipartFile file
    ) {
        JobApplication application = jobApplicationRepository.findById(applicationId)
                .orElseThrow(() -> new EntityNotFoundException("JobApplication not found"));

        if (file.isEmpty() || (!file.getContentType().equals("application/pdf") && !file.getContentType().contains("word"))) {
            return ResponseEntity.badRequest().body(new Response(HttpStatus.BAD_REQUEST.value(), "Only PDF or WOR accepted"));
        }

        try {
            // Sauvegarde du fichier
            String filePath = fileStorageService.saveDocumentFile(file);
            application.setDocumentPath(filePath);
            application.setDocumentSend(Boolean.TRUE);
            application.setLastDocumentSubmission(LocalDateTime.now());

            jobApplicationRepository.save(application);

            emailService.envoyerMailHTML(application.getJobOffer().getEmployer().getEmail(), "Documents Submitted",
                    String.format("<p>The job seeker %s has submitted their documents.</p>", application.getJobSeeker().getName()));

            return ResponseEntity.ok().body(new Response(HttpStatus.OK.value(), "Documents submitted successfully"));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to save document");
        }

    }

    // Programmation des interview
    public ResponseEntity<?> scheduleInterview(Long applicationId, List<InterviewStepDTO> steps) {
        JobApplication application = jobApplicationRepository.findById(applicationId)
                .orElseThrow(() -> new EntityNotFoundException("JobApplication not found"));

        for (InterviewStepDTO stepDTO : steps) {
            InterviewStep step = new InterviewStep();
            step.setJobApplication(application);
            step.setName(stepDTO.getName());
            step.setDescription(stepDTO.getDescription());
            step.setDate(stepDTO.getDate());
            step.setTime(stepDTO.getTime());
            step.setPlace(stepDTO.getPlace());
            step.setInterviewerEmails(stepDTO.getInterviewerEmails());
            interviewStepRepository.save(step);

            // Envoyer une notification aux interviewers
            String[] emails = stepDTO.getInterviewerEmails().split(";");
            for (String email : emails) {
                emailService.envoyerMailHTML(
                        email.trim(),
                        "Interview Scheduled",
                        "<p>You are scheduled for an interview step: " + stepDTO.getName() + " on " + stepDTO.getDate()
                        + " at " + stepDTO.getPlace() + ".</p>"
                );
            }
        }

        return ResponseEntity.ok("Interview steps scheduled successfully.");
    }

    public void sendDocumentReminders() {
        List<JobApplication> pendingApplications = jobApplicationRepository.findByStatusAndLastDocumentSubmissionBefore(ApplicationStatus.PRE_INTERVIEW, LocalDateTime.now().minusDays(2));

        List<User> notNotifiedUsers = new ArrayList<>();
        for (JobApplication application : pendingApplications) {
            try {
                emailService.envoyerMailHTML(application.getJobSeeker().getEmail(),
                        "Reminder: Submit Your Documents",
                        "<p>Please submit your documents for the interview.</p>");
            } catch (Exception e) {
                notNotifiedUsers.add(application.getJobSeeker());
            }
        }

        if (!notNotifiedUsers.isEmpty()) {
            System.out.println("Failed to notify: " + notNotifiedUsers.stream().map(User::getName)
                    .collect(Collectors.joining(", ")));
        }
    }

    public ResponseEntity<?> scheduleInterviewForCandidate(
            Long applicationId,
            InterviewScheduleDTO interviewScheduleDTO) {
        // Récupérer la candidature spécifique
        JobApplication application = jobApplicationRepository.findById(applicationId)
                .orElseThrow(() -> new EntityNotFoundException("JobApplication not found"));

        for (InterviewStepDTO stepDTO : interviewScheduleDTO.getSteps()) {
            InterviewStep step = new InterviewStep();
            step.setJobApplication(application);
            step.setName(stepDTO.getName());
            step.setDescription(stepDTO.getDescription());
            step.setDate(stepDTO.getDate());
            step.setTime(stepDTO.getTime());
            step.setPlace(stepDTO.getPlace());
            step.setInterviewerEmails(stepDTO.getInterviewerEmails());

            // Vérifier si l'interview existe déjà pour ce candidat à cette heure
            List<InterviewStep> existingSteps = interviewStepRepository.findByJobApplicationAndDateAndTime(application, stepDTO.getDate(), stepDTO.getTime());
            if (!existingSteps.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("This interview has already been scheduled for this candidate at the specified time.");
            }

            interviewStepRepository.save(step);

            application.setStatus(ApplicationStatus.INTERVIEW);
            jobApplicationRepository.save(application);
        }

        // Envoyer des notifications aux interviewers
        String[] emails = interviewScheduleDTO.getSteps().get(0).getInterviewerEmails().split(";");
        for (String email : emails) {
            emailService.envoyerMailHTML(
                    email.trim(),
                    "Interview Scheduled for " + application.getJobSeeker().getName(),
                    "<p>You are scheduled for an interview step: " + interviewScheduleDTO.getSteps().get(0).getName() + " on " + interviewScheduleDTO.getSteps().get(0).getDate()
                    + " at " + interviewScheduleDTO.getSteps().get(0).getPlace() + ".</p>"
            );
        }

        return ResponseEntity.ok("Interview step scheduled successfully for candidate " + application.getJobSeeker().getName());
    }

    public ResponseEntity<?> scheduleInterviewForGroup(
            GroupInterviewScheduleDTO groupInterviewScheduleDTO) {

        // Liste des candidatures pour les candidats sélectionnés
        List<JobApplication> applications = jobApplicationRepository.findAllById(groupInterviewScheduleDTO.getJobApplicationIds());

        // Récupérer l'offre d'emploi
        JobOffer jobOffer = jobOfferRepository.findById(groupInterviewScheduleDTO.getJobOfferId())
                .orElseThrow(() -> new EntityNotFoundException("JobOffer not found"));

        // Créer le groupe d'entretien
        InterviewGroup interviewGroup = new InterviewGroup();
        interviewGroup.setJobOffer(jobOffer);
        interviewGroupRepository.save(interviewGroup);

        // Créer les étapes d'entretien
        for (InterviewStepDTO stepDTO : groupInterviewScheduleDTO.getSteps()) {
            InterviewStep step = new InterviewStep();
            step.setInterviewGroup(interviewGroup);
            step.setName(stepDTO.getName());
            step.setDescription(stepDTO.getDescription());
            step.setDate(stepDTO.getDate());
            step.setTime(stepDTO.getTime());
            step.setPlace(stepDTO.getPlace());
            step.setInterviewerEmails(stepDTO.getInterviewerEmails());
            interviewStepRepository.save(step);

        }

        // Associer les candidatures au groupe d'entretien
        for (Long applicationId : groupInterviewScheduleDTO.getJobApplicationIds()) {
            JobApplication application = jobApplicationRepository.findById(applicationId)
                    .orElseThrow(() -> new EntityNotFoundException("JobApplication not found"));

            // Vérifier si un candidat a déjà une étape d'entretien à cette heure
            List<InterviewStep> existingSteps = interviewStepRepository.findByJobApplicationAndDateAndTime(application,
                    groupInterviewScheduleDTO.getSteps().get(0).getDate(), groupInterviewScheduleDTO.getSteps().get(0).getTime());
            if (!existingSteps.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("This interview step has already been scheduled for this candidate at the specified time.");
            }

            application.setInterviewGroup(interviewGroup); // Associer la candidature au groupe
            application.setStatus(ApplicationStatus.INTERVIEW);
            jobApplicationRepository.save(application);

            // Envoyer une notification par e-mail à chaque candidat
            emailService.envoyerMailHTML(
                    application.getJobSeeker().getEmail(),
                    "Interview Invitation for " + jobOffer.getTitle(),
                    "<p>You have been invited to a interview for the job position: " + jobOffer.getTitle()
                    + ". Please check your interview steps.</p>"
            );
        }

        // Envoyer des notifications aux interviewers pour chaque candidat
        String[] emails = groupInterviewScheduleDTO.getSteps().get(0).getInterviewerEmails().split(";");
        for (String email : emails) {
            emailService.envoyerMailHTML(
                    email.trim(),
                    "Interview Scheduled for Group",
                    "<p>You are scheduled for an interview step: " + groupInterviewScheduleDTO.getSteps().get(0).getName() + " on " + groupInterviewScheduleDTO.getSteps().get(0).getDate()
                    + " at " + groupInterviewScheduleDTO.getSteps().get(0).getPlace() + " for the selected candidates.</p>"
            );
        }

        return ResponseEntity.ok("Interview steps scheduled successfully for the selected group of candidates.");
    }

    public ResponseEntity<?> getInterviewSteps(Long applicationId) {
        List<InterviewStep> steps = interviewStepRepository.findByJobApplicationId(applicationId);
        return ResponseEntity.ok(steps);
    }

    public ResponseEntity<?> getGroupInterviewSteps(Long groupId) {
        InterviewGroup interviewGroup = interviewGroupRepository.findById(groupId)
                .orElseThrow(() -> new EntityNotFoundException("InterviewGroup not found"));

        List<InterviewStep> steps = interviewGroup.getSteps(); // Liste des étapes pour ce groupe
        return ResponseEntity.ok(steps);
    }

    // add note after interview
    public JobApplication updateJobApplication(JobApplicationUpdateDTO dto) {
        // Récupérer la candidature
        JobApplication application = jobApplicationRepository.findById(dto.getJobApplicationId())
                .orElseThrow(() -> new EntityNotFoundException("JobApplication not found"));

        // Mettre à jour la note, le statut et le commentaire
        if (dto.getScore() != null) {
            application.setScore(dto.getScore());
        }
        if (dto.getStatus() != null) {
            application.setStatus(dto.getStatus());
        }
        if (dto.getComment() != null) {
            application.setComment(dto.getComment());
        }

        if (dto.getStatus() == ApplicationStatus.ACCEPTED) {
            emailService.envoyerMailHTML(
                    application.getJobSeeker().getEmail(),
                    "Congratulations!",
                    "<p>Dear " + application.getJobSeeker().getName() + ",</p>"
                    + "<p>We are pleased to inform you that you have been hired for the position: "
                    + application.getJobOffer().getTitle() + ".</p>"
            );
        } else if (dto.getStatus() == ApplicationStatus.REJECTED) {
            emailService.envoyerMailHTML(
                    application.getJobSeeker().getEmail(),
                    "Application Status",
                    "<p>Dear " + application.getJobSeeker().getName() + ",</p>"
                    + "<p>We regret to inform you that your application for the position: "
                    + application.getJobOffer().getTitle() + " has been rejected.</p>"
            );
        }

        // Sauvegarder les modifications
        return jobApplicationRepository.save(application);
    }

    // get all application with submitted document
    public List<JobApplication> getApplicationsWithDocumentSubmitted(User user, JobOffer jobOffer) {
        if(user.getId() == jobOffer.getEmployer().getId()) {
            return jobApplicationRepository.findByJobOfferIdAndDocumentSend(jobOffer.getId(), Boolean.TRUE);
        }
        else {
            throw new RuntimeException("Unauthorized");
        }
        
    }

    //export jobApplication 
//    public List<InterviewGroup> getAllInterview(JobOffer offer, User authenticatedUser) {
//        User user = userRepository.findById(offer.getEmployer().getId())
//                .orElseThrow(() -> new RuntimeException("User not found"));
//
//        List<InterviewGroup> groups = new ArrayList<>();
//
//        for (InterviewGroup group : interviewGroupRepository.findByJobOfferId(offer.getId())) {
//            if (user.getId() == authenticatedUser.getId()) {
//                group.setFirstName(group.getJobApplications().g);
//                groups.add(group);
//            }
//        }
//
//        return groups;
//    }
    
    public List<InterviewGroupDTO> getAllInterview(JobOffer offer, User authenticatedUser) {
    // Vérifier si l'utilisateur est l'employeur de l'offre
    if (!offer.getEmployer().getId().equals(authenticatedUser.getId())) {
        throw new RuntimeException("You are not authorized to access this data.");
    }

    // Récupérer les groupes d'interview pour l'offre d'emploi
    List<InterviewGroup> interviewGroups = interviewGroupRepository.findByJobOfferId(offer.getId());
    List<InterviewGroupDTO> result = new ArrayList<>();

    for (InterviewGroup group : interviewGroups) {
        // Transformer les données en DTO avec les informations des JobSeekers
        InterviewGroupDTO groupDTO = new InterviewGroupDTO();
        groupDTO.setId(group.getId());
        groupDTO.setSteps(group.getSteps());

        // Ajouter les informations des JobSeekers programmés
        List<JobSeekerDTO> jobSeekerDTOs = new ArrayList<>();
        for (JobApplication application : group.getJobApplications()) {
            JobSeeker jobSeeker = application.getJobSeeker();

            JobSeekerDTO jobSeekerDTO = new JobSeekerDTO();
            jobSeekerDTO.setId(jobSeeker.getId());
            jobSeekerDTO.setFirstName(jobSeeker.getName());
            jobSeekerDTO.setLastName(jobSeeker.getSurname());
            jobSeekerDTO.setEmail(jobSeeker.getEmail());
            jobSeekerDTO.setPhoneNumber(jobSeeker.getPhone());

            jobSeekerDTOs.add(jobSeekerDTO);
        }
        groupDTO.setJobSeekers(jobSeekerDTOs);

        result.add(groupDTO);
    }

    return result;
}


}
