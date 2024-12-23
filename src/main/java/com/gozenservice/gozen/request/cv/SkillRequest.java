/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.gozenservice.gozen.request.cv;

/**
 *
 * @author TCHINGANG Steve
 */
import lombok.Data;

@Data
public class SkillRequest {
    private String name;
    private String level;
    private String description;

    public SkillRequest() {
    }

    public SkillRequest(String name, String level, String description) {
        this.name = name;
        this.level = level;
        this.description = description;
    }
}

