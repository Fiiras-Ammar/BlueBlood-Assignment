package com.example.taskmanagement.controller;

import com.example.taskmanagement.security.JwtTokenProvider;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Authentication controller that handles user login and JWT token generation.
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    /**
     * Constructor to inject dependencies for authentication and JWT token management.
     */
    public AuthController(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    /**
     * Endpoint for user authentication.
     * Validates user credentials and returns a JWT token if authentication is successful.
     *
     * @param loginRequest The user's login credentials (username and password).
     * @return A JWT token if authentication is successful, or a 401 response if authentication fails.
     */
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        try {
            // Authenticate user with provided credentials
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
            );

            // Generate JWT token for authenticated user
            String token = jwtTokenProvider.generateToken(authentication.getName());
            return ResponseEntity.ok(token);
        } catch (AuthenticationException e) {
            // Return unauthorized status if authentication fails
            return ResponseEntity.status(401).body("Invalid username or password");
        }
    }

    /**
     * Represents the login request payload containing username and password.
     */
    static class LoginRequest {
        private String username;
        private String password;

        // Getters and setters
        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }
}
