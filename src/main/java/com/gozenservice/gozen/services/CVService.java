/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gozenservice.gozen.services;

import com.gozenservice.gozen.models.CV;
import com.gozenservice.gozen.models.Education;
import com.gozenservice.gozen.models.Experience;
import com.gozenservice.gozen.models.JobSeeker;
import com.gozenservice.gozen.models.Reference;
import com.gozenservice.gozen.models.Skill;
import com.gozenservice.gozen.models.User;
import com.gozenservice.gozen.repository.UserRepository;
import com.gozenservice.gozen.repository.cv.CVRepository;
import com.gozenservice.gozen.request.CVRequest;
import com.gozenservice.gozen.request.cv.CVDetailRequest;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.draw.SolidLine;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.LineSeparator;
import com.itextpdf.layout.element.ListItem;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.properties.HorizontalAlignment;
import com.itextpdf.layout.properties.TextAlignment;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author TCHINGANG Steve
 */
@Service
public class CVService {

    @Autowired
    private CVRepository cvRepository;

    @Autowired
    private UserRepository userRepository;
    

    // Create a new CV for a user
    @Transactional
    public void createCV(CVRequest cvRequest, @AuthenticationPrincipal User user) {
        // Vérifier si l'utilisateur est un JobSeeker
        if (!(user instanceof JobSeeker)) {
            throw new RuntimeException("Only JobSeekers can create a CV");
        }

        // Récupérer le JobSeeker
        JobSeeker jobSeeker = (JobSeeker) user;

        // Vérifier si un CV existe déjà pour ce JobSeeker
        CV existingCV = cvRepository.findByUserId(jobSeeker.getId()).orElse(null);
        CV cv;

        if (existingCV == null) {
            // Créer un nouveau CV
            cv = new CV();
            cv.setUser(jobSeeker);
            jobSeeker.setCv(cv); // Associer le CV au JobSeeker
        } else {
            // Mettre à jour le CV existant
            cv = existingCV;
            cv.getSkills().clear();
            cv.getExperiences().clear();
            cv.getEducations().clear();
            cv.getReferences().clear();
        }

        // Ajouter les expériences
        cvRequest.getExperiences().forEach(e -> {
            e.setCv(cv); // Associer l'expérience au CV
            cv.getExperiences().add(e);
        });

        // Ajouter les éducations
        cvRequest.getEducations().forEach(edu -> {
            edu.setCv(cv); // Associer l'éducation au CV
            cv.getEducations().add(edu);
        });

        // Ajouter les compétences
        cvRequest.getSkills().forEach(skill -> {
            skill.setCv(cv); // Associer la compétence au CV
            cv.getSkills().add(skill);
        });

        // Ajouter les références
        cvRequest.getReferences().forEach(ref -> {
            ref.setCv(cv); // Associer la référence au CV
            cv.getReferences().add(ref);
        });

        // Mettre à jour les autres propriétés du CV
        cv.setStatus(cvRequest.getStatus());
        cv.setExperiencePoints(cvRequest.getExperiencePoints());

        // Sauvegarder le CV et l'utilisateur
        cvRepository.save(cv);
    }

      /**
     * Génère un fichier PDF du CV d'un utilisateur
     * @param userId ID de l'utilisateur
     * @return Fichier PDF en tant que ressource
     * @throws IOException En cas d'erreur
     */
    public Resource generateCVPdf(Long cvId) throws IOException {
//        // Vérification de l'utilisateur
//        User user = userRepository.findById(userId)
//                .orElseThrow(() -> new RuntimeException("User not found"));
//        if (!(user instanceof JobSeeker)) {
//            throw new RuntimeException("User is not a JobSeeker");
//        }

        // Récupérer le CV associé
        CV cv = cvRepository.findById(cvId)
                .orElseThrow(() -> new RuntimeException("CV not found for user"));

        // Créer un fichier temporaire pour le PDF
        File pdfFile = File.createTempFile("CV_" + cvId, ".pdf");

        // Générer le PDF
        generatePdf(cv, pdfFile.getAbsolutePath());

        return new ByteArrayResource(Files.readAllBytes(pdfFile.toPath()));
    }

    /**
     * Génère le PDF à partir du modèle CV
     * @param cv Modèle CV
     * @param filePath Chemin de destination du PDF
     */
    private void generatePdf(CV cv, String filePath) {
    try (PdfWriter writer = new PdfWriter(filePath);
         PdfDocument pdfDoc = new PdfDocument(writer);
         Document document = new Document(pdfDoc)) {

        // Ajouter une couleur d'arrière-plan au document
        document.setBackgroundColor(new DeviceRgb(240, 240, 240));

        // Titre principal avec couleur
        document.add(new Paragraph("Curriculum Vitae")
                .setFontSize(24).setBold()
                .setFontColor(new DeviceRgb(0, 102, 204))
                .setTextAlignment(TextAlignment.CENTER)
                .setMarginBottom(30));

        // Ajouter une ligne de séparation avec une couleur
        LineSeparator lineSeparator = new LineSeparator(new SolidLine(2f)); // Taille augmentée
        lineSeparator.setFontColor(new DeviceRgb(0, 102, 204)); // Bleu

        // Ajouter l'image avec bords arrondis
        if (cv.getUser().getProfilePhotoUrl() != null) {
            String path = "uploads/profile-pictures/";
            ImageData imageData = ImageDataFactory.create(path + cv.getUser().getProfilePhotoUrl());
            Image profilePhoto = new Image(imageData)
                    .scaleToFit(100, 100)
                    .setMargins(0, 0, 20, 0);
                 //   .setBorder(new RoundDotsBorder(10)); // Bords arrondis
            document.add(profilePhoto.setHorizontalAlignment(HorizontalAlignment.CENTER));
        }

        // Section Informations personnelles avec couleur
        document.add(new Paragraph("Personal Information")
                .setFontSize(16).setBold()
                .setFontColor(new DeviceRgb(0, 153, 76)) // Vert
                .setMarginBottom(10));
        document.add(lineSeparator);
        document.add(new Paragraph("Name: " + cv.getUser().getName()));
        document.add(new Paragraph("Email: " + cv.getUser().getEmail()));
        document.add(new Paragraph("Phone: " + cv.getUser().getPhone()));

        // Section Description avec couleur
        if (cv.getDescription() != null) {
            document.add(new Paragraph("Profile Description")
                    .setFontSize(16).setBold()
                    .setFontColor(new DeviceRgb(0, 153, 76)) // Vert
                    .setMarginBottom(10));
            document.add(lineSeparator);
            document.add(new Paragraph(cv.getDescription()));
        }

        // Section Compétences avec couleur
        if (!cv.getSkills().isEmpty()) {
            document.add(new Paragraph("Skills")
                    .setFontSize(16).setBold()
                    .setFontColor(new DeviceRgb(0, 153, 76)) // Vert
                    .setMarginBottom(10));
            document.add(lineSeparator);
            com.itextpdf.layout.element.List list = new com.itextpdf.layout.element.List();
            cv.getSkills().forEach(skill -> list.add(new ListItem(String.format("%s :  %s", skill.getName(), skill.getDescription()))));
            document.add(list);
        }

        // Section Expériences avec couleur
        if (!cv.getExperiences().isEmpty()) {
            document.add(new Paragraph("Experience")
                    .setFontSize(16).setBold()
                    .setFontColor(new DeviceRgb(0, 153, 76)) // Vert
                    .setMarginBottom(10));
            document.add(lineSeparator);
            cv.getExperiences().forEach(exp -> {
                document.add(new Paragraph(exp.getPosition() + " at " + exp.getCompany())
                        .setFontColor(new DeviceRgb(0, 153, 76)) // Vert
                        .setBold());
                document.add(new Paragraph(exp.getStartDate() + " - " +
                        (exp.getEndDate() != null ? exp.getEndDate() : "Present")));
            });
        }

        // Section Éducation avec couleur
        if (!cv.getEducations().isEmpty()) {
            document.add(new Paragraph("Education")
                    .setFontSize(16).setBold()
                    .setFontColor(new DeviceRgb(0, 153, 76)) // Vert
                    .setMarginBottom(10));
            document.add(lineSeparator);
            cv.getEducations().forEach(edu -> {
                document.add(new Paragraph(edu.getDiplomaName() + " at " + edu.getInstitution())
                        .setFontColor(new DeviceRgb(0, 153, 76))); // Vert
                document.add(new Paragraph(edu.getStartDate() + " - " +
                        (edu.getEndDate() != null ? edu.getEndDate() : "Present")));
            });
        }

        // Section Références avec couleur
        if (!cv.getReferences().isEmpty()) {
            document.add(new Paragraph("References")
                    .setFontSize(16).setBold()
                    .setFontColor(new DeviceRgb(0, 153, 76)) // Vert
                    .setMarginBottom(10));
            document.add(lineSeparator);
            cv.getReferences().forEach(ref -> document.add(new Paragraph(ref.getName() + " - " + ref.getContactInfo())));
        }

    } catch (IOException e) {
        throw new RuntimeException("Error generating PDF", e);
    }
}

    
    public void updateCV(Long cvId, CVRequest cvRequest) {
        CV cv = cvRepository.findById(cvId).orElseThrow(() -> new RuntimeException("CV not found"));
        cv.setSkills(cvRequest.getSkills());
        cv.setExperiences(cvRequest.getExperiences());
        cv.setEducations(cvRequest.getEducations());
        cv.setReferences(cvRequest.getReferences());
        cv.setExperiencePoints(cvRequest.getExperiencePoints());
        cv.setStatus(cvRequest.getStatus());
        cvRepository.save(cv);
    }
    
    public void updateCVDetail(Long cvId, CVDetailRequest cvRequest) {
        CV cv = cvRepository.findById(cvId).orElseThrow(() -> new RuntimeException("CV not found"));
        cv.setDescription(cvRequest.getDescription());
        cv.setJobTitle(cvRequest.getJobTitle());
        cvRepository.save(cv);
    }

    // Méthode pour récupérer le CV d'un utilisateur
    public CV getCVByUser(Long userId) {
        return cvRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("No CV found for this user"));
    }
    
    // Méthode pour récupérer le CV d'un utilisateur
    public java.util.List<CV> getAllCV() {
        return cvRepository.findAll();
    }

//    /**
//     * Génère un fichier CV à partir d'un ID de CV et d'un type de template
//     * @param cvId ID du CV
//     * @param templateType Type de template
//     * @return Fichier CV sous forme de ressource
//     * @throws IOException Exception en cas d'erreur de lecture/écriture
//     */
//    public Resource generateCVFile(Long cvId, String templateType) throws IOException {
//        CV cv = cvRepository.findById(cvId)
//                .orElseThrow(() -> new RuntimeException("CV not found"));
//
//        String htmlContent = getHtmlTemplate(cv, templateType);
//        byte[] pdfBytes = generatePdfFromHtml(htmlContent);
//        return new ByteArrayResource(pdfBytes);
//    }

    /**
     * Charge et remplit le template HTML avec les données du CV
     * @param cv Le CV à utiliser
     * @param templateType Type du template
     * @return Contenu HTML complet
     * @throws IOException Exception en cas d'erreur de lecture
     */
    public String getHtmlTemplate(CV cv, String templateType) throws IOException {
        // Récupération du chemin de la photo de profil
        File file = new File("C:\\Users\\r.informatique\\Documents\\NetBeansProjects\\Gozen Service Backend\\uploads\\profile-pictures\\");
        String profilePhotoPath = file.getAbsolutePath() + "\\" + cv.getUser().getProfilePhotoUrl();

        // Charger le fichier template en fonction du type
        String templateFile = "templates/cv_" + templateType + ".html"; // Exemple : cv_canadian.html
        Path templatePath = new ClassPathResource(templateFile).getFile().toPath();

        // Lire et charger le contenu du template HTML
        String htmlContent = new String(Files.readAllBytes(templatePath));

        // Remplacer les placeholders par les données du CV
        htmlContent = htmlContent.replace("{{name}}", formatFullName(cv.getUser()));
        htmlContent = htmlContent.replace("{{email}}", cv.getUser().getEmail());
        htmlContent = htmlContent.replace("{{phone}}", cv.getUser().getPhone());
        htmlContent = htmlContent.replace("{{profilePhoto}}", profilePhotoPath);
        htmlContent = htmlContent.replace("{{skills}}", formatSkills(cv.getSkills()));
        htmlContent = htmlContent.replace("{{experience}}", formatExperience(cv.getExperiences()));
        htmlContent = htmlContent.replace("{{education}}", formatEducation(cv.getEducations()));
        htmlContent = htmlContent.replace("{{references}}", formatReferences(cv.getReferences()));
        htmlContent = htmlContent.replace("{{qualification}}", String.valueOf(cv.getExperiencePoints()));

        return htmlContent;
    }

    /**
     * Génère un fichier PDF à partir d'un contenu HTML
     * @param htmlContent Contenu HTML
     * @return Bytes du PDF
     */
//    private byte[] generatePdfFromHtml(String htmlContent) {
//        // Implémentation de génération de PDF (exemple avec iText ou autre librairie)
//        return new byte[0];
//    }

    /**
     * Formate le nom complet de l'utilisateur
     * @param user Utilisateur associé
     * @return Nom complet formaté
     */
    private String formatFullName(User user) {
        return user.getName() + " " + user.getSurname();
    }

    /**
     * Formate la liste des compétences
     * @param skills Liste des compétences
     * @return Compétences formatées sous forme de liste
     */
    private String formatSkills(List<Skill> skills) {
        return skills.stream()
                .map(Skill::getName)
                .collect(Collectors.joining("</li><li>", "<ul><li>", "</li></ul>"));
    }

    /**
     * Formate les expériences professionnelles
     * @param experiences Liste des expériences
     * @return Expériences formatées sous forme de liste
     */
    private String formatExperience(List<Experience> experiences) {
        return experiences.stream()
                .map(exp -> exp.getPosition() + " at " + exp.getCompany() + " (" + exp.getStartDate() + " - " + exp.getEndDate() + ")")
                .collect(Collectors.joining("</li><li>", "<ul><li>", "</li></ul>"));
    }

    /**
     * Formate les études
     * @param educations Liste des études
     * @return Études formatées sous forme de liste
     */
    private String formatEducation(List<Education> educations) {
        return educations.stream()
                .map(edu -> edu.getDiplomaName() + " from " + edu.getInstitution() + " (" + edu.getStartDate() + " - " + edu.getEndDate() + ")")
                .collect(Collectors.joining("</li><li>", "<ul><li>", "</li></ul>"));
    }

    /**
     * Formate les références
     * @param references Liste des références
     * @return Références formatées sous forme de liste
     */
    private String formatReferences(List<Reference> references) {
        return references.stream()
                .map(ref -> ref.getName() + ": " + ref.getContactInfo())
                .collect(Collectors.joining("</li><li>", "<ul><li>", "</li></ul>"));
    }


//    public byte[] generatePdfFromHtml(String htmlContent) throws IOException {
//        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//        Document document = new Document();
//
//        try {
//            PdfWriter.getInstance(document, byteArrayOutputStream);
//            document.open();
//
//            // Parse the HTML and add it to the PDF
//            HTMLWorker htmlWorker = new HTMLWorker(document);
//            htmlWorker.parse(new StringReader(htmlContent));
//
//            document.close();
//        } catch (DocumentException e) {
//            e.printStackTrace();
//            throw new IOException("Error generating PDF");
//        }
//
//        return byteArrayOutputStream.toByteArray();
//    }
}
