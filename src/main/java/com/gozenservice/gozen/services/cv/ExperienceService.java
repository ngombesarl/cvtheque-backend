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
import com.gozenservice.gozen.models.Experience;
import com.gozenservice.gozen.models.Task;
import com.gozenservice.gozen.repository.cv.CVRepository;
import com.gozenservice.gozen.repository.cv.ExperienceRepository;
import com.gozenservice.gozen.request.cv.ExperienceRequest;
import com.gozenservice.gozen.response.cv.ExperienceResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ExperienceService {

    @Autowired
    private ExperienceRepository experienceRepository;

    @Autowired
    private CVRepository cvRepository;

    @Transactional
    public ExperienceResponse addExperienceToCV(Long cvId, ExperienceRequest request) {
        CV cv = cvRepository.findById(cvId).orElseThrow(() -> new RuntimeException("CV not found"));

        Experience experience = new Experience();
        experience.setPosition(request.getPosition());
        experience.setCompany(request.getCompany());
        experience.setStartDate(request.getStartDate());
        experience.setEndDate(request.getEndDate());
        experience.setCv(cv);

        // Lier les tâches à l'expérience
        List<Task> tasks = new ArrayList<>();
        for (Task task : request.getTasks()) {
            task.setExperience(experience);
            tasks.add(task);
        }

        experience.setTask(tasks);

        experience = experienceRepository.save(experience);

        // Préparer la réponse
        ExperienceResponse response = new ExperienceResponse();
        response.setId(experience.getId());
        response.setPosition(experience.getPosition());
        response.setCompany(experience.getCompany());
        response.setStartDate(experience.getStartDate());
        response.setEndDate(experience.getEndDate());
        response.setTask(tasks);

        return response;
    }

    @Transactional
    public ExperienceResponse updateExperience(Long experienceId, ExperienceRequest request) {
        Experience experience = experienceRepository.findById(experienceId).orElseThrow(() -> new RuntimeException("Experience not found"));

        // Mettre à jour les champs simples
        experience.setPosition(request.getPosition());
        experience.setCompany(request.getCompany());
        experience.setStartDate(request.getStartDate());
        experience.setEndDate(request.getEndDate());
        
        // Gérer les tâches (supprimer les orphelins et ajouter les nouvelles)
        List<Task> updatedTasks = request.getTasks().stream()
                .map(taskRequest -> new Task(taskRequest.getName()))
                .toList();
        
        List<Task> tasks = new ArrayList<>();
        for (Task task : updatedTasks) {
            task.setExperience(experience);
            tasks.add(task);
        }

        //experience.setTask(tasks);

        experience.getTask().clear(); // Supprime les orphelins
        experience.getTask().addAll(tasks); // Ajoute les nouvelles tâches
        
        experienceRepository.save(experience);

        ExperienceResponse response = new ExperienceResponse();
        response.setId(experience.getId());
        response.setPosition(experience.getPosition());
        response.setCompany(experience.getCompany());
        response.setStartDate(experience.getStartDate());
        response.setEndDate(experience.getEndDate());
        response.setTask(experience.getTask());

        return response;
    }

    @Transactional(readOnly = true)
    public List<ExperienceResponse> getExperiencesByCV(Long cvId) {
        return experienceRepository.findByCvId(cvId).stream().map(exp -> {
            ExperienceResponse response = new ExperienceResponse();
            response.setId(exp.getId());
            response.setPosition(exp.getPosition());
            response.setCompany(exp.getCompany());
            response.setStartDate(exp.getStartDate());
            response.setEndDate(exp.getEndDate());
            response.setTask(exp.getTask());
            return response;
        }).collect(Collectors.toList());
    }

    @Transactional
    public void deleteExperience(Long experienceId) {
        experienceRepository.deleteById(experienceId);
    }
}
