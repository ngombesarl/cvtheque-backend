/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.gozenservice.gozen.services.offer;

import com.gozenservice.gozen.models.JobOffer;
import com.gozenservice.gozen.models.SavedJobOffer;
import com.gozenservice.gozen.models.User;
import com.gozenservice.gozen.repository.UserRepository;
import com.gozenservice.gozen.repository.offer.JobOfferRepository;
import com.gozenservice.gozen.repository.offer.SavedJobOfferRepository;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author TCHINGANG Steve
 */
@Service
@RequiredArgsConstructor
public class SavedJobOfferService {

    private final SavedJobOfferRepository savedJobOfferRepository;
    private final JobOfferRepository jobOfferRepository;
    private final UserRepository userRepository;

    // Sauvegarder une offre d'emploi
    @Transactional
    public void saveJobOffer(Long jobOfferId, User jobSeeker) {
        JobOffer jobOffer = jobOfferRepository.findById(jobOfferId)
                .orElseThrow(() -> new RuntimeException("Job offer not found"));

        savedJobOfferRepository.findByJobSeekerIdAndJobOfferId(jobSeeker.getId(), jobOfferId)
                .ifPresent(saved -> {
                    throw new RuntimeException("Job offer already saved.");
                });

        SavedJobOffer savedJobOffer = new SavedJobOffer();
        savedJobOffer.setJobSeeker(jobSeeker);
        savedJobOffer.setJobOffer(jobOffer);
        savedJobOffer.setSavedAt(LocalDateTime.now());

        savedJobOfferRepository.save(savedJobOffer);
    }

    // Supprimer une offre sauvegardée
    @Transactional
    public void deleteSavedJobOffer(Long jobOfferId, User jobSeeker) {
        SavedJobOffer savedJobOffer = savedJobOfferRepository.findByJobSeekerIdAndJobOfferId(jobSeeker.getId(), jobOfferId)
                .orElseThrow(() -> new RuntimeException("Saved job offer not found."));

        savedJobOfferRepository.delete(savedJobOffer);
    }

    // Récupérer toutes les offres sauvegardées par un postulant
    public List<SavedJobOffer> getSavedJobOffers(User jobSeeker) {
        return savedJobOfferRepository.findByJobSeekerId(jobSeeker.getId());
    }
}

