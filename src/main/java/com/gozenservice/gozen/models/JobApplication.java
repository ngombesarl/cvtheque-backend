/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gozenservice.gozen.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;

/**
 *
 * @author TCHINGANG Steve
 */
@Data
@Entity
public class JobApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonBackReference("job-application")
    private JobOffer jobOffer;

    @Enumerated(EnumType.STRING)
    private ApplicationStatus status; // PRE-INTERVIEW, INTERVIEW_SCHEDULED, etc.

    //private List<String> additionalSkills;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_seeker_id", nullable = false)
    private JobSeeker jobSeeker;
    
    @Lob
    private byte[] cv; // Stockage du CV en binaire

    private String cvFileName;

    @Lob
    private byte[] coverLetter; // Stockage de la lettre de motivation

    private String coverLetterFileName;
    
    private LocalDateTime applicationDate = LocalDateTime.now();
    
    private Boolean isProposed = Boolean.FALSE;

    private LocalDateTime lastDocumentSubmission;
    
    @Lob
    private String documentPath; //chemin ou url du fichier soumis
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "jobApplication")
    @JsonManagedReference("interview-step")
    private List<InterviewStep> interviewSteps = new ArrayList<>();
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonManagedReference("interview-group-application")
    private InterviewGroup interviewGroup;
    
    private Double score; // Note donnée après l'interview

    private String comment; // Commentaire facultatif
    
    private Boolean documentSend = Boolean.FALSE;
    // Getters and Setters
}
