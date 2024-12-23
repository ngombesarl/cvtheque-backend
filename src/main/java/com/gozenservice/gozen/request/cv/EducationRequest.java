/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.gozenservice.gozen.request.cv;

/**
 *
 * @author TCHINGANG Steve
 */

import lombok.Data;

@Data
public class EducationRequest {
    private String institution;
    private String diplomaName;
    private String description;
    private String startDate;
    private String endDate;
    private String town;

    public EducationRequest() {
    }

    public EducationRequest(String institution, String diplomaName, String description, String startDate, String endDate, String town) {
        this.institution = institution;
        this.diplomaName = diplomaName;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.town = town;
    }

    
    
    
}
