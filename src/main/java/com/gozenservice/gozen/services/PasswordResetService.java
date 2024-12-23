/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gozenservice.gozen.services;

import com.gozenservice.gozen.models.User;
import com.gozenservice.gozen.repository.UserRepository;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 *
 * @author TCHINGANG Steve
 */
@Service
public class PasswordResetService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    // Vous pourriez avoir besoin d'un service d'email pour envoyer le jeton
    // private EmailService emailService;
    public void createPasswordResetToken(String email) {
        Optional<User> userOpt = userRepository.findByEmail(email);

        if (userOpt.isEmpty()) {
            throw new RuntimeException("Email non trouvé.");
        }

        User user = userOpt.get();
        String token = UUID.randomUUID().toString();

        // Enregistrer le token dans la base de données, avec une expiration (ex: 15 minutes)
        user.setPasswordResetToken(token);
        user.setTokenExpirationDate(System.currentTimeMillis() + 900000); // 15 minutes
        userRepository.save(user);

        // je vais arranger ça après
        StringBuilder resetEmailTemplate = new StringBuilder(String.format("<!DOCTYPE html>\n"
                + "<html lang=\"fr\">\n"
                + "<head>\n"
                + "    <meta charset=\"UTF-8\">\n"
                + "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n"
                + "    <title>Réinitialisation du Mot de Passe</title>\n"
                + "    <style>\n"
                + "        body {\n"
                + "            font-family: Arial, sans-serif;\n"
                + "            background-color: #f4f4f7;\n"
                + "            color: #333;\n"
                + "            margin: 0;\n"
                + "            padding: 0;\n"
                + "        }\n"
                + "        .container {\n"
                + "            max-width: 600px;\n"
                + "            margin: 20px auto;\n"
                + "            background-color: #fff;\n"
                + "            padding: 20px;\n"
                + "            border-radius: 8px;\n"
                + "            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);\n"
                + "        }\n"
                + "        h1 {\n"
                + "            color: #333;\n"
                + "            font-size: 24px;\n"
                + "            text-align: center;\n"
                + "        }\n"
                + "        p {\n"
                + "            font-size: 16px;\n"
                + "            line-height: 1.5;\n"
                + "            color: #555;\n"
                + "        }\n"
                + "        .btn {\n"
                + "            display: inline-block;\n"
                + "            padding: 12px 20px;\n"
                + "            margin: 20px 0;\n"
                + "            background-color: #007bff;\n"
                + "            color: #ffffff;\n"
                + "            text-decoration: none;\n"
                + "            border-radius: 5px;\n"
                + "            text-align: center;\n"
                + "            font-size: 16px;\n"
                + "            font-weight: bold;\n"
                + "        }\n"
                + "        .btn:hover {\n"
                + "            background-color: #0056b3;\n"
                + "        }\n"
                + "        .footer {\n"
                + "            font-size: 14px;\n"
                + "            color: #777;\n"
                + "            text-align: center;\n"
                + "            margin-top: 20px;\n"
                + "        }\n"
                + "    </style>\n"
                + "</head>\n"
                + "<body>\n"
                + "    <div class=\"container\">\n"
                + "        <h1>Réinitialisation de votre mot de passe</h1>\n"
                + "        <p>Bonjour,</p>\n"
                + "        <p>Vous avez demandé la réinitialisation de votre mot de passe. Votre code de reinitialisation est : </p>\n"
                + "        \n"
                + "        <a href=\"%s\" class=\"btn\">%s</a>\n"
                + "\n"
                + "        <p>Si vous n'avez pas demandé cette réinitialisation, vous pouvez ignorer cet email. Le lien expirera dans 15 minutes.</p>\n"
                + "        <p>Merci,</p>\n"
                + "        <p>L'équipe Support</p>\n"
                + "        \n"
                + "        <div class=\"footer\">\n"
                + "            &copy; 2024 Gozen Service. Tous droits réservés.\n"
                + "        </div>\n"
                + "    </div>\n"
                + "</body>\n"
                + "</html>", token, token));

        // Envoyer le jeton par email
        emailService.envoyerMailHTML(user.getEmail(), "Réinitialisation du mot de passe", resetEmailTemplate.toString());
    }

    public void resetPassword(String token, String newPassword) {
        User user = userRepository.findByPasswordResetToken(token)
                .orElseThrow(() -> new RuntimeException("Jeton invalide ou expiré"));

        if (System.currentTimeMillis() > user.getTokenExpirationDate()) {
            throw new RuntimeException("Jeton expiré");
        }

        user.setPassword(new BCryptPasswordEncoder().encode(newPassword));
        user.setPasswordResetToken(null);
        user.setTokenExpirationDate(null);
        userRepository.save(user);
    }
}
