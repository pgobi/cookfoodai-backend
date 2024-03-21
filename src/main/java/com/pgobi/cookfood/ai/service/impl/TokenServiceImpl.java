package com.pgobi.cookfood.ai.service.impl;

import com.pgobi.cookfood.ai.entities.Token;
import com.pgobi.cookfood.ai.entities.User;
import com.pgobi.cookfood.ai.exceptions.TokenException;
import com.pgobi.cookfood.ai.model.TokenRequest;
import com.pgobi.cookfood.ai.model.TokenResponse;
import com.pgobi.cookfood.ai.repository.TokenRepository;
import com.pgobi.cookfood.ai.service.JwtTokenService;
import com.pgobi.cookfood.ai.service.TokenService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {

    private final TokenRepository tokenRepository;
    private final JwtTokenService jwtTokenService;

    @Override
    public Token verifyExpiration(Token token) {
        if(token == null){
            throw new TokenException(null, "Token is null");
        }
        if(token.isExpired() == true ){
            tokenRepository.delete(token);
            throw new TokenException(token.getAccessToken(), "Refresh token was expired. Please make a new authentication request");
        }
        return token;
    }

    @Override
    public TokenResponse refreshToken(TokenRequest request) {
        User user = tokenRepository.findByAccessToken(request.getRefreshToken())
                .map(this::verifyExpiration)
                .map(Token::getUser)
                .orElseThrow(() -> new TokenException(request.getRefreshToken(),"Refresh token does not exist"));
        String token = jwtTokenService.generateAccessToken(user);
        System.out.print("generateNewToken:"+ token +"/n");
        return TokenResponse.builder()
                .accessToken(token)
                .refreshToken(request.getRefreshToken())
                .build();
    }

}
