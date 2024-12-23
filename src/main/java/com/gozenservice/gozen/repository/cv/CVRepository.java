/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.gozenservice.gozen.repository.cv;

import com.gozenservice.gozen.models.CV;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author r.informatique
 */
public interface CVRepository extends JpaRepository<CV, Long> {
    Optional<CV> findByUserId(Long userId);
}
