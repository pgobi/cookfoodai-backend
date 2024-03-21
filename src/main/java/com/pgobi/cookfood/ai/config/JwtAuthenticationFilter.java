package com.pgobi.cookfood.ai.config;

import com.pgobi.cookfood.ai.constants.ApplicationConstants;
import com.pgobi.cookfood.ai.service.JwtService;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        String userEmail = null;
        String jwtToken = jwtService.getJwt(request);
        System.out.print("doFilterInternal jwtToken:"+ jwtToken + " ");

        final String authHeader = request.getHeader(ApplicationConstants.AUTHORIZATION_HEADER);

        if((jwtToken == null && (authHeader ==  null || !authHeader.startsWith(ApplicationConstants.AUTHORIZATION_BEARER +" ")))){
            filterChain.doFilter(request, response);
            return;
        }

        if (jwtToken == null && authHeader.startsWith(ApplicationConstants.AUTHORIZATION_BEARER +" ")) {
            System.out.print("doFilterInternal authHeader:"+ authHeader + " ");
            jwtToken = authHeader.substring(7);
            userEmail = jwtService.extractUserName(jwtToken);
            System.out.print("doFilterInternal userEmail:"+ userEmail + " ");

        }
        if(StringUtils.isNotEmpty(userEmail)
                && SecurityContextHolder.getContext().getAuthentication() == null){
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
            if(jwtService.isTokenValid(jwtToken, userDetails)){
                SecurityContext context = SecurityContextHolder.createEmptyContext();
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                context.setAuthentication(authToken);
                SecurityContextHolder.setContext(context);
            }
        }
        filterChain.doFilter(request,response);
    }

}
