/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gozenservice.gozen.dto;

import lombok.Data;

/**
 *
 * @author TCHINGANG Steve
 */
@Data
public class FavoriteDTO {

    private Long id;
    private CVDTO cv;

    // Constructeur
    public FavoriteDTO(Long id, CVDTO cv) {
        this.id = id;
        this.cv = cv;
    }

    // Getters et setters
}
