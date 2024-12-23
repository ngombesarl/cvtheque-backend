/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.gozenservice.gozen.dto;

/**
 *
 * @author TCHINGANG Steve
 */

import com.gozenservice.gozen.request.cv.EducationRequest;
import com.gozenservice.gozen.request.cv.ExperienceRequest;
import com.gozenservice.gozen.request.cv.InterestRequest;
import com.gozenservice.gozen.request.cv.LanguageRequest;
import com.gozenservice.gozen.request.cv.SkillRequest;
import java.util.List;
import lombok.Data;

@Data
public class CvData {

    private String firstName;
    private String lastName;
    private String title;
    private String email;
    private String phone;
    private String address;
    private String linkedin;
    private String portraitUrl;
    private String about;
    private List<SkillRequest> skills;
    private List<LanguageRequest> languages;
    private List<InterestRequest> interests;
    private List<ExperienceRequest> experiences;
    private List<EducationRequest> education;
    private List<String> additionalExperiences;

    public CvData(String firstName, String lastName, String title, String email, String phone, String address, String linkedin, String portraitUrl, String about, List<SkillRequest> skills, List<LanguageRequest> languages, List<InterestRequest> interests, List<ExperienceRequest> experiences, List<EducationRequest> education, List<String> additionalExperiences) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.title = title;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.linkedin = linkedin;
        this.portraitUrl = portraitUrl;
        this.about = about;
        this.skills = skills;
        this.languages = languages;
        this.interests = interests;
        this.experiences = experiences;
        this.education = education;
        this.additionalExperiences = additionalExperiences;
    }

    

    
    
    
}

