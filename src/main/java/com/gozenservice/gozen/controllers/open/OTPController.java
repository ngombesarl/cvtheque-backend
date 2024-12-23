/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.gozenservice.gozen.controllers.open;

import com.gozenservice.gozen.models.OTP;
import com.gozenservice.gozen.services.EmailService;
import com.gozenservice.gozen.services.OTPService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author TCHINGANG Steve
 */
@RestController
@RequestMapping("/api/public/auth")
public class OTPController {

    @Autowired
    private OTPService otpService;

    @Autowired
    private EmailService emailService;

    @PostMapping("/send-otp")
    public ResponseEntity<String> sendOTP(@RequestParam String email) {
        OTP otp = otpService.generateOTP(email);

        // Envoyer l'OTP par e-mail
        emailService.envoyerEmail(email, "Votre code OTP", 
                "Votre code OTP est : " + otp.getCode() + ". Ce code expire dans 5 minutes.");

        return ResponseEntity.ok("Code OTP envoyé avec succès.");
    }

    @PostMapping("/resend-otp")
    public ResponseEntity<String> resendOTP(@RequestParam String email) {
        OTP otp = otpService.generateOTP(email);

        // Envoyer le nouvel OTP
        emailService.envoyerEmail(email, "Votre nouveau code OTP", 
                "Votre nouveau code OTP est : " + otp.getCode() + ". Ce code expire dans 5 minutes.");

        return ResponseEntity.ok("Nouveau code OTP envoyé avec succès.");
    }

    @PostMapping("/validate-otp")
    public ResponseEntity<String> validateOTP(@RequestParam String email, @RequestParam String code) {
        boolean isValid = otpService.validateOTP(email, code);

        if (isValid) {
            return ResponseEntity.ok("Code OTP validé avec succès.");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Code OTP invalide ou expiré.");
        }
    }
}
