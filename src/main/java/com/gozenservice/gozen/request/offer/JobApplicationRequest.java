/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.gozenservice.gozen.request.offer;

/**
 *
 * @author TCHINGANG Steve
 */
import java.util.List;
import lombok.Data;

@Data
public class JobApplicationRequest {
    private Long jobOfferId;
    private String applicantEmail;
    private List<String> additionalSkills;
}
