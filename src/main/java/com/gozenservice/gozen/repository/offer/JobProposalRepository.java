/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.gozenservice.gozen.repository.offer;

import com.gozenservice.gozen.models.JobOffer;
import com.gozenservice.gozen.models.JobProposal;
import com.gozenservice.gozen.models.ProposalStatus;
import com.gozenservice.gozen.models.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author TCHINGANG Steve
 */
@Repository
public interface JobProposalRepository extends JpaRepository<JobProposal, Long> {
    List<JobProposal> findByJobSeekerId(Long jobSeekerId);
    List<JobProposal> findByJobOfferId(Long jobOfferId);
    List<JobProposal> findByEmployerId(Long employerId);
    List<JobProposal> findByStatus(ProposalStatus status);
    boolean existsByJobOfferAndJobSeeker(JobOffer jobOffer, User jobSeeker);
}

