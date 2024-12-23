/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.gozenservice.gozen.response.cv;

/**
 *
 * @author TCHINGANG Steve
 */
import lombok.Data;

@Data
public class EducationResponse {
    private Long id;
    private String institution;
    private String diplomaName;
    private String description;
    private String startDate;
    private String endDate;
    private String town;
}
