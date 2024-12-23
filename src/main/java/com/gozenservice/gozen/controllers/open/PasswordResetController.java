/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.gozenservice.gozen.controllers.open;



import com.gozenservice.gozen.request.PasswordResetConfirmRequest;
import com.gozenservice.gozen.request.PasswordResetRequest;
import com.gozenservice.gozen.response.Response;
import com.gozenservice.gozen.services.PasswordResetService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


/**
 *
 * @author TCHINGANG Steve
 */
@RestController
@RequestMapping("/api/public/password")
public class PasswordResetController {

    @Autowired
    private PasswordResetService passwordResetService;

    @Operation(summary = "Demander la réinitialisation du mot de passe", description = "Génère un jeton de réinitialisation et l'envoie par email")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Email de réinitialisation envoyé"),
            @ApiResponse(responseCode = "404", description = "Email non trouvé")
    })
    @PostMapping("/reset-request")
    public ResponseEntity<?> requestPasswordReset(@RequestBody PasswordResetRequest request) {
        passwordResetService.createPasswordResetToken(request.getEmail());
        return ResponseEntity.ok(new Response(200, "Email de réinitialisation envoyé si l'email est valide.").toJson());
    }

    @Operation(summary = "Confirmer la réinitialisation du mot de passe", description = "Valide le jeton et met à jour le mot de passe de l'utilisateur")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Mot de passe réinitialisé avec succès"),
            @ApiResponse(responseCode = "400", description = "Jeton invalide ou expiré")
    })
    @PostMapping("/reset-confirm")
    public ResponseEntity<?> confirmPasswordReset(@RequestBody PasswordResetConfirmRequest request) {
        passwordResetService.resetPassword(request.getToken(), request.getNewPassword());
        return ResponseEntity.ok(new Response(200, "Mot de passe réinitialisé avec succès.").toJson());
    }
}
