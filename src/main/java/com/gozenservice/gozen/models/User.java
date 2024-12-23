/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.gozenservice.gozen.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

/**
 *
 * @author TCHINGANG STEVE 
 */
@Data
@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.JOINED)
@RequiredArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name; // Nom pour les chercheurs d'emploi ou nom de la structure pour les employeurs

    @Column(nullable = true)
    private String surname;

    @Column
    private String phone;

    @Column
    private String profilePhotoUrl;

    private String passwordResetToken;
    private Long tokenExpirationDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id", nullable = false)
    private Role roles;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
    
    private boolean isVerified = false; // Indique si l'utilisateur a validé son email
    private String verificationCode; // Code de vérification envoyé à l'utilisateur

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
    
    public Map<String, Object> toJson() {
        Map<String, Object> json = new HashMap<>();
        json.put("id", id);
        json.put("username", username);
        json.put("email", email);
        json.put("name", name);
        json.put("surname", surname);
        json.put("phone", phone);
        json.put("profilePhotoUrl", profilePhotoUrl);
        
        return json;
    }
    
    @JsonProperty("profilePhotoUrl")
    public String getProfilePhotoUrl() {
        return profilePhotoUrl; // Construction de l'URL complète
    } 
    
    public User(Long id) {
        this.id = id;
    }
}
