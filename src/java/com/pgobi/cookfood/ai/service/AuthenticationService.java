package com.pgobi.cookfood.ai.service;

import com.pgobi.cookfood.ai.model.AuthenticationRequest;
import com.pgobi.cookfood.ai.model.SignUpRequest;
import com.pgobi.cookfood.ai.model.AuthenticationResponse;
import org.springframework.stereotype.Service;

@Service
public interface AuthenticationService {
    AuthenticationResponse signUp(SignUpRequest request);
    AuthenticationResponse signIn(AuthenticationRequest request);
    AuthenticationResponse signOut(AuthenticationRequest request);
}
