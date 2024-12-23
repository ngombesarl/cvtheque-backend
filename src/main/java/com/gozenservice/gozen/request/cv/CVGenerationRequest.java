/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.gozenservice.gozen.request.cv;

/**
 *
 * @author TCHINGANG Steve
 */

import com.gozenservice.gozen.dto.CvData;
import java.util.List;
import lombok.Data;

@Data
public class CVGenerationRequest{

    private String format;
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

    // Getters and setters

    public CvData toCvData() {
        return new CvData(firstName, lastName, title, email, phone, address, linkedin, portraitUrl, about, skills,
                languages, interests, experiences, education, additionalExperiences);
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }
}

