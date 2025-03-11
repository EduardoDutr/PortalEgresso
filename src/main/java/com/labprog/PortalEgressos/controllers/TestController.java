package com.labprog.PortalEgressos.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @GetMapping("test-authentication")
    public String testAuthentication() {
        return "OK!";
    }
}
