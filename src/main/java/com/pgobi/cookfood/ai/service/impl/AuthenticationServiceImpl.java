package com.pgobi.cookfood.ai.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pgobi.cookfood.ai.entities.Token;
import com.pgobi.cookfood.ai.enums.Role;
import com.pgobi.cookfood.ai.model.AuthenticationRequest;
import com.pgobi.cookfood.ai.model.RegisterRequest;
import com.pgobi.cookfood.ai.model.AuthenticationResponse;
import com.pgobi.cookfood.ai.repository.TokenRepository;
import com.pgobi.cookfood.ai.service.AuthenticationService;
import com.pgobi.cookfood.ai.service.JwtTokenService;
import com.pgobi.cookfood.ai.entities.User;
import com.pgobi.cookfood.ai.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenService jwtTokenService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        var user = User.builder()
                .firstName(request.getFirstname())
                .lastName(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
        var savedUser = userRepository.save(user);
        var accessToken = jwtTokenService.generateAccessToken(user);
        var refreshToken = jwtTokenService.generateRefreshToken(user);
        saveUserToken(savedUser,accessToken, refreshToken);

        return AuthenticationResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .email(request.getEmail())
                .role(user.getRole().toString())
                .build();
    }

    public AuthenticationResponse logout(AuthenticationRequest request) {
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
                .email(request.getEmail())
                .build();
    }

    public AuthenticationResponse login(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow();
        var accessToken = jwtTokenService.generateAccessToken(user);
        var refreshToken = jwtTokenService.generateRefreshToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, accessToken, refreshToken);
        return AuthenticationResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .email(request.getEmail())
                .role(user.getRole().toString())
                .build();
    }

    private void saveUserToken(User user, String accessToken , String refreshToken) {
        var token = Token.builder()
                .user(user)
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
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
        userEmail = jwtTokenService.extractUsername(refreshToken);
        if (userEmail != null) {
            var user = this.userRepository.findByEmail(userEmail)
                    .orElseThrow();
            if (jwtTokenService.isTokenValid(refreshToken, user)) {
                var accessToken = jwtTokenService.generateAccessToken(user);
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
}
