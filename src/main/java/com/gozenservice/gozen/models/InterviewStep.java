/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.gozenservice.gozen.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import java.time.LocalDate;
import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 *
 * @author TCHINGANG Steve
 */
@Entity
@Data
@RequiredArgsConstructor
public class InterviewStep {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JsonBackReference("interview-step")
    private JobApplication jobApplication;
    
    private String name;  // Nom de l'étape
    private String description;
    
    private LocalDate date;
    private String time;  // Heure au format HH:mm
    private String place;  // Lieu de l'interview
    
    @Lob
    private String interviewerEmails;
    
    @ManyToOne
    @JsonBackReference("interview-step")
    private InterviewGroup interviewGroup; // Le groupe d'entretien auquel cette étape appartient
}
