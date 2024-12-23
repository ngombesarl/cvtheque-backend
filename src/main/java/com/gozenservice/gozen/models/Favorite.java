/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gozenservice.gozen.models;

/**
 *
 * @author TCHINGANG Steve
 */
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import java.util.HashMap;
import java.util.Map;
import lombok.Data;

@Entity
@Data
public class Favorite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employer_id")
    @JsonBackReference("favorites")
    private User employer;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cv_id")
    @JsonBackReference("cv-favorites")
    private CV cv;

    public Favorite() {

    }

    public Favorite(User employer, CV cv) {
        this.employer = employer;
        this.cv = cv;
    }

    public Map<String, Object> toResponse() {
        Map<String, Object> json = new HashMap<>();

        json.put("employer", employer);
        json.put("cv", cv);

        return json;
    }

    // Getters et setters
}
