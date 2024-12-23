/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.gozenservice.gozen.repository.offer;

/**
 *
 * @author TCHINGANG Steve
 */
import com.gozenservice.gozen.models.Favorite;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
    List<Favorite> findByEmployerId(Long employerId);
    boolean existsByEmployerIdAndCvId(Long employerId, Long cvId);
    Optional<Favorite> findByEmployerIdAndCvId(Long employerid, Long cvId);
}
