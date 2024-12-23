/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.gozenservice.gozen.controllers.cv;

/**
 *
 * @author TCHINGANG Steve
 */

import com.gozenservice.gozen.request.cv.LanguageRequest;
import com.gozenservice.gozen.response.cv.LanguageResponse;
import com.gozenservice.gozen.services.cv.LanguageService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/applicant/cv/language")
public class LanguageController {

    @Autowired
    private LanguageService languageService;

    @PostMapping("/{cvId}")
    public LanguageResponse addLanguage(@PathVariable Long cvId, @RequestBody LanguageRequest request) {
        return languageService.addLanguageToCV(cvId, request);
    }
    
    @PutMapping("/{languageId}")
    public LanguageResponse updateLanguage(@PathVariable Long languageId, @RequestBody LanguageRequest request) {
        return languageService.updateLanguage(languageId, request);
    }

    @GetMapping("/{cvId}")
    public List<LanguageResponse> getLanguagesByCV(@PathVariable Long cvId) {
        return languageService.getLanguagesByCV(cvId);
    }

    @DeleteMapping("/{languageId}")
    public void deleteLanguage(@PathVariable Long languageId) {
        languageService.deleteLanguage(languageId);
    }
}

