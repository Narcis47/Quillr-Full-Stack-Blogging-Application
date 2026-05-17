package com.narcis.quillr.service;

import com.narcis.quillr.model.Profile;
import com.narcis.quillr.repository.ProfileRepository;
import com.narcis.quillr.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProfileService {
    private final ProfileRepository profileRepository;
    private final UserRepository userRepository;

    public ProfileService(ProfileRepository profileRepository,UserRepository userRepository) {
        this.profileRepository = profileRepository;
        this.userRepository = userRepository;
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

    public Optional<Profile> getProfileByUsername(String username){
        return userRepository.findByUsernameIgnoreCase(username).flatMap(user -> profileRepository.findByUserId(user.getId()));
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
