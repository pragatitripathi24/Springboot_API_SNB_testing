package com.Api_testing.controller;

import com.Api_testing.entity.User;
import com.Api_testing.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signUpUser(@RequestBody User user) {
        try {
            User savedUser = userRepository.save(user);
            return ResponseEntity.ok("User registered successfully with ID: " + savedUser.getId());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to register user: " + e.getMessage());
        }
    }

    @GetMapping("/signin")
    public ResponseEntity<?> signInUser(@RequestParam("name") String name,
                                        @RequestParam("password") String password) {
        System.out.println("Sign-in attempt with name: " + name + " and password: " + password);
        try {
            Optional<User> user = userRepository.findByNameAndPassword(name, password);
            if (user.isPresent()) {
                System.out.println("User found: " + user.get().getName());
                return ResponseEntity.ok(user.get());
            } else {
                System.out.println("No user found with provided credentials.");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid name or password");
            }
        } catch (Exception e) {
            System.out.println("Error during sign-in: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to sign in: " + e.getMessage());
        }
    }

}