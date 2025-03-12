package com.labprog.PortalEgressos.service.exceptions;

public class EgressoNotFoundException extends RuntimeException {
    public EgressoNotFoundException(Long id) {
        super("Egresso com ID " + id + " não encontrado");
    }
}
