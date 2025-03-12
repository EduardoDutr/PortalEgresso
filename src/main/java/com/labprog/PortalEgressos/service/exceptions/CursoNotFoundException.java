package com.labprog.PortalEgressos.service.exceptions;

public class CursoNotFoundException extends RuntimeException {
    public CursoNotFoundException(Long id) {
        super("Curso com ID " + id + " não encontrado");
    }
}
