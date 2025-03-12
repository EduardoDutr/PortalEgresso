package com.labprog.PortalEgressos.service.exceptions;

public class EgressoNotFoundException extends RuntimeException {
    public EgressoNotFoundException(Long id) {
        super("Egresso de ID " + id + "não foi encontrado");
    }
}
