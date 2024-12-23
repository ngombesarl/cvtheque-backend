/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.gozenservice.gozen.services.offer;

/**
 *
 * @author TCHINGANG Steve
 */
import com.gozenservice.gozen.models.JobApplication;
import com.opencsv.CSVWriter;
import java.io.StringWriter;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class JobApplicationExportService {

    public String exportJobApplicationsToCSV(List<JobApplication> jobApplications) {
        StringWriter writer = new StringWriter();

        try (CSVWriter csvWriter = new CSVWriter(writer)) {
            // En-tête du CSV
            String[] header = {"ID", "Job Title", "Candidate Name", "Status", "Applied On", "Type"};
            csvWriter.writeNext(header);

            // Remplir le CSV avec les données filtrées
            for (JobApplication application : jobApplications) {
                String type = application.getIsProposed() ? "Invited" : "Self-Applied";
                csvWriter.writeNext(new String[]{
                        String.valueOf(application.getId()),
                        application.getJobOffer().getTitle(),
                        application.getJobSeeker().getName(),
                        application.getStatus().name(),
                        application.getApplicationDate().toString(),
                        type
                });
            }
        } catch (Exception e) {
            throw new RuntimeException("Error while exporting CSV", e);
        }

        return writer.toString();
    }
}
