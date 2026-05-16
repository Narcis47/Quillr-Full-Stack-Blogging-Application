package com.narcis.quillr.controller;

import com.narcis.quillr.model.Profile;
import com.narcis.quillr.service.ProfileService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/profile")
public class ProfileController {
    private final ProfileService profileService;
    public record ProfileRequest(String bio, String avatarUrl, String website) {}

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Profile> getProfileById(@PathVariable Long userId){
        Optional<Profile> profile = profileService.getProfileByUserId(userId);
        return profile.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{userId}")
    public ResponseEntity<String> updateProfileById(@PathVariable Long userId,@RequestBody ProfileRequest request){
        boolean update = profileService.updateProfile(userId, request.bio(), request.avatarUrl(), request.website());
        if (update){
            return ResponseEntity.ok("Profile updated!");
        }
        return ResponseEntity.badRequest().body("Cannot update the profile!");
    }
}
