package com.labprog.PortalEgressos.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    @Autowired
    private JwtService service;

    public AuthenticationService(JwtService service) {
        this.service = service;
    }

    public String authenticate(Authentication authentication) {
        return service.generateToken(authentication);
    }
}
