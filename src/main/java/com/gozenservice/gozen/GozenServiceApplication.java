package com.gozenservice.gozen;

import java.security.SecureRandom;
import java.util.Base64;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;

@Configuration
@SpringBootApplication
//@EnableSwagger2
//@EnableWebMvc
public class GozenServiceApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(GozenServiceApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        SecureRandom secureRandom = new SecureRandom();
        byte[] secretBytes = new byte[32];  // 32 bytes = 256 bits, suffisamment sécurisé
        secureRandom.nextBytes(secretBytes);

        String jwtSecret = Base64.getEncoder().encodeToString(secretBytes);
        System.out.println("Generated JWT Secret: " + jwtSecret);
    }

}
