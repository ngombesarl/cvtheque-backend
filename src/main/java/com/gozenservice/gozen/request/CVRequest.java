/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.gozenservice.gozen.request;

import com.gozenservice.gozen.models.Education;
import com.gozenservice.gozen.models.Experience;
import com.gozenservice.gozen.models.Reference;
import com.gozenservice.gozen.models.Skill;
import java.util.List;
import lombok.Data;

/**
 *
 * @author TCHINGANG Steve
 */
@Data
public class CVRequest {

    private Long userId;
    private String status;
    private String description;
    private String jobTitle;
    private List<Skill> skills;
    private List<Experience> experiences;
    private List<Education> educations;
    private List<Reference> references;
    private int experiencePoints;

}

