/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */

package com.gozenservice.gozen.repository.offer;

import com.gozenservice.gozen.models.InterviewGroup;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author TCHINGANG Steve
 */
public interface InterviewGroupRepository extends JpaRepository<InterviewGroup, Long> {
    List<InterviewGroup> findByJobOfferId(Long jobOfferId);
}
