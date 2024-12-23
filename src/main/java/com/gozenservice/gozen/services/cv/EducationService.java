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
import com.gozenservice.gozen.models.Education;
import com.gozenservice.gozen.repository.cv.CVRepository;
import com.gozenservice.gozen.repository.cv.EducationRepository;
import com.gozenservice.gozen.request.cv.EducationRequest;
import com.gozenservice.gozen.response.cv.EducationResponse;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EducationService {

    @Autowired
    private EducationRepository educationRepository;

    @Autowired
    private CVRepository cvRepository;

    @Transactional
    public EducationResponse addEducationToCV(Long cvId, EducationRequest request) {
        CV cv = cvRepository.findById(cvId).orElseThrow(() -> new RuntimeException("CV not found"));

        Education education = new Education();
        education.setInstitution(request.getInstitution());
        education.setDiplomaName(request.getDiplomaName());
        education.setTown(request.getTown());
        education.setStartDate(request.getStartDate());
        education.setEndDate(request.getEndDate());
        education.setDescription(request.getDescription());
        education.setCv(cv);

        education = educationRepository.save(education);

        EducationResponse response = new EducationResponse();
        response.setId(education.getId());
        response.setInstitution(education.getInstitution());
        response.setDiplomaName(education.getDiplomaName());
        response.setDescription(education.getDescription());
        response.setStartDate(education.getStartDate());
        response.setEndDate(education.getEndDate());
        response.setTown(education.getTown());

        return response;
    }
    
    @Transactional
    public EducationResponse updateEducation(Long educationId, EducationRequest request) {
        Education education = educationRepository.findById(educationId).orElseThrow(() -> new RuntimeException("Education not found"));
        
        education.setInstitution(request.getInstitution());
        education.setDiplomaName(request.getDiplomaName());
        education.setTown(request.getTown());
        education.setStartDate(request.getStartDate());
        education.setEndDate(request.getEndDate());
        education.setDescription(request.getDescription());

        education = educationRepository.save(education);

        EducationResponse response = new EducationResponse();
        response.setId(education.getId());
        response.setInstitution(education.getInstitution());
        response.setDiplomaName(education.getDiplomaName());
        response.setDescription(education.getDescription());
        response.setStartDate(education.getStartDate());
        response.setEndDate(education.getEndDate());
        response.setTown(education.getTown());

        return response;
    }

    @Transactional(readOnly = true)
    public List<EducationResponse> getEducationByCV(Long cvId) {
        return educationRepository.findByCvId(cvId).stream().map(education -> {
            EducationResponse response = new EducationResponse();
            response.setId(education.getId());
            response.setInstitution(education.getInstitution());
            response.setDiplomaName(education.getDiplomaName());
            response.setDescription(education.getDescription());
            response.setStartDate(education.getStartDate());
            response.setEndDate(education.getEndDate());
            response.setTown(education.getTown());
            return response;
        }).collect(Collectors.toList());
    }

    @Transactional
    public void deleteEducation(Long educationId) {
        educationRepository.deleteById(educationId);
    }
}

