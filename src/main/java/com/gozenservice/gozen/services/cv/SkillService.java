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
import com.gozenservice.gozen.models.Skill;
import com.gozenservice.gozen.repository.cv.CVRepository;
import com.gozenservice.gozen.repository.cv.SkillRepository;
import com.gozenservice.gozen.request.cv.SkillRequest;
import com.gozenservice.gozen.response.cv.SkillResponse;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SkillService {

    @Autowired
    private SkillRepository skillRepository;

    @Autowired
    private CVRepository cvRepository;

    @Transactional
    public SkillResponse addSkillToCV(Long cvId, SkillRequest request) {
        CV cv = cvRepository.findById(cvId).orElseThrow(() -> new RuntimeException("CV not found"));

        Skill skill = new Skill();
        skill.setName(request.getName());
        skill.setLevel(request.getLevel());
        skill.setDescription(request.getDescription());
        skill.setCv(cv);

        skill = skillRepository.save(skill);

        SkillResponse response = new SkillResponse();
        response.setId(skill.getId());
        response.setName(skill.getName());
        response.setLevel(skill.getLevel());
        response.setDescription(skill.getDescription());

        return response;
    }
    
    @Transactional
    public SkillResponse updateSkill(Long skillId, SkillRequest request) {
        Skill skill = skillRepository.findById(skillId).orElseThrow(() -> new RuntimeException("Skill not found"));
        skill.setName(request.getName());
        skill.setLevel(request.getLevel());
        skill.setDescription(request.getDescription());

        skill = skillRepository.save(skill);

        SkillResponse response = new SkillResponse();
        response.setId(skill.getId());
        response.setName(skill.getName());
        response.setLevel(skill.getLevel());
        response.setDescription(skill.getDescription());

        return response;
    }

    @Transactional(readOnly = true)
    public List<SkillResponse> getSkillsByCV(Long cvId) {
        return skillRepository.findByCvId(cvId).stream().map(skill -> {
            SkillResponse response = new SkillResponse();
            response.setId(skill.getId());
            response.setName(skill.getName());
            response.setLevel(skill.getLevel());
            response.setDescription(skill.getDescription());
            return response;
        }).collect(Collectors.toList());
    }

    @Transactional
    public void deleteSkill(Long skillId) {
        skillRepository.deleteById(skillId);
    }
}

