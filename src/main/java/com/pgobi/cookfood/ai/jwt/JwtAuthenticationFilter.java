package com.pgobi.cookfood.ai.jwt;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import com.pgobi.cookfood.ai.constants.ApplicationConstants;
import com.pgobi.cookfood.ai.service.JwtService;
import com.pgobi.cookfood.ai.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserService userService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response, @NonNull FilterChain filterChain)
            throws ServletException, IOException {

        List<String> ignorePaths = new ArrayList<>();
        ignorePaths.add(" /api/authentication/login"); // ignore login
        ignorePaths.add("/h2-console/**"); //ignore h2-console url's

        final String authHeader = request.getHeader(ApplicationConstants.AUTHORIZATION_HEADER);
        final String accessToken;
        final String userEmail;

        System.out.print("[JwtAuthenticationFilter] getHeader: " + authHeader);

        if (StringUtils.isEmpty(authHeader) || !StringUtils.startsWith(authHeader, "Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        accessToken  = authHeader.substring(7);
        userEmail = jwtService.extractEmail(accessToken);
        System.out.print("[JwtAuthenticationFilter] userEmail:" +userEmail + " accessToken:"+ accessToken);

        if (StringUtils.isNotEmpty(userEmail)
                && SecurityContextHolder.getContext().getAuthentication() == null) {

            UserDetails userDetails = userService.userDetailsService()
                    .loadUserByUsername(userEmail);

            System.out.print("[JwtAuthenticationFilter] isTokenValid:"+ jwtService.isTokenValid(accessToken, userDetails));

            if (jwtService.isTokenValid(accessToken, userDetails)) {
                SecurityContext context = SecurityContextHolder.createEmptyContext();
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                System.out.print("[JwtAuthenticationFilter]  authToken:"+ authToken);
                context.setAuthentication(authToken);
                SecurityContextHolder.setContext(context);
            }

        }
        filterChain.doFilter(request, response);
    }
}
