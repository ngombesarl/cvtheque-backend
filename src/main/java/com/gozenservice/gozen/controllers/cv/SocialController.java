/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.gozenservice.gozen.controllers.cv;

/**
 *
 * @author TCHINGANG Steve
 */


import com.gozenservice.gozen.request.cv.SocialRequest;
import com.gozenservice.gozen.response.cv.SocialResponse;
import com.gozenservice.gozen.services.cv.SocialService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/applicant/cv/socials")
public class SocialController {

    @Autowired
    private SocialService socialService;

    // Ajouter un réseau social à un CV spécifique
    @PostMapping("/{cvId}")
    public SocialResponse addSocial(@PathVariable Long cvId, @RequestBody SocialRequest request) {
        return socialService.addSocialToCV(cvId, request);
    }
    
    // Récupé
    @GetMapping("/{cvId}")
    public List<SocialResponse> getSocialsByCV(@PathVariable Long cvId) {
        return socialService.findAllSocialsByCvId(cvId);
    }

    @DeleteMapping("/{socialId}")
    public void deleteSocial(@PathVariable Long socialId) {
        socialService.deleteSocial(socialId);
    }
    
    // Récupérer tous les réseaux sociaux
    @GetMapping
    public List<SocialResponse> getAllSocials() {
        return socialService.findAllSocials();
    }

    // Récupérer un réseau social par son id
    @GetMapping("/id/{id}")
    public SocialResponse getSocialById(@PathVariable Long id) {
        return socialService.findSocialById(id);
    }
    
    // Récupérer un réseau social par son nom
    @GetMapping("/name/{name}")
    public SocialResponse getSocialByName(@PathVariable String name) {
        return socialService.findSocialByName(name);
    }

    // Récupérer tous les réseaux sociaux associés à un CV spécifique
    @GetMapping("/cv/{cvId}")
    public List<SocialResponse> getAllSocialsByCvId(@PathVariable Long cvId) {
        return socialService.findAllSocialsByCvId(cvId);
    }

    // Récupérer un réseau social spécifique associé à un CV
    @GetMapping("/cv/{cvId}/social/{socialId}")
    public SocialResponse getSocialByCvIdAndSocialId(@PathVariable Long cvId, @PathVariable Long socialId) {
        return socialService.findSocialByCvIdAndSocialId(cvId, socialId);
    }
}

