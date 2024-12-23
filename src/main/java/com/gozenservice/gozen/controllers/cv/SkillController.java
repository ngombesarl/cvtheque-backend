/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.gozenservice.gozen.controllers.cv;

/**
 *
 * @author TCHINGANG Steve
 */

import com.gozenservice.gozen.request.cv.SkillRequest;
import com.gozenservice.gozen.response.cv.SkillResponse;
import com.gozenservice.gozen.services.cv.SkillService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/applicant/cv/skills")
public class SkillController {

    @Autowired
    private SkillService skillService;

    @PostMapping("/{cvId}/add")
    public SkillResponse addSkill(@PathVariable Long cvId, @RequestBody SkillRequest request) {
        return skillService.addSkillToCV(cvId, request);
    }
    
    @PutMapping("/{skillId}")
    public SkillResponse updateSkill(@PathVariable Long skillId, @RequestBody SkillRequest request) {
        return skillService.updateSkill(skillId, request);
    }

    @GetMapping("/{cvId}")
    public List<SkillResponse> getSkillsByCV(@PathVariable Long cvId) {
        return skillService.getSkillsByCV(cvId);
    }

    @DeleteMapping("/{skillId}")
    public void deleteSkill(@PathVariable Long skillId) {
        skillService.deleteSkill(skillId);
    }
}

