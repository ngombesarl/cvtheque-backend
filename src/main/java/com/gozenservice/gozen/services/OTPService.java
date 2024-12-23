/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.gozenservice.gozen.services;

import com.gozenservice.gozen.models.OTP;
import com.gozenservice.gozen.repository.OTPRepository;
import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author TCHINGANG Steve
 */
@Service
public class OTPService {

    @Autowired
    private OTPRepository otpRepository;

    private final long EXPIRATION_MINUTES = 5; // Durée d'expiration (en minutes)

    public OTP generateOTP(String email) {
        String otpCode = String.valueOf((int)(Math.random() * 900000) + 100000); // Génère un code à 6 chiffres
        LocalDateTime expirationTime = LocalDateTime.now().plusMinutes(EXPIRATION_MINUTES);

        OTP otp = new OTP();
        otp.setEmail(email);
        otp.setCode(otpCode);
        otp.setExpirationTime(expirationTime);

        return otpRepository.save(otp);
    }

    public boolean validateOTP(String email, String code) {
        Optional<OTP> otp = otpRepository.findByEmailAndCodeAndIsUsedFalse(email, code);

        if (otp.isPresent() && otp.get().getExpirationTime().isAfter(LocalDateTime.now())) {
            // Marquer l'OTP comme utilisé
            otp.get().setUsed(true);
            otpRepository.save(otp.get());
            return true;
        }
        return false; // L'OTP est invalide ou expiré
    }
}
