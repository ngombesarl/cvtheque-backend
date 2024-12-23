/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gozenservice.gozen.controllers;

/**
 *
 * @author TCHINGANG Steve
 */
import com.gozenservice.gozen.dto.UpdatePasswordDTO;
import com.gozenservice.gozen.models.JobSeeker;
import com.gozenservice.gozen.models.User;
import com.gozenservice.gozen.repository.UserRepository;
import com.gozenservice.gozen.response.Response;
import com.gozenservice.gozen.services.UserDetailsImpl;
import com.gozenservice.gozen.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/all/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;
    
    @Value("${upload.dir}")
    private String uploadDir;

    @Operation(summary = "Mise à jour du mot de passe de l'utilisateur",
            description = "Permet de mettre à jour le mot de passe d'un utilisateur après validation du mot de passe actuel. le token doit etre "
            + "ajouté au header ex: Authorization: Bearer token")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Mot de passe mis à jour avec succès"),
        @ApiResponse(responseCode = "400", description = "Validation du mot de passe échouée"),
        @ApiResponse(responseCode = "404", description = "Utilisateur non trouvé"),
        @ApiResponse(responseCode = "500", description = "Mot de passe actuel incorrect")

    })
    @PutMapping("/update-password")
    public ResponseEntity<?> updatePassword(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @Valid @RequestBody UpdatePasswordDTO updatePasswordDTO) {
        System.out.println(userDetails.getUsername());
        Long userId = getCurrentUserId(userDetails); // Extrait l'ID de l'utilisateur connecté
        String message = userService.updatePassword(userId, updatePasswordDTO);
        return ResponseEntity.ok(new Response(HttpStatus.OK.value(), message));
    }

    private Long getCurrentUserId(UserDetails userDetails) {

        System.out.println(userDetails.getUsername());
        User user = userRepository.findByEmail(userDetails.getUsername()).orElseThrow();
        return user.getId();
    }

    @Operation(summary = "Get logged-in user details", description = "Fetches the details of the currently logged-in user, including profile picture.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User details retrieved successfully"),
        @ApiResponse(responseCode = "401", description = "Unauthorized access")
    })
    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized access");
        }

        // Récupérer l'utilisateur depuis le principal
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        User user = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        // Nettoyer le mot de passe avant d'envoyer la réponse
        user.setPassword("");

        return ResponseEntity.ok(user);
    }
    
    @Operation(summary = "Get all jobSeeker", description = "Fetches the details of the user, including profile picture.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User details retrieved successfully"),
        @ApiResponse(responseCode = "401", description = "Unauthorized access")
    })
    @GetMapping
    public ResponseEntity<?> getAllJobSeeker() {
        List<User> allUsers = userService.findAllUsers();
        List<User> users = new ArrayList<>();
        for(User user : allUsers) {
            user.setPassword("");
            if(user instanceof JobSeeker)
                users.add(user);
        }

        return ResponseEntity.ok(users);
    }
    
    @Operation(summary = "Get all jobSeeker filtered", description = "Fetches the details of the user about filter, including profile picture.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User details retrieved successfully"),
        @ApiResponse(responseCode = "401", description = "Unauthorized access")
    })
    @GetMapping("/filter")
    public ResponseEntity<?> filterJobSeeker(@RequestParam String skill, @RequestParam String jobTitle) {
        List<User> allUsers = userService.findAllUsers();
        List<User> users = new ArrayList<>();
        String[] skillSearch = skill.split(" ");
        String[] jobSearch = jobTitle.split("");
        
        // fusion des deux parametres de recherches
        String [] searchTable = Stream.concat(Arrays.stream(skillSearch), Arrays.stream(jobSearch))
                .toArray(String[]::new);

        // Filtrage des JobSeeker
        for(User user : allUsers) {
            user.setPassword("");
            if(user instanceof JobSeeker) {
                // découpage du poste de l'utilisateur (jobSeeker)
                String [] jobTitleTable = ((JobSeeker) user).getCv().getJobTitle().split(" ");
                for(int i = 0; i< searchTable.length; i++) {
                    for(int j = 0; j< jobTitleTable.length; j++) {
                        if(jobTitleTable[j].contains(searchTable[i]) || searchTable[i].contains(jobTitleTable[j])) {
                            users.add(user);
                        }
                    }
                } 
            }
        }
        
        List<User> finalUser = new ArrayList<User>();
        // Elimination des doublons
        for(int i = 0; i<users.size(); i++) {
            if(!finalUser.contains(users.get(i))) 
                finalUser.add(users.get(i));
        }
        

        return ResponseEntity.ok(finalUser);
    }

    @GetMapping("/uploads/{fileName:.+}")
    public ResponseEntity<Resource> serveFile(@PathVariable String fileName) {
        try {
            Path filePath = Paths.get(uploadDir).resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists() && resource.isReadable()) {
                if(resource.getURL().toString().endsWith("jpeg") || resource.getURL().toString().endsWith("jpg"))
                return ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_JPEG) // ou ajustez selon le type de fichier
                        .body(resource);
                else if (resource.getURL().toString().endsWith("png")) 
                    return ResponseEntity.ok()
                            .contentType(MediaType.IMAGE_PNG)
                            .body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
        return null;
    }

}
