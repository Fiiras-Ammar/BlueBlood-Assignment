package com.example.taskmanagement.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtTokenProvider {

    // Secret key for signing JWT tokens, injected from application properties
    @Value("${jwt.secret}")
    private String jwtSecret;

    // Expiration time for the JWT token, injected from application properties
    @Value("${jwt.expiration}")
    private long jwtExpiration;

    // Convert the secret key to bytes for signing/verifying
    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes()); // Returns the signing key for JWT creation/verification
    }

    // Generate a JWT token for a given username
    public String generateToken(String username) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpiration * 1000); // Calculate expiration date

        // Build and sign the JWT token
        return Jwts.builder()
                .subject(username) // Use username as the subject (claim)
                .issuedAt(now)      // Set the issue date
                .expiration(expiryDate) // Set the expiration date
                .signWith(getSigningKey()) // Sign with the secret key
                .compact(); // Generate the compact JWT string
    }

    // Validate the JWT token (checks if it is well-formed and not expired)
    public boolean validateToken(String token) {
        try {
            // Parse and verify the token with the signing key
            Jwts.parser()
                    .verifyWith(getSigningKey()) // Set the signing key for verification
                    .build()
                    .parseSignedClaims(token); // Parse the token and validate it
            return true; // If no exception is thrown, the token is valid
        } catch (MalformedJwtException | ExpiredJwtException | UnsupportedJwtException |
                 SignatureException | IllegalArgumentException e) {
            // Return false for invalid token types or expired token
            return false;
        }
    }

    // Extract the username (subject) from the JWT token
    public String getUsernameFromToken(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(getSigningKey()) // Set the signing key for verification
                .build()
                .parseSignedClaims(token) // Parse and verify the token
                .getPayload(); // Get the claims (payload) of the token
        return claims.getSubject(); // Return the username (subject)
    }

    // Extract the JWT token from the "Authorization" header of the HTTP request
    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7); // Remove "Bearer " prefix to get the token
        }
        return null; // Return null if no token is found
    }
}
