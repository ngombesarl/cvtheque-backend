/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.gozenservice.gozen.services;

/**
 *
 * @author TCHINGANG Steve
 */

import com.gozenservice.gozen.models.*;
import com.gozenservice.gozen.repository.UserRepository;
import com.gozenservice.gozen.repository.offer.JobApplicationRepository;
import com.gozenservice.gozen.repository.offer.JobOfferRepository;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EmployerService {

    private final JobOfferRepository jobOfferRepository;
    private final JobApplicationRepository jobApplicationRepository;
    private final UserRepository userRepository;

    public EmployerService(JobOfferRepository jobOfferRepository, 
                           JobApplicationRepository jobApplicationRepository, 
                           UserRepository userRepository) {
        this.jobOfferRepository = jobOfferRepository;
        this.jobApplicationRepository = jobApplicationRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public JobOffer createJobOffer(JobOffer jobOffer, @AuthenticationPrincipal User employer) {
        if (!(employer instanceof Employer)) {
            throw new RuntimeException("Only employers can create job offers");
        }

        Employer employerEntity = (Employer) employer;
        jobOffer.setEmployer(employerEntity);
        return jobOfferRepository.save(jobOffer);
    }

    @Transactional
    public JobApplication applyForJob(Long jobOfferId, JobApplication jobApplication, @AuthenticationPrincipal User jobSeeker) {
        if (!(jobSeeker instanceof JobSeeker)) {
            throw new RuntimeException("Only job seekers can apply for jobs");
        }

        JobOffer jobOffer = jobOfferRepository.findById(jobOfferId)
                .orElseThrow(() -> new RuntimeException("Job offer not found"));

        JobSeeker jobSeekerEntity = (JobSeeker) jobSeeker;
        jobApplication.setJobSeeker(jobSeekerEntity);
        jobApplication.setJobOffer(jobOffer);
        return jobApplicationRepository.save(jobApplication);
    }
}
