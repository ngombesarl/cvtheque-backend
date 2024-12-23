/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.gozenservice.gozen.request.offer;

/**
 *
 * @author TCHINGANG Steve
 */
import com.gozenservice.gozen.models.Employer;
import com.gozenservice.gozen.models.EmploymentType;
import com.gozenservice.gozen.models.JobOfferStatus;
import com.gozenservice.gozen.models.JobRequirement;
import com.gozenservice.gozen.models.WorkLocation;
import java.time.LocalDate;
import java.util.List;
import lombok.Data;

@Data
public class JobOfferRequest {

    private String title;
    
    private String company;

    private String description;
    
    private String location;

    private String salary;
    
    private EmploymentType employmentType;

    private JobOfferStatus status; 
    
    private WorkLocation workLocation;
    
    private List<String> benefits;
    
    private List<String> responsibilities;
    
    private LocalDate applicationDeadline;
    
     private String contactEmail;
    
    private Employer employer;
    
    private JobRequirement requirements;
}


