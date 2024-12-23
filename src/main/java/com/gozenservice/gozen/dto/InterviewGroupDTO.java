/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.gozenservice.gozen.dto;

import com.gozenservice.gozen.models.InterviewStep;
import java.util.Date;
import java.util.List;
import lombok.Data;

/**
 *
 * @author TCHINGANG Steve
 */
@Data
public class InterviewGroupDTO {
    private Long id;
    private Date date;
    private List<JobSeekerDTO> jobSeekers;
    private List<InterviewStep> steps;

    // Getters and Setters
}

