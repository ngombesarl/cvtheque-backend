/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.gozenservice.gozen.controllers.cv;

/**
 *
 * @author TCHINGANG Steve
 */

import com.gozenservice.gozen.dto.CvData;
import com.gozenservice.gozen.request.cv.CVGenerationRequest;
import com.gozenservice.gozen.response.Response;
import com.gozenservice.gozen.services.cv.CVGenerationService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/all/cv")
public class CVGenerationController {

    private final CVGenerationService cvService;

    public CVGenerationController(CVGenerationService cvService) {
        this.cvService = cvService;
    }

    @PostMapping("/generate")
    public ResponseEntity<?> generateCv(@RequestBody CVGenerationRequest cvRequest) {
        CvData cvData = cvRequest.toCvData();

        if ("html".equalsIgnoreCase(cvRequest.getFormat())) {
            String htmlContent = cvService.generateHtml(cvData);
            return ResponseEntity.ok()
                    .contentType(MediaType.TEXT_HTML)
                    .body(htmlContent);
        } else if ("pdf".equalsIgnoreCase(cvRequest.getFormat())) {
            byte[] pdfContent = cvService.generatePdf(cvData);
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"cv.pdf\"")
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(pdfContent);
        } else {
            return ResponseEntity.badRequest().body(new Response(HttpStatus.BAD_REQUEST.value(), "Unsupported format : " + cvRequest.getFormat()));
        }
    }
}

