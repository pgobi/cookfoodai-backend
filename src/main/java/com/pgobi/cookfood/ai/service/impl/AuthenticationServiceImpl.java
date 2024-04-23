package com.pgobi.cookfood.ai.service.impl;

import com.pgobi.cookfood.ai.entities.Token;
import com.pgobi.cookfood.ai.enums.Role;
import com.pgobi.cookfood.ai.model.AuthenticationRequest;
import com.pgobi.cookfood.ai.model.SignUpRequest;
import com.pgobi.cookfood.ai.model.AuthenticationResponse;
import com.pgobi.cookfood.ai.repository.TokenRepository;
import com.pgobi.cookfood.ai.service.AuthenticationService;
import com.pgobi.cookfood.ai.entities.User;
import com.pgobi.cookfood.ai.repository.UserRepository;
import com.pgobi.cookfood.ai.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    @Value("#{new Long('${security.jwt.expiration}')}")
    private Long jwtExpiration;

    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse signUp(SignUpRequest request) {

        String userRole = Role.USER.name();

        var user = User.builder()
                .firstName(request.getFirstname())
                .lastName(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(userRole)
                .build();
        var savedUser = userRepository.save(user);

        var accessTokenClaims = new HashMap<String, Object>();
        accessTokenClaims.put("tokenType", "access");

        var refreshTokenClaims = new HashMap<String, Object>();
        refreshTokenClaims.put("tokenType", "refresh");

        var accessToken = jwtService.generateToken(accessTokenClaims, user);

        var refreshToken = jwtService.generateToken(refreshTokenClaims, user);

        saveToken(savedUser,accessToken, refreshToken);

        return AuthenticationResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .expiration(jwtExpiration)
                .role(userRole)
                .build();
    }

    public AuthenticationResponse signOut(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow();
        List<Token> tokens = tokenRepository.findAllByUser(user.getId());
        tokenRepository.deleteAll(tokens);
        return AuthenticationResponse.builder()
                .build();
    }

    public AuthenticationResponse signIn(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow();
        var accessTokenClaims = new HashMap<String, Object>();
        accessTokenClaims.put("tokenType", "access");

        var refreshTokenClaims = new HashMap<String, Object>();
        refreshTokenClaims.put("tokenType", "refresh");

        System.out.print("[AuthenticationServiceImpl] getEmail: " +user.getEmail() );

        var accessToken = jwtService.generateToken(accessTokenClaims, user);
        var refreshToken = jwtService.generateToken(refreshTokenClaims, user);

        revokeAllUserTokens(user);
        saveToken(user, accessToken, refreshToken);
        return AuthenticationResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .expiration(jwtExpiration)
                .role(user.getRole())
                .build();
    }

    private void saveToken(User user, String accessToken, String refreshToken) {

        Token existingToken = tokenRepository.findByUserId(user.getId());

        if (existingToken != null) {
            existingToken.setAccessToken(accessToken);
            existingToken.setRefreshToken(refreshToken);
            existingToken.setExpired(false);
            existingToken.setRevoked(false);
            tokenRepository.save(existingToken);
        } else {
            Token newToken = Token.builder()
                    .user(user)
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .expired(false)
                    .revoked(false)
                    .build();
            tokenRepository.save(newToken);
        }
    }



    private void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }


}
