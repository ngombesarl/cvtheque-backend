/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.gozenservice.gozen.dto;

import java.time.LocalDate;
import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 *
 * @author TCHINGANG Steve
 */
@Data
@RequiredArgsConstructor
public class InterviewStepDTO {
    private String name;
    private String description;
    private LocalDate date;
    private String time; // HH:mm
    private String place;
    private String interviewerEmails; // Emails séparés par des ;
}
