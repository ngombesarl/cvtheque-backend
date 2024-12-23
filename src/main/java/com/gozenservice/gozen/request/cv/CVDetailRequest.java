/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.gozenservice.gozen.request.cv;

import java.time.LocalDate;
import lombok.Data;

/**
 *
 * @author TCHINGANG Steve
 */
@Data
public class CVDetailRequest {

    private Long userId;
    private String description;
    private String jobTitle;
    private String name;
    private String surname;
    private LocalDate birthdate;
    private String phone;
}

