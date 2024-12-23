/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.gozenservice.gozen.repository.cv;

import com.gozenservice.gozen.models.Language;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author TCHINGANG Steve
 */
@Repository
public interface LanguageRepository extends JpaRepository<Language, Long> {
    List<Language> findByCvId(Long cvId);
}
