/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.gozenservice.gozen.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 *
 * @author TCHINGANG Steve
 */
@Data
@Entity
@Table(name = "employers")
@RequiredArgsConstructor
public class Employer extends User {

    @Column(nullable = true)
    private String sector;

    @Column(nullable = true)
    private String activity;

    @OneToMany(mappedBy = "employer", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    //@JsonManagedReference
    private List<JobOffer> jobOffers = new ArrayList<>();
    
    @OneToMany(mappedBy = "employer", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("job-proposal")
    private  List<JobProposal> jobProposal = new ArrayList<>();
    
    @OneToMany(mappedBy = "employer", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("favorites")
    private List<Favorite> favorites;
    
//    @Override
//    public Map<String, Object> toJson() {
//        Map<String, Object> json = new HashMap<>();
//        json.put("sector", sector);
//        json.put("activity", activity);
//        json.put("email", jobOffers.toArray());
//        
//        return json;
//    }
}

