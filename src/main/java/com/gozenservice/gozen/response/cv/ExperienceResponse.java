/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.gozenservice.gozen.response.cv;

/**
 *
 * @author TCHINGANG Steve
 */

import com.gozenservice.gozen.models.Task;
import java.util.List;
import lombok.Data;

@Data
public class ExperienceResponse {
    private Long id;
    private String position;
    private String company;
    private String startDate;
    private String endDate;
    private List<Task> task;
}

