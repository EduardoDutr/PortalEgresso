package com.labprog.PortalEgressos.service.auth;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UserProvider {
    public boolean userIsAdmin() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            return auth.getAuthorities().stream().anyMatch(authority -> authority.getAuthority().equals("SCOPE_adm"));
        }
        return false;
    }
}
