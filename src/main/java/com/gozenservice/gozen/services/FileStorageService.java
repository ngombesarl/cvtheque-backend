/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gozenservice.gozen.services;

import jakarta.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Service
@RequiredArgsConstructor
public class FileStorageService {

    @Value("${upload.dir}")
    private String uploadDir;
    
    @Value("${upload.dir.document}")
    private String uploadDirDocument;

    // Initialisation du dossier de stockage
    @PostConstruct
    public void init() {
        Path uploadPath = Paths.get(uploadDir);
        Path uploadDocumentPath = Paths.get(uploadDirDocument);
        
        if (!Files.exists(uploadPath)) {
            try {
                Files.createDirectories(uploadPath);
            } catch (IOException e) {
                throw new RuntimeException("Could not create upload directory: " + uploadDir, e);
            }
        }
        
        if (!Files.exists(uploadDocumentPath)) {
            try {
                Files.createDirectories(uploadDocumentPath);
            } catch (IOException e) {
                throw new RuntimeException("Could not create upload document directory: " + uploadDirDocument, e);
            }
        }
    }

    // Sauvegarder un fichier
    public String saveFile(MultipartFile file) {
        String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        Path filePath = Paths.get(uploadDir).resolve(fileName);

        try {
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException("Failed to store file: " + fileName, e);
        }

        return fileName; // Retourne le nom du fichier pour sauvegarde en base
    }
    
    // Sauvegarder un fichier
    public String saveDocumentFile(MultipartFile file) {
        String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        Path filePath = Paths.get(uploadDirDocument).resolve(fileName);

        try {
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException("Failed to store file: " + fileName, e);
        }

        return fileName; // Retourne le nom du fichier pour sauvegarde en base
    }

    public String getFileUrl(String fileName) {
        return ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/uploads/")
                .path(fileName)
                .toUriString();
    }
}
