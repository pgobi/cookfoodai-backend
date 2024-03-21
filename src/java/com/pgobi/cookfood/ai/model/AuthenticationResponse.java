package com.pgobi.cookfood.ai.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.pgobi.cookfood.ai.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationResponse {

    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("refresh_token")
    private String refreshToken;

    private Long expiration;

    private String role;
}
