/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.gozenservice.gozen.services.cv;

/**
 *
 * @author TCHINGANG Steve
 */

import com.gozenservice.gozen.models.CV;
import com.gozenservice.gozen.models.Interest;
import com.gozenservice.gozen.repository.cv.CVRepository;
import com.gozenservice.gozen.repository.cv.InterestRepository;
import com.gozenservice.gozen.request.cv.InterestRequest;
import com.gozenservice.gozen.response.cv.InterestResponse;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class InterestService {

    @Autowired
    private InterestRepository interestRepository;

    @Autowired
    private CVRepository cvRepository;

    @Transactional
    public InterestResponse addInterestToCV(Long cvId, InterestRequest request) {
        CV cv = cvRepository.findById(cvId).orElseThrow(() -> new RuntimeException("CV not found"));

        Interest interest = new Interest();
        interest.setName(request.getInterestName());
        interest.setCv(cv);

        interest = interestRepository.save(interest);

        InterestResponse response = new InterestResponse();
        response.setId(interest.getId());
        response.setInterestName(interest.getName());

        return response;
    }
    
    @Transactional
    public InterestResponse updateInterest(Long interestId, InterestRequest request) {
        Interest interest = interestRepository.findById(interestId).orElseThrow(() -> new RuntimeException("Interest not found"));

        interest.setName(request.getInterestName());

        interest = interestRepository.save(interest);

        InterestResponse response = new InterestResponse();
        response.setId(interest.getId());
        response.setInterestName(interest.getName());

        return response;
    }

    @Transactional(readOnly = true)
    public List<InterestResponse> getInterestsByCV(Long cvId) {
        return interestRepository.findByCvId(cvId).stream().map(interest -> {
            InterestResponse response = new InterestResponse();
            response.setId(interest.getId());
            response.setInterestName(interest.getName());
            return response;
        }).collect(Collectors.toList());
    }

    @Transactional
    public void deleteInterest(Long interestId) {
        interestRepository.deleteById(interestId);
    }
}

