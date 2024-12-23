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
import com.gozenservice.gozen.models.Social;
import com.gozenservice.gozen.repository.cv.CVRepository;
import com.gozenservice.gozen.repository.cv.SocialRepository;
import com.gozenservice.gozen.request.cv.SocialRequest;
import com.gozenservice.gozen.response.cv.SocialResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SocialService {

    @Autowired
    private SocialRepository socialRepository;

    @Autowired
    private CVRepository cvRepository;

    // Un dictionnaire qui associe les noms de réseaux sociaux à leurs SVG respectifs
        private static final Map<String, String> SOCIAL_ICONS = new HashMap<>() {{
    put("facebook", "<svg xmlns=\"http://www.w3.org/2000/svg\" viewBox=\"0 0 24 24\"><path d=\"M9 8h2V6c0-.6.4-1 1-1h1V3h-2c-1.7 0-3 1.3-3 3v2H6v3h2v7h3v-7h2l1-3H9z\"/></svg>");
    put("twitter", "<svg xmlns=\"http://www.w3.org/2000/svg\" viewBox=\"0 0 24 24\"><path d=\"M22 5.9c-.8.4-1.6.6-2.5.8 1-1 1.7-2.3 2-3.8-1 .6-2 1-3.2 1.2C17.9 3 16.5 2 15 2c-3 0-5 2.4-5 5 0 .4 0 .7.1 1C6.7 7.8 4.4 6.5 2.7 4.5c-.4.8-.7 1.7-.7 2.6 0 1.8.9 3.4 2.3 4.3-.8 0-1.6-.2-2.2-.6 0 0 0 .1 0 .2 0 2.5 1.8 4.5 4.1 5-.5.1-1.1.2-1.7.2-.4 0-.8 0-1.2-.1.8 2.5 3.1 4.2 5.7 4.2-2.1 1.6-4.8 2.5-7.6 2.5-.5 0-1 0-1.5-.1C2.8 22 6.1 23 9.7 23c11.7 0 18.1-9.8 18.1-18.3 0-.3 0-.6-.1-.9C21 7.4 21.6 6.7 22 5.9z\"/></svg>");
    put("linkedin", "<svg xmlns=\"http://www.w3.org/2000/svg\" viewBox=\"0 0 24 24\"><path d=\"M19 3H5C4 3 3 4 3 5v14c0 1 1 2 2 2h14c1 0 2-1 2-2V5c0-1-1-2-2-2zm-7 14h-3v-7h3v7zm-1.5-8c-.8 0-1.5-.7-1.5-1.5S9.7 6 10.5 6s1.5.7 1.5 1.5S11.3 9 10.5 9zm9 8h-3v-3.5c0-.8-.7-1.5-1.5-1.5s-1.5.7-1.5 1.5V17h-3v-7h3v1c.8-.6 2-.9 3-.9 2 0 3.5 1.6 3.5 3.5V17z\"/></svg>");
    put("instagram", "<svg xmlns=\"http://www.w3.org/2000/svg\" viewBox=\"0 0 24 24\"><path d=\"M12 2.2c3.2 0 3.6 0 4.9.1 1.2.1 1.9.3 2.3.5.5.2.8.4 1.1.8.3.3.6.6.8 1.1.2.4.4 1.1.5 2.3.1 1.3.1 1.7.1 4.9s0 3.6-.1 4.9c-.1 1.2-.3 1.9-.5 2.3-.2.5-.4.8-.8 1.1-.3.3-.6.6-1.1.8-.4.2-1.1.4-2.3.5-1.3.1-1.7.1-4.9.1s-3.6 0-4.9-.1c-1.2-.1-1.9-.3-2.3-.5-.5-.2-.8-.4-1.1-.8-.3-.3-.6-.6-.8-1.1-.2-.4-.4-1.1-.5-2.3C2.2 15.6 2.2 15.2 2.2 12s0-3.6.1-4.9c.1-1.2.3-1.9.5-2.3.2-.5.4-.8.8-1.1.3-.3.6-.6 1.1-.8.4-.2 1.1-.4 2.3-.5 1.3-.1 1.7-.1 4.9-.1zm0 1.7c-3.1 0-3.5 0-4.7.1-.9.1-1.3.2-1.6.3-.4.1-.6.2-.9.4-.3.3-.5.5-.7.7-.2.3-.3.5-.4.9-.1.3-.2.7-.3 1.6-.1 1.1-.1 1.5-.1 4.7s0 3.5.1 4.7c.1.9.2 1.3.3 1.6.1.4.2.6.4.9.3.3.5.5.7.7.3.2.5.3.9.4.3.1.7.2 1.6.3 1.1.1 1.5.1 4.7.1s3.5 0 4.7-.1c.9-.1 1.3-.2 1.6-.3.4-.1.6-.2.9-.4.3-.3.5-.5.7-.7.2-.3.3-.5.4-.9.1-.3.2-.7.3-1.6.1-1.1.1-1.5.1-4.7s0-3.5-.1-4.7c-.1-.9-.2-1.3-.3-1.6-.1-.4-.2-.6-.4-.9-.3-.3-.5-.5-.7-.7-.3-.2-.5-.3-.9-.4-.3-.1-.7-.2-1.6-.3C15.5 3.9 15.1 3.9 12 3.9zm0 3.3c2.4 0 4.3 1.9 4.3 4.3s-1.9 4.3-4.3 4.3-4.3-1.9-4.3-4.3 1.9-4.3 4.3-4.3zm0 1.7c-1.5 0-2.7 1.2-2.7 2.7s1.2 2.7 2.7 2.7 2.7-1.2 2.7-2.7-1.2-2.7-2.7-2.7zm5.8-.5c0 .5-.4.9-.9.9s-.9-.4-.9-.9.4-.9.9-.9.9.4.9.9z\"/></svg>");
    put("snapchat", "<svg xmlns=\"http://www.w3.org/2000/svg\" viewBox=\"0 0 24 24\"><path d=\"M12 2c-3 0-5 2-6 5-1 2 0 4 2 5 0 1-1 3-2 4 0 1 1 2 2 2s2-1 3-1 3 1 4 1 2-1 2-2c-1-1-2-3-2-4 2-1 3-3 2-5-1-3-3-5-5-5z\"/></svg>");
    put("youtube", "<svg xmlns=\"http://www.w3.org/2000/svg\" viewBox=\"0 0 24 24\"><path d=\"M21.8 8s-.2-1.6-.9-2.3c-.8-.9-1.7-.9-2.2-1-3.1-.2-7.7-.2-7.7-.2h-.1s-4.6 0-7.7.2c-.5 0-1.4.1-2.2 1C2.4 6.4 2.2 8 2.2 8S2 9.6 2 11.3v1.4c0 1.7.2 3.3.2 3.3s.2 1.6.9 2.3c.8.9 1.7.9 2.2 1 3.1.2 7.7.2 7.7.2h.1s4.6 0 7.7-.2c.5 0 1.4-.1 2.2-1 .7-.7.9-2.3.9-2.3s.2-1.6.2-3.3v-1.4c0-1.7-.2-3.3-.2-3.3zM9.6 15.5v-7l5.9 3.5-5.9 3.5z\"/></svg>");
    put("pinterest", "<svg xmlns=\"http://www.w3.org/2000/svg\" viewBox=\"0 0 24 24\"><path d=\"M12 2C6.5 2 2 6.4 2 12c0 3.9 2.3 7.2 5.6 8.7-.1-.7-.2-1.7 0-2.5.2-.8 1.3-5.5 1.3-5.5s-.3-.7-.3-1.8c0-1.6.9-2.7 2-2.7 1 0 1.5.7 1.5 1.7 0 1-.6 2.6-.9 4-.2 1 .5 1.8 1.5 1.8 1.8 0 3.1-2 3.1-4.8 0-2.5-2-4.2-4.8-4.2-3.3 0-5.2 2.4-5.2 4.9 0 1 .4 2 1 2.6.1.1.1.2.1.4 0 .4-.4 2-.4 2.3 0 .2-.1.3-.4.2C6 15.5 5.4 13.9 5.4 12c0-3.4 2.8-6.7 7.6-6.7 4 0 7 2.8 7 6.4 0 4-2.2 6.9-5.5 6.9-1.1 0-2.1-.6-2.4-1.3 0 0-.5 2-1.3 3.1-.4.5-.9.9-1.4 1.3 1.3.4 2.7.6 4.2.6 5.5 0 10-4.4 10-10S17.5 2 12 2z\"/></svg>");
    put("tiktok", "<svg xmlns=\"http://www.w3.org/2000/svg\" viewBox=\"0 0 24 24\"><path d=\"M15.5 3c-.8 0-1.5.7-1.5 1.5v13c0 2.2-1.8 4-4 4s-4-1.8-4-4 1.8-4 4-4c.5 0 1 .1 1.4.3v-4.1c-.5-.1-1-.2-1.4-.2-4 0-7.5 3.4-7.5 7.5S7.5 22 11.5 22s7.5-3.4 7.5-7.5V8c.7.3 1.6.5 2.5.5V6c-1.4 0-2.6-.7-3.5-1.7-.6-.8-1-1.7-1-2.7h-1z\"/></svg>");
    put("reddit", "<svg xmlns=\"http://www.w3.org/2000/svg\" viewBox=\"0 0 24 24\"><path d=\"M21.4 10.1c-.4-.3-.9-.3-1.3 0-.3.2-.5.6-.4.9.2.4.7.5 1.1.4.5-.1.9-.7.6-1.3zm-6.4 8.4c-.4 0-.7-.4-.7-.8s.3-.7.7-.7.7.4.7.7-.3.8-.7.8zm3.4-9.5c0 1.3-.5 2.5-1.4 3.4.4.4.7.9.8 1.4.1.6-.4 1.1-1 1.2-3 1-6.2 1-9.2 0-.6-.1-1.1-.6-1-1.2.1-.5.4-1 .8-1.4-.8-.9-1.4-2-1.4-3.4 0-2.8 2.2-5 5-5 .6 0 1.1.1 1.6.3l.7-3.4c.1-.5.5-.9 1-.9.5 0 .9.4 1 .9l.7 3.4c.5-.2 1-.3 1.6-.3 2.8 0 5 2.2 5 5zm-2.4-.6c.3-.3.3-.8 0-1.1-.3-.3-.8-.3-1.1 0l-2 2-2-2c-.3-.3-.8-.3-1.1 0-.3.3-.3.8 0 1.1l2 2-2 2c-.3.3-.3.8 0 1.1.3.3.8.3 1.1 0l2-2 2 2c.3.3.8.3 1.1 0 .3-.3.3-.8 0-1.1l-2-2 2-2z\"/></svg>");

    }};

    public SocialResponse addSocialToCV(Long cvId, SocialRequest request) {
        CV cv = cvRepository.findById(cvId).orElseThrow(() -> new RuntimeException("CV not found"));

        String name = request.getName().toLowerCase();
        String iconSvg = SOCIAL_ICONS.getOrDefault(name, null);
        String url = request.getUrl();
        
        if (iconSvg == null) {
            throw new RuntimeException("Unsupported social network");
        }

        Social social = new Social();
        social.setCv(cv);
        social.setName(name);
        social.setIconSvg(iconSvg);
        social.setUrl(url);

        social = socialRepository.save(social);

        SocialResponse response = new SocialResponse();
        response.setId(social.getId());
        response.setName(social.getName());
        response.setIconSvg(social.getIconSvg());
        response.setUrl(social.getUrl());

        return response;
    }
    
    // Méthode pour récupérer tous les réseaux sociaux
    public List<SocialResponse> findAllSocials() {
        List<SocialResponse> response = new ArrayList<>();

        for (Map.Entry<String, String> entry : SOCIAL_ICONS.entrySet()) {
            SocialResponse social = new SocialResponse();
            social.setIconSvg(entry.getValue());
            social.setName(entry.getKey());
            
            response.add(social);
        }
        
        return  response;
    }

    // Méthode pour récupérer un réseau social par son id
    public SocialResponse findSocialById(Long id) {
        Social social = socialRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Social network not found"));
        return convertToResponse(social);
    }
    
    // Méthode pour récupérer un réseau social par son id
    public SocialResponse findSocialByName(String name) {
        Social social = socialRepository.findByName(name);
        if(social == null) {
            throw new RuntimeException("Social network not found");
        }
        return convertToResponse(social);
    }

    // Méthode pour récupérer tous les réseaux sociaux associés à un CV spécifique
    public List<SocialResponse> findAllSocialsByCvId(Long cvId) {
        CV cv = cvRepository.findById(cvId)
                .orElseThrow(() -> new RuntimeException("CV not found"));
        
        return cv.getSocial().stream()
                .map(this::convertToResponse)
                .toList();
    }

    // Méthode pour récupérer un réseau social spécifique associé à un CV
    public SocialResponse findSocialByCvIdAndSocialId(Long cvId, Long socialId) {
        CV cv = cvRepository.findById(cvId)
                .orElseThrow(() -> new RuntimeException("CV not found"));
        
        Social social = socialRepository.findById(socialId)
                .filter(s -> s.getCv().getId().equals(cvId))
                .orElseThrow(() -> new RuntimeException("Social network not found for this CV"));

        return convertToResponse(social);
    }

    // Méthode pour supprimer un réseau social par son id
    public void deleteSocial(Long id) {
        if (!socialRepository.existsById(id)) {
            throw new RuntimeException("Social network not found");
        }
        socialRepository.deleteById(id);
    }

    // Conversion de Social en SocialResponse
    private SocialResponse convertToResponse(Social social) {
        SocialResponse response = new SocialResponse();
        response.setId(social.getId());
        response.setName(social.getName());
        response.setIconSvg(social.getIconSvg());
        response.setUrl(social.getUrl());
        return response;
    }
}
