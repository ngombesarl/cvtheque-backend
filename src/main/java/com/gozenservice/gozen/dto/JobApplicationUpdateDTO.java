/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.gozenservice.gozen.dto;

import com.gozenservice.gozen.models.ApplicationStatus;
import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 *
 * @author TCHINGANG Steve
 */
@Data
@RequiredArgsConstructor
public class JobApplicationUpdateDTO {

    private Long jobApplicationId; // ID de la candidature
    private Double score; // Note donnée au candidat
    private ApplicationStatus status; // Nouveau statut de la candidature
    private String comment; // Commentaire facultatif
}
