package com.pgobi.cookfood.ai.service.impl;

import com.pgobi.cookfood.ai.entities.Token;
import com.pgobi.cookfood.ai.entities.User;
import com.pgobi.cookfood.ai.exceptions.TokenException;
import com.pgobi.cookfood.ai.model.TokenRequest;
import com.pgobi.cookfood.ai.model.TokenResponse;
import com.pgobi.cookfood.ai.repository.TokenRepository;
import com.pgobi.cookfood.ai.service.JwtService;
import com.pgobi.cookfood.ai.service.TokenService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;

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

    /*
     public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userEmail;
        if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
            return;
        }
        refreshToken = authHeader.substring(7);
        userEmail = jwtService.extractEmail(refreshToken);
        if (userEmail != null) {
            var user = this.userRepository.findByEmail(userEmail)
                    .orElseThrow();
            if (jwtService.isTokenValid(refreshToken, user)) {
                var accessToken = jwtService.generateToken(user);
                revokeAllUserTokens(user);
                saveUserToken(user, accessToken,refreshToken);
                var authResponse = AuthenticationResponse.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }
     */

}
