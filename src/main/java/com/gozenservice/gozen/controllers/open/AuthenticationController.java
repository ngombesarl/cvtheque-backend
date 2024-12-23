package com.gozenservice.gozen.controllers.open;

import com.gozenservice.gozen.config.JwtUtil;
import com.gozenservice.gozen.dto.EmployeerDTO;
import com.gozenservice.gozen.dto.UserDTO;
import com.gozenservice.gozen.models.CV;
import com.gozenservice.gozen.models.Employer;
import com.gozenservice.gozen.models.JobSeeker;
import com.gozenservice.gozen.models.Role;
import com.gozenservice.gozen.models.User;
import com.gozenservice.gozen.repository.RoleRepository;
import com.gozenservice.gozen.repository.UserRepository;
import com.gozenservice.gozen.request.LoginRequest;
import com.gozenservice.gozen.request.VerificationRequest;
import com.gozenservice.gozen.response.Response;
import com.gozenservice.gozen.services.EmailService;
import com.gozenservice.gozen.services.FileStorageService;
import com.gozenservice.gozen.services.UserDetailsImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/public/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private EmailService emailService;

    @Operation(summary = "Register a new user", description = "Registers a new user with an email, password, role, and profile picture.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User registered successfully"),
        @ApiResponse(responseCode = "400", description = "Email is already taken or password is too short")
    })
    @PostMapping(value = "/register", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> registerUser(
            @RequestPart("user") @Valid UserDTO userDTO,
            @RequestPart(value = "profilePicture", required = false) MultipartFile profilePicture
    ) {
        // Vérification si l'email est déjà utilisé
        if (userRepository.findByEmail(userDTO.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("EMAIL_ALREADY_TAKEN_ERROR");
        }

        // Vérification si le Username est déjà utilisé
        if (userRepository.findByUsername(userDTO.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body("USERNAME_ALREADY_TAKEN_ERROR");
        }

        // Vérification de la longueur du mot de passe
        if (userDTO.getPassword().length() < 8) {
            return ResponseEntity.badRequest().body("Password must be at least 8 characters.");
        }

        // Sauvegarde de la photo de profil
        String profilePicturePath = "";
        if (profilePicture != null && !profilePicture.isEmpty()) {
            if (!profilePicture.getContentType().startsWith("image/")) {
                return ResponseEntity.badRequest().body("Profile picture must be an image.");
            }
            profilePicturePath = fileStorageService.saveFile(profilePicture);
        }

        // Création de l'utilisateur
        JobSeeker user = new JobSeeker();
        user.setEmail(userDTO.getEmail());
        user.setPassword(new BCryptPasswordEncoder().encode(userDTO.getPassword()));
        user.setName(userDTO.getName());
        user.setPhone(userDTO.getPhone());
        user.setProfilePhotoUrl(profilePicturePath); // Enregistre le chemin de l'image
        user.setSurname(userDTO.getSurname());
        user.setUsername(userDTO.getUsername());
        user.setVerified(false);

        // Attribution du rôle à l'utilisateur
        Role role = roleRepository.findByName(userDTO.getRole())
                .orElseThrow(() -> new RuntimeException("Role not found"));
        user.setRoles(role);

        // Création et association du CV
        CV cv = new CV();
        cv.setUser(user); // Associe l'utilisateur au CV
        user.setCv(cv); // Associe le CV à l'utilisateur

        // Sauvegarde de l'utilisateur et du CV
        userRepository.save(user);

        sendVerificationCode(user);

        return ResponseEntity.ok().body(new Response(200, "User registered successfully").toJson());
    }

    @Operation(summary = "Register a new user", description = "Registers a new user with an email, password, and role.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User registered successfully"),
        @ApiResponse(responseCode = "400", description = "Email is already taken or password is too short")
    })
    @PostMapping(value = "/register-employeer", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> registerEmployeer(@RequestPart("user") @Valid EmployeerDTO userDTO,
            @RequestPart(value = "profilePicture", required = false) MultipartFile profilePicture) {
        // Vérification si l'email est déjà utilisé
        if (userRepository.findByEmail(userDTO.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("Email is already taken.");
        }

        // Vérification de la longueur du mot de passe
        if (userDTO.getPassword().length() < 8) {
            return ResponseEntity.badRequest().body("Password must be at least 8 characters.");
        }

        // Vérification si le Username est déjà utilisé
        if (userRepository.findByUsername(userDTO.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body("Username is already taken.");
        }

        // Sauvegarde de la photo de profil
        String profilePicturePath = "";
        if (profilePicture != null && !profilePicture.isEmpty()) {
            if (!profilePicture.getContentType().startsWith("image/")) {
                return ResponseEntity.badRequest().body("Profile picture must be an image.");
            }
            profilePicturePath = fileStorageService.saveFile(profilePicture);
        }

        // Création de l'utilisateur
        Employer user = new Employer();
        user.setEmail(userDTO.getEmail());
        user.setPassword(new BCryptPasswordEncoder().encode(userDTO.getPassword()));
        user.setName(userDTO.getName()); // Répresente la raison sociale de l'entreprise 
        user.setPhone(userDTO.getPhone());
        user.setProfilePhotoUrl(profilePicturePath); // Enregistre le chemin de l'image
        user.setSector(userDTO.getSector());
        user.setActivity(userDTO.getActivity());
        user.setUsername(userDTO.getUsername());
        user.setVerified(false);

        // Attribution du rôle à l'utilisateur
        Role role = roleRepository.findByName(userDTO.getRole())
                .orElseThrow(() -> new RuntimeException("Role not found"));
        user.setRoles(role);

        // Sauvegarde de l'utilisateur et du CV (cascade ALL garantit la sauvegarde des deux)
        userRepository.save(user);

        sendVerificationCode(user);

        return ResponseEntity.ok(new Response(200, "User registered successfully").toJson());
    }

    @Operation(summary = "User login", description = "Logs in a user and returns a JWT token in the headers.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Logged in successfully"),
        @ApiResponse(responseCode = "401", description = "Invalid credentials")
    })
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    loginRequest.getEmail(), loginRequest.getPassword()));

            User user = userRepository.findByEmail(loginRequest.getEmail())
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));

            if (!user.isVerified()) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Account not verified. Please verify your email.");
            }

            String token = jwtUtil.generateToken(new UserDetailsImpl(user));

            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + token);
            headers.set("User-Type", user.getRoles().getName());

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null) {
                System.out.println("User Authorities: " + authentication.getAuthorities());
            }
            user.setPassword("");

            return ResponseEntity.ok().headers(headers).body(user.toJson());
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }

    public void sendVerificationCode(User user) {
        String code = String.valueOf((int) (Math.random() * 900000) + 100000); // Génère un OTP de 6 chiffres
        user.setVerificationCode(code);
        userRepository.save(user);

        String subject = "Verify your account";
        String body = "Hello, here is your verification code : " + code;
        emailService.envoyerMailHTML(user.getEmail(), subject, body);
    }

    @PostMapping("/verify")
    public ResponseEntity<?> verifyEmail(@RequestBody VerificationRequest verificationRequest) {
        User user = userRepository.findByEmail(verificationRequest.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        if (user.getVerificationCode().equals(verificationRequest.getCode())) {
            user.setVerified(true);
            user.setVerificationCode(null);
            userRepository.save(user);
            return ResponseEntity.status(HttpStatus.OK).body(new Response(HttpStatus.OK.value(), "Account verified successfully."));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid verification code.");
        }
    }
    
    @PostMapping("/resend-code")
    public ResponseEntity<?> resendCode(@RequestBody String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        
        String code = String.valueOf((int) (Math.random() * 900000) + 100000); // Génère un OTP de 6 chiffres
        
        user.setVerificationCode(code);
        userRepository.save(user);
        
        String subject = "Verify your account";
        String body = "Hello, here is your verification code : " + code;
        try{
        emailService.envoyerMailHTML(user.getEmail(), subject, body);
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), "An Error occured while sending code. Please retry"));
        }
        
        return ResponseEntity.ok(new Response(HttpStatus.OK.value(), "Verification email send successfully"));
    }

}
