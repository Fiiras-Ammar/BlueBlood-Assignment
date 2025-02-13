package com.example.taskmanagement.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    // Dependencies to resolve and validate JWT tokens and load user details
    private final JwtTokenProvider jwtTokenProvider;
    private final CustomUserDetailsService customUserDetailsService;

    // Constructor to inject dependencies
    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider, CustomUserDetailsService customUserDetailsService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.customUserDetailsService = customUserDetailsService;
    }

    // Main method for filtering HTTP requests
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        // Step 1: Extract the JWT token from the request header
        String token = jwtTokenProvider.resolveToken(request);

        // Step 2: Validate the token if it exists
        if (token != null && jwtTokenProvider.validateToken(token)) {
            // Step 3: Extract the username from the valid token
            String username = jwtTokenProvider.getUsernameFromToken(token);

            // Step 4: Load the user details based on the extracted username
            var userDetails = customUserDetailsService.loadUserByUsername(username);

            // Step 5: Create an authentication token and set it in the SecurityContext
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            // Set the authentication object in the security context
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        // Continue the filter chain to allow other filters to process the request
        filterChain.doFilter(request, response);
    }
}
