/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.gozenservice.gozen.request;

import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 *
 * @author TCHINGANG Steve
 */
@Data
@RequiredArgsConstructor
public class VerificationRequest {
    private String email;
    private String code;
}