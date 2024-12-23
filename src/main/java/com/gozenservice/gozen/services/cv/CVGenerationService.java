package com.gozenservice.gozen.services.cv;


import com.gozenservice.gozen.dto.CvData;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
/***
 * @author Tchingang Steve
*/
@Service
public class CVGenerationService {

    private final TemplateEngine templateEngine;

    public CVGenerationService(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    /**
     * Generate HTML content based on the CvData.
     */
    public String generateHtml(CvData cvData) {
        Context context = new Context();
        context.setVariable("cv", cvData);
        return templateEngine.process("cv_template", context);
    }

    /**
     * Generate PDF using OpenHTMLtoPDF.
     */
    public byte[] generatePdf(CvData cvData) {
        try {
            String htmlContent = generateHtml(cvData);

            // Convert HTML to PDF using OpenHTMLtoPDF
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            try (var pdfStream = outputStream) {
                com.openhtmltopdf.pdfboxout.PdfRendererBuilder builder = new com.openhtmltopdf.pdfboxout.PdfRendererBuilder();
                builder.useFastMode();
                builder.withHtmlContent(htmlContent, null);
                builder.toStream(pdfStream);
                builder.run();
            }

            return outputStream.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Error while generating PDF", e);
        }
    }

    /**
     * Save the generated PDF to a file.
     */
    public void savePdfToFile(CvData cvData, String filePath) {
        try {
            byte[] pdfBytes = generatePdf(cvData);
            try (FileOutputStream fos = new FileOutputStream(filePath)) {
                fos.write(pdfBytes);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error while saving PDF", e);
        }
    }
}
