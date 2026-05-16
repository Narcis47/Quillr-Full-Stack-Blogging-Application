package com.narcis.quillr.service;

import com.narcis.quillr.model.Profile;
import com.narcis.quillr.repository.ProfileRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProfileService {
    private final ProfileRepository profileRepository;

    public ProfileService(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    public boolean createProfile(Long userId){
        if(profileRepository.findByUserId(userId).isEmpty()){
            Profile profile = Profile.builder().userId(userId).build();
            profileRepository.save(profile);
            return true;
        }
        return false;
    }

    public Optional<Profile> getProfileByUserId(Long userId){
        return profileRepository.findByUserId(userId);
    }

    public boolean updateProfile(Long userId, String bio, String avatarUrl, String website){
        Optional<Profile> existingProfile = profileRepository.findByUserId(userId);
        if(existingProfile.isPresent()){
            Profile profile = existingProfile.get();
            if (!bio.isEmpty()){profile.setBio(bio);}
            if (!avatarUrl.isEmpty()){profile.setAvatarUrl(avatarUrl);}
            if (!website.isEmpty()){profile.setWebsite(website);}
            profileRepository.save(profile);
            return true;
        }
        return false;
    }

}
