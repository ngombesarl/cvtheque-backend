/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.gozenservice.gozen.dto;

import java.util.List;
import lombok.Data;

/**
 *
 * @author TCHINGANG Steve
 */
@Data
public class GroupInterviewScheduleDTO {

    private Long jobOfferId; // Offre d'emploi associée

    private List<InterviewStepDTO> steps; // Étapes d'entretien du groupe

    private List<Long> jobApplicationIds; // Candidatures associées au groupe
}
