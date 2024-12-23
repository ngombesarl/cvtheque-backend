package com.gozenservice.gozen.services;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender emailSender;

    public void envoyerEmail(String destinataire, String sujet, String texte) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(destinataire);
        message.setSubject(sujet);
        message.setText(texte);
        emailSender.send(message);
    }
    
    public void envoyerMailHTML(String destinataire, String sujet, String texte) {
        try {
            MimeMessage message = emailSender.createMimeMessage();
            
            message.setFrom(new InternetAddress("Gozen Service <andoboys28@gmail.com>"));
            message.setRecipients(MimeMessage.RecipientType.TO, destinataire);
            message.setSubject(sujet);
            message.setContent(texte, "text/html; charset=utf-8");
            emailSender.send(message);
        } catch (AddressException ex) {
            Logger.getLogger(EmailService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MessagingException ex) {
            Logger.getLogger(EmailService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
