package com.pgobi.cookfood.ai.model;


import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

@EqualsAndHashCode
@Builder
@Getter
@RequiredArgsConstructor
public class AuthUser implements UserDetails {

    static final String FIELD_EMAIL = "email";

    private final String email;

    public static AuthUser fromUserClaim(Map<String, String> userClaim) {
        String email = userClaim.get(FIELD_EMAIL);
        return AuthUser.builder()
                .email(email)
                .build();
    }

    public Map<String, String> getUserClaim() {
        Map<String, String> userClaim = new LinkedHashMap<>();
        userClaim.put(FIELD_EMAIL, email);
        return userClaim;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

