package com.labprog.PortalEgressos.service.exceptions;

public class AuthorizationException extends RuntimeException {
    public AuthorizationException() {
        super("Usuário não está autorizado");
    }
}
