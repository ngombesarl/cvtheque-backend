/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.gozenservice.gozen.repository;

import com.gozenservice.gozen.models.JobSeeker;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import java.util.List;

/**
 *
 * @author TCHINGANG Steve
 */
public class JobSeekerCustomRepositoryImpl implements JobSeekerCustomRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<JobSeeker> findByFilters(List<String> skills, List<String> jobTitles, List<String> languages) {
        // Construire la requête JPQL de base
        StringBuilder queryBuilder = new StringBuilder(
                "SELECT DISTINCT js "
                + "FROM JobSeeker js "
                + "JOIN js.cv cv "
                + "LEFT JOIN cv.skills skill "
                + "LEFT JOIN cv.language language "
                + "WHERE 1=1 "
        );

        // Ajouter les filtres dynamiquement
        if (skills != null && !skills.isEmpty()) {
            queryBuilder.append("AND skill.name IN :skills ");
        }
        if (languages != null && !languages.isEmpty()) {
            queryBuilder.append("AND language.name IN :languages ");
        }
        if (jobTitles != null && !jobTitles.isEmpty()) {
            queryBuilder.append("AND cv.jobTitle LIKE :jobTitlePattern ");
        }

        // Créer une requête typée
        TypedQuery<JobSeeker> query = entityManager.createQuery(queryBuilder.toString(), JobSeeker.class);

        // Ajouter les paramètres
        if (skills != null && !skills.isEmpty()) {
            query.setParameter("skills", skills);
        }
        if (languages != null && !languages.isEmpty()) {
            query.setParameter("languages", languages);
        }
        if (jobTitles != null && !jobTitles.isEmpty()) {
            query.setParameter("jobTitlePattern", "%" + jobTitles.get(0) + "%");
        }

        // Exécuter la requête et retourner les résultats
        return query.getResultList();
    }

}
