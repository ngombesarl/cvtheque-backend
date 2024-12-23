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
public class InterestRequest {
    private String interestName;

    public InterestRequest(String interestName) {
        this.interestName = interestName;
    }

    public InterestRequest() {
    }
    
}

