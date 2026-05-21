package com.narcis.quillr.controller;

import com.narcis.quillr.JwtService;
import com.narcis.quillr.model.Profile;
import com.narcis.quillr.service.ProfileService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/profile")
public class ProfileController {
    private final ProfileService profileService;
    private final JwtService jwtService;
    public record ProfileRequest(String bio, String avatarUrl, String website) {}

    public ProfileController(ProfileService profileService, JwtService jwtService) {
        this.profileService = profileService;
        this.jwtService = jwtService;
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<Profile> getProfileByUsername(@PathVariable String username){
        Optional<Profile> profile = profileService.getProfileByUsername(username);
        return profile.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Profile> getProfileById(@PathVariable Long userId){
        Optional<Profile> profile = profileService.getProfileByUserId(userId);
        return profile.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{userId}")
    public ResponseEntity<String> updateProfileById(@PathVariable Long userId,@RequestBody ProfileRequest request,@RequestHeader("Authorization") String authHead){
        Long userIdFromToken = jwtService.extractUserId(authHead.substring(7));

        if(!userId.equals(userIdFromToken)) return ResponseEntity.status(403).body("You can only edit your own profile!");
        boolean update = profileService.updateProfile(userId, request.bio(), request.avatarUrl(), request.website());
        return update ? ResponseEntity.ok("Profile Updated!") : ResponseEntity.badRequest().body("Cannot update profile!");
    }
}
