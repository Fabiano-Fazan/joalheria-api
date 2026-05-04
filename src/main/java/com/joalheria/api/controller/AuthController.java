package com.joalheria.api.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @GetMapping("/me")
    public Map<String, Object> me(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return Map.of("authenticated", false);
        }

        String role = authentication.getAuthorities()
                .stream()
                .findFirst()
                .map(GrantedAuthority::getAuthority)
                .orElse("ROLE_CLIENTE");

        Object principal = authentication.getPrincipal();

        if (principal instanceof OAuth2User user) {
            return Map.of(
                    "authenticated", true,
                    "name", user.getAttribute("name"),
                    "email", user.getAttribute("email"),
                    "picture", user.getAttribute("picture"),
                    "role", role
            );
        }

        return Map.of(
                "authenticated", true,
                "name", authentication.getName(),
                "role", role
        );
    }
}