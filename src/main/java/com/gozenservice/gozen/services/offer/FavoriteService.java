/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gozenservice.gozen.services.offer;

/**
 *
 * @author TCHINGANG Steve
 */
import com.gozenservice.gozen.dto.CVDTO;
import com.gozenservice.gozen.dto.FavoriteDTO;
import com.gozenservice.gozen.models.CV;
import com.gozenservice.gozen.models.Education;
import com.gozenservice.gozen.models.Experience;
import com.gozenservice.gozen.models.Favorite;
import com.gozenservice.gozen.models.Interest;
import com.gozenservice.gozen.models.Language;
import com.gozenservice.gozen.models.Reference;
import com.gozenservice.gozen.models.Skill;
import com.gozenservice.gozen.models.Social;
import com.gozenservice.gozen.models.User;
import com.gozenservice.gozen.repository.offer.FavoriteRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FavoriteService {

    @Autowired
    private FavoriteRepository favoriteRepository;

    public Favorite addFavorite(Long employerId, CV cv) {
        if (favoriteRepository.existsByEmployerIdAndCvId(employerId, cv.getId())) {
            throw new RuntimeException("CV already added to favorites");
        }
        Favorite favorite = new Favorite(new User(employerId), cv);
        return favoriteRepository.save(favorite);
    }

    public void removeFavorite(Long employerId, Long cvId) {
        Favorite favorite = favoriteRepository.findByEmployerIdAndCvId(employerId, cvId)
                .orElseThrow(() -> new RuntimeException("Favorite not found"));
        favoriteRepository.delete(favorite);
    }

//    public List<Favorite> getFavorites(Long employerId) {
//        return favoriteRepository.findByEmployerId(employerId);
//    }

    public List<FavoriteDTO> getFavorites(Long employerId) {
        List<Favorite> favorites = favoriteRepository.findByEmployerId(employerId);
        return favorites.stream()
                .map(this::mapToFavoriteDTO) // Mapper chaque `Favorite` vers un DTO
                .collect(Collectors.toList());
    }

    public FavoriteDTO mapToFavoriteDTO(Favorite favorite) {
        if (favorite == null) {
            return null;
        }
        CVDTO cvDto = mapToCVDTO(favorite.getCv());
        return new FavoriteDTO(favorite.getId(), cvDto);
    }

    public CVDTO mapToCVDTO(CV cv) {
        if (cv == null) {
            return null;
        }

        CVDTO dto = new CVDTO();
        dto.setId(cv.getId());
        dto.setStatus(cv.getStatus());
        dto.setDescription(cv.getDescription());
        dto.setJobTitle(cv.getJobTitle());
        dto.setCreatedAt(cv.getCreatedAt());
        dto.setUpdatedAt(cv.getUpdatedAt());

        // Convertir les relations en DTO si nécessaire
        dto.setSkills(cv.getSkills().stream().map(skill -> new Skill(skill.getName(), skill.getDescription(), skill.getLevel())).collect(Collectors.toList()));
        dto.setExperiences(cv.getExperiences().stream().map(exp -> new Experience(exp.getPosition(), exp.getCompany(), exp.getStartDate(), exp.getEndDate())).collect(Collectors.toList()));
        dto.setEducations(cv.getEducations().stream().map(edu -> new Education(edu.getInstitution(), edu.getDiplomaName(), edu.getTown(), edu.getDescription(), edu.getStartDate(), edu.getEndDate())).collect(Collectors.toList()));
        dto.setReferences(cv.getReferences().stream().map(ref -> new Reference(ref.getName(), ref.getContactInfo(), ref.getRelation())).collect(Collectors.toList()));
        dto.setInterests(cv.getInterest().stream().map(inte -> new Interest(inte.getName())).collect(Collectors.toList()));
        dto.setSocials(cv.getSocial().stream().map(soc -> new Social(soc.getName(), soc.getUrl())).collect(Collectors.toList()));
        dto.setLanguages(cv.getLanguage().stream().map(lang -> new Language(lang.getName(), lang.getLevel())).collect(Collectors.toList()));

        return dto;
    }
}
