/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gozenservice.gozen.controllers.offer;

/**
 *
 * @author TCHINGANG Steve
 */
import com.gozenservice.gozen.dto.FavoriteDTO;
import com.gozenservice.gozen.models.CV;
import com.gozenservice.gozen.models.User;
import com.gozenservice.gozen.repository.UserRepository;
import com.gozenservice.gozen.services.CVService;
import com.gozenservice.gozen.services.UserDetailsImpl;
import com.gozenservice.gozen.services.offer.FavoriteService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/employer/job/favorite")
public class FavoriteController {

    @Autowired
    private FavoriteService favoriteService;

    @Autowired
    private CVService cvService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/{jobSeekerId}")
    public ResponseEntity<String> addFavorite(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long jobSeekerId) {
        Optional<User> user = userRepository.findByEmail(userDetails.getUsername());

        CV cv = cvService.getCVByUser(jobSeekerId);
        favoriteService.addFavorite(user.get().getId(), cv);
        return ResponseEntity.ok("CV added to favorites");
    }

    @DeleteMapping("/{cvId}")
    public ResponseEntity<String> removeFavorite(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long cvId) {
        Optional<User> user = userRepository.findByEmail(userDetails.getUsername());
        favoriteService.removeFavorite(user.get().getId(), cvId);
        return ResponseEntity.ok("CV removed from favorites");
    }

//    @GetMapping
//    public ResponseEntity<List<Favorite>> getFavorites(@AuthenticationPrincipal UserDetailsImpl userDetails) {
//        Optional<User> user = userRepository.findByEmail(userDetails.getUsername());
//        return ResponseEntity.ok(favoriteService.getFavorites(user.get().getId()));
//    }
    @GetMapping
    public ResponseEntity<List<FavoriteDTO>> getFavorites(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        Optional<User> user = userRepository.findByEmail(userDetails.getUsername());
        if (user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        List<FavoriteDTO> favorites = favoriteService.getFavorites(user.get().getId());
        return ResponseEntity.ok(favorites);
    }
}
