/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.gozenservice.gozen.request;

/**
 *
 * @author TCHINGANG Steve
 */

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(description = "Requête pour confirmer la réinitialisation du mot de passe avec un jeton")
public class PasswordResetConfirmRequest {

    @NotBlank
    @Schema(description = "Jeton de réinitialisation envoyé par email", example = "abc123token")
    private String token;

    @NotBlank
    @Schema(description = "Nouveau mot de passe", example = "newSecurePassword123!")
    private String newPassword;

    // Getters and Setters
}
