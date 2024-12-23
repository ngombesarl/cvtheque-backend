/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.gozenservice.gozen.repository.offer;

import com.gozenservice.gozen.models.SavedJobOffer;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author TCHINGANG Steve
 */
@Repository
public interface SavedJobOfferRepository extends JpaRepository<SavedJobOffer, Long> {
    List<SavedJobOffer> findByJobSeekerId(Long jobSeekerId);

    Optional<SavedJobOffer> findByJobSeekerIdAndJobOfferId(Long jobSeekerId, Long jobOfferId);
}
