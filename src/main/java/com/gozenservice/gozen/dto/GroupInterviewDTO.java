/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.gozenservice.gozen.dto;

import java.time.LocalDate;
import java.util.List;
import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 *
 * @author TCHINGANG Steve
 */
@Data
@RequiredArgsConstructor
public class GroupInterviewDTO {

    private List<Long> applicationIds; // Liste des IDs des candidatures
    private String name; // Nom de l'étape
    private String description;
    private LocalDate date;
    private String time; // Heure au format HH:mm
    private String place;
    private String interviewerEmails; // Emails des interviewers, séparés par des ;

}
