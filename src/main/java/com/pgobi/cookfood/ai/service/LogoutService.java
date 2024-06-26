package com.pgobi.cookfood.ai.service;

import com.pgobi.cookfood.ai.constants.ApplicationConstants;
import com.pgobi.cookfood.ai.repository.TokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogoutService implements LogoutHandler {

    private final TokenRepository tokenRepository;

    @Override
    public void logout(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) {
        final String authHeader = request.getHeader(ApplicationConstants.AUTHORIZATION_HEADER);
        final String accessToken;
        if (authHeader == null ||!authHeader.startsWith(ApplicationConstants.AUTHORIZATION_BEARER +" ")) {
            return;
        }
        accessToken = authHeader.substring(7);
        var storedToken = tokenRepository.findByAccessToken(accessToken)
                .orElse(null);
        if (storedToken != null) {
            storedToken.setExpired(true);
            storedToken.setRevoked(true);
            tokenRepository.save(storedToken);
            SecurityContextHolder.clearContext();
        }
    }
}
