/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.gozenservice.gozen.services;

import com.gozenservice.gozen.models.JobSeeker;
import com.gozenservice.gozen.repository.JobSeekerRepository;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 *
 * @author TCHINGANG Steve
 */
@Service
public class JobSeekerService {

    private final JobSeekerRepository jobSeekerRepository;

    public JobSeekerService(JobSeekerRepository jobSeekerRepository) {
        this.jobSeekerRepository = jobSeekerRepository;
    }

    public List<JobSeeker> filterJobSeekers(List<String> skills, List<String> jobTitles, List<String> languages) {
        return jobSeekerRepository.findByFilters(skills, jobTitles, languages);
    }
}

