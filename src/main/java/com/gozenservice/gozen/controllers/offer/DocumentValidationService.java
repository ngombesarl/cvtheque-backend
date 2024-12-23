/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.gozenservice.gozen.controllers.offer;

/**
 *
 * @author TCHINGANG Steve
 */
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class DocumentValidationService {

    private static final long MAX_FILE_SIZE = 2 * 1024 * 1024; // 2 Mo
    private static final String[] ALLOWED_TYPES = {"application/pdf", "application/msword", "application/vnd.openxmlformats-officedocument.wordprocessingml.document"};

    public void validateFile(MultipartFile file, String fileType) {
        if (file == null || file.isEmpty()) {
            return; // Fichier non obligatoire
        }
        if (file.getSize() > MAX_FILE_SIZE) {
            throw new RuntimeException(fileType + " exceeds maximum size of 2MB");
        }
        if (!isAllowedType(file.getContentType())) {
            throw new RuntimeException(fileType + " must be a PDF or Word document");
        }
    }

    private boolean isAllowedType(String contentType) {
        for (String type : ALLOWED_TYPES) {
            if (type.equals(contentType)) {
                return true;
            }
        }
        return false;
    }
}

