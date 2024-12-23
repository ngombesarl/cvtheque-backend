/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gozenservice.gozen.dto;

import com.gozenservice.gozen.models.Education;
import com.gozenservice.gozen.models.Experience;
import com.gozenservice.gozen.models.Interest;
import com.gozenservice.gozen.models.Language;
import com.gozenservice.gozen.models.Reference;
import com.gozenservice.gozen.models.Skill;
import com.gozenservice.gozen.models.Social;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;

/**
 *
 * @author TCHINGANG Steve
 */
@Data
public class CVDTO {

    private Long id;
    private String status;
    private String description;
    private String jobTitle;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private List<Skill> skills;
    private List<Experience> experiences;
    private List<Education> educations;
    private List<Reference> references;
    private List<Interest> interests;
    private List<Social> socials;
    private List<Language> languages;

    // Getters et setters
}
