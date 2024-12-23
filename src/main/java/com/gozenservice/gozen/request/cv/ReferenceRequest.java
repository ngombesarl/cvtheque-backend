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
public class ReferenceRequest {
    private String name;
    private String contactInfo;
    private String relation;

    public ReferenceRequest() {
    }

    public ReferenceRequest(String name, String contactInfo, String relation) {
        this.name = name;
        this.contactInfo = contactInfo;
        this.relation = relation;
    }
    
    
}
