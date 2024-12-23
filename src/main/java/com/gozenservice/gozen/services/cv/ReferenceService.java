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
import com.gozenservice.gozen.models.Reference;
import com.gozenservice.gozen.repository.cv.CVRepository;
import com.gozenservice.gozen.repository.cv.ReferenceRepository;
import com.gozenservice.gozen.request.cv.ReferenceRequest;
import com.gozenservice.gozen.response.cv.ReferenceResponse;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReferenceService {

    @Autowired
    private ReferenceRepository referenceRepository;

    @Autowired
    private CVRepository cvRepository;

    @Transactional
    public ReferenceResponse addReferenceToCV(Long cvId, ReferenceRequest request) {
        CV cv = cvRepository.findById(cvId).orElseThrow(() -> new RuntimeException("CV not found"));

        Reference reference = new Reference();
        reference.setName(request.getName());
        reference.setContactInfo(request.getContactInfo());
        reference.setRelation(request.getRelation());
        reference.setCv(cv);

        reference = referenceRepository.save(reference);

        ReferenceResponse response = new ReferenceResponse();
        response.setId(reference.getId());
        response.setName(reference.getName());
        response.setContactInfo(reference.getContactInfo());
        response.setRelation(reference.getRelation());

        return response;
    }
    
    @Transactional
    public ReferenceResponse updateReference(Long referenceId, ReferenceRequest request) {
        Reference reference = referenceRepository.findById(referenceId).orElseThrow(() -> new RuntimeException("Reference not found"));
        
        reference.setName(request.getName());
        reference.setContactInfo(request.getContactInfo());
        reference.setRelation(request.getRelation());

        reference = referenceRepository.save(reference);

        ReferenceResponse response = new ReferenceResponse();
        response.setId(reference.getId());
        response.setName(reference.getName());
        response.setContactInfo(reference.getContactInfo());
        response.setRelation(reference.getRelation());

        return response;
    }

    @Transactional(readOnly = true)
    public List<ReferenceResponse> getReferencesByCV(Long cvId) {
        return referenceRepository.findByCvId(cvId).stream().map(reference -> {
            ReferenceResponse response = new ReferenceResponse();
            response.setId(reference.getId());
            response.setName(reference.getName());
            response.setContactInfo(reference.getContactInfo());
            response.setRelation(reference.getRelation());
            return response;
        }).collect(Collectors.toList());
    }

    @Transactional
    public void deleteReference(Long referenceId) {
        referenceRepository.deleteById(referenceId);
    }
}

