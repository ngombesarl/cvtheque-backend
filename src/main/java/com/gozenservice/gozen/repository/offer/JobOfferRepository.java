/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.gozenservice.gozen.repository.offer;

/**
 *
 * @author TCHINGANG Steve
 */
import com.gozenservice.gozen.models.JobOffer;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobOfferRepository extends JpaRepository<JobOffer, Long> {
    List<JobOffer> findByEmployerId(Long employerId);
}

