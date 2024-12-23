/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.gozenservice.gozen.response;

import java.util.HashMap;
import java.util.Map;
import lombok.Data;

/**
 *
 * @author TCHINGANG Steve
 */
@Data
public class Response {
    private int status;
    private String message;
    
    public Response() {
        
    }

    public Response(int status, String message) {
        this.status = status;
        this.message = message;
    }
    
    public Map<String, Object> toJson() {
        Map<String, Object> json = new HashMap<>();
        json.put("status", status);
        json.put("message", message);
        
        return json;
    }
}
