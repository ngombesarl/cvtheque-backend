/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.gozenservice.gozen.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import java.util.List;
import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 *
 * @author TCHINGANG Steve
 */
@Entity
@Data
@RequiredArgsConstructor
public class JobRequirement {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String education;
    
    private String experience;
    
    @ElementCollection
    private List<String> skills;
    
    @OneToOne
    @JoinColumn(name = "jobOffer_id")
    @JsonBackReference("job-requirement")
    private JobOffer jobOffer;
    
}
