package com.labprog.PortalEgressos.controllers;


import com.labprog.PortalEgressos.service.auth.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CoordenadorController {
    @Autowired
    private AuthenticationService service;

    @PostMapping("/authenticate")
    public String authenticate(Authentication authentication) {
        return service.authenticate(authentication);
    }
}
