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
public class SkillResponse {
    private Long id;
    private String name;
    private String level;
    private String description;
}

