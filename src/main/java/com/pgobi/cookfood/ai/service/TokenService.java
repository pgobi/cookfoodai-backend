package com.pgobi.cookfood.ai.service;

import com.pgobi.cookfood.ai.entities.Token;
import com.pgobi.cookfood.ai.model.TokenRequest;
import com.pgobi.cookfood.ai.model.TokenResponse;
import org.springframework.stereotype.Service;


@Service
public interface TokenService {
    Token verifyExpiration(Token token);
    TokenResponse refreshToken(TokenRequest request);
}
