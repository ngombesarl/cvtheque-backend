/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.gozenservice.gozen.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.time.LocalDate;
import java.util.List;
import lombok.Data;

/**
 *
 * @author TCHINGANG Steve
 */
@Entity
@Data
public class InterviewGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonBackReference("interview-group")
    private JobOffer jobOffer; // Offre d'emploi à laquelle l'entretien est associé

    private String name; // Nom du groupe (par exemple "Session d'entretien groupe 1")
    private LocalDate groupDate; // Date du groupe d'entretien

    @OneToMany(mappedBy = "interviewGroup")
    @JsonManagedReference("interview-step")
    private List<InterviewStep> steps; // Liste des étapes pour ce groupe

    @OneToMany(mappedBy = "interviewGroup")
    @JsonBackReference("interview-group-application")
    private List<JobApplication> jobApplications; // Liste des candidatures associées à ce groupe
}
