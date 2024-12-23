/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.gozenservice.gozen.repository.cv;

/**
 *
 * @author TCHINGANG Steve
 */

import com.gozenservice.gozen.models.Reference;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReferenceRepository extends JpaRepository<Reference, Long> {
    List<Reference> findByCvId(Long cvId);
}

