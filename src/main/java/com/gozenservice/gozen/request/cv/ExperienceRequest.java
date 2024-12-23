/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.gozenservice.gozen.request.cv;

/**
 *
 * @author TCHINGANG Steve
 */

import com.gozenservice.gozen.models.Task;
import java.util.List;
import lombok.Data;

@Data
public class ExperienceRequest {
    private String position;
    private String company;
    private String startDate;
    private String endDate;
    private List<Task> tasks;

    public ExperienceRequest() {
    }

    public ExperienceRequest(String position, String company, String startDate, String endDate, List<Task> tasks) {
        this.position = position;
        this.company = company;
        this.startDate = startDate;
        this.endDate = endDate;
        this.tasks = tasks;
    }
    
    
}
