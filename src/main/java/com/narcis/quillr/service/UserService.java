package com.narcis.quillr.service;

import com.narcis.quillr.model.User;
import com.narcis.quillr.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final ProfileService profileService;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, ProfileService profileService, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.profileService = profileService;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean registerUser(String username, String email, String password){
        if(!userRepository.existsByEmail(email)){
            String hash = passwordEncoder.encode(password);
            User user = User.builder().username(username).email(email).createdAt(LocalDateTime.now()).passwordHash(hash).build();
            User savedUser = userRepository.save(user);
            profileService.createProfile(savedUser.getId());
            return true;
        }
        return false;
    }

    public Optional<User> loginUser(String email, String password){
        Optional<User> user = userRepository.findByEmail(email);
        if(user.isPresent() && passwordEncoder.matches(password, user.get().getPasswordHash())){
            return user;
        }
        return Optional.empty();
    }

    public Optional<User> getUserById(Long id){
        return userRepository.findById(id);
    }
}
