/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.gozenservice.gozen.repository.offer;

/**
 *
 * @author TCHINGANG Steve
 */
import com.gozenservice.gozen.models.ApplicationStatus;
import com.gozenservice.gozen.models.JobApplication;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobApplicationRepository extends JpaRepository<JobApplication, Long> {
    // Récupérer les candidatures par offre d'emploi
    List<JobApplication> findByJobOfferId(Long jobOfferId);

    // Récupérer les candidatures par chercheur d'emploi
    List<JobApplication> findByJobSeekerId(Long jobSeekerId);
    
    // Recuperer les candidatures par status et date de soumission
    List<JobApplication> findByStatusAndLastDocumentSubmissionBefore(ApplicationStatus status, LocalDateTime lastDocumentSubmission);
    
    List<JobApplication> findByStatus(ApplicationStatus status);
    
    List<JobApplication> findByJobOfferIdAndDocumentSend(Long id, Boolean documentSend);

    List<JobApplication> findByJobOfferIdAndIsProposed(Long jobOfferId, boolean isProposed);
}

