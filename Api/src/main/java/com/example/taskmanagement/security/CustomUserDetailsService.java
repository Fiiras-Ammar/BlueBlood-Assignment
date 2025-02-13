package com.example.taskmanagement.security;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    // Loads user details by username
    // This method is called to authenticate the user during the login process
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Check if the provided username matches "user"
        if ("user".equals(username)) {
            // Return a UserDetails object with a hashed password and the "USER" role
            return User.withUsername(username)
                    .password("$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HZWzG3YB1tlRy.fqvM/BG") // Hashed password for "password"
                    .roles("USER")
                    .build();
        }

        // Throw exception if the user is not found
        throw new UsernameNotFoundException("User not found with username: " + username);
    }
}
