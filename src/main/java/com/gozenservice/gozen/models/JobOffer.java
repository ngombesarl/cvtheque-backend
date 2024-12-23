/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gozenservice.gozen.models;

/**
 *
 * @author TCHINGANG Steve
 */
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Data
public class JobOffer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    
    private String company;

    @Lob
    private String description;

    private String location;

    private String salary;
    
    @Enumerated(EnumType.STRING)
    private EmploymentType employmentType = EmploymentType.FULL_TIME;

    @Enumerated(EnumType.STRING)
    private JobOfferStatus status = JobOfferStatus.ACTIVE;
    
    @Enumerated(EnumType.STRING)
    private WorkLocation workLocation = WorkLocation.ONSITE;
    
    @ElementCollection
    private List<String> benefits;
    
    @ElementCollection
    private List<String> responsibilities;
    
    private LocalDate applicationDeadline;
    
    private String contactEmail;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employer_id", nullable = false)
    @JsonIgnore
    private Employer employer;

    @OneToMany(mappedBy = "jobOffer", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("job-application")
    private List<JobApplication> applications;
    
    @OneToOne(mappedBy = "jobOffer", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("job-requirement")
    private JobRequirement requirements;
    
    @OneToMany(mappedBy = "jobOffer", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("interview-group")
    private List<InterviewGroup> interviewGroup;
    
    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
    
    public Map<String, Object> toResponse() {
    Map<String, Object> json = new HashMap<>();
    json.put("id", id);
    json.put("title", title);
    json.put("company", company);
    json.put("description", description);
    json.put("location", location);
    json.put("salary", salary);
    json.put("employmentType", employmentType.name());
    json.put("status", status.name());
    json.put("workLocation", workLocation.name());
    json.put("benefits", benefits);
    json.put("responsibilities", responsibilities);
    json.put("applicationDeadline", applicationDeadline);
    json.put("contactEmail", contactEmail);

    // Serialize only necessary Employer data
    Map<String, Object> employerJson = new HashMap<>();
    employerJson.put("id", employer.getId());
    employerJson.put("name", employer.getName());
    employerJson.put("sector", employer.getSector());
    employerJson.put("activity", employer.getActivity());
    json.put("employer", employerJson);

    // Serialize other fields
    json.put("applications", applications);
    json.put("requirements", requirements);
    json.put("createdAt", createdAt);
    json.put("updatedAt", updatedAt);
    return json;
}

}