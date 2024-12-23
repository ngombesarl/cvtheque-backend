/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */

package com.gozenservice.gozen.repository;

import com.gozenservice.gozen.models.JobSeeker;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author TCHINGANG Steve
 */
public interface JobSeekerRepository extends JpaRepository<JobSeeker, Long>, JobSeekerCustomRepository {
}

