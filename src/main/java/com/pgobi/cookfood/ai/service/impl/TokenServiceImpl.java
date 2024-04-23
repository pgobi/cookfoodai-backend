package com.pgobi.cookfood.ai.service.impl;

import com.pgobi.cookfood.ai.constants.ApplicationConstants;
import com.pgobi.cookfood.ai.entities.Token;
import com.pgobi.cookfood.ai.entities.User;
import com.pgobi.cookfood.ai.exceptions.TokenException;
import com.pgobi.cookfood.ai.model.TokenRequest;
import com.pgobi.cookfood.ai.model.TokenResponse;
import com.pgobi.cookfood.ai.model.UserResponse;
import com.pgobi.cookfood.ai.repository.TokenRepository;
import com.pgobi.cookfood.ai.service.JwtService;
import com.pgobi.cookfood.ai.service.TokenService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {

    private final TokenRepository tokenRepository;
    private final JwtService jwtService;

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
        User user = tokenRepository.findByRefreshToken(request.getRefreshToken())
                .map(this::verifyExpiration)
                .map(Token::getUser)
                .orElseThrow(() -> new TokenException(request.getRefreshToken(),"Refresh token does not exist"));

        var accessTokenClaims = new HashMap<String, Object>();
        accessTokenClaims.put("tokenType", "access");

        var accessToken = jwtService.generateToken(accessTokenClaims, user);

        System.out.print("generateNewToken:"+ accessToken +"/n");
        return TokenResponse.builder()
                .accessToken(accessToken)
                .refreshToken(request.getRefreshToken())
                .build();
    }

    @Override
    public UserResponse getUserInfo(String authorizationHeader) {

            if (authorizationHeader == null && !authorizationHeader.startsWith(ApplicationConstants.AUTHORIZATION_BEARER + " ")) {
                throw new TokenException(authorizationHeader.substring(7), "Access token does not exist");
            }

            String token = authorizationHeader.substring(7);

            System.out.print("getUserInfo : " + token);

            User user = tokenRepository.findUserByAccessToken(token)
                    .orElseThrow(() -> new TokenException(token, "Access token does not exist"));

            System.out.print("getUserInfo:"+ user.getFirstName() +"/n" +user.getRole()+"/n");

            return UserResponse.builder()
                    .firstName(user.getFirstName())
                    .lastName(user.getLastName())
                    .role(user.getRole())
                    .build();
    }

}
