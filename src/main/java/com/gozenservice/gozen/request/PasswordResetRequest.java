/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.gozenservice.gozen.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/***
 * 
 * @author Tchingang Steve
 */
@Data
@Schema(description = "Requête pour initier la réinitialisation du mot de passe")
public class PasswordResetRequest {

    @NotBlank
    @Email
    @Schema(description = "Email de l'utilisateur pour la réinitialisation du mot de passe", example = "user@example.com")
    private String email;

    // Getters and Setters
}

