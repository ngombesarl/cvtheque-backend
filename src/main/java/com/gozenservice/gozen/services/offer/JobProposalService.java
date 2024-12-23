/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gozenservice.gozen.services.offer;

import com.gozenservice.gozen.models.ApplicationStatus;
import com.gozenservice.gozen.models.JobApplication;
import com.gozenservice.gozen.models.JobOffer;
import com.gozenservice.gozen.models.JobProposal;
import com.gozenservice.gozen.models.JobSeeker;
import com.gozenservice.gozen.models.ProposalStatus;
import com.gozenservice.gozen.models.User;
import com.gozenservice.gozen.repository.UserRepository;
import com.gozenservice.gozen.repository.offer.JobApplicationRepository;
import com.gozenservice.gozen.repository.offer.JobOfferRepository;
import com.gozenservice.gozen.repository.offer.JobProposalRepository;
import com.gozenservice.gozen.services.EmailService;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author TCHINGANG Steve
 */
@Service
@RequiredArgsConstructor
public class JobProposalService {

    private final JobProposalRepository jobProposalRepository;
    private final JobOfferRepository jobOfferRepository;
    private final UserRepository userRepository;
    @Autowired
    private JobApplicationRepository jobApplicationRepository;
    @Autowired
    private EmailService emailService;

    @Transactional
    public void proposeJob(Long jobOfferId, List<Long> jobSeekerIds, User employer) {
        // Vérification de l'offre d'emploi
        JobOffer jobOffer = jobOfferRepository.findById(jobOfferId)
                .orElseThrow(() -> new RuntimeException("Job offer not found"));

        // Vérifier que l'utilisateur est l'employeur de l'offre
        if (!jobOffer.getEmployer().getId().equals(employer.getId())) {
            throw new RuntimeException("You are not authorized to propose this job offer.");
        }

        // Liste des Job Seekers exclus
        StringBuilder excludedJobSeekers = new StringBuilder();

        // Créer des propositions pour chaque postulant
        for (Long jobSeekerId : jobSeekerIds) {
            User jobSeeker = userRepository.findById(jobSeekerId)
                    .orElseThrow(() -> new RuntimeException("Job seeker not found"));

            // Vérifier si une proposition existe déjà
            boolean exists = jobProposalRepository.existsByJobOfferAndJobSeeker(jobOffer, jobSeeker);

            if (exists) {
                // Ajouter à la liste des exclus
                excludedJobSeekers.append("Job seeker ").append(jobSeeker.getName())
                        .append(" already has a proposal.\n");
                continue;
            }

            // Créer et enregistrer une nouvelle proposition
            JobProposal proposal = new JobProposal();
            proposal.setJobOffer(jobOffer);
            proposal.setJobSeeker(jobSeeker);
            proposal.setEmployer(employer);

            jobProposalRepository.save(proposal);
        }

        // Si des postulants ont été exclus, retourner un message d'erreur
        if (excludedJobSeekers.length() > 0) {
            throw new RuntimeException("Some job seekers were excluded:\n" + excludedJobSeekers);
        }
    }

    @Transactional
    public void updateProposalStatus(Long proposalId, ProposalStatus status, User jobSeeker) {
        // Vérification de la proposition
        JobProposal proposal = jobProposalRepository.findById(proposalId)
                .orElseThrow(() -> new RuntimeException("Job proposal not found"));

        // Vérifier que l'utilisateur est bien le postulant associé à la proposition
//        if (!proposal.getJobSeeker().getId().equals(jobSeeker.getId())) {
//            throw new RuntimeException("You are not authorized to update this proposal.");
//        }
        // Mise à jour du statut
        proposal.setStatus(status);
        if (status == ProposalStatus.ACCEPTED) {
            JobApplication application = new JobApplication();
            JobOffer jobOffer = jobOfferRepository.findById(proposal.getJobOffer().getId())
                    .orElseThrow(() -> new RuntimeException("Job offer not found"));
            //application.setAdditionalSkills(request.getAdditionalSkills());

            application.setJobOffer(jobOffer);
            application.setStatus(ApplicationStatus.PRE_INTERVIEW);
            application.setJobSeeker((JobSeeker) jobSeeker);
            application.setIsProposed(Boolean.TRUE);

            String object = "Next Steps: Documents submission";
            String message = "You have accepted the job proposition. <br> Please submit your document for pre-interview.";
            String email = jobSeeker.getEmail();

            try {
                emailService.envoyerMailHTML(email, object, message);
            } catch (Exception e) {
                new RuntimeException("An error occur while sending email to jobSeeker");
            }
            String objectEmpl = String.format("User %s accept your proposition", proposal.getEmployer().getName());
            String messageEmp = String.format("User %s accept your proposition on job with title : %s", proposal.getJobSeeker().getName(), proposal.getJobOffer().getTitle());
            String emailEmp = proposal.getEmployer().getEmail();

            try {
                emailService.envoyerMailHTML(emailEmp, objectEmpl, messageEmp);
            } catch (Exception e) {
                new RuntimeException("An error occur while sending email to recruiter");
            }

            jobApplicationRepository.save(application);
        }
        jobProposalRepository.save(proposal);
    }

    public List<JobProposal> getProposalsForJobSeeker(User jobSeeker) {
        List<JobProposal> proposals = jobProposalRepository.findByJobSeekerId(jobSeeker.getId());
        List<JobProposal> allProposals = new ArrayList<>();
        for (JobProposal proposal : proposals) {
            if (proposal.getStatus() != ProposalStatus.ABORTED) {
                allProposals.add(proposal);
            }
        }
        return allProposals;
    }

    // Récupérer les propositions pour un employeur
    public List<JobProposal> getProposalsForEmployer(User employer) {
        return jobProposalRepository.findByEmployerId(employer.getId());
    }

    public List<JobProposal> filterByStatus(ProposalStatus status) {
        return jobProposalRepository.findByStatus(status);
    }

    // Retirer une proposition
    @Transactional
    public void removeProposal(Long proposalId, User employer) {
        JobProposal proposal = jobProposalRepository.findById(proposalId)
                .orElseThrow(() -> new RuntimeException("Job proposal not found"));

        if (!proposal.getJobOffer().getEmployer().getId().equals(employer.getId())) {
            throw new RuntimeException("You are not authorized to remove this proposal.");
        }

        proposal.setStatus(ProposalStatus.ABORTED);
        jobProposalRepository.save(proposal);
    }
}
