package com.gozenservice.gozen.config;

import com.gozenservice.gozen.models.User;
import java.nio.file.AccessDeniedException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

public class Utilitaire {

    // Fonction pour crypter un mot de passe
    public static String crypterMotDePasse(String motDePasse) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(motDePasse.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    // Fonction pour vérifier si une chaîne de caractères est une adresse email
    public static boolean estAdresseEmail(String chaine) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pat = Pattern.compile(emailRegex);
        if (chaine == null) {
            return false;
        }
        return pat.matcher(chaine).matches();
    }

    // Fonction pour vérifier si une chaîne est un mot de passe valide
    public static boolean estMotDePasseValide(String motDePasse) {
        if (motDePasse.length() < 8) {
            return false;
        }

        boolean contientChiffre = false;
        boolean contientMajuscule = false;
        boolean contientMinuscule = false;
        boolean contientSymbole = false;

        for (char c : motDePasse.toCharArray()) {
            if (Character.isDigit(c)) {
                contientChiffre = true;
            } else if (Character.isUpperCase(c)) {
                contientMajuscule = true;
            } else if (Character.isLowerCase(c)) {
                contientMinuscule = true;
            } else {
                contientSymbole = true;
            }
        }

        return contientChiffre && contientMajuscule && contientMinuscule && contientSymbole;
    }

    // Exemple d'utilisation des fonctions
    public static void main(String[] args) {
        String motDePasse = "P@ssw0rd";
        String email = "example@domain.com";

        System.out.println("Mot de passe crypté : " + crypterMotDePasse(motDePasse));
        System.out.println("Est une adresse email : " + estAdresseEmail(email));
        System.out.println("Est un mot de passe valide : " + estMotDePasseValide(motDePasse));
    }

    public void checkPermission(User user, String requiredPermission) {
        boolean hasPermission = user.getRoles().getPermissions().stream()
                .anyMatch(permission -> permission.getName().equals(requiredPermission));

        if (!hasPermission) {
            try {
                throw new AccessDeniedException("You do not have permission to perform this action");
            } catch (AccessDeniedException ex) {
                Logger.getLogger(Utilitaire.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
