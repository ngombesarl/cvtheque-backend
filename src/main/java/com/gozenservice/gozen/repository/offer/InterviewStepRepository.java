/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */

package com.gozenservice.gozen.repository.offer;

import com.gozenservice.gozen.models.InterviewStep;
import com.gozenservice.gozen.models.JobApplication;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author TCHINGANG Steve
 */
public interface InterviewStepRepository extends JpaRepository<InterviewStep, Long>{
    List<InterviewStep> findByJobApplicationAndDateAndTime(JobApplication jobApplication, LocalDate date, String time);
    List<InterviewStep> findByJobApplicationId(Long jobApplicationId);
}
