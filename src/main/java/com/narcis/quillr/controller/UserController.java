package com.narcis.quillr.controller;

import com.narcis.quillr.model.User;
import com.narcis.quillr.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;
    public record RegisterRequest(String username, String email, String password) {}
    public record LoginRequest(String email, String password) {}

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id){
        Optional<User> user = userService.getUserById(id);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody RegisterRequest request){
        if(userService.registerUser(request.username(), request.email(), request.password())){
            return ResponseEntity.ok("New user registered!");
        }
        return ResponseEntity.badRequest().body("Couldn't register new user!");
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest request){
        Optional<User> user = userService.loginUser(request.email(), request.password());
        if (user.isPresent()){
            return  ResponseEntity.ok(user.get());
        }
        return ResponseEntity.badRequest().body("Invalid credentials!");
    }
}
