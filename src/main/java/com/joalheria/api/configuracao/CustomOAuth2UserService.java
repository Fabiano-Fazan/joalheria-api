package com.joalheria.api.configuracao;

import org.springframework.beans.factory.annotation.Value; // Importação necessária
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    @Value("${ADMIN_EMAIL}")
    private String adminEmail;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        String email = oAuth2User.getAttribute("email");


        String role = (email != null && email.equalsIgnoreCase(adminEmail))
                ? "ROLE_ADMIN"
                : "ROLE_CLIENTE";

        return new DefaultOAuth2User(
                Collections.singletonList(new SimpleGrantedAuthority(role)),
                oAuth2User.getAttributes(),
                "email"
        );
    }
}