package com.pgobi.cookfood.ai.controller;

import com.pgobi.cookfood.ai.model.AuthenticationRequest;
import com.pgobi.cookfood.ai.model.TokenRequest;
import com.pgobi.cookfood.ai.model.RegisterRequest;
import com.pgobi.cookfood.ai.model.AuthenticationResponse;
import com.pgobi.cookfood.ai.model.TokenResponse;
import com.pgobi.cookfood.ai.service.AuthenticationService;
import com.pgobi.cookfood.ai.service.TokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;


@Tag(name = "Authentication", description = "The API contains a method for authentication")
@RestController
@RequestMapping("/api/authentication")
@SecurityRequirements()
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final TokenService tokenService;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authenticationService.register(request));
    }

    @PostMapping("/login")
    @Operation(
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Unauthorized",
                            responseCode = "401",
                            content = {@Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json")}
                    )
            }
    )
    public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(authenticationService.login(request));
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<TokenResponse> refreshToken(@RequestBody TokenRequest request) {
        return ResponseEntity.ok(tokenService.refreshToken(request));
    }

    /*
    @GetMapping("/me")
    public Authentication getAuthentication(@RequestBody AuthenticationRequest request){
        return authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(),request.getPassword()));
    }
    */

    @GetMapping("/me")
    public Authentication getAuthentication(@RequestParam String email, @RequestParam String password){
        return authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password));
    }

    @PostMapping("/logout")
    public ResponseEntity<AuthenticationResponse> logout(@RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(authenticationService.logout(request));
    }

}
