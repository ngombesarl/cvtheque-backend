/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.gozenservice.gozen.dto;

import com.gozenservice.gozen.dto.InterviewStepDTO;
import java.util.List;
import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 *
 * @author TCHINGANG Steve
 */
@Data
@RequiredArgsConstructor
public class InterviewScheduleDTO {

    private Long jobApplicationId; // ID de la candidature
    private List<InterviewStepDTO> steps; // Liste des étapes

}