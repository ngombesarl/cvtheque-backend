/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */

package com.gozenservice.gozen.repository;

import com.gozenservice.gozen.models.JobSeeker;
import java.util.List;

/**
 *
 * @author TCHINGANG Steve
 */
public interface JobSeekerCustomRepository {
    List<JobSeeker> findByFilters(List<String> skills, List<String> jobTitles, List<String> languages);
}

