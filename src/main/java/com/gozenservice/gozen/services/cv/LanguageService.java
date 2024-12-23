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
import com.gozenservice.gozen.models.Language;
import com.gozenservice.gozen.repository.cv.CVRepository;
import com.gozenservice.gozen.repository.cv.LanguageRepository;
import com.gozenservice.gozen.request.cv.LanguageRequest;
import com.gozenservice.gozen.response.cv.LanguageResponse;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LanguageService {

    @Autowired
    private LanguageRepository languageRepository;

    @Autowired
    private CVRepository cvRepository;

    @Transactional
    public LanguageResponse addLanguageToCV(Long cvId, LanguageRequest request) {
        CV cv = cvRepository.findById(cvId).orElseThrow(() -> new RuntimeException("CV not found"));

        Language language = new Language();
        language.setName(request.getName());
        language.setLevel(request.getLevel());
        language.setCv(cv);

        language = languageRepository.save(language);

        LanguageResponse response = new LanguageResponse();
        response.setId(language.getId());
        response.setName(language.getName());
        response.setLevel(language.getLevel());

        return response;
    }
    
    @Transactional
    public LanguageResponse updateLanguage(Long interestId, LanguageRequest request) {
        Language language = languageRepository.findById(interestId).orElseThrow(() -> new RuntimeException("Language not found"));
        
        language.setName(request.getName());
        language.setLevel(request.getLevel());

        language = languageRepository.save(language);

        LanguageResponse response = new LanguageResponse();
        response.setId(language.getId());
        response.setName(language.getName());
        response.setLevel(language.getLevel());

        return response;
    }

    @Transactional(readOnly = true)
    public List<LanguageResponse> getLanguagesByCV(Long cvId) {
        return languageRepository.findByCvId(cvId).stream().map(language -> {
            LanguageResponse response = new LanguageResponse();
            response.setId(language.getId());
            response.setName(language.getName());
            response.setLevel(language.getLevel());
            return response;
        }).collect(Collectors.toList());
    }

    @Transactional
    public void deleteLanguage(Long languageId) {
        languageRepository.deleteById(languageId);
    }
}

