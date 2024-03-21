package com.pgobi.cookfood.ai.service;

import com.pgobi.cookfood.ai.model.AuthenticationRequest;
import com.pgobi.cookfood.ai.model.RegisterRequest;
import com.pgobi.cookfood.ai.model.AuthenticationResponse;
import org.springframework.stereotype.Service;

@Service
public interface AuthenticationService {
    AuthenticationResponse register(RegisterRequest request);
    AuthenticationResponse login(AuthenticationRequest request);
    AuthenticationResponse logout(AuthenticationRequest request);
}
